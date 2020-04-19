/**
 * 
 */
package neu.dlokwani.connecteddevices.project.commons;

import neu.dlokwani.connecteddevices.labs.module02.SMTPClientConnector;
import neu.dlokwani.connecteddevices.project.protocols.MQTTClientConnector;
import neu.dlokwani.connecteddevices.project.protocols.UbidotsApiConnector;
import neu.dlokwani.connecteddevices.project.protocols.UbidotsClientConnector;

/**
 * @author Deepak_Lokwani
 * 
 * NUID: 001316769
 * 
 * Project name: iot-gateway
 * Package name: neu.dlokwani.connecteddevices.project.commons
 * Created on: 09-Apr-2020
 * 
 */

public class SensorDataManager {

	/**
	 * 
	 */
	
	UbidotsApiConnector ubiApi = new UbidotsApiConnector();
	
	public SensorDataManager() {
		// TODO Auto-generated constructor stub
	}

	public void processUSSensorData(SensorData sensorData) {
		
		
		if(sensorData.isUSRepeatedValueFlag()) {
			//send Email
			byte[] payload = sensorData.getSensorData().getBytes();
			SMTPClientConnector smtp = new SMTPClientConnector();
			smtp.publishMessage("US Sensor Values Repeating", payload);
		}
		
		if(sensorData.isUSLowValueFlag()) {
			//send email
			byte[] payload = sensorData.getSensorData().getBytes();
			SMTPClientConnector smtp = new SMTPClientConnector();
			smtp.publishMessage("US Sensor Values Low", payload);
		}
		
		ubiApi.publishUsToUbidots(sensorData.getCurValue());
		
	}
	
	public void processPirSensorData(SensorData sensorData) {
		
		ubiApi.publishPiRToUbidots(sensorData.isPirValue());
	}
	
		
	
	
}
