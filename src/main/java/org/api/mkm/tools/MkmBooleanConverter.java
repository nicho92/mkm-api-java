package org.api.mkm.tools;

import org.api.mkm.modele.MkmBoolean;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class MkmBooleanConverter extends AbstractSingleValueConverter {

	  public boolean canConvert(Class type) {    
	    return type.equals(MkmBoolean.class);    
	  }

	  public Object fromString(String str) {
	    return new MkmBoolean(str);
	  }
	}