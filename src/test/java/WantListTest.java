import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.api.mkm.services.WantsService;
import org.api.mkm.tools.MkmAPIConfig;
import org.junit.Test;

public class WantListTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		
		Properties p = new Properties();
		p.load(new FileInputStream(new File("C:\\Users\\Nicolas\\.magicDeskCompanion\\pricers\\MagicCardMarket.conf")));
		MkmAPIConfig.getInstance().init(p);
		
		
		WantsService wants = new WantsService();
		
		System.out.println(wants.getWantList());
		
		
	}

}
