import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Provides an interactive command-line shell for managing a DictionaryBuilder instance. Users can add, remove, search, list, and view statistics about words stored in a hash table-backed dictionary. The shell supports input from a file or interactive use from the terminal. The class acts as a driver to test and demonstrate the functionality of the DictionaryBuilder system through a text-based interface.
 */
public class DictionaryShell {
	public static void main(String [] args) {
		DictionaryBuilder dictionary = null;
		//if no arguments are provided, initialize an empty dictionary with an estimated size of 100 entries
		if (args.length == 0) {
			dictionary = new DictionaryBuilder(100);
		}
		//otherwise, the argument is the name of the file to be loaded
		else {
			try {
				dictionary = new DictionaryBuilder(args[0]);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		
		System.out.println("Welcome to the Dictionary Builder CLI");
		System.out.println("Available commands: add <word>, delete <word>, search <word>, list, stats, exit");
		
		Scanner scnr = new Scanner(System.in);
		
		boolean exit;
		
		do {
			exit = false;
			System.out.print("> ");
			String input = scnr.nextLine().toLowerCase();

			if (input.contains("add")) {
				String word = input.split(" ")[1];
				dictionary.addWord(word);
				System.out.println("\"" + word + "\" added.");
				continue;
			} 
			
			if (!dictionary.isEmpty()) {
				if (input.contains("delete")) {
					String word = input.split(" ")[1];
					try {
						dictionary.removeWord(word);
						System.out.println("\"" + word + "\" deleted.");
					} catch(DictionaryEntryNotFoundException e) {
						System.out.println(e.getMessage());
					}
				} else if (input.contains("search")) {
					String word = input.split(" ")[1];
					int frequency = dictionary.getFrequency(word);
					if (frequency > 0) {
						System.out.println(frequency + " instance(s) of \"" + word + "\" found.");
					} else {
						System.out.println("\"" + word + "\" not found.");
					}
				} else if (input.contains("list")) {
					for (String s: dictionary.getAllWords()) {
						System.out.println(s);
					}
				} else if(input.contains("stats")) {
					System.out.println("Total words: " + dictionary.getTotalWordCount());
					System.out.println("Total unique words: " + dictionary.getUniqueWordCount());
					System.out.printf("Estimated load factor: %.2f%n", dictionary.getEstimatedLoadFactor());
				} else if (input.contains("exit")) {
					System.out.println("Quitting...");
					exit = true;
				} else {
					System.out.println("Invalid command");
				}
			} else {
				System.out.println("Dictionary is empty");
			}
		} while (!exit);
		
		scnr.close();
	}
}
