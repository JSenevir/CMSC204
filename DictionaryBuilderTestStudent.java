import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DictionaryBuilderTestStudent {
	private DictionaryBuilder dbFile;
	private DictionaryBuilder db;

	@BeforeEach
	public void setUp() throws Exception {
		dbFile = new DictionaryBuilder("sample_input.txt");
		
		db = new DictionaryBuilder(30);
		String string = "This is a test for the dictionary builder class. Gibberish ahead. Class the test DICTionary builder!? Test a is the your the DICTIONARY, done";
		String[] tokens = string.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			db.addWord(tokens[i]);
		}
		
		/*
		 * db list:
		 * this = 1
		 * is = 2
		 * a = 2
		 * test = 3
		 * for = 1
		 * the = 4
		 * dictionary = 3
		 *  builder = 2
		 *  class = 2
		 *  gibberish = 1
		 *  ahead = 1
		 *  your = 1
		 *  done = 1
		 */
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		db = dbFile = null;
	}

	@Test
	public void testHashTableSize() {
		/*	size = number of elements/load factor = 30/0.6 = 50
		*	next 4k+3 prime is 59
		*/
		assertEquals(db.getTableSize(), 59);
		
		DictionaryBuilder db1 = new DictionaryBuilder(191);
		/* size = 191/0.6 = 318.3 = 319 when rounded up
		 * next 4k+3 prime is 331
		 */
		assertEquals(db1.getTableSize(), 331);
	}
	
	@Test
	public void testUniqueWordCount() {
		assertEquals(13, db.getUniqueWordCount());
		assertEquals(12, dbFile.getUniqueWordCount());
	}
	
	@Test
	public void testTotalWordCount() {
		assertEquals(24, db.getTotalWordCount());
		assertEquals(18, dbFile.getTotalWordCount());
	}
	
	@Test
	public void testLoadFactor() {
		assertEquals(db.getEstimatedLoadFactor(), (double)db.getUniqueWordCount()/db.getTableSize());
	}
	
	@Test
	public void testGetFrequency() {
		assertEquals(4, db.getFrequency("the"));
		assertEquals(1, db.getFrequency("gibberish"));
		assertEquals(2, db.getFrequency("class"));
		assertEquals(3, db.getFrequency("dictionary"));
		assertEquals(0, db.getFrequency("nonexistent"));
		
		assertEquals(2, dbFile.getFrequency("quick"));
		assertEquals(1, dbFile.getFrequency("lazy"));
		assertEquals(0, dbFile.getFrequency("nonexistent"));
	}
	
	@Test
	public void testRemoveWord() {
		int unique = db.getUniqueWordCount();
		int total = db.getTotalWordCount();
	
		try {
			db.removeWord("the");
		} catch (DictionaryEntryNotFoundException e) {}
		
		assertEquals(0, db.getFrequency("the"));
		assertTrue(db.getUniqueWordCount() == unique-1 && db.getTotalWordCount() == total-4);
		
		try {
			dbFile.removeWord("lazy");
		} catch (DictionaryEntryNotFoundException e) {}
		
		assertEquals(0, dbFile.getFrequency("lazy"));
	}
	
	@Test
	public void testAddWord() {
		int unique = db.getUniqueWordCount();
		int total = db.getTotalWordCount();
		
		db.addWord("the");
		assertTrue(db.getUniqueWordCount() == unique && db.getTotalWordCount() == total+1);
		assertEquals(5, db.getFrequency("the"));
		
		total = db.getTotalWordCount();
		
		db.addWord("unique");
		assertTrue(db.getUniqueWordCount() == unique+1 && db.getTotalWordCount() == total+1);
		assertTrue(db.getEstimatedLoadFactor() <= 0.6);		
		
		dbFile.addWord("new");
		assertEquals(1, dbFile.getFrequency("new"));
		assertTrue(dbFile.getEstimatedLoadFactor() <= 0.6);		
	}
	
	@Test
	public void testIsEmpty() {
		assertFalse(db.isEmpty());
		
		try {
			db.removeWord("the");
			db.removeWord("this");
			db.removeWord("a");
			db.removeWord("is");
			db.removeWord("dictionary");
			db.removeWord("test");
			db.removeWord("builder");
			db.removeWord("gibberish");
			db.removeWord("class");
			db.removeWord("done");
			db.removeWord("ahead");
			db.removeWord("your");
			db.removeWord("for");
		} catch (DictionaryEntryNotFoundException e) {}
		
		assertTrue(db.isEmpty());
	}
}
