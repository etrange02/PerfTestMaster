/**
 * 
 */
package controls.ctestplanmanagement;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Jean-Luc Amitousa-Mankoy jeanluc.amitousa.mankoy@gmail.com
 * @version 1.0
 */
public abstract class TCPProxy implements Runnable {

	public static final int PROXY_PORT = 3000;
	private static final int DATA_MAXLENGTH = 9998;
	private ServerSocket serverSocket;
	private String hostname;
	private int port;

	private TestPlanManagementFacade testPlanManagementFacade;






	/* *********************************************************************
	 * CONSTRUCTORS ********************************************************
	 * *********************************************************************/

	/**
	 * @param hostname the hostname where find the tested server.
	 * @param port the port to use to discuss with the tested server.
	 * @param instructions the list of instructions to complete.
	 * @throws IOException
	 */
	public TCPProxy(String hostname, int port) throws IOException {
		serverSocket = new ServerSocket(PROXY_PORT);
		this.hostname = hostname;
		this.port = port;
	}






	/* *********************************************************************
	 * GETTERS/SETTERS *****************************************************
	 * *********************************************************************/

	/**
	 * Returns the associated TestPlan manager
	 * @return a testPlan manager
	 */
	public TestPlanManagementFacade getTestPlanManagementFacade() {
		return testPlanManagementFacade;
	}

	/**
	 * Modifies the associated TestPlan manager
	 * @param testPlanManagementFacade a testPlan manager
	 */
	public void setTestPlanManagementFacade( TestPlanManagementFacade testPlanManagementFacade) {
		this.testPlanManagementFacade = testPlanManagementFacade;
	}






	/* *********************************************************************
	 * IMPORTANT METHODS ***************************************************
	 * *********************************************************************/

	/**
	 * Here, we going to used readed tcpData to complete instructions
	 * @param tcpData the readed tcp data
	 */
	protected abstract void handleTCPData(byte[] tcpData);

	@Override
	public void run() {

		Socket proxySocket = null;
		InputStream proxyIN = null;
		DataOutputStream proxyOUT = null;
		byte[] proxyTCPData = null;

		Socket socket = null;
		InputStream in = null;
		DataOutputStream out = null;
		byte[] tcpData = null;
		
		try {

			//STEP 1. init and wait for a client...

			proxySocket = serverSocket.accept();
			proxyIN = proxySocket.getInputStream();
			proxyOUT = new DataOutputStream(proxySocket.getOutputStream());

			socket = new Socket(hostname, port);
			in = socket.getInputStream();
			out = new DataOutputStream(socket.getOutputStream());

			while(true) {	

				
				
				//STEP 2. wait for a request and do wanted operations with it

				if((proxyTCPData = read(proxyIN)) == null) {
					break; //EOF
				}

				handleTCPData(proxyTCPData);
				
				
				
				//STEP 3. redirect request and wait request treatment.
				
				out.write(proxyTCPData);
				
				if((tcpData = read(in)) == null) {
					//EOF
					break;
				}

				
				//STEP 4. do wanted operations on response and retransmit it
				
				handleTCPData(tcpData);
				proxyOUT.write(tcpData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read a byte array from an tcp byte stream
	 * @param in the readed tcp byte stream
	 * @return the readed byte array
	 * @throws Exception
	 */
	private byte[] read(InputStream in) throws Exception {

		byte[] tmpArray = new byte[DATA_MAXLENGTH];
		byte[] resizedArray = null;//the resized array 
		int datasSize = -1;

		datasSize = in.read(tmpArray, 0, DATA_MAXLENGTH);

		if(datasSize > 0) {
			resizedArray = new byte[datasSize];
			System.arraycopy(
					tmpArray, 0, 
					resizedArray, 0, 
					datasSize);	
		}
		else if(datasSize != -1){ //-1 means EOF: end of connection
			throw new Exception("Error while reading BER data");
		}

		return resizedArray;
	}
}
