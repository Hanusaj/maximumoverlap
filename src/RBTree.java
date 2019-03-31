import java.util.LinkedList;
import java.util.Queue;

/**
 * Team members:
 * @author AJ Hanus
 * @author Jordan Cowen
 * @author Brett Peterson
 * 
 * RBTree class, maintains operations on RBTree.
 */
public class RBTree {

	Node root, nilNode;
	int size;
	
	/**
	 * RB Tree constructor. It initializes nil node as well.
	 */
	public RBTree() {
		nilNode = new Node();
		nilNode.left = nilNode;
		nilNode.right = nilNode;
		nilNode.parent = nilNode;
		root = nilNode;
		size = 0;
	}
	
	/**
	 * Returns the root of teh tree.
	 * @return
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * Returns reference for the nil node, for the rbTree.
	 * @return
	 */
	public Node getNILNode() {
		return nilNode;
	}
	
	/**
	 * Returns the number of internal nodes in the tree.
	 * @return
	 */
	public int getSize() {
		return size;
	}
	
	
	/**
	 * Returns the height of the tree.
	 * @return
	 */
	public int getHeight() {
		return 0;
	}
	
	public void insert(Node z) {
		Node x = root;
		Node y = nilNode;
		while (x != nilNode) {
			x.val += z.p;
			y = x;
			if (z.getKey() < x.getKey()) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;
		if (y == nilNode) {
			root = z;
		} else if (z.getKey() < y.getKey()) {
			y.left = z;
		} else {
			y.right = z;
		}
		z.left = nilNode;
		z.right = nilNode;
		z.color = 0;
		z.emax = z.p == 1 ? z.getEndpoint() : nilNode.getEndpoint();
		size++;
		setMaxValRec(z);
		insertFixup(z);
	}

	private void insertFixup(Node z) {
		while (z.parent.color == 0) {
			if (z.parent == z.parent.parent.left) {
				Node y = z.parent.parent.right;
				if (y.color == 0) {
					z.parent.color = 1;
					y.color = 1;
					z.parent.parent.color = 0;
					z = z.parent.parent;
				} else {
					if (z == z.parent.right) {
						z = z.parent;
						leftRotate(z);
					}
					z.parent.color = 1;
					z.parent.parent.color = 0;
					rightRotate(z.parent.parent);
				}
			} else if (z.parent == z.parent.parent.right){
				Node y = z.parent.parent.left;
				if (y.color == 0) {
					z.parent.color = 1;
					y.color = 1;
					z.parent.parent.color = 0;
					z = z.parent.parent;
				} else {
					if (z == z.parent.left) {
						z = z.parent;
						rightRotate(z);
					}
					z.parent.color = 1;
					z.parent.parent.color = 0;
					leftRotate(z.parent.parent);
				}
			}
		}
		root.color = 1;
	}

	private void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != nilNode) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == nilNode) {
			root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
		y.val = x.val;
		x.val -= y.p + y.right.val;
		setMaxVal(x);
		setMaxVal(y);
	}

	private void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != nilNode) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == nilNode) {
			root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
		y.val = x.val;
		x.val -= y.p + y.left.val;
		setMaxVal(x);
		setMaxVal(y);
	}

	private void setMaxValRec(Node x) {
		setMaxVal(x);
		if (x.parent != nilNode) {
			setMaxValRec(x.parent);
		}
	}

	private void setMaxVal(Node x) {
		if (x.left.maxval > x.left.val + x.p && x.left.maxval > x.left.val + x.p + x.right.maxval) {
			x.maxval = x.left.maxval;
			x.emax = x.left.emax;
		} else if (x.left.val + x.p > x.left.maxval && x.left.val + x.p > x.left.val + x.p + x.right.maxval) {
			x.maxval = x.left.val + x.p;
			x.emax = x.getEndpoint();
		} else {
			x.maxval = x.left.val + x.p + x.right.maxval;
			x.emax = x.right.emax;
		}
	}

	public void delete(Node z) {
		Node y = z, x;
		int yOrigColor = y.color;

		if (z.left == nilNode) {
			x = z.right;
			transplant(z, z.right);
		} else if (z.right == nilNode) {
			x = z.left;
			transplant(z, z.left);
		} else {
			y = minimum(z.right);
			yOrigColor = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			} else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.right;
			y.left.parent = y;
			y.color = z.color;
		}
		if (yOrigColor == 1) {
			deleteFixup(x);
		}
	}

	private void deleteFixup(Node x) {
		Node w;
		while (x != root && x.color == 1) {
			if (x == x.parent.left) {
				w = x.parent.right;
				if (w.color == 0) {
					w.color = 1;
					x.parent.color = 0;
					leftRotate(x.parent);
					w = x.parent.right;
				}
				if (w.left.color == 1 && w.right.color == 1) {
					w.color = 0;
					x = x.parent;
				}
				if (w.right.color == 1) {
					w.left.color = 1;
					w.color = 0;
					rightRotate(w);
					w = x.parent.right;
				}
				w.color = x.parent.color;
				x.parent.color = 1;
				w.right.color = 1;
				leftRotate(x.parent);
				x = root;
			} else {
				w = x.parent.left;
				if (w.color == 0) {
					w.color = 1;
					x.parent.color = 0;
					rightRotate(x.parent);
					w = x.parent.left;
				}
				if (w.right.color == 1 && w.left.color == 1) {
					w.color = 0;
					x = x.parent;
				}
				if (w.left.color == 1) {
					w.right.color = 1;
					w.color = 0;
					leftRotate(w);
					w = x.parent.left;
				}
				w.color = x.parent.color;
				x.parent.color = 1;
				w.left.color = 1;
				rightRotate(x.parent);
				x = root;
			}
		}
		x.color = 1;
	}

	private void transplant(Node u, Node v) {
		if (u.parent == nilNode) {
			root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	public Node minimum(Node r) {
		Node current = r;
		while (current.left != nilNode) {
			current = current.left;
		}
		return current;
	}
	
	public Node maximum(Node r) {
		Node current = r;
		while (current.right != nilNode) {
			current = current.right;
		}
		return current;
	}

	public void inorder() {
		inorderRec(root);
	}

	private void inorderRec(Node r) {
		if (r == nilNode) return;
		inorderRec(r.left);
		System.out.println(r.getKey());
		inorderRec(r.right);
	}
	
	/**
	 * For testing, this prints the val for each node in order. 
	 */
	public void inorderVal() {
		inorderValRec(root);
	}

	private void inorderValRec(Node r) {
		if (r == nilNode) return;
		inorderValRec(r.left);
		System.out.println(r.getVal());
		inorderValRec(r.right);
	}

	public void topView() {
		topViewRec(root);
	}

	private void topViewRec(Node r) 
    { 
		if (r == nilNode) return;
        Queue<Node> q = new LinkedList<Node>(); 

        q.add(r); 
          
        while(true) 
        { 
            int nodeCount = q.size(); 
			if(nodeCount == 0) break; 
			
            while(nodeCount > 0) 
            { 
                Node node = q.peek(); 
                System.out.print(node.getKey() + " "); 
                q.remove(); 
                if(node.left != nilNode) 
                    q.add(node.left); 
                if(node.right != nilNode) 
                    q.add(node.right); 
                nodeCount--; 
            } 
            System.out.println(); 
        } 
	} 
	
	public void topViewColor() {
		topViewColorRec(root);
	}

	private void topViewColorRec(Node r) 
    { 
		if (r == nilNode) return;
        Queue<Node> q = new LinkedList<Node>(); 

        q.add(r); 
          
        while(true) 
        { 
            int nodeCount = q.size(); 
			if(nodeCount == 0) break; 
			
            while(nodeCount > 0) 
            { 
                Node node = q.peek(); 
                System.out.print((node.getColor() == 1 ? "black" : "red") + " "); 
                q.remove(); 
                if(node.left != nilNode) 
                    q.add(node.left); 
                if(node.right != nilNode) 
                    q.add(node.right); 
                nodeCount--; 
            } 
            System.out.println(); 
        } 
	}
	
	public void topViewVal() {
		topViewValRec(root);
	}

	private void topViewValRec(Node r) 
    { 
		if (r == nilNode) return;
        Queue<Node> q = new LinkedList<Node>(); 

        q.add(r); 
          
        while(true) 
        { 
            int nodeCount = q.size(); 
			if(nodeCount == 0) break; 
			
            while(nodeCount > 0) 
            { 
                Node node = q.peek(); 
                System.out.print(node.getMaxVal() + " "); 
                q.remove(); 
                if(node.left != nilNode) 
                    q.add(node.left); 
                if(node.right != nilNode) 
                    q.add(node.right); 
                nodeCount--; 
            } 
            System.out.println(); 
        } 
    }
	

	/**
	 * updateNodeVals() calls the in-order recursive method updateNodeValsRec() on root in order to update
	 * the val field of every node in this tree. 
	 */
	public void updateNodeVals(){
		updateNodeValsRec(root);		
	}
	
	/**
	 * Called by updateNodeVals() to update the val field of each node in the tree. The val field for each
	 * node is the sum of the p-values of every node in the subtree rooted at itself. 
	 * @param r
	 * @return
	 */
	public int updateNodeValsRec(Node r){
		if (r == nilNode) return 0;
	
		r.val += updateNodeValsRec(r.left);
		r.val += r.p;
		System.out.println("p-val: " + r.p);
		r.val += updateNodeValsRec(r.right);
		
		return r.val;
	
	}
}
