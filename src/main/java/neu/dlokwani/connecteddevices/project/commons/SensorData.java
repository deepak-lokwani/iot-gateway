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
 * Created on: 08-Apr-2020
 * 
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


public class SensorData implements Serializable{

	/**
	 * 
	 * 
	 * This class is used to store and manuver the sensor data 
	 * received and sent from Constrained devices this class contains
	 * the  information on all the sensor value and its derivatives  
	 *  
	 */
	private String timeStamp = null;
	private String name = null;
	private float curValue = 0.0f;
	private float avgValue = 0.0f;
	private float minValue = Float.MAX_VALUE;
	private float maxValue = 0.0f;
	private float totValue = 0.0f;
	private int sampleCount = 0;
	private boolean pirValue = false;
	private boolean USRepeatedValueFlag = false;
	private boolean USLowValueFlag = false;
	
	
	public SensorData()
	{
		super();
		
	}
	
	/*
	 * Update the timestamp
	 */
	public void updateTimeStamp() {
		LocalDate ld = LocalDate.now();
		LocalTime lt = LocalTime.now();
	}
	
	/*
	 * Add the new value  to the sensorData database
	 */
	public void addValue(float val)
	{
		updateTimeStamp();
		++this.sampleCount;
		this.curValue = val;
		this.totValue += val;
		if (this.curValue < this.minValue) {
		this.minValue = this.curValue;
		}
		if (this.curValue > this.maxValue) {
		this.maxValue = this.curValue;
		}
		if (this.totValue != 0 && this.sampleCount > 0) {
		this.avgValue = this.totValue / this.sampleCount;
	}
 }
	/*
	 * Add Pir Values
	 */
	public void addPIRValue(boolean bool) {
		this.pirValue = bool;
		updateTimeStamp();
		++this.sampleCount;
	}
	
	/*
	 * Get the SD string
	 */
	public String getSensorData() {

		String outputScreen ;
		outputScreen = name + "\n\tCurrent US Distance Value: " + curValue + "\n\tAverage Value: "
		+ avgValue + "\n\tMin Value: " + minValue + "\n\tMax Value: " + maxValue +  "\n\tSampleCount: " + sampleCount;
		return outputScreen;
		
	}
	
	/*
	 * Set the flags
	 */
	public void setUSRepeatedValueFlag(boolean uSRepeatedValueFlag) {
		USRepeatedValueFlag = uSRepeatedValueFlag;
	}
	
	/*
	 * Set the low flag
	 */
	public void setUSLowValueFlag(boolean uSLowValueFlag) {
		USLowValueFlag = uSLowValueFlag;
	}
	
	/*
	 * Get timestamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}
	public boolean isPirValue() {
		return pirValue;
	}
	public boolean isUSRepeatedValueFlag() {
		return USRepeatedValueFlag;
	}
	public boolean isUSLowValueFlag() {
		return USLowValueFlag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getCurValue() {
		return curValue;
	}
	public float getAvgValue() {
		return avgValue;
	}
	public float getMinValue() {
		return minValue;
	}
	public float getMaxValue() {
		return maxValue;
	}
	public float getTotValue() {
		return totValue;
	}
	public int getSampleCount() {
		return sampleCount;
	}
}
