/**
 * 
 */
package neu.dlokwani.connecteddevices.project;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import java.util.logging.Logger;
import com.sun.management.OperatingSystemMXBean;

import com.sun.management.*;

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


public class SystemCpuUtilTask {
	/**
	 * 
	 * This class is responsible for getting the cpu utilization of the gateway device computer
	 * It returns a value in terms of a percentage of the capacity of the processor
	 */

	private static Logger log1 = Logger.getLogger(SystemCpuUtilTask.class.getName());

	public SystemCpuUtilTask() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * This method returns the CPU Utilization float values from the sensor
	 */
	public float getDataFromSensor() {

		OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		double cpuUtilPercent = (double) operatingSystemMXBean.getSystemCpuLoad() * 100;

		log1.info("\tCPU Usage(%): " + (float) cpuUtilPercent);

		return (float) cpuUtilPercent;

	}
}
