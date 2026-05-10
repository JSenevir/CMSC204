public class DictionaryEntryNotFoundException extends Exception {
	public DictionaryEntryNotFoundException(String message) {
		super(message);
	}
	
	public DictionaryEntryNotFoundException() {
		super("The dictionary does not contain the specified word");
	}
}
