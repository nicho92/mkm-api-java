package org.api.mkm.modele;

import java.io.Serializable;

public class MkmBoolean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String value;
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Boolean)
			return Boolean.parseBoolean(value)==Boolean.valueOf(obj.toString());
		if(obj instanceof MkmBoolean)
			return value.equals(((MkmBoolean)obj).toString());
		
		return super.equals(obj);
	}
	
	public MkmBoolean(MkmBoolean m)
	{
		if(m==null)
			value="";
		else
			value=m.toString();
	}
	
	public MkmBoolean(String value) {
		this.value=value;
		if(value==null || value.equals(""))
			value="";
	}
	
	public MkmBoolean(Boolean b) {
		
		this.value=String.valueOf(b);
		if(b==null)
			value="";
	
	}
	
	public MkmBoolean(Integer i) {

		if(i==null)
			value="";
		else if(i<=0)
			value="false";
		else if(i>0)
			value="true";
		
		
	}
	
	public String toString()
	{
		if(value==null)
			return "";
		
		return value;
	}
	
	public boolean getBooleanValue()
	{
		return (value.equalsIgnoreCase("true")||value.equalsIgnoreCase("1"));
	}

	
	
}
