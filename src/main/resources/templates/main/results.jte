@import com.sars.model.TaxResult
@param TaxResult result

@template.layout.base(content = @`
    <div class="bg-white p-6 md:p-10 rounded-xl shadow-custom border border-blue-100">
        <h2 class="text-3xl font-extrabold text-blue-700 mb-8 border-b pb-3">Tax Calculation Results</h2>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div class="p-4 rounded-xl border border-gray-200 bg-blue-50 text-center">
                <p class="text-sm font-medium text-gray-500">Taxable Income</p>
                <p class="text-2xl font-bold text-gray-800 mt-1">R ${String.format("%,.2f", result.taxableIncome())}</p>
            </div>
            <div class="p-4 rounded-xl border border-gray-200 bg-green-50 text-center">
                <p class="text-sm font-medium text-gray-500">Estimated Tax Payable</p>
                <p class="text-2xl font-bold text-green-700 mt-1">R ${String.format("%,.2f", result.taxPayable())}</p>
            </div>
            <div class="p-4 rounded-xl border border-blue-500 bg-blue-100 text-center">
                <p class="text-sm font-medium text-blue-800">Your Tax Bracket</p>
                <p class="text-2xl font-bold text-blue-900 mt-1">${result.taxClassification()}</p>
            </div>
        </div>

        <h3 class="text-xl font-semibold text-gray-800 mb-4 border-b pb-2">Step-by-Step Breakdown</h3>

        <div class="space-y-4">
            @for (String step : result.calculationSteps())
                <div class="flex items-start bg-gray-50 p-4 rounded-lg border border-gray-200">
                    <svg class="w-5 h-5 text-blue-500 mt-1 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                    <p class="ml-3 text-gray-700 text-sm md:text-base whitespace-pre-line">${step}</p>
                </div>
            @endfor
        </div>

        <div class="mt-8 text-center">
            <a href="/" class="inline-block tax-gradient text-white py-2 px-6 rounded-xl font-semibold shadow-md hover:shadow-lg transition duration-300">
                Start New Calculation
            </a>
        </div>
    </div>
`)