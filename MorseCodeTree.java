import java.util.ArrayList;

/**
 * A generic linked binary tree which inherits from the LinkedConverterTreeInterface.  The class uses an external generic TreeNode class parameterized as a String: TreeNode<String>.  This class uses the private member of root.  Nodes are added based on their morse code value.  
 */
public class MorseCodeTree implements LinkedConverterTreeInterface<String> {
	private TreeNode<String> root;
	
	/**
	 * Constructor calls the buildTree method
	 */
	public MorseCodeTree() {
		buildTree();
	}

	@Override
	public void buildTree() {
		//level 1
		root = new TreeNode<String>("");
		
		//level 2
		insert(".", "e"); 
		insert("-", "t");
		
		//level 3
		insert("..", "i"); 
		insert(".-", "a"); 
		insert("-.", "n");
		insert("--", "m");
		
		//level 4
		insert("...", "s"); 
		insert("..-", "u"); 
		insert(".-.", "r");
		insert(".--", "w");
		insert("-..", "d"); 
		insert("-.-", "k"); 
		insert("--.", "g");
		insert("---", "o");
		
		//level 5
		insert("....", "h"); 
		insert("...-", "v"); 
		insert("..-.", "f");
		insert(".-..", "l");
		insert(".--.", "p"); 
		insert(".---", "j"); 
		insert("-...", "b");
		insert("-..-", "x");
		insert("-.-.", "c");
		insert("-.--", "y");
		insert("--..", "z");
		insert("--.-", "q");
	}
	
	@Override
	public TreeNode<String> getRoot(){
		return root;
	}
	
	/**
	 * Replaces the tree at this root
	 */
	@Override
	public void setRoot(TreeNode<String> newRoot) {
		root = new TreeNode<String>(newRoot);
	}
	
	@Override
	public void insert(String code, String letter) {
		addNode(root, code, letter);
	}
	
	@Override
	public void addNode(TreeNode<String> root, String code, String letter) {
		String nullString = null;
		
		//base case: code has only one digit, add a child at this root
		if (code.length() == 1) {
			if (code.charAt(0) == '.') {
				//if left child already exists, replace its data
				if (root.hasLeftChild()) {
					root.getLeftChild().setData(letter);
				} else {
					//otherwise add a new leaf
					root.setLeftChild(new TreeNode<String>(letter));
				}
			} else if (code.charAt(0) == '-') {
				if (root.hasRightChild()) {
					root.getRightChild().setData(letter);
				} else {
					root.setRightChild(new TreeNode<String>(letter));
				}
			} else {
				throw new IllegalArgumentException();
			}
			return;
		}
		
		//recursive cases - continue traversing down left or right subtree of current root
		
		//'.' means left traversal
		if (code.charAt(0) == '.') {
			if (!root.hasLeftChild()) {
				//letter doesn't exist here yet, create a node referencing null data (may be replaced in a later add) to allow traversal to continue
				root.setLeftChild(new TreeNode<String>(nullString)); 
			}
			addNode(root.getLeftChild(), code.substring(1), letter);
		} 
		//'-' means right traversal
		else if (code.charAt(0) == '-') {
			if (!root.hasRightChild()) {
				root.setRightChild(new TreeNode<String>(nullString));
			}
			addNode(root.getRightChild(), code.substring(1), letter);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public String fetch(String code) {
		return fetchNode(root, code);
	}
	
	@Override
	public String fetchNode(TreeNode<String> root, String code) {
		//base case
		if(code.length() == 1) {
			if (code.charAt(0) == '.') {
				return root.getLeftChild().getData();
			} else if (code.charAt(0) == '-') {
				return root.getRightChild().getData();
			} else {
				throw new IllegalArgumentException();
			}
		}
				
		//'.' means left traversal
		if (code.charAt(0) == '.') {
			return fetchNode(root.getLeftChild(), code.substring(1));
		} 
		//'-' means right traversal
		else if (code.charAt(0) == '-') {
			return fetchNode(root.getRightChild(), code.substring(1));
		} else {
			throw new IllegalArgumentException();
		}		
	}
	
	@Override
	public ArrayList<String> toArrayList() {
		ArrayList<String> list = new ArrayList<>();
		LNRoutputTraversal(root, list);
		return list;
	}
	
	@Override
	public void LNRoutputTraversal(TreeNode<String> root, ArrayList<String> list) {
		if (root != null) {
			LNRoutputTraversal(root.getLeftChild(), list);
			list.add(root.getData());
			LNRoutputTraversal(root.getRightChild(), list);
		}
	}
	
	@Override
	public MorseCodeTree delete(String data) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public MorseCodeTree update() throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
}
