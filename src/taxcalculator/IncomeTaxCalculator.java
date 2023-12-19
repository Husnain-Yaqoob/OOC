/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package taxcalculator;

/**
 *
 * @author hasan
 */
public class IncomeTaxCalculator implements TaxCalculator{
    	@Override
    public double calculateTax(double grossIncome, double taxCredit) {
        double taxableIncome = calculateTaxableIncome(grossIncome, taxCredit);
        double taxRate = getTaxRate(taxableIncome);
        return taxableIncome * taxRate;
    }

    private static double calculateTaxableIncome(double grossIncome, double taxCredit) {
        // Subtract tax credit from gross income to get taxable income
        return Math.max(0, grossIncome - taxCredit);
    }

    private static double getTaxRate(double taxableIncome) {
        // Define your tax rate logic based on taxable income
        // Example logic: 10% tax rate for the first $50,000, 20% for the rest
        if (taxableIncome <= 50000) {
            return 0.1;
        } else {
            return 0.2;
        }
    }
}
