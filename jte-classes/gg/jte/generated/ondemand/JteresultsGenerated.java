package gg.jte.generated.ondemand;
import com.sars.model.TaxResult;
public final class JteresultsGenerated {
	public static final String JTE_NAME = "results.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,3,3,3,3,10,10,10,14,14,14,18,18,18,25,25,28,28,28,30,30,39,39,39};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, TaxResult result) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtebaseGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"bg-white p-6 md:p-10 rounded-xl shadow-custom border border-blue-100\">\n        <h2 class=\"text-3xl font-extrabold text-blue-700 mb-8 border-b pb-3\">Tax Calculation Results</h2>\n\n        <div class=\"grid grid-cols-1 md:grid-cols-3 gap-6 mb-8\">\n            <div class=\"p-4 rounded-xl border border-gray-200 bg-blue-50 text-center\">\n                <p class=\"text-sm font-medium text-gray-500\">Taxable Income</p>\n                <p class=\"text-2xl font-bold text-gray-800 mt-1\">R ");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(String.format("%,.2f", result.taxableIncome()));
				jteOutput.writeContent("</p>\n            </div>\n            <div class=\"p-4 rounded-xl border border-gray-200 bg-green-50 text-center\">\n                <p class=\"text-sm font-medium text-gray-500\">Estimated Tax Payable</p>\n                <p class=\"text-2xl font-bold text-green-700 mt-1\">R ");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(String.format("%,.2f", result.taxPayable()));
				jteOutput.writeContent("</p>\n            </div>\n            <div class=\"p-4 rounded-xl border border-blue-500 bg-blue-100 text-center\">\n                <p class=\"text-sm font-medium text-blue-800\">Your Tax Bracket</p>\n                <p class=\"text-2xl font-bold text-blue-900 mt-1\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(result.taxClassification());
				jteOutput.writeContent("</p>\n            </div>\n        </div>\n\n        <h3 class=\"text-xl font-semibold text-gray-800 mb-4 border-b pb-2\">Step-by-Step Breakdown</h3>\n\n        <div class=\"space-y-4\">\n            ");
				for (String step : result.calculationSteps()) {
					jteOutput.writeContent("\n                <div class=\"flex items-start bg-gray-50 p-4 rounded-lg border border-gray-200\">\n                    <svg class=\"w-5 h-5 text-blue-500 mt-1 flex-shrink-0\" fill=\"none\" stroke=\"currentColor\" viewBox=\"0 0 24 24\" xmlns=\"http://www.w3.org/2000/svg\"><path stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\"2\" d=\"M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z\"></path></svg>\n                    <p class=\"ml-3 text-gray-700 text-sm md:text-base whitespace-pre-line\">");
					jteOutput.setContext("p", null);
					jteOutput.writeUserContent(step);
					jteOutput.writeContent("</p>\n                </div>\n            ");
				}
				jteOutput.writeContent("\n        </div>\n\n        <div class=\"mt-8 text-center\">\n            <a href=\"/\" class=\"inline-block tax-gradient text-white py-2 px-6 rounded-xl font-semibold shadow-md hover:shadow-lg transition duration-300\">\n                Start New Calculation\n            </a>\n        </div>\n    </div>\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		TaxResult result = (TaxResult)params.get("result");
		render(jteOutput, jteHtmlInterceptor, result);
	}
}
