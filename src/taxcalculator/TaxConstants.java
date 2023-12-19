/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package taxcalculator;

/**
 *
 * @author moizb
 */
public class TaxConstants {
    public static final double MAX_CREDIT = 12.0;
    public static final double PRSI_RATE = 0.04;
    public static final double THRESHOLD = 352.01;
    
    // For USC Calculator    
    public static final double RATE_1 = 0.005;  // 0.5%
    public static final double RATE_2 = 0.02;   // 2%
    public static final double RATE_3 = 0.045;  // 4.5%
    public static final double RATE_4 = 0.08;   // 8%
    public static final double RATE_5 = 0.11;   // 11%

    public static final double BAND_1 = 12012.0;
    public static final double BAND_2 = 22920.0;
    public static final double BAND_3 = 70044.0;
    public static final double BAND_4 = 100000.0;
    
    // For IncomeTax Calculator
    public static final double RATE_1_IT = 0.2;   // 20%
    public static final double RATE_2_IT = 0.4;   // 40%

    public static final double SINGLE_PERSON_CUTOFF = 40000.0;
    public static final double LONE_PARENT_CUTOFF = 44000.0;
    public static final double MARRIED_ONE_INCOME_CUTOFF = 49000.0;
    public static final double MARRIED_TWO_INCOMES_CUTOFF = 80000.0;
    
    public static final String SINGLE_PERSON = "Single person";
    public static final String LONE_PARENT = "Lone parent";
    public static final String MARRIED_ONE_INCOME = "Married couple/civil partners, one income";
    public static final String MARRIED_TWO_INCOMES = "Married couple/civil partners, two incomes";
}
