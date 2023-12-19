/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package taxcalculator;

/**
 *
 * @author moizb
 */
public class PRSICalculator implements TaxCalculator {
    @Override
    public double calculateTax(double grossIncome, double taxCredits) {
        // Calculate one-sixth of earnings over �352.01
        double oneSixthEarnings = Math.max(grossIncome - TaxConstants.THRESHOLD, 0) / 6.0;

        // Calculate PRSI credit
        double prsiCredit = Math.max(TaxConstants.MAX_CREDIT - oneSixthEarnings, 0);

        // Calculate basic PRSI charge at 4% of earnings
        double prsiCharge = grossIncome * TaxConstants.PRSI_RATE;

        // Subtract PRSI credit from the basic PRSI charge
        double prsiTax = Math.max(prsiCharge - prsiCredit, 0);

        return prsiTax;
    }
}
