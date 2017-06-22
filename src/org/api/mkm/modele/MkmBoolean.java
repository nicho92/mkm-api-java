package org.api.mkm.modele;

public class MkmBoolean{
	
	String value;
	
	
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Boolean)
			return Boolean.parseBoolean(value)==Boolean.valueOf(obj.toString());
		
		return super.equals(obj);
	}
	
	public MkmBoolean(String value) {
		this.value=value;
		if(value==null)
			value="";
	}
	
	public MkmBoolean(Boolean b) {
		
		this.value=String.valueOf(b);
		if(b==null)
			value="";
	
	}
	
	public MkmBoolean(Integer i) {
		
		if(i==0)
			value="false";
		if(i>0)
			value="true";
		
		if(i==null)
			value="";
		
	}
	
	public String toString()
	{
		if(value==null)
			return "";
		
		return value;
	}
	
	public boolean getValue()
	{
		if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("1"))
			return true;
		
		return false;
	}

	
	
}
