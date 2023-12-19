/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package taxcalculator;

/**
 *
 * @author hasan
 */
public class USCcalculator implements TaxCalculator {
    
    @Override
    public double calculateTax(double grossIncome, double taxCredits) {
        double uscTax = 0.0;

        // Calculate USC tax based on income bands and rates
        if (grossIncome <= TaxConstants.BAND_1) {
            uscTax = grossIncome * TaxConstants.RATE_1;
        } else if (grossIncome <= TaxConstants.BAND_2) {
            uscTax = TaxConstants.BAND_1 * TaxConstants.RATE_1 + (grossIncome - TaxConstants.BAND_1) * TaxConstants.RATE_2;
        } else if (grossIncome <= TaxConstants.BAND_3) {
            uscTax = TaxConstants.BAND_1 * TaxConstants.RATE_1 + (TaxConstants.BAND_2 - TaxConstants.BAND_1) * TaxConstants.RATE_2 + (grossIncome - TaxConstants.BAND_2) * TaxConstants.RATE_3;
        } else if (grossIncome <= TaxConstants.BAND_4) {
            uscTax = TaxConstants.BAND_1 * TaxConstants.RATE_1 + (TaxConstants.BAND_2 - TaxConstants.BAND_1) * TaxConstants.RATE_2 + (TaxConstants.BAND_3 - TaxConstants.BAND_2) * TaxConstants.RATE_3 +
                    (grossIncome - TaxConstants.BAND_3) * TaxConstants.RATE_4;
        } else {
            uscTax = TaxConstants.BAND_1 * TaxConstants.RATE_1 + (TaxConstants.BAND_2 - TaxConstants.BAND_1) * TaxConstants.RATE_2 + (TaxConstants.BAND_3 - TaxConstants.BAND_2) * TaxConstants.RATE_3 +
                    (TaxConstants.BAND_4 - TaxConstants.BAND_3) * TaxConstants.RATE_4 + (grossIncome - TaxConstants.BAND_4) * TaxConstants.RATE_5;
        }

        return uscTax;
    }
    
}
