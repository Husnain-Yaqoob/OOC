/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author hasan
 */
public class TaxUsers {
    private String name;
	private double grossIncome;
	private double taxCredits;
	private double taxOwed;
	private int taxType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(double grossIncome) {
		this.grossIncome = grossIncome;
	}
	public double getTaxCredits() {
		return taxCredits;
	}
	public void setTaxCredits(double taxCredits) {
		this.taxCredits = taxCredits;
	}
	public double getTaxOwed() {
		return taxOwed;
	}
	public void setTaxOwed(double taxOwed) {
		this.taxOwed = taxOwed;
	}
	public int getTaxType() {
		return taxType;
	}
	public void setTaxType(int taxType) {
		this.taxType = taxType;
	}
}
