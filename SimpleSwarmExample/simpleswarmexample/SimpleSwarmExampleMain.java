/**
 * 
 */
package simpleswarmexample;

/**
 * @author mark
 *
 */
public class SimpleSwarmExampleMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleSwarmExampleApplication program = new SimpleSwarmExampleApplication();
		
//		for (;;) {
			
			try {
				program.run();
			} // try
			catch (Throwable e)
			{
				e.printStackTrace();
				
			} // catch
//		} // for
	}

}
