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
	 * 
	 * This is my main class. It starts the process at 
	 * the gateway end. It starts two threads one for the 
	 * data manager and the other for the system performance.
	 */
	
	public static void main(String[] args) {
		
		/*
		 * start the thread for the data manger
		 */
		GatewayDataManager gateway_manager = new GatewayDataManager();
		gateway_manager.enableEmulator=true;
		gateway_manager.start();
		
		/*
		 * start the thread for the system performance app
		 */
		SystemPerformanceAdaptor systemPerformanceAdaptor = new SystemPerformanceAdaptor();
		Thread performanceThread = new Thread(systemPerformanceAdaptor);
		performanceThread.start();
	}

}