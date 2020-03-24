package neu.dlokwani.connecteddevices.labs.module06;
import neu.dlokwani.connecteddevices.labs.module06.MQTTClientConnector;

/**
 * @author Deepak Lokwani
 * NUID: 001316769
 */

/**
 * This class inherits the thread class and is threaded by the GatewayHandler class.
 * This class has the inherent run method
 * This class instantiates the MQTT broker and connects, subscribes, unsubscribes and disconnnects to the  broker   
 */

/*
 * @param: enableEmulator = this sets my emulator to start the thread
 */
public class GatewayDataManager extends Thread {

	boolean enableEmulator = false;
		
		/*
		 * My thread starts here 
		 * This method connects, subscribes, unsubsrbes and then disconnects with the 
		 */
		@Override
		public void run() {
			try {
				while (true) {
					
					if (enableEmulator) {
						MQTTClientConnector mqttClientConnector = new MQTTClientConnector();
						mqttClientConnector.mqtt_connect();
						mqttClientConnector.mqtt_subscribe();
						sleep(70000);
						mqttClientConnector.unSubscribe();
						mqttClientConnector.disconnect();
						
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}	

}
