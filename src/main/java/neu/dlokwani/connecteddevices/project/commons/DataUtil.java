/**
 * 
 */
package neu.dlokwani.connecteddevices.project.commons;

/**
 * @author Deepak_Lokwani
 * 
 * NUID: 001316769
 * 
 * Project name: iot-gateway
 * Package name: neu.dlokwani.connecteddevices.project.commons
 * Created on: 09-Apr-2020
 * 
 */
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;

public class DataUtil {

	/**
	 * This class is used to process manipulate the sensorData received and being
	 * sent.vert the json data to the sensordata object and vice-versa using the
	 * GSON libraries.
	 * 
	 */

	private static FileWriter file;

	/*
	 * Method to convert SensorData to Json Data
	 */
	public String toJsonFromSensorData(SensorData sensorData) {

		String jsonData = null;
		if (sensorData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(sensorData);
		}
		return jsonData;
	}

	/*
	 * Method to convert from Json to SensorData
	 */
	public SensorData toSensorDataFromJson(String jsonData) {
		SensorData sensorData = null;
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			sensorData = gson.fromJson(jsonData, SensorData.class);
		}
		return sensorData;
	}

	/*
	 * Method to convert SensorData to a file
	 */
	public boolean writeSensorDatatoFile(SensorData sensorData) {
		String jsonData = null;
		if (sensorData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(sensorData);
		}
		try {
			file = new FileWriter("json_SensorData.txt");
			file.append(jsonData);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
