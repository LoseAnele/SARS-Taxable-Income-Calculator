package com.sars.app;

import com.sars.model.TaxInput;
import com.sars.model.TaxResult;
import com.sars.service.TaxCalculatorService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.resolve.DirectoryCodeResolver;
import java.nio.file.Path;

import java.math.BigDecimal;
import java.util.HashMap; // <--- NEW IMPORT
import java.util.Map;
import java.util.Objects;

public class App {

    private static final TaxCalculatorService taxService = new TaxCalculatorService();

    public static void main(String[] args) {
        var codeResolver = new DirectoryCodeResolver(Path.of("src/main/resources/templates"));
        TemplateEngine engine = TemplateEngine.create(codeResolver, ContentType.Html);

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/templates");
            config.fileRenderer(new JavalinJte(engine));
        }).start(7070);

        app.get("/", App::showTaxForm);
        app.post("/calculate", App::processCalculation);

        System.out.println("Javalin server running on http://localhost:7070");
    }

    /**
     * Renders the main input form. FIX: Using HashMap to allow for 'null' error value.
     */
    private static void showTaxForm(Context ctx) {
        // Use BigDecimal.ZERO as a safe default for input fields to avoid nulls
        TaxInput defaultInput = new TaxInput(BigDecimal.ZERO, 0, BigDecimal.ZERO);

        // Use HashMap to allow the error value to be null
        Map<String, Object> model = new HashMap<>();
        model.put("input", defaultInput);
        model.put("error", null); // HashMap allows nulls, unlike Map.of()

        ctx.render("index.jte", model);
    }

    /**
     * Processes the form submission, performs the calculation, and renders the result.
     */
    private static void processCalculation(Context ctx) {
        TaxInput input = null;
        try {
            // 1. Extract and Validate Input
            BigDecimal grossAnnualIncome = new BigDecimal(Objects.requireNonNull(ctx.formParam("grossAnnualIncome")));
            int age = Integer.parseInt(Objects.requireNonNull(ctx.formParam("age")));
            BigDecimal retirementContribution = new BigDecimal(Objects.requireNonNull(ctx.formParam("retirementContribution")));

            if (grossAnnualIncome.compareTo(BigDecimal.ZERO) < 0 || age < 0 || retirementContribution.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Input values cannot be negative.");
            }
            if (age < 18 || age > 120) {
                throw new IllegalArgumentException("Age must be between 18 and 120.");
            }

            // 2. Create Model Input
            input = new TaxInput(grossAnnualIncome, age, retirementContribution);

            // 3. Call the Service
            TaxResult result = taxService.calculateTax(input);

            // 4. Render the Results
            ctx.render("results.jte", Map.of("result", result)); // Map.of is safe here, as 'result' is non-null

        } catch (NumberFormatException e) {
            String errorMessage = "Invalid input format. Please ensure all fields are numeric.";
            System.err.println("Input format error: " + e.getMessage());

            // Use HashMap for rendering the error page
            Map<String, Object> model = new HashMap<>();
            model.put("input", input != null ? input : new TaxInput(BigDecimal.ZERO, 0, BigDecimal.ZERO));
            model.put("error", errorMessage);
            ctx.render("index.jte", model);

        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            System.err.println("Validation error: " + errorMessage);

            // Use HashMap for rendering the error page
            Map<String, Object> model = new HashMap<>();
            model.put("input", input != null ? input : new TaxInput(BigDecimal.ZERO, 0, BigDecimal.ZERO));
            model.put("error", errorMessage);
            ctx.render("index.jte", model);

        } catch (Exception e) {
            e.printStackTrace();
            ctx.result("An unexpected server error occurred. Check logs.");
            ctx.status(500);
        }
    }
}