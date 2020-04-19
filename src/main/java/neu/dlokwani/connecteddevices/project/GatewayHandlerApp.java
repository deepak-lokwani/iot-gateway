/**
 * 
 */
package neu.dlokwani.connecteddevices.project;


/**
 * @author Deepak_Lokwani
 * 
 * NUID: 001316769
 * 
 * Project name: iot-gateway
 * Package name: neu.dlokwani.connecteddevices.project
 * Created on: 05-Apr-2020
 * 
 */
public class GatewayHandlerApp {

	/**
	 * @param enableEmulator = Setting the thread emulator
	 */
	
	public static void main(String[] args) {
		GatewayDataManager gateway_manager = new GatewayDataManager();
		gateway_manager.enableEmulator=true;
		gateway_manager.start();
		SystemPerformanceAdaptor systemPerformanceAdaptor = new SystemPerformanceAdaptor();
		Thread performanceThread = new Thread(systemPerformanceAdaptor);
		performanceThread.start();
	}

}