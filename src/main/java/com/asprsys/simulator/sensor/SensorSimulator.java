package com.asprsys.simulator.sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.asprsys.simulator.config.queue.Sender;

@Component
public class SensorSimulator implements Runnable {
	private static final String SENSORS [] =  {"TEMP_1","PRES_1","TEMP_2","PRES_2","TEMP_3","PRES_3"};
	@Value("${destination.temp.sensor.queue}")
	private String tempSensorQueue;
	@Value("${destination.pres.sensor.queue}")
	private String presSensorQueue;	
	
	@Autowired
	private Sender sender;
	private ExecutorService threadPool;

	public void run() 
	{
		try 
		{
			this.runSensorSimulation();
		} 
		catch (InterruptedException e) 
		{
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * For each vehicle, a thread is started which simulates a sensor data. 
	 * Continue to run until infinity. 
	 * @throws InterruptedException 
	 */

	public void runSensorSimulation() throws InterruptedException 
	{
		threadPool = Executors.newCachedThreadPool();		
		boolean stillRunning = true;
		while (stillRunning)
		{
			List<Callable<Object>> calls = new ArrayList<>();

			for(int i = 0; i < SENSORS.length; i++) {
				calls.add(getSensorObjectForQueue(SENSORS[i]));
			}
			threadPool.invokeAll(calls);
			if (threadPool.isShutdown())
			{
				stillRunning = false;
			}
		}
	}	
	
	private Sensor getSensorObjectForQueue(String sensorName) {
		Sensor sensor = new Sensor();
		sensor.setSensorName(sensorName);
		if(sensorName.startsWith("TEMP"))
			sensor.setDestinationName(tempSensorQueue);
		else if(sensorName.startsWith("PRES"))
			sensor.setDestinationName(presSensorQueue);
		sensor.setSender(sender);
		return sensor;
	}	

	public void finish() 
	{
		threadPool.shutdownNow();
	}


}
