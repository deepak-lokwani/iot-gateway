package neu.dlokwani.connecteddevices.common;

import neu.dlokwani.connecteddevices.common.ActuatorDataListener;
import neu.dlokwani.connecteddevices.common.SensorDataListener;
import neu.dlokwani.connecteddevices.common.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author: Deepak Lokwani
 * NUID: 001316769
 * 
 * this class has the task for the updating the actuator/sensor data from/to the  DBMS using other config files
 */
public class PersistenceUtil {

	/**
	 * Initializing my variables and instances
	 */
	Jedis jedis = new Jedis("redis-11983.c57.us-east-1-4.ec2.cloud.redislabs.com", 11983);
	ActuatorDataListener actuatorDataListener = new ActuatorDataListener();
	SensorDataListener sensorDataListener = new SensorDataListener();
	DataUtil dataUtil = new DataUtil();

	/**
	 * method to write Actuator data to DBMS
	 */
	public boolean writeActuatorDataToDbms(ActuatorData actuatorData) {
		jedis.auth("12345678");
		String jsonDataActuator = dataUtil.toJsonFromActuatorData(actuatorData);
		jedis.publish("ActuatorData", jsonDataActuator);
		registerActuatorDataToDbmsListener();
		jedis.close();
		return true;
	}

	/**
	 *  Method to subscribe sensor data channel from DBMS
	 */
	public boolean writeSensorDataToDbms() {
		jedis.auth("12345678");
		jedis.subscribe(new JedisPubSub() {
			SensorData sensorData = new SensorData();

			@Override
			public void onMessage(String channel, String message) {
				sensorData = dataUtil.toSensorDataFromJson(message);
				registerSensorDataToDbmsListener(sensorData);
				if (channel.equals("SensorData")) {
					unsubscribe(channel);
				}
			}
		}, "SensorData");
		jedis.close();
		return true;
	}

	/**
	 * method to display sensordata 
	 */
	public void registerSensorDataToDbmsListener(SensorData sensorData) {
		sensorDataListener.OnMessage(sensorData);
	}

	/**
	 * method to display Actuator Data 
	 */
	public void registerActuatorDataToDbmsListener() {
		actuatorDataListener.OnMessage();
	}
}
