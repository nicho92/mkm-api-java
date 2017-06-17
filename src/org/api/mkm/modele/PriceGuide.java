package org.api.mkm.modele;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class PriceGuide {

	
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
		SELL = sELL;
	}
	public double getLOW() {
		return LOW;
	}
	public void setLOW(double lOW) {
		LOW = lOW;
	}
	public double getLOWEX() {
		return LOWEX;
	}
	public void setLOWEX(double lOWEX) {
		LOWEX = lOWEX;
	}
	public double getLOWFOIL() {
		return LOWFOIL;
	}
	public void setLOWFOIL(double lOWFOIL) {
		LOWFOIL = lOWFOIL;
	}
	public double getAVG() {
		return AVG;
	}
	public void setAVG(double aVG) {
		AVG = aVG;
	}
	public double getTREND() {
		return TREND;
	}
	public void setTREND(double tREND) {
		TREND = tREND;
	}
	
	
}
