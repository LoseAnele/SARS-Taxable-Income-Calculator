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
import gg.jte.resolve.ResourceCodeResolver;
import java.nio.file.Path;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class App {

    private static final TaxCalculatorService taxService = new TaxCalculatorService();

    public static void main(String[] args) {
        TemplateEngine engine = createTemplateEngine();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/templates");
            config.fileRenderer(new JavalinJte(engine));
        }).start(7070);

        app.get("/", App::showTaxForm);
        app.post("/calculate", App::processCalculation);

        System.out.println("Javalin server running on http://localhost:7070");
    }

    private static TemplateEngine createTemplateEngine() {
        if (System.getenv("RENDER") != null) {
            return TemplateEngine.create(new ResourceCodeResolver("templates"), ContentType.Html);
        } else {
            return TemplateEngine.create(new DirectoryCodeResolver(Path.of("src", "main", "resources", "templates")), ContentType.Html);
        }
    }

    /**
     * Renders the main input form. FIX: Using HashMap to allow for 'null' error value.
     */
    private static void showTaxForm(Context ctx) {
        TaxInput defaultInput = new TaxInput(BigDecimal.ZERO, 0, BigDecimal.ZERO);

        Map<String, Object> model = new HashMap<>();
        model.put("input", defaultInput);
        model.put("error", null);

        ctx.render("index.jte", model);
    }

    /**
     * Processes the form submission, performs the calculation, and renders the result.
     */
    private static void processCalculation(Context ctx) {
        TaxInput input = null;
        try {
            BigDecimal grossAnnualIncome = new BigDecimal(Objects.requireNonNull(ctx.formParam("grossAnnualIncome")));
            int age = Integer.parseInt(Objects.requireNonNull(ctx.formParam("age")));
            BigDecimal retirementContribution = new BigDecimal(Objects.requireNonNull(ctx.formParam("retirementContribution")));

            if (grossAnnualIncome.compareTo(BigDecimal.ZERO) < 0 || age < 0 || retirementContribution.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Input values cannot be negative.");
            }
            if (age < 18 || age > 120) {
                throw new IllegalArgumentException("Age must be between 18 and 120.");
            }

            input = new TaxInput(grossAnnualIncome, age, retirementContribution);

            TaxResult result = taxService.calculateTax(input);

            ctx.render("results.jte", Map.of("result", result));

        } catch (NumberFormatException e) {
            String errorMessage = "Invalid input format. Please ensure all fields are numeric.";
            System.err.println("Input format error: " + e.getMessage());

            Map<String, Object> model = new HashMap<>();
            model.put("input", input != null ? input : new TaxInput(BigDecimal.ZERO, 0, BigDecimal.ZERO));
            model.put("error", errorMessage);
            ctx.render("index.jte", model);

        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            System.err.println("Validation error: " + errorMessage);

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