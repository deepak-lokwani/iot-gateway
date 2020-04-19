/**
 * 
 */
package neu.dlokwani.connecteddevices.project.protocols;

/**
 * @author Deepak_Lokwani
 * 
 * NUID: 001316769
 * 
 * Project name: iot-gateway
 * Package name: neu.dlokwani.connecteddevices.project.protocols
 * Created on: 05-Apr-2020
 * 
 */

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import neu.dlokwani.connecteddevices.project.commons.DataUtil;
import neu.dlokwani.connecteddevices.project.commons.SensorData;
import neu.dlokwani.connecteddevices.project.commons.SensorDataManager;
import neu.dlokwani.connecteddevices.project.protocols.UbidotsApiConnector;

/**
 * 
 * This class  establishes, subscribes and disconnects the client connection with the channel through a MQTT Broker using Java libraries of Paho
 *
 */

public class MQTTClientConnector implements MqttCallback{

	/**
	 * Declaring the variables and instantiating my objects
	 */
	private static final Logger log = Logger.getLogger("");
	private MqttClient mqttClient;
	private String protocol;
	private int port;
	private String clientID;
	private String brokerAddr;
	private String serverURL;
	String topic = "US";
	int qos = 2;
	
	/**
	 * Default constructor
	 */
	public MQTTClientConnector() {
		super();
		this.protocol = "tcp";
		this.brokerAddr="mqtt.eclipse.org";
//		this.brokerAddr = "test.mosquitto.org";
		this.port = 1883;
		this.clientID = MqttClient.generateClientId();
		this.serverURL = protocol + "://" + brokerAddr + ":" + port;

		//showing the information of clientID and broker address
		log.info("MQTT Broker Connection made using Client ID: " + clientID);
	}

	/**
	 * This method connects to my MQTT broker
	 */
	public boolean mqtt_connect() {
		if(mqttClient == null) {
			try {
				mqttClient = new MqttClient(serverURL, clientID);

				// connect to Mqtt connect options
				MqttConnectOptions connOptions = new MqttConnectOptions();
				connOptions.setCleanSession(true);
				mqttClient.setCallback(this);
				mqttClient.connect(connOptions);
				return true;

			} 
			catch (MqttException e) {
				log.log(Level.SEVERE, "connection Failed to broker: " + serverURL, e);
			}
		}
		return false;
	}

	/**
	 * This method subscribes to my MQTT Broker topic
	 */
	public boolean mqtt_subscribe(String topic) {
		try {
			mqttClient.subscribe(topic, qos);
			log.info("Subscribed to the Topic:  " + topic);

			return true;
		}
		catch (MqttException e) {
			log.log(Level.WARNING, "Failed to subscribe the topic", e);
		}
		return false;
	}


	/**
	 * This method unsubscribes from my MQTT broker topic
	 */
	public boolean unSubscribe(String topic) {
		boolean success = false;
		try {
			// unsubscribe call
			mqttClient.unsubscribe(topic);
			success = true;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * this method disconnects from my MQTT broker
	 */
	public boolean disconnect() {
		try {
			mqttClient.disconnect();
			log.info("Disconnect from broker: " + brokerAddr);
			return true;
		} catch (Exception ex) {
			log.log(Level.SEVERE, "Failed to disconnect from broker: " + brokerAddr, ex);
			return false;
		}
	}


	/**
	 * this  method logs whenever my connection is lost with the broker
	 */
	public void connectionLost(Throwable cause) {
		log.log(Level.WARNING, "Connection to broker lost");
	}

	/**
	 * This method logs whenever a message arrives
	 */
	public void messageArrived( String topic, MqttMessage message) throws Exception {
		
		
		log.info("Json Received from  topic : " + topic + "   to gateway: " + message.toString());
		DataUtil dUtil = new DataUtil();
		SensorDataManager sensorDataManager = new SensorDataManager();
		SensorData sData = dUtil.toSensorDataFromJson(message.toString());
		
		if((topic).equals("USFromSensor")) {
			sensorDataManager.processUSSensorData(sData);
		}
		
		else if((topic).equals("PIRFromSensor")) {
			sensorDataManager.processPirSensorData(sData);
		}
		
//		else if ((topic).equals("/v1.6/devices/pedestrian-project/led-actuator/lv")) {
//			log.info("message from UBIDOTS fro LED ACTUATOR:  " + message.toString());
//			System.out.println("ACTUATOR   :  " + message.toString());
//		}
		
	}
	
	/**
	 * This method logs whenever the message delivery is successfully done
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		try {
			log.info("Delivery complete: ID = " + token.getMessageId());
		} 
		catch (Exception e) {
			log.log(Level.SEVERE, "Failed to retrieve message from token", e);
		}
	}

}
