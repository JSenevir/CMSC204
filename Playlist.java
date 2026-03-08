import java.util.ListIterator;

/**
 * Represents a user's playlist
 */
public class Playlist {
	private String name;
	private GenericLinkedList<Song> songsList;
	private Song currentSong;
	private ListIterator<Song> songsIterator;

	/**
	 * Instantiates a Playlist object
	 * @param name the name of the playlist
	 */
	public Playlist(String name) {
		this.name = name;
		songsList = new GenericLinkedList<>();
		songsIterator = songsList.iterator();
	}
	
	/**
	 * Adds a song to the playlist
	 * @param song the song to be added
	 * @return true if successfully adds
	 */
	public boolean addSong(Song song) {
		boolean isAdded = songsList.addLast(song);
		
		//initialize currentSong when first song is added
		if (getSize() == 1) {
			currentSong = song;
			songsIterator = songsList.iterator();
		} 
		return isAdded;
	}
	
	/**
	 * Checks if the playlist is empty
	 * @return true if the playlist has no songs, false otherwise
	 */
	public boolean isEmpty() {
		return songsList.isEmpty();
	}
	
	/**
	 * Moves iterator over the next song in the playlist
	 * @return the next song if it exists, null otherwise
	 */
	public Song nextSong() {
		if (songsIterator.hasNext()) {
			currentSong = songsIterator.next();
			return currentSong;
		}
		else return null;
	}
	
	/**
	 * Moves iterator over the previous song in the playlist
	 * @return the previous song if it exists, null otherwise
	 */
	public Song previousSong() {
		if (songsIterator.hasPrevious()) {
			currentSong = songsIterator.previous();
			return currentSong;
		}
		else return null;
	}
	
	/**
	 * Removes a given song from the playlist
	 * @param song the song to be removed
	 * @return true if removal is successful, false otherwise
	 */
	public boolean removeSong(Song song) {
		boolean isRemoved = songsList.remove(song);
		
		if (isEmpty()) {
			currentSong = null;
		}  else {
			//bring cursor back to top of playlist
			songsIterator = songsList.iterator();
			currentSong = songsList.getFirst();
		}
		
		return isRemoved;
	}
	
	/**
	 * Gets the current song playing
	 * @return the current song
	 */
	public Song getCurrentSong() {
		return currentSong;
	}
	
	/**
	 * Returns the number of songs in the playlist
	 * @return the size of the playlist
	 */
	public int getSize() {
		return songsList.size();
	}
	
	/**
	 * Gets the name of the playlist
	 * @return the name of the playlist
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a shallow copy of the linked list of songs
	 * @return shallow copy of the songs list
	 */
	public GenericLinkedList<Song> getSongs(){
		GenericLinkedList<Song> songs = new GenericLinkedList<>();
		ListIterator<Song> iterator = songsList.iterator();
		
		while (iterator.hasNext()) {
			songs.addLast(iterator.next());
		}
		return songs;
	}
	
	@Override
	public boolean equals(Object o) {		
		if (o == null || o.getClass() != Playlist.class) {
			return false;
		}
		
		Playlist other = (Playlist) o;
		
		if (this == other) {
			return true;
		}
		if (name.equals(other.name)) {
			return true;
		}
		
		return false;
	}
}
