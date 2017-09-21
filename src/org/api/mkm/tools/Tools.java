package org.api.mkm.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

public class Tools {

	
	private static final byte[] BUFFER_SIZE = new byte[256];
	
    
   public static void unzip(File zipFilePath,File to) throws IOException {
    	GZIPInputStream zipIn = new GZIPInputStream(new FileInputStream(zipFilePath));
    	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(to));
        int read = 0;
        while ((read = zipIn.read(BUFFER_SIZE)) != -1) 
           bos.write(BUFFER_SIZE, 0, read);

        bos.close();
        zipIn.close();
        zipIn.close();
    }
	
    
    public static String join(Collection<?> s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        Iterator<?> iter = s.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (!iter.hasNext()) {
              break;                  
            }
            builder.append(delimiter);
        }
        return builder.toString();
    }
  
    
}
