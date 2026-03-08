import static org.junit.jupiter.api.Assertions.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenericLinkedListTestStudent {
	
	private GenericLinkedList<User> userList;
	
	private User user1 = new User ("u1", "p1");
	private User user2 = new User("u2", "p2");
	private User user3 = new User("u3", "u3");
	
	private GenericLinkedList<Playlist> playlistList;
	private GenericLinkedList<Song> songList;

	@BeforeEach
	public void setUp() throws Exception {
		userList = new GenericLinkedList<>();
		playlistList = new GenericLinkedList<>();
		songList = new GenericLinkedList<>();
		
		userList.addLast(user2);
		userList.addLast(user3);
		userList.addFirst(user1);
		
		playlistList.addFirst(new Playlist("list2"));
		playlistList.addFirst(new Playlist("list1"));
		playlistList.addLast(new Playlist("list3"));
		
		songList.addFirst(new Song("song2", "artist2"));
		songList.addLast(new Song("song3", "artist3"));
		songList.addFirst(new Song("song1", "artist1"));
	}

	@AfterEach
	public void tearDown() throws Exception {
		userList = null;
		playlistList = null;
		songList = null;
		user1 = user2 = user3 = null;
	}

	@Test
	public void testContainsAndRemoveByIndexandElement() {
		assertTrue(userList.contains(user2));
		
		assertTrue(playlistList.contains(new Playlist("list3")));
		playlistList.remove(new Playlist("list3"));
		assertFalse(playlistList.contains(new Playlist("list3")));

		assertTrue(songList.contains(new Song("song2", "artist2")));
		songList.remove(1);
		assertFalse(songList.contains(new Song("song2", "artist2")));
	}
	
	@Test
	public void testGetAndRemoveFirstandLast() {
		assertEquals(userList.getFirst(), user1);
		assertEquals(userList.getLast(), user3);
		
		assertEquals(songList.getFirst(), new Song("song1", "artist1"));
		assertEquals(songList.getLast(), new Song("song3", "artist3"));
		
		assertEquals(playlistList.getFirst(), new Playlist("list1"));
		assertEquals(playlistList.getLast(), new Playlist("list3"));
		
		assertEquals(userList.removeFirst(), user1);
		assertEquals(userList.removeLast(), user3);
		
		assertEquals(songList.removeFirst(), new Song("song1", "artist1"));
		assertEquals(songList.removeLast(), new Song("song3", "artist3"));
		
		assertEquals(playlistList.removeFirst(), new Playlist("list1"));
		assertEquals(playlistList.removeLast(), new Playlist("list3"));
	}
	
	@Test
	public void testIterator() {
		ListIterator<Song> songIt = songList.iterator();
				
		//test iterator's next method
		int count = 0;
		while (songIt.hasNext()) {
			assertEquals(songIt.next().toString(), "song" + (count+1) + " by " + "artist" + (count+1));
			count++;
		}
		assertEquals(count, songList.size());
		assertThrows(NoSuchElementException.class, () -> songIt.next());

		//test that remove() works after next call to next(), but not when called again without
		songIt.remove();
		assertFalse(songList.contains(new Song("song3", "artist3")));
		assertThrows(IllegalStateException.class, () -> songIt.remove());
		
		//test iterator's previous method
		count = songList.size();
		while(songIt.hasPrevious()) {
			assertEquals(songIt.previous().toString(), "song"+count + " by " + "artist"+count);
			count--;
		}
		assertEquals(count, 0);
		assertThrows(NoSuchElementException.class, () -> songIt.previous());
		//test that remove() works after call to previous(), not when called again without
		songIt.remove();
		assertThrows(IllegalStateException.class, () -> songIt.remove());
		assertThrows(IllegalStateException.class, () -> songIt.remove());

		
		//test getFirst, getLast, removeFirst, removeLast
		ListIterator<Playlist> playlistIt = playlistList.iterator();
		
		while(playlistIt.hasNext()) {
			playlistIt.next();
			playlistIt.remove();
		}
		assertTrue(playlistList.isEmpty());
		
		//assert empty list throws
		assertThrows(NoSuchElementException.class, () -> playlistList.getFirst());
		assertThrows(NoSuchElementException.class, () -> playlistList.getLast());
		assertThrows(NoSuchElementException.class, () -> playlistList.removeFirst());
		assertThrows(NoSuchElementException.class, () -> playlistList.removeLast());
		assertThrows(IndexOutOfBoundsException.class, () -> playlistList.remove(0));
		assertEquals(false, playlistList.remove(new Playlist("list1")));

		//test that iterator remove works
		ListIterator<User> userIt = userList.iterator();
		userIt.next();
		userIt.next();
		User returnedUser = userIt.next();
		assertEquals(user3, returnedUser);
		userIt.remove();
		assertTrue(userList.size() == 2);
		assertEquals(userList.getFirst(), user1);
		assertEquals(userList.getLast(), user2);
		assertFalse(userList.contains(user3));
		
		assertEquals(userIt.previous(), user2);
		userIt.remove();
		assertFalse(userList.contains(user2));
	}
	
	@Test
	public void testIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> playlistList.remove(3));
		assertThrows(IndexOutOfBoundsException.class, () -> songList.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> songList.get(4));
	}
}
