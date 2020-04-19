/**
 * 
 */
package neu.dlokwani.connecteddevices.project;

import neu.dlokwani.connecteddevices.project.protocols.MQTTClientConnector;
import neu.dlokwani.connecteddevices.project.protocols.UbidotsClientConnector;

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
/**
 * This class inherits the thread class and is threaded by the GatewayHandler
 * class. This class has the inherent run method This class instantiates the
 * MQTT broker and connects, subscribes, unsubscribes and disconnnects to the
 * broker
 */

/*
 * @param: enableEmulator = this sets my emulator to start the thread
 */
public class GatewayDataManager extends Thread {

	boolean enableEmulator = false;

	/*
	 * My thread starts here This method connects, subscribes, unsubsrbes and then
	 * disconnects with the
	 */
	@Override
	public void run() {
		try {

			MQTTClientConnector mqttForCDToGD = new MQTTClientConnector();

			UbidotsClientConnector ubi = new UbidotsClientConnector("industrial.api.ubidots.com",
					"BBFF-yegKC0ObS7wjfGO8Bx2IU53hjRv9il", "C:\\Users\\deepa\\git\\workspace\\ubidots_cert.pem");

			mqttForCDToGD.mqtt_connect();
			ubi.ubidots_mqtt_connect();

			mqttForCDToGD.mqtt_subscribe("USFromSensor");
			mqttForCDToGD.mqtt_subscribe("PIRFromSensor");

			while (enableEmulator) {
				ubi.mqtt_subscribe("/v1.6/devices/pedestrian-project/led-actuator/lv");
				sleep(10000);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
