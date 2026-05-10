/**
 * Represents a dictionary entry with a word and a frequency count.
 */
public class Entry implements Comparable<Entry>{
	private String word;
	private int frequency;
	
	/*
	 * Initializes the entry with the specified word and frequency=1
	 */
	public Entry(String word) {
		this.word = word;
		frequency = 1;
	}
	
	/**
	 * Returns the entry's word
	 * @return the word
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Returns the entry's frequency
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	
	/**
	 * Increment the entry's frequency
	 */
	public void incrementFrequency() {
		frequency++;
	}
	
	/**
	 * Checks if two entries are equal. They are equal if their words are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass()) {
			return false;
		}
		Entry other = (Entry) o;
		if (other.word.equals(word)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Compares two entries by calling the word's (String) compareTo method
	 */
	@Override
	public int compareTo(Entry other) {
		return other.word.compareTo(word);
	}
}