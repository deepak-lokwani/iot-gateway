/**
 */
package neu.dlokwani.connecteddevices.labs.module01;
import neu.dlokwani.connecteddevices.labs.module02.*;

/*
 * @author deepak
 * NUID: 001316769
 * This class is the master class and it initiates the thread for the SystemPerformanceAdaptor class and the TempEmulatorAdaptor Class
 */

public class GatewayHandlerApp {

	/*
	 * Creating two new threads for the two requisite classes
	 */
	SystemPerformanceAdaptor systemPerformanceAdaptor = new SystemPerformanceAdaptor();
	Thread threadA = new Thread(systemPerformanceAdaptor);
	
	TempEmulatorAdaptor tempAdaptor = new TempEmulatorAdaptor();
	Thread threadB = new Thread(tempAdaptor);

	public GatewayHandlerApp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GatewayHandlerApp gatewayHandlerApp = new GatewayHandlerApp();
		gatewayHandlerApp.run();
	}

	private void run() {
		// TODO Auto-generated method stub
		threadA.start();  //threadA is commented right now to check only the threadB
//		threadB.start();
	}

}
