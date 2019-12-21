package org.api.mkm.modele;

import java.io.Serializable;

public class PriceGuide implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double SELL;
	private double LOW;
	private double LOWEX;
	private double LOWFOIL;
	private double AVG;
	private double TREND;
	
	
	@Override
	public String toString() {
		return "AVG="+AVG+", LOWEX="+LOWEX+", LOWFOIL="+LOWFOIL+", SELL="+SELL+", LOW="+LOW+", TREND="+TREND;
	}
	
	
	public double getSELL() {
		return SELL;
	}
	public void setSELL(double sELL) {
		this.SELL = sELL;
	}
	public double getLOW() {
		return LOW;
	}
	public void setLOW(double lOW) {
		this.LOW = lOW;
	}
	public double getLOWEX() {
		return LOWEX;
	}
	public void setLOWEX(double lOWEX) {
		this.LOWEX = lOWEX;
	}
	public double getLOWFOIL() {
		return LOWFOIL;
	}
	public void setLOWFOIL(double lOWFOIL) {
		this.LOWFOIL = lOWFOIL;
	}
	public double getAVG() {
		return AVG;
	}
	public void setAVG(double aVG) {
		this.AVG = aVG;
	}
	public double getTREND() {
		return TREND;
	}
	public void setTREND(double tREND) {
		this.TREND = tREND;
	}
	
	
}
