import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * Represents a list of users and is responsible for loading data from a text file to the list and finding a user by its username and password. 
 */
public class SpotifyManager {
	private GenericLinkedList<User> userList;

	/**
	 * Instantiates a SpotifyManager object
	 */
	public SpotifyManager() {
		userList = new GenericLinkedList<>();
	}
	
	/**
	 * Loads data for each user into the userList (their username and password, playlists, songs in each playlist)
	 * @param filename the name of the file
	 * @throws IOException if an error occurs in opening or reading the file
	 * @throws InvalidUserFormatException if the file is formatted incorrectly
	 */
	public void loadUsersFromFile(String filename) throws IOException, InvalidUserFormatException {
		Scanner fileReader = new Scanner(new File(filename));
	
		int userIndex = 0; //track user

		if (fileReader.nextLine().contains("# USER")) {
			while (fileReader.hasNextLine()) {
				//username
				String[] tokens = fileReader.nextLine().split(":");
				String username = tokens[1].trim(); //username is the String after delimiter ":", with leading whitespace removed

				//password
				tokens = fileReader.nextLine().split(":");
				String password = tokens[1].trim();

				userList.addLast(new User(username, password));

				String next = fileReader.nextLine();
				
				int playlistIndex = 0; //track playlist
				
				while (!next.contains("# USER") && fileReader.hasNextLine()) {
					if (next.contains("playlist")) {
						tokens = next.split(":");
						String playlistName = tokens[1].trim();
		
						userList.get(userIndex).addPlaylist(new Playlist(playlistName));
						
						next = fileReader.nextLine();
						//there will be at least one song per playlist added
						do {
							tokens = next.split(":");
							String songAndArtist = tokens[1];
							tokens = songAndArtist.split("-");
							String song = tokens[0].trim();  //song title is String before delimiter "-" with leading and trailing whitespace removed
							String artist = tokens[1].trim(); //artist name is String after delimiter "-" with leading and trailing whitespace removed
							
							userList.get(userIndex).getPlaylists().get(playlistIndex).addSong(new Song(song, artist));
							
							if (fileReader.hasNextLine()) {
								next = fileReader.nextLine();
							} else {
								break;
							}
						} while (next.contains("song"));
						
						playlistIndex++;
					} else {
						//skip blank lines
						next = fileReader.nextLine();
					}
				}
				userIndex++;
			}
			
		} else {
			throw new InvalidUserFormatException();
		}
	}

	/**
	 * Retrieves a user from the userList given their username and password
	 * @param username the username of the user to find
	 * @param password the provided password
	 * @return the user
	 * @throws UserNotFoundException if a user with the provided username cannot be found in the list
	 * @throws InvalidPasswordException if the provided password does not match that of the designated user
	 */
	public User findUser(String username, String password) throws UserNotFoundException, InvalidPasswordException {
		ListIterator<User> userIterator = userList.iterator();

		while (userIterator.hasNext()) {
			User currentUser = userIterator.next();
			if (currentUser.getUsername().equals(username)) {
				if (currentUser.getPassword().equals(password)) {
					return currentUser;
				} else {
					throw new InvalidPasswordException();
				}
			}
		}
		throw new UserNotFoundException();
	}

	/**
	 * Returns a reference to the list of users
	 * @return the userList
	 */
	public GenericLinkedList<User> getUsers() {
		return userList;
	}

}
