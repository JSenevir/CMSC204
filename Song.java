/**
 * Represents a song that can be added to a playlist
 */
public class Song {
	private String title;
	private String artist;
	
	/**
	 * Instantiates a Song object
	 * @param title the title of the song
	 * @param artist the artist's name
	 */
	public Song(String title, String artist) {
		this.title = title;
		this.artist = artist;
	}
	
	/**
	 * Gets the song title
	 * @return the title of the song
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the artist's name
	 * @return the name of the artist
	 */
	public String getArtist() {
		return artist;
	}
	
	@Override
	public boolean equals(Object o) {		
		if (o == null || o.getClass() != Song.class) {
			return false;
		}
		
		Song other = (Song) o;
		
		if (this == other) {
			return true;
		}
		if (title.equals(other.title) && artist.equals(other.artist)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * String representation of a song in the GUI app playlist
	 */
	@Override
	public String toString() {
		return (title + " by " + artist);
	}
}
