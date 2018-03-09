package org.api.mkm.modele;

import java.io.Serializable;

public class PriceGuide implements Serializable{

	
	private double sell;
	private double low;
	private double lowex;
	private double lowfoil;
	private double avg;
	private double trend;
	
	
	@Override
	public String toString() {
		return "AVG="+avg+", LOWEX="+lowex+", LOWFOIL="+lowfoil+", SELL="+sell+", LOW="+low+", TREND="+trend;
	}
	
	
	public double getSELL() {
		return sell;
	}
	public void setSELL(double sELL) {
		sell = sELL;
	}
	public double getLOW() {
		return low;
	}
	public void setLOW(double lOW) {
		low = lOW;
	}
	public double getLOWEX() {
		return lowex;
	}
	public void setLOWEX(double lOWEX) {
		lowex = lOWEX;
	}
	public double getLOWFOIL() {
		return lowfoil;
	}
	public void setLOWFOIL(double lOWFOIL) {
		lowfoil = lOWFOIL;
	}
	public double getAVG() {
		return avg;
	}
	public void setAVG(double aVG) {
		avg = aVG;
	}
	public double getTREND() {
		return trend;
	}
	public void setTREND(double tREND) {
		trend = tREND;
	}
	
	
}
