/**
 * This generic class is used in the MorseCodeTree classes. The class consists of a reference to the data and a reference to the left and right child.  
 * @param <T> the data type this node contains
 */
public class TreeNode<T> {
	private T data;
	private TreeNode<T> leftChild;
	private TreeNode<T> rightChild;
	
	/**
	 * Create a new TreeNode with data set to the provided data, and left and right child set to null by default
	 * @param data the data this node should reference
	 */
	public TreeNode(T data){
		this.data = data;
	}

	/**
	 * Recursive constructor used for making deep copies
	 * @param node the node to be deep copied into this node
	 */
	public TreeNode(TreeNode<T> node) {
		data = node.data;
		
		if (node.leftChild != null) {
			leftChild = new TreeNode<T>(node.leftChild);
		}
		
		if (node.rightChild != null) {
			rightChild = new TreeNode<T>(node.rightChild);
		}
	}
	
	/**
	 * Returns the node's data 
	 * @return the data this node references
	 */
	public T getData() {
		return data;
	}	
	
	/**
	 * Sets the node's data
	 * @param data the new data to be referenced by this node 
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	/**
	 * Checks if left child exists
	 * @return true if left child exists, false otherwise
	 */
	public boolean hasLeftChild() {
		return leftChild!=null;
	}

	/**
	 * Checks if right child exists
	 * @return true if right child exists, false otherwise
	 */
	public boolean hasRightChild() {
		return rightChild!=null;
	}
	
	/**
	 * Sets the left child to the provided node
	 * @param node the node to become the new left child of this node
	 */
	public void setLeftChild(TreeNode<T> node) {
		leftChild = node;
	}
	
	/**
	 * Sets the right child to the provided node
	 * @param node the node to become the new right child of this node
	 */
	public void setRightChild(TreeNode<T> node) {
		rightChild = node;
	}
	
	/**
	 * Gets the left child of this node
	 * @return the left child node
	 */
	public TreeNode<T> getLeftChild() {
		return leftChild;
	}
	
	/**
	 * Gets the right child of this node
	 * @return the right child node
	 */
	public TreeNode<T> getRightChild() {
		return rightChild;
	}
	
}
