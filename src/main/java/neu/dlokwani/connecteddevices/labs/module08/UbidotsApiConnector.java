package neu.dlokwani.connecteddevices.labs.module08;

import java.util.Random;
import java.util.logging.Logger;
import com.ubidots.*;

/**
 * 
 * @author deepak lokwani
 * 
 *         NUID: 001316769
 *
 *         This class connects to my Ubidots API client connector and updates
 *         the value of a tempsonsor variable in the ubidots cloud
 * 
 */

public class UbidotsApiConnector {

	/**
	 * for testing purpose, we are generating a random values within the range to
	 * check the integration with the cloud
	 */

	private static final Logger log = Logger.getLogger("");

	ApiClient api = new ApiClient("BBFF-7602977eea40738bd2e69bfd271747a80e0");
	DataSource datasource = api.getDataSource("5e7551bf1d847215cca167e7");
	Variable tempsensor = api.getVariable("5e75524f1d8472176cbd55b3");

	public UbidotsApiConnector() {
		super();
	}

	/*
	 * Publish method publishes the values to the sensor variable to the cloud
	 */
	public void publish(float min, float max) {
		float randomvalue = generateRandomvalue(min, max);
		tempsensor.saveValue(randomvalue);
		log.info("Temperature Reading sent to Ubidots via API : " + Float.toString(randomvalue));
	}

	/*
	 * Method to generate my random float numbers for the simulation purpose
	 */
	public float generateRandomvalue(float min, float max) {
		Random r = new Random();
		float random = min + r.nextFloat() * (max - min);
		return random;
	}

}