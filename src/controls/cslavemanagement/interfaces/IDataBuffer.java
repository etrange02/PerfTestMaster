/**
 * 
 */
package controls.cslavemanagement.interfaces;

import java.util.List;

import controls.cslavemanagement.TCPDataBuffer;

/**
 * 
 * @author David Lecoconnier david.lecoconnier@gmail.com
 * @author Jean-Luc Amitousa-Mankoy jeanluc.amitousa.mankoy@gmail.com
 * @version 1.0
 */
public interface IDataBuffer {
	
	/**
	 * Returns the list of TCPDataBuffer handled by the DataBuffer. A new list 
	 * is created on internal
	 * @return a list
	 */
	public List<TCPDataBuffer> getTCPDataBuffer();

	/**
	 * Returns the number of received instructions
	 * @return the count
	 */
	public int countReceivedInstructions();
}