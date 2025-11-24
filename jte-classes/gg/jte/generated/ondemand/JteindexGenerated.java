package gg.jte.generated.ondemand;
import com.sars.model.TaxInput;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,4,4,4,4,8,8,10,10,10,12,12,20,20,20,20,20,20,20,20,20,28,28,28,28,28,28,28,28,28,36,36,36,36,36,36,36,36,36,49,49,49};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, TaxInput input, String error) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtebaseGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"bg-white p-6 md:p-10 rounded-xl shadow-custom border border-blue-100\">\n        <h2 class=\"text-2xl font-bold text-gray-800 mb-6 border-b pb-2\">Your Income Details</h2>\n\n        ");
				if (error != null) {
					jteOutput.writeContent("\n            <div class=\"bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4\" role=\"alert\">\n                <span class=\"block sm:inline\">");
					jteOutput.setContext("span", null);
					jteOutput.writeUserContent(error);
					jteOutput.writeContent("</span>\n            </div>\n        ");
				}
				jteOutput.writeContent("\n\n        <form method=\"POST\" action=\"/calculate\">\n            <div class=\"grid grid-cols-1 md:grid-cols-2 gap-6\">\n                <div>\n                    <label for=\"grossAnnualIncome\" class=\"block text-sm font-medium text-gray-700\">Gross Annual Income (R)</label>\n                    <input type=\"number\" id=\"grossAnnualIncome\" name=\"grossAnnualIncome\" step=\"0.01\" required\n                           class=\"mt-1 block w-full rounded-lg border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-3 border\"\n                           placeholder=\"e.g. 550000\" min=\"0\"");
				var __jte_html_attribute_0 = input != null && input.grossAnnualIncome() != null? input.grossAnnualIncome().toPlainString() : "";
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n                    <p class=\"mt-1 text-xs text-gray-500\">Total salary before any deductions.</p>\n                </div>\n\n                <div>\n                    <label for=\"age\" class=\"block text-sm font-medium text-gray-700\">Your Age</label>\n                    <input type=\"number\" id=\"age\" name=\"age\" required\n                           class=\"mt-1 block w-full rounded-lg border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-3 border\"\n                           placeholder=\"e.g. 45\" min=\"18\" max=\"120\"");
				var __jte_html_attribute_1 = input != null && input.age() > 0 ? Integer.toString(input.age()) : "";
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(__jte_html_attribute_1);
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n                    <p class=\"mt-1 text-xs text-gray-500\">Used to determine Primary, Secondary, and Tertiary rebates.</p>\n                </div>\n\n                <div class=\"md:col-span-2\">\n                    <label for=\"retirementContribution\" class=\"block text-sm font-medium text-gray-700\">Total Retirement Contributions (R)</label>\n                    <input type=\"number\" id=\"retirementContribution\" name=\"retirementContribution\" step=\"0.01\" required\n                           class=\"mt-1 block w-full rounded-lg border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-3 border\"\n                           placeholder=\"e.g. 60000\" min=\"0\"");
				var __jte_html_attribute_2 = input != null && input.retirementContribution() != null ? input.retirementContribution().toPlainString() : "0";
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_2)) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(__jte_html_attribute_2);
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n                    <p class=\"mt-1 text-xs text-gray-500\">Pension/Provident/RAF contributions. Max deduction is R350,000 or 27.5% of income.</p>\n                </div>\n            </div>\n\n            <div class=\"mt-8\">\n                <button type=\"submit\"\n                        class=\"w-full tax-gradient text-white py-3 px-4 rounded-xl font-semibold shadow-md hover:shadow-lg transition duration-300 focus:outline-none focus:ring-4 focus:ring-blue-500 focus:ring-opacity-50\">\n                    Calculate My Tax\n                </button>\n            </div>\n        </form>\n    </div>\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		TaxInput input = (TaxInput)params.get("input");
		String error = (String)params.get("error");
		render(jteOutput, jteHtmlInterceptor, input, error);
	}
}
