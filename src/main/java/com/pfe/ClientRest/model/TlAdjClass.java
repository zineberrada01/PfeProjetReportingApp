package com.pfe.ClientRest.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class TlAdjClass  {
    
	
	private int id ;
	private String anneee;
	private double SEM12;
	private double SEM24;
	private double SEM38;
	private double ANS1;
	private double ANS5;
	private double ANS8;
	private double ANS13;
	private double ANS16;
	
	
	/*
	 * public TlAdjClass() { super(); // TODO Auto-generated constructor stub }
	 * public TlAdjClass(int id, String anneee, double sEM12, double sEM24, double
	 * sEM38, double aNS1, double aNS5, double aNS8, double aNS13, double aNS16) {
	 * this.id = id; this.anneee = anneee; this.SEM12 = sEM12; this.SEM24 = sEM24;
	 * this.SEM38 = sEM38; this.ANS1 = aNS1; this.ANS5 = aNS5; this.ANS8 = aNS8;
	 * this.ANS13 = aNS13; this.ANS16 = aNS16; }
	 */

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public double getSEM12() {
		return SEM12;
	}
	public void setSEM12(double sEM12) {
		SEM12 = sEM12;
	}
	public String getAnneee() {
		return anneee;
	}
	public void setAnneee(String annee) {
		this.anneee = annee;
	}
	public double getSEM24() {
		return SEM24;
	}
	public void setSEM24(double sEM24) {
		SEM24 = sEM24;
	}
	public double getSEM38() {
		return SEM38;
	}
	public void setSEM38(double sEM38) {
		SEM38 = sEM38;
	}
	public double getANS1() {
		return ANS1;
	}
	public void setANS1(double aNS1) {
		ANS1 = aNS1;
	}
	public double getANS5() {
		return ANS5;
	}
	public void setANS5(double aNS5) {
		ANS5 = aNS5;
	}
	public double getANS8() {
		return ANS8;
	}
	public void setANS8(double aNS8) {
		ANS8 = aNS8;
	}
	public double getANS13() {
		return ANS13;
	}
	public void setANS13(double aNS13) {
		ANS13 = aNS13;
	}
	public double getANS16() {
		return ANS16;
	}
	public void setANS16(double aNS16) {
		ANS16 = aNS16;
	}
	
}

