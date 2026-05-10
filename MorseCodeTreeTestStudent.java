import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MorseCodeTreeTestStudent {
	private MorseCodeTree mct;

	@BeforeEach
	public void setUp() throws Exception {
		mct = new MorseCodeTree();
	}

	@AfterEach
	public void tearDown() throws Exception {
		mct = null;
	}

	@Test
	public void testGetRootData() {
		assertEquals("", mct.getRoot().getData());
	}
	
	@Test
	public void testReplaceTree() {
		TreeNode<String> newRoot = new TreeNode<String>("new root");
		newRoot.setLeftChild(new TreeNode<String>("new left"));
		newRoot.setRightChild(new TreeNode<String>("new right"));
		newRoot.getLeftChild().setRightChild(new TreeNode<String>("left's right"));
		newRoot.getLeftChild().setLeftChild(new TreeNode<String>("left's left"));
		newRoot.getRightChild().setLeftChild(new TreeNode<String>("right's left"));
		newRoot.getRightChild().getLeftChild().setRightChild(new TreeNode<String>("right's left's right"));
		
		mct.setRoot(newRoot);
		assertEquals(newRoot.getData() , mct.getRoot().getData());
		
		//should replace "new right"
		mct.insert("-", "t");
		
		//should be added as children to existing nodes (positions are not occupied)
		mct.insert("-..", "d");
		mct.insert("--", "m");
		mct.insert(".-.", "r");
		
		List<String> list = List.of("left's left", "new left", "r", "left's right", "new root", "d", "right's left", "right's left's right", "t", "m");
		List<String> mctList = mct.toArrayList();
		
		for (int i = 0; i < mctList.size(); i++) {
			assertEquals(list.get(i), mctList.get(i));
		}
	}
	
	@Test
	public void testInsert() {
		mct.insert("..--", "new letter");
		assertEquals("new letter", mct.fetch("..--"));
	}

}
