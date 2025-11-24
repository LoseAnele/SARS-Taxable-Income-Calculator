package gg.jte.generated.ondemand.layout;
public final class JtebaseGenerated {
	public static final String JTE_NAME = "layout/base.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,8,8,23,23,23,26};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, gg.jte.Content content) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>SARS Tax Calculator</title>\n    ");
		jteOutput.writeContent("\n    <script src=\"https://cdn.tailwindcss.com\"></script>\n    <link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap\" rel=\"stylesheet\">\n    <style>\n        body { font-family: 'Inter', sans-serif; background-color: #f8fafc; }\n        .tax-gradient { background: linear-gradient(90deg, #1d4ed8, #3b82f6); }\n        .shadow-custom { box-shadow: 0 10px 15px -3px rgba(59, 130, 246, 0.2), 0 4px 6px -2px rgba(59, 130, 246, 0.1); }\n    </style>\n</head>\n<body class=\"min-h-screen flex flex-col items-center p-4\">\n<header class=\"w-full max-w-4xl text-center py-6\">\n    <h1 class=\"text-3xl font-extrabold text-gray-900\">SARS Tax Calculator</h1>\n    <p class=\"text-gray-500 mt-1\">2025/2026 Year of Assessment</p>\n</header>\n<main class=\"w-full max-w-4xl\">\n    ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n</main>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		gg.jte.Content content = (gg.jte.Content)params.get("content");
		render(jteOutput, jteHtmlInterceptor, content);
	}
}
