package neu.dlokwani.connecteddevices.labs.module08;

import neu.dlokwani.connecteddevices.labs.module08.GatewayDataManager;

/**
 * 
 * @author deepak lokwani
 * 
 *         NUID: 001316769
 * 
 */

public class GatewayHandlerApp {
	/**
	 * This public Class is used to start and run my thread class GatewayDataManager
	 * 
	 */

	/**
	 * 
	 * @param args: null and returns null This main function sets up my emulator and
	 *              starts the thread
	 */
	public static void main(String[] args) {

		GatewayDataManager gatewayManager = new GatewayDataManager();
		gatewayManager.enableEmulator = true;
		gatewayManager.start();
	}

}
