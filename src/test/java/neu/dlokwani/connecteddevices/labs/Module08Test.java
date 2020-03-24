/**
 * 
 */
package neu.dlokwani.connecteddevices.labs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.dlokwani.connecteddevices.labs.module08.UbidotsApiConnector;
import neu.dlokwani.connecteddevices.labs.module08.UbidotsClientConnector;

/**
 * Test class for all requisite Module08 functionality.
 * 
 * Instructions:
 * 1) Rename 'testSomething()' method such that 'Something' is specific to your needs; add others as needed, beginning each method with 'test...()'.
 * 2) Add the '@Test' annotation to each new 'test...()' method you add.
 * 3) Import the relevant modules and classes to support your tests.
 * 4) Run this class as unit test app.
 * 5) Include a screen shot of the report when you submit your assignment.
 * 
 * Please note: While some example test cases may be provided, you must write your own for the class.
 */
public class Module08Test
{
	// setup methods
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	// test methods
	
	/**
	 * 
	 */
	@Test
	public void testUbidotsApiConnector()
	{
		float testTemp = 15;
		
		UbidotsApiConnector api = new UbidotsApiConnector();
		api.publish(testTemp, testTemp);
		

	}
	
	@Test
	public void testUbidotsClientconnector() 
	{
		String testToken = "BBFF-VMZ1BQxYxH2IZzPLU5o8mkcCv0sDwa";
		
		UbidotsClientConnector ubi = new UbidotsClientConnector("industrial.api.ubidots.com",
				testToken,
				"C:\\Users\\deepa\\git\\workspace\\ubidots_cert.pem");
		ubi.ubidots_mqtt_connect();
		ubi.ubidots_mqtt_subscribe();
		
	}
	
	@Test
	public void testMqttPublishToConstrainedDevice()
	{
		int i= 10;
		while(i!=0)
		{
		String testData = "test Data";
		byte[] testPayload = testData.getBytes();
		UbidotsClientConnector mqtt = new UbidotsClientConnector();
		mqtt.ubidots_mqtt_connect();
		mqtt.publishMessage("Mqtt_Test", 2, testPayload);
		i--;
		}
	}
	
}
