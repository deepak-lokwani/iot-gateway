package neu.dlokwani.connecteddevices.labs.module06;

/**
 * @author Deepak Lokwani
 * NUID: 001316769
 */

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import neu.dlokwani.connecteddevices.common.DataUtil;
import neu.dlokwani.connecteddevices.common.SensorData;

/**
 * 
 * This class  establishes, subscribes and disconnects the client connection with the channel through a MQTT Broker using Java libraries of Paho
 *
 */

public class MQTTClientConnector implements MqttCallback{

	/*
	 * Declaring the variables and instantiating my objects
	 */
	private static final Logger log = Logger.getLogger("");
	private MqttClient mqttClient;
	private String protocol;
	private int port;
	private String clientID;
	private String brokerAddr;
	private String serverURL;
	String topic = "Raspi/Temperature";
	int qos = 2;
	
	/*
	 * Default constructor
	 */
	public MQTTClientConnector() {
		super();
		this.protocol = "tcp";
		this.brokerAddr="mqtt.eclipse.org";
		this.port = 1883;
		this.clientID = MqttClient.generateClientId();
		this.serverURL = protocol + "://" + brokerAddr + ":" + port;

		//showing the information of clientID and broker address
		log.info("MQTT Broker Connection made using Client ID: " + clientID);
	}

	/*
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

	/*
	 * This method subscribes to my MQTT Broker topic
	 */
	public boolean mqtt_subscribe() {
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


	/*
	 * This method unsubscribes from my MQTT broker topic
	 */
	public boolean unSubscribe() {
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
	
	/*
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


	/*
	 * this  method logs whenever my connection is lost with the broker
	 */
	public void connectionLost(Throwable cause) {
		log.log(Level.WARNING, "Connection to broker lost");
	}

	/*
	 * This method logs whenever a message arrives
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		log.info("Message arrived.........");
		log.info("Json Received:"+message.toString());
		DataUtil data_util = new DataUtil();
		SensorData sensor_data = data_util.toSensorDataFromJson(message.toString());
		String final_json = data_util.toJsonFromSensorData(sensor_data);
		log.info("Json After Conversion:"+final_json);
	}
	
	/*
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
