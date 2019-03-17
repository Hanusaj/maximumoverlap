/**
 * Team members:
 * @author John Doe
 * @author Jane Doe
 * 
 * Endpoint class for Node.
 */
public class Endpoint {
	
	int value;

	public Endpoint(int v) {
		value = v;
	}

	/**
	 * returns the endpoint value.  For example if the
	 * End point object represents the left end point of the 
	 * interval [1,3], this would return 1.
	 * @return
	 */
	public int getValue() {
		return value;
	}
}
