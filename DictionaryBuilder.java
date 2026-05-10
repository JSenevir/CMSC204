import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Builds and manages a dictionary using a hash table with separate chaining. Each unique word is stored with a frequency count. Words are normalized by trimming punctuation and converting to lowercase. This class supports word addition, removal, lookup, and listing in alphabetical order.
 */
public class DictionaryBuilder {
	/**
	 * Desired ratio of the number of elements in the hash table to the hash table size
	 */
	public static final double LOAD_FACTOR = 0.6;
	private GenericLinkedList<Entry>[] hashTable;
	private int totalWords;
	private int uniqueWords;	
	
	/**
	 * Constructor that creates a hash table appropriate for the estimated number of entries, considering load factor and 4k+3 prime table size.
	 * @param estimatedEntries the estimated number of entries
	 */
	public DictionaryBuilder(int estimatedEntries) {
		int size;
		double calc = estimatedEntries/LOAD_FACTOR;
		//if size calculation is an integer, casts to integer as is
		if (calc == (int)calc) {
			size = (int)calc;
		}
		//otherwise, it is a double. Round decimal up before truncating to it integer
		else {
			size = (int)(calc+1);
		}
		
		//initialize hash table with appropriate size
		int hashSize = computeHashTableSize(size);
		@SuppressWarnings("unchecked")
		GenericLinkedList<Entry>[] tempArray = (GenericLinkedList<Entry>[]) new GenericLinkedList[hashSize];
		hashTable = tempArray;
	}
	
	/**
	 * Constructor that reads a file and adds all the words to the DictionaryBuilder
	 * @param filename The input file
	 */
	public DictionaryBuilder(String filename) throws FileNotFoundException {
		File file = new File(filename);
		long numberOfBytes = file.length();
		
		/*
		 * Given that: table size = number of elements/load factor
		 * Only unique words are add to table, so: number of elements = number of unique words
		 * And: estimated number of unique words = file size in bytes/100
		 * 
		 * table size = (file size in bytes/100)/load factor
		 */
		
		int size; 
		double calc = (numberOfBytes/100)/LOAD_FACTOR;
		
		//size calculation is already an integer
		if (calc == (int)calc) {
			size = (int)calc;
		}
		//round decimal up before casting to integer
		else {
			size = (int)(calc+1);
		}
				
		//initialize hash table with appropriate size
		int hashSize = computeHashTableSize(size);
		@SuppressWarnings("unchecked")
		GenericLinkedList<Entry>[] tempArray = (GenericLinkedList<Entry>[]) new GenericLinkedList[hashSize];
		hashTable = tempArray;
		
		Scanner fileReader = new Scanner(file);
		
		while(fileReader.hasNext()) {
			String input = fileReader.next().toLowerCase();
			addWord(input);		
		}
		
		fileReader.close();
	}
	
	/**
	 * Computes the hash table size to be the least 4k+3 prime that is greater than or equal to the given integer (minimum size)
	 * @param size the minimum size calculated using the load factor
	 * @return the computed hash table size
	 */
	private int computeHashTableSize(int size) {
		int k = 0;
		int hashTableSize;
		do {
			do {
			hashTableSize = 4*k+3;
			k++;
			} while (!isPrime(hashTableSize));
		} while(hashTableSize < size);
		return hashTableSize;
	}

	/**
	 * Determines whether an integer is prime using the 6k+1 optimization. 
	 * @param n the integer to be checked
	 * @return true if the integer is prime, false otherwise
	 */
	private static boolean isPrime(int n) {
		//Corner case
		if (n <= 1) {
			return false;
		}
		//For n=2 or n=3, isPrime is true
		if (n==2 || n==3) {
			return true;
		}
		//For a multiple of 2 or 3, isPrime is false
		if (n%2 == 0 || n%3 == 0) {
			return false;
		}
		//Check for remaining possible factors
		for (int i = 5; i<= Math.sqrt(n); i = i + 6) {
			if (n % i == 0 || n% (i+2)==0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Adds a word to the dictionary
	 * @param word the word to be added
	 */
	public void addWord(String word) {
		//lengthen table size and rehash before adding a new word if the current load factor exceeds desired load factor
		if (getEstimatedLoadFactor() >= LOAD_FACTOR) {
			rehash();
		}
		
		word = word.toLowerCase();
		
		//strip punctuation
		while (word.contains(".")){
			word = word.replace(".","");
		}
		while (word.contains("?")) {
			word = word.replace("?", "");
		}
		while (word.contains("!")) {
			word = word.replace("!", "");
		}
		while (word.contains(",")) {
			word = word.replace(",", "");
		}
				
		int index = Math.abs(word.hashCode()) % hashTable.length;
		
		Entry newWordEntry = new Entry(word);
		
		//first entry in the given index
		if (hashTable[index] == null) {
			hashTable[index] = new GenericLinkedList<Entry>();
			hashTable[index].addFirst(newWordEntry);
				
			totalWords++;
			uniqueWords++;
			
			//exit method (no need to check for duplicates)
			return;
		}
		
		Iterator<Entry> it = hashTable[index].iterator();
		boolean containsDuplicates = false;
				
		//check for duplicates
		while (it.hasNext()) {
			Entry nextEntry = it.next();
			if (nextEntry.equals(newWordEntry)) {
				containsDuplicates = true;
				nextEntry.incrementFrequency();
				break;
			}
		}
		
		//only add to hashTable if it does not already contain the word
		if (!containsDuplicates) {
			hashTable[index].addLast(newWordEntry);
			uniqueWords++;
		}
		//increment total word count regardless
		totalWords++;
	}
	
	/**
	 * Returns the frequency of a given word in the dictionary
	 * @param word the word to be counted
	 * @return the frequency
	 */
	public int getFrequency(String word){
		//returns the index of the chain where the word is stored
		int index = Math.abs(word.hashCode()) % hashTable.length;
		
		if (hashTable[index] != null) {
			//create a temporary copy of word to check against all entries in the chain
			Entry entry = new Entry(word);
			
			Iterator<Entry> it = hashTable[index].iterator();
	
			while(it.hasNext()) {
				Entry nextEntry = it.next();
				if (nextEntry.equals(entry)) {
					return nextEntry.getFrequency();
				}
			}
		}
		
		//if the index of the table is null or
		//if iterator loop finishes traversing without returning, the dictionary does not contain an entry of that word (frequency = 0)
		return 0;
	}
	
	/**
	 * Removes a word completely from the dictionary
	 * @param word the word to be removed
	 * @throws DictionaryEntryNotFoundException if the word cannot be found in the dictionary
	 * @throws EmptyDictionaryException if the dictionary is empty
	 */
	public void removeWord(String word) throws DictionaryEntryNotFoundException {		
		boolean removed = false;
		
		//returns the index of the chain where the word is stored
		int index = Math.abs(word.hashCode()) % hashTable.length;
		
		if (hashTable[index] != null) {
			//create a temporary copy of word to check against all entries in the chain
			Entry entry = new Entry(word);
					
			Iterator<Entry> it = hashTable[index].iterator();
	
			while(it.hasNext()) {
				Entry nextEntry = it.next();
				if (nextEntry.equals(entry)) {
					totalWords -= nextEntry.getFrequency();
					uniqueWords--;
					it.remove();
					removed = true;
					break;
				}
			}
		}
		
		if (removed) {
			//if removal makes list empty, remove reference to linked list
			if (hashTable[index].isEmpty()) {
				hashTable[index] = null;
			}
		} else {
			throw new DictionaryEntryNotFoundException("\"" + word + "\" not found.");
		}
	}
	
	/**
	 * Hash table is empty if it contains only null references
	 * @return true if the table is empty, false otherwise
	 */
	public boolean isEmpty() {
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns a list of all sorted words in alphabetical order
	 * @return a sorted List of all dictionary's words
	 */
	public List<String> getAllWords(){
		List<String> list = new ArrayList<>();

		for (GenericLinkedList<Entry> bucket: hashTable) {
			if (bucket == null) {
				continue;
			}
			
			Iterator<Entry> it = bucket.iterator();
			
			//traverse chain and add entry's word to the list
			while (it.hasNext()) {
				list.add(it.next().getWord());
			}
		}
		
		sortList(list);
		return list;
	}
	
	/**
	 * Selection sort for a list of Strings
	 * @param list the list of Strings to be sorted
	 */
	private void sortList(List<String> list) {
		for (int i = 0; i < list.size()-1; i++) {
			int minimum = i;
			for (int j = i+1; j < list.size(); j++) {
				if (list.get(j).compareTo(list.get(minimum)) < 0) {
					minimum = j;
				}
			}
			String temp = list.get(i);
			list.set(i, list.get(minimum));
			list.set(minimum, temp);
		}
	}
	
	/**
	 * Gets the total number of words entered, duplicates included
	 * @return the total word count
	 */
	public int getTotalWordCount() {
		return totalWords;
	}
	
	/**
	 * Gets the total of word entries in the dictionary
	 * @return the number of unique words
	 */
	public int getUniqueWordCount() {
		return uniqueWords;
	}
	
	/**
	 * Returns the current load factor
	 * @return the estimated load factor
	 */
	public double getEstimatedLoadFactor() {
		return (double)uniqueWords/hashTable.length;
	}
	
	/**
	 * For testing purposes to check size of table
	 * @return size of table
	 */
	public int getTableSize() {
		return hashTable.length;
	}
	
	/**
	 * Called by addWord method when load factor surpasses desired ratio
	 */
	public void rehash() {
		int hashSize = computeHashTableSize(2*getTableSize());
		GenericLinkedList<Entry>[] oldHashTable = hashTable;
		
		@SuppressWarnings("unchecked")
		GenericLinkedList<Entry>[] newHashTable = (GenericLinkedList<Entry>[]) new GenericLinkedList[hashSize];
		
		//assign hashTable field to reference the new hash table
		hashTable = newHashTable;
		
		for (GenericLinkedList<Entry> oldBucket : oldHashTable) {
			if (oldBucket != null) {
				Iterator<Entry> it = oldBucket.iterator();
				
				while (it.hasNext()) {
					Entry entry = it.next();
					
					//generate new index for each entry in the old bucket
					int index = Math.abs(entry.getWord().hashCode()) % hashTable.length;
					
					//insert old entries into their new bucket 
					if (hashTable[index] == null) {
						//create a bucket if the entry is to be the first element in the chain
						hashTable[index] = new GenericLinkedList<Entry>();
						hashTable[index].addFirst(entry);
					} else {
						//or add element to the end of the chain if a bucket already exists
						hashTable[index].addLast(entry);
					}
				}
			}
		}
	}
	
}
