import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;


public class MorseCodeConverterTestStudent{
	
	@Test
	public void testConvertMorseToEnglish() {	
		String testString = MorseCodeConverter.convertToEnglish("- .... .. ... / .. ... / .- / - . ... -");
		assertEquals("this is a test", testString);
	}
	
	@Test
	public void testConvertMorseFileToEnglishString() {	
		
		/*Make sure howDoILoveThee.txt is in the src directory for this 
		  test to pass
		*/
		File file = new File("src/test file.txt"); 
		try {
			assertEquals("you may say im a dreamer but im not the only one", MorseCodeConverter.convertToEnglish(file));
		} catch (FileNotFoundException e) {
			assertTrue("An unwanted exception was caught", false);
		}
	}
	

}
