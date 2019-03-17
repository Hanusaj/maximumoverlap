/**
 * Team members:
 * @author John Doe
 * @author Jane Doe
 * 
 * Node class for RBTree.
 */
public class Node {
	
	Node parent, left, right;
	Endpoint endpoint;
	int color;

	public Node() {
		parent = null;
		left = null;
		right = null;
		endpoint = null;
		color = 1;
	}

	public Node(Endpoint ep) {
		parent = null;
		left = null;
		right = null;
		endpoint = ep;
		color = 1;
	}

	public Node(Node l, Node r, Endpoint ep) {
		parent = null;
		left = l;
		right = r;
		endpoint = ep;
		color = 1;
	}
	
	public Node(Node l, Node r, Endpoint ep, int c) {
		parent = null;
		left = l;
		right = r;
		endpoint = ep;
		color = c;
	}

	/**
	 * Returns the parent of this node.
	 * @return
	 */
	public Node getParent() {
		return parent;
	}
	
	/**
	 * Returns the left child.
	 * @return
	 */
	public Node getLeft() {
		return left;
	}
	
	/**
	 * Returns the right child.
	 * @return
	 */
	public Node getRight() {
		return right;
	}
	
	/**
	 * Returns the endpoint value, which is an integer.
	 * @return
	 */
	public int getKey() {
		return endpoint.getValue();
	}
	
	/**
	 * Returns the value of the functionpbased on this endpoint.
	 * @return
	 */
	public int getP() {
		//TODO: Modify it accordingly.
		return 0;
	}
	
	/**
	 * Returns the val of the node as described in this assignment.
	 * @return
	 */
	public int getVal() {
		//TODO: Modify it accordingly.
		return 0;
	}
	
	/**
	 * Returns themaxvalof the node as described in this assignment.
	 * @return
	 */
	public int getMaxVal() {
		//TODO: Modify it accordingly.
		return 0;
	}
	
	/**
	 * Returns theEndpointobject that this node represents.
	 * @return
	 */
	public Endpoint getEndpoint() {
		return endpoint;
	}
	
	/**
	 * Returns anEndpointobject that represents emax. 
	 * Calling this method on the root node will give the End point object whose getValue() 
	 * provides a point of maximum overlap.
	 * @return
	 */
	public Endpoint getEmax() {
		//TODO: Modify it accordingly.
		return null;
	}
	
	/**
	 * Returns 0 if red. Returns 1 if black.
	 * @return
	 */
	public int getColor() {
		//TODO: Modify it accordingly.
		return color;
	}
	
	//Add more functions as  you see fit.
}
