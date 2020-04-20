
package neu.dlokwani.connecteddevices.project.protocols;

/**
 * 
 * @author Deepak_Lokwani
 * 
 * NUID: 001316769
 * 
 * Project name: iot-gateway
 * Package name: neu.dlokwani.connecteddevices.project.protocols
 * Created on: 08-Apr-2020
 * 
 */

import java.util.logging.Logger;
import com.ubidots.*;


public class UbidotsApiConnector {

	/**
	 * the values are sent to the Ubidots variables using this class values within the range to
	 * check the integration with the cloud
	 */

	private static final Logger log = Logger.getLogger("");

	ApiClient api = new ApiClient("BBFF-7602977eea40738bd2e69bfd271747a80e0");
	DataSource datasource = api.getDataSource("5e8fc66a1d8472158564bdfa");
	Variable UsSensor = api.getVariable("5e8fc6921d84721408a95124");
	Variable PirSensor = api.getVariable("5e9002cb1d84727760f4fd04");
	Variable cpuUtil = api.getVariable("5e9c8bd91d847221490ebf54");
	Variable memUtil = api.getVariable("5e9c8c511d847221490ebf55");

	public UbidotsApiConnector() {
		super();
	}

	/*
	 * Publish method publishes the values to the sensor variable to the cloud
	 */
	public boolean publishUsToUbidots(float value) {
		
		UsSensor.saveValue(value);
		log.info("UltraSonic Distance Reading sent to Ubidots via API : " + Float.toString(value));
		return true;
	}

	public void publishPiRToUbidots(boolean value) {
		
		int x;
		if (value == true)  {
			x = 1;
			PirSensor.saveValue(x);
			log.info("PIR sensor current status sent to Ubidots via API :  " + x);
		}
		
		else if(value == false) { 
			x = 0;
			PirSensor.saveValue(x);
			log.info("PIR sensor current status sent to Ubidots via API :  " + x);
		}
	}
	
	/*
	 * Publish System Performance Data to Ubidots
	 */
	public boolean publishCpuUtilToUbidots(float cpuUtilization) {
		cpuUtil.saveValue(cpuUtilization);
		return true;
	}
	public void publishMemUtilToUbidots(float memUtilization) {
		memUtil.saveValue(memUtilization);
	}
}