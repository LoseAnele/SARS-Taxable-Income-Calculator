@import com.sars.model.TaxInput
@param TaxInput input
@param String error

@template.layout.base(content = @`
    <div class="bg-white p-6 md:p-10 rounded-xl shadow-custom border border-blue-100">
        <h2 class="text-2xl font-bold text-gray-800 mb-6 border-b pb-2">Your Income Details</h2>

        @if(error != null)
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4" role="alert">
                <span class="block sm:inline">${error}</span>
            </div>
        @endif

        <form method="POST" action="/calculate">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                    <label for="grossAnnualIncome" class="block text-sm font-medium text-gray-700">Gross Annual Income (R)</label>
                    <input type="number" id="grossAnnualIncome" name="grossAnnualIncome" step="0.01" required
                           class="mt-1 block w-full rounded-lg border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-3 border"
                           placeholder="e.g. 550000" min="0" value="${input != null && input.grossAnnualIncome() != null? input.grossAnnualIncome().toPlainString() : ""}">
                    <p class="mt-1 text-xs text-gray-500">Total salary before any deductions.</p>
                </div>

                <div>
                    <label for="age" class="block text-sm font-medium text-gray-700">Your Age</label>
                    <input type="number" id="age" name="age" required
                           class="mt-1 block w-full rounded-lg border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-3 border"
                           placeholder="e.g. 45" min="18" max="120" value="${input != null && input.age() > 0 ? Integer.toString(input.age()) : ""}">
                    <p class="mt-1 text-xs text-gray-500">Used to determine Primary, Secondary, and Tertiary rebates.</p>
                </div>

                <div class="md:col-span-2">
                    <label for="retirementContribution" class="block text-sm font-medium text-gray-700">Total Retirement Contributions (R)</label>
                    <input type="number" id="retirementContribution" name="retirementContribution" step="0.01" required
                           class="mt-1 block w-full rounded-lg border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-3 border"
                           placeholder="e.g. 60000" min="0" value="${input != null && input.retirementContribution() != null ? input.retirementContribution().toPlainString() : "0"}">
                    <p class="mt-1 text-xs text-gray-500">Pension/Provident/RAF contributions. Max deduction is R350,000 or 27.5% of income.</p>
                </div>
            </div>

            <div class="mt-8">
                <button type="submit"
                        class="w-full tax-gradient text-white py-3 px-4 rounded-xl font-semibold shadow-md hover:shadow-lg transition duration-300 focus:outline-none focus:ring-4 focus:ring-blue-500 focus:ring-opacity-50">
                    Calculate My Tax
                </button>
            </div>
        </form>
    </div>
`)