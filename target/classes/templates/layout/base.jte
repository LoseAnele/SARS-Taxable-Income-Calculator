@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SARS Tax Calculator</title>
    <!-- Tailwind CSS CDN for a beautiful, responsive design -->
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; background-color: #f8fafc; }
        .tax-gradient { background: linear-gradient(90deg, #1d4ed8, #3b82f6); }
        .shadow-custom { box-shadow: 0 10px 15px -3px rgba(59, 130, 246, 0.2), 0 4px 6px -2px rgba(59, 130, 246, 0.1); }
    </style>
</head>
<body class="min-h-screen flex flex-col items-center p-4">
<header class="w-full max-w-4xl text-center py-6">
    <h1 class="text-3xl font-extrabold text-gray-900">SARS Tax Calculator</h1>
    <p class="text-gray-500 mt-1">2025/2026 Year of Assessment</p>
</header>
<main class="w-full max-w-4xl">
    ${content}
</main>
</body>
</html>