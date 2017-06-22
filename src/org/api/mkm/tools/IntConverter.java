package org.api.mkm.tools;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class IntConverter extends AbstractSingleValueConverter {

	  public boolean canConvert(Class type) {    
	    return type.equals(int.class) || type.equals(Integer.class);    
	  }

	  public Object fromString(String str) {
	    /* If empty tag. */    
	    if (str == null || str.trim().length() == 0) {

	      /* Default to zero. */    
	      str = "0";
	    }

	    return Integer.decode(str);
	  }
	}