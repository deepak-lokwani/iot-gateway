package neu.dlokwani.connecteddevices.labs.module08;

//Importing libraries
import neu.dlokwani.connecteddevices.labs.module08.UbidotsClientConnector;;

/**
 * 
 * @author deepak lokwani
 * 
 *         NUID: 001316769
 * 
 */

public class GatewayDataManager extends Thread {
	/**
	 * This class is a threaded class and it maintains my connection to the Ubidots
	 * Api via a Apli Client connector and subscribes the actuator data via a
	 * MqttClientConnector
	 * 
	 */

	boolean enableEmulator = false;
	float minTemp = 15.0f;
	float maxTemp = 30.0f;

	@Override
	public void run() {
		/**
		 * this is my default run method for the thread class it connects to ubidots API
		 * and publishes the sensordata it connects to the ubidots-mqtt and subscribes
		 * for the updated actuator data it connects via MQTT to the sensor device
		 * 
		 */
		try {
			while (true) {

				if (enableEmulator) {
					/*
					 * this is for connecting it to the ubidots API to send the sensordata to my
					 * cloud
					 */
					UbidotsApiConnector api = new UbidotsApiConnector();
					api.publish(minTemp, maxTemp);
					sleep(10000);

					/*
					 * this connects to the ubidots mqtt to subscribe to the actuator data to the
					 * gateway device
					 */
					UbidotsClientConnector ubi = new UbidotsClientConnector("industrial.api.ubidots.com",
							"BBFF-yegKC0ObS7wjfGO8Bx2IU53hjRv9il",
							"C:\\Users\\deepa\\git\\workspace\\ubidots_cert.pem");
					ubi.ubidots_mqtt_connect();
					ubi.ubidots_mqtt_subscribe();
					sleep(10000);

					/*
					 * this connects to my constrained device
					 */
					UbidotsClientConnector mqtt = new UbidotsClientConnector();
					mqtt.ubidots_mqtt_connect();
					sleep(10000);
					mqtt.publishMessage("Mqtt_Test", 2, UbidotsClientConnector.device_payload);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
