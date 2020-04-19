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
import java.lang.management.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SystemMemUtilTask {

	private static Logger log = Logger.getLogger(SystemMemUtilTask.class.getName());

	public SystemMemUtilTask() {
		// TODO Auto-generated constructor stub
	}

	public float getDataFromSensor() {

		MemoryMXBean memBeanObject = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapUsageObject = memBeanObject.getHeapMemoryUsage();
		MemoryUsage nonheapUsageObject = memBeanObject.getNonHeapMemoryUsage();

		double heapUtilPercent = ((double) heapUsageObject.getUsed() / heapUsageObject.getMax()) * 100;
		double nonheapUtilPercent = ((double) nonheapUsageObject.getUsed() / nonheapUsageObject.getMax()) * 100;
		if (heapUtilPercent < 0.0d)
			heapUtilPercent = 0.0d;
		if (nonheapUtilPercent < 0.0d)
			nonheapUtilPercent = 0.0d;

//		log.info("\tHeap Max: " + heapUsageObject.getMax() + "\tHeap Used: " + heapUsageObject.getUsed()
//				+ "\tNon-Heap Max: " + nonheapUsageObject.getMax() + "\tNonHeap Used: " + nonheapUsageObject.getUsed());
//		log.info("\tHeap Utilization (%): " + (float) heapUtilPercent + "\n");

		return (float) heapUtilPercent;
	}
}
