package neu.dlokwani.connecteddevices.labs.module08;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import neu.dlokwani.connecteddevices.common.DataUtil;
import neu.dlokwani.connecteddevices.common.SensorData;
import com.labbenchstudios.iot.common.*;

/**
 * 
 * @author deepak lokwani
 * 
 * NUID: 001316769
 * 
 * this class connects my gateway device to the ubidots cloud via MQTT broker
 * to update the tempactuator value to the gateway
 * 
 * Further it relays this information to the constrained device via a local MQTT host
 * 
 */

/**
 * 
 * @param: mqttClient: client to connect with the MQTT broker
 * @param: protocol: protocol to be used for communication
 * @param: port: specifies the port number for the MQTT
 * @param: client_id: client ID for the MQTT communication
 * @param: broker_address
 */
public class UbidotsClientConnector implements MqttCallback {
	/**
	 * This class uses the eclipse paho library for Java to import the methods of
	 * Mqtt and subscribe to the channel to get the data from Python program
	 */

	private static final Logger log = Logger.getLogger("");
	private MqttClient mqttClient;
	private String protocol;
	private int port;
	private String client_id;
	private String broker_address;
	private String server_url;
	private String host_name;
	private String auth_token;
	private String pem_path;
	private String pem_file;
	private boolean tls_flag = false;
	private String ubidots_topic = "/v1.6/devices/tempmonitor/tempactuator/lv";
	String topic = "Mqtt_Test";
	public static byte[] device_payload;
	int qos = 2;
	int ubi_qos=1;	

	/*
	 * My method to connect with the MQTT Broker for the ActuatorData  to be 
	 * updated back to the constrained device
	 *  
	 */
	public UbidotsClientConnector() {
		super();
		//setting up my variables
		this.protocol = "tcp";
		this.broker_address = "mqtt.eclipse.org";
		this.port = 1883;
		this.client_id = MqttClient.generateClientId();
		this.server_url = protocol + "://" + broker_address + ":" + port;
		log.info("Connecting Gateway to Constrained Device via MQTT broker using client ID : " + client_id);
	}

	/*
	 * My method to connect with the Ubidots_mqtt broker for the temperature actuator data 
	 * to be sent from the Ubidots cloud to my gateway device
	 */
	public UbidotsClientConnector(String host_name, String auth_token, String pem_path) {
		super();
		//setting up my variables
		this.host_name = host_name;
		this.auth_token = auth_token;
		this.pem_file = pem_path;
		this.protocol = "ssl";
		this.port = 8883;
		this.tls_flag=true;
		this.server_url = protocol + "://" + host_name  + ":" + port;
		this.client_id = MqttClient.generateClientId();
		log.info("Connecting Ubidots to Gateway device via MQTT broker using client ID : " + client_id);
	}

	/*
	 * method to establish the MQTT connection based on the parameters defined
	 */
	public boolean ubidots_mqtt_connect() {
		if (mqttClient == null) {
			try {
				mqttClient = new MqttClient(server_url, client_id);

				// connect to Mqtt connect options
				MqttConnectOptions conn_opt = new MqttConnectOptions();

				if (tls_flag == true) {
					try {
						SSLContext sslContext = SSLContext.getInstance("SSL");
						TrustManagerFactory trustManagerFactory = TrustManagerFactory
								.getInstance(TrustManagerFactory.getDefaultAlgorithm());
						KeyStore keyStore = readKeyStore();
						trustManagerFactory.init(keyStore);
						sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
						conn_opt.setSocketFactory(sslContext.getSocketFactory());
//						cmu.loadCertificate("ubidots_cert.pem");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				conn_opt.setCleanSession(true);
				if(auth_token!=null) {
					conn_opt.setUserName(auth_token);
				}
				mqttClient.setCallback(this);
				mqttClient.connect(conn_opt);
				return true;
			} catch (MqttException e) {
				log.log(Level.SEVERE, "Failed to connect to Mqtt broker: " + server_url, e);
			}
		}
		return false;
	}

	/*
	 * This method generates and returns a keystore instance for the encryption purpose of SSL
	 */
	private KeyStore readKeyStore()
			throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream fis = new FileInputStream(pem_file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		ks.load(null);
		while (bis.available() > 0) {
			Certificate cert = cf.generateCertificate(bis);
			ks.setCertificateEntry("jy_store" + bis.available(), cert);
		}
		return ks;
	}

	/*
	 *  Method to check and disconnect from the MQTT broker
	 */
	public boolean ubidot_mqtt_disconnect() {
		try {
			mqttClient.disconnect();
			log.info("Disconnected from broker: " + broker_address);
			return true;
		} catch (MqttException e) {
			log.log(Level.SEVERE, "Failed to disconnect from MQTT broker: " + broker_address, e);
			return false;
		}

	}

	/*
	 *  My method to Subscribe to the topic
	 */
	public boolean ubidots_mqtt_subscribe() {
		try {
			mqttClient.subscribe(ubidots_topic, ubi_qos);
			log.info("Subscribed to Topic:  " + ubidots_topic);
			
			return true;
		} catch (MqttException e) {
			log.log(Level.WARNING, "Failed to subscribe topics.", e);
		}
		return false;
	}

	/* 
	 * Method to Unsubscribe from Topic
	 */
	public boolean mqtt_unsubscribe() {
		try {
			mqttClient.unsubscribe(topic);
			return true;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return false;

	}

	/*
	 * Method to Publish to the Topic
	 */
	public boolean publishMessage(String topic, int qos, byte[] payload) {
		boolean success = false;
		try {
			log.info("Publishing message to topic: " + topic);
			// set the payload of the message
			MqttMessage message = new MqttMessage();
			message.setPayload(payload);
			// set  the qos level 
			message.setQos(qos);
			// link to the topic
			mqttClient.publish(topic, message);
			log.info("Published Success!  MqttMessageID:" + message.getId());
			success = true;
		} catch (MqttException e) {
			log.log(Level.SEVERE, "Failed to publish MQTT message: " + e.getMessage());
		}

		return success;

	}

	/* 
	 * Callback method to notify about the lost connection
	 */
	public void connectionLost(Throwable cause) {
		log.log(Level.WARNING, "Connection to broker lost");
	}

	/* 
	 * Callback method to notify about the arrival of message
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		//log.info("Message arrived.........");
		log.info("Json Received from  Ubidots to gateway:" + message.toString());
		
		device_payload=message.getPayload();
	}

	/*
	 *  Callback method to notify about the completion of delivery of message
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		try {
			log.info("Delivery complete: ID = " + token.getMessageId());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to retrieve message from token", e);
		}
	}

}
