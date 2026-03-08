/**
 * Represents a user of a Spotify app
 */
public class User {
	private String username;
	private String password;
	private GenericLinkedList<Playlist> playlists;
	private int playlistCount;
	
	/**
	 * Instantiates a User given their username and password
	 * @param username the given username
	 * @param password the given password
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		playlists = new GenericLinkedList<>();
	}
	
	/**
	 * Adds a playlist to the user's account
	 * @param playlist the playlist to be added
	 */
	public void addPlaylist(Playlist playlist) {
		//add to the end of the list
		playlists.addLast(playlist); 
		playlistCount++;
	}
	
	/**
	 * Gets the number of playlists the user has
	 * @return the playlist count
	 */
	public int getPlaylistCount() {
		return playlistCount;
	}
	
	/**
	 * Gets the username of the user
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the password of the user
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets a reference to the list of playlists
	 * @return a reference to the user's playlist list
	 */
	public GenericLinkedList<Playlist> getPlaylists(){
		return playlists;
	}
}
