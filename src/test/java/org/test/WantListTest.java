package org.test;

import java.io.File;
import java.io.IOException;

import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.WantsService;
import org.api.mkm.tools.MkmAPIConfig;

public class WantListTest {

	public void createWL() throws IOException
	{
		MkmAPIConfig.getInstance().init(new File("C:\\Users\\nicol\\mkm.properties"));
		WantsService service = new WantsService();
		
		
		Wantslist want = service.createWantList("TEST API 2");
		
		System.out.println(want.getIdWantsList());
		
	}
	
}
