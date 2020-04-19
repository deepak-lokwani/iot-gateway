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
 * Created on: 19-Apr-2020
 * 
 */
import neu.dlokwani.connecteddevices.project.protocols.UbidotsApiConnector;

public class SystemPerformanceAdaptor implements Runnable {

	/*
	 * initializing the variables and the creating the instances of the CPU & Memory classes
	 */
	protected boolean done = false;
	SystemCpuUtilTask systemCpuUtilTask = new SystemCpuUtilTask();
	SystemMemUtilTask systemMemUtilTask = new SystemMemUtilTask();
	UbidotsApiConnector ubidotsApiConnector = new UbidotsApiConnector();

	public SystemPerformanceAdaptor() {
		// TODO Auto-generated constructor stub

	}

	/*
	 * Run method for the thread
	 */
	public void run() {
		// TODO Auto-generated method stub
		while (!done) {
			doWork();
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	/*
	 * Do work while the thread is running
	 */
	private void doWork() {
		// TODO Auto-generated method stub
		float cpuUtil = systemCpuUtilTask.getDataFromSensor();
		float  memUtil = systemMemUtilTask.getDataFromSensor();
		ubidotsApiConnector.publishCpuUtilToUbidots(cpuUtil);
		ubidotsApiConnector.publishMemUtilToUbidots(memUtil);
	}

}
