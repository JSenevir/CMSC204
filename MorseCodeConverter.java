import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Contains a static MorseCodeTree object that is used to convert morse code text from a file or String to English.  
 */
public class MorseCodeConverter {
	/**
	 * The MorseCodeTree object
	 */
	public static MorseCodeTree letterTree = new MorseCodeTree();
	
	/**
	 * Converts a file of Morse code into English. Each letter is delimited by a space. Each word is delimited by a slash.
	 * @param codeFile the file that contains Morse Code
	 * @return the English translation of the file
	 * @throws FileNotFoundException 
	 */
	public static String convertToEnglish(File codeFile) throws FileNotFoundException {
		String fileContents = "", sentence = "";
		Scanner fileReader = new Scanner(codeFile);
			
		while (fileReader.hasNextLine()) {
			fileContents += fileReader.nextLine() + " "; //copy line and add back space that was not read
		}
		fileReader.close();
		
		String words[] = fileContents.split("/");
		
		for (String word: words) {
			String letters[] = word.split(" ");
			
			for (String letter : letters) {
				if (letter.isEmpty()) {
					continue;
				}
				sentence += letterTree.fetch(letter);
			}
			sentence += " ";
		}
		
		return sentence.trim();
	}
	
	/**
	 * Converts a file of Morse code into English Each letter is delimited by a space (‘ ‘). Each word is delimited by a ‘/’.
	 * @param code the morse code
	 * @return the English translation
	 */
	public static String convertToEnglish(String code) {
		String words[] = code.split("/");
		String sentence = "";
		for (String word: words) {
			String letters[] = word.split(" ");
			for (String letter: letters) {
				if (letter.isEmpty()) {
					continue;
				}
				sentence += letterTree.fetch(letter);
			}
			sentence += " ";
		}
		return sentence.trim();
	}
	
	/**
	 * returns a string with all the data in the tree in LNR order with an space in between them. Uses the toArrayList method in MorseCodeTree It should return the data in this order:
"h s v i f u e l r a p w j b d x n c k y t z g q m o"
Note the extra space between j and b - that is because there is an empty string that is the root
	 * @return the data in the tree in LNR order separated by a space
	 */
	public static String printTree() {
		String LNRstring = "";
		for (String letter : letterTree.toArrayList()) {
			LNRstring += letter + " ";
		}
		return LNRstring.trim();
	}
	
}