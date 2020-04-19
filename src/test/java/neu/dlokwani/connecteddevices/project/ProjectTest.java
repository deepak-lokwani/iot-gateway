/**
 * 
 */
package neu.dlokwani.connecteddevices.project;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.dlokwani.connecteddevices.labs.module02.SMTPClientConnector;
import neu.dlokwani.connecteddevices.project.protocols.MQTTClientConnector;
import neu.dlokwani.connecteddevices.project.protocols.UbidotsApiConnector;
import neu.dlokwani.connecteddevices.project.protocols.UbidotsClientConnector;

/**
 * Test class for all requisite Project functionality.
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
public class ProjectTest
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
	
	/**
	 * Unit Test Method to check the functioning of Ubidots connect method with Test
	 * Token
	 */
	@Test
	public void testUbidots_connect() {
		UbidotsClientConnector ubi = new UbidotsClientConnector("industrial.api.ubidots.com",
				"BBFF-yegKC0ObS7wjfGO8Bx2IU53hjRv9il", "C:\\Users\\deepa\\git\\workspace\\ubidots_cert.pem");
		assertTrue(ubi.ubidots_mqtt_connect());
	}

	/**
	 * Unit Test Method to check the functioning of subscribe method with Test Token
	 */
	@Test
	public void testUbidots_Subscriber() {
		MQTTClientConnector mqtt = new MQTTClientConnector();
		mqtt.mqtt_connect();
		assertTrue(mqtt.mqtt_subscribe("USFromSensor"));
		assertTrue(mqtt.mqtt_subscribe("PIRFromSensor"));
	}

	/**
	 * Unit Test Method to check the functioning of publish method using Ubidots Api
	 */
	@Test
	public void testUbidots_api() {
		UbidotsApiConnector api = new UbidotsApiConnector();
		assertTrue(api.publishCpuUtilToUbidots(45.5f));
	}
	@Test
	public void testSMTP()
	{
		SMTPClientConnector smtp = new SMTPClientConnector();
		String s = "Test Mail";
		smtp.publishMessage("Unit Test", s.getBytes());
	}
	
	@Test
	public void testSystemCpuUtilTask() {
		SystemCpuUtilTask cpuUtil = new SystemCpuUtilTask();
		assertTrue("The CPU Utilization is below 0%: 		", 	cpuUtil.getDataFromSensor()	>=  0.0	);
		assertTrue("The CPU Utilization is above 100%: 		", 	cpuUtil.getDataFromSensor()	<= 	100.0);
	}
	
	@Test
	public void testSystemMemUtilTask() {
		SystemMemUtilTask memUtil = new SystemMemUtilTask();
		assertTrue("The Memory Utilization is below 0%: 	", memUtil.getDataFromSensor() 	>= 	0.0);
		assertTrue("The Memory Utilization is above 100%: 	", memUtil.getDataFromSensor() 	<= 	100.0);
	}
	
}
