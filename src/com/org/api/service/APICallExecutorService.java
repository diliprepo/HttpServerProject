package com.org.api.service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class APICallExecutorService {
	final static Logger logger = Logger.getLogger(APICallExecutorService.class);
	private static int threadNum = 0;
	 
	public static void main(String args[]) throws Exception {
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
			String url = "http://localhost:8080";
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(getRandomSleepTime()*1000);
				} catch (InterruptedException e1) {
					logger.error("Error:"+e1.getMessage());
				}
			Runnable worker = new MyRunnable(url);
			executor.execute(worker);
			}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
 
		}
		logger.info("\nFinished all threads");
	}
 
	public static class MyRunnable implements Runnable {
		private final String url;
 
		MyRunnable(String url) {
			this.url = url;
		}
 
		@Override
		public void run() {
 
			String result = "";
			int responseCode = 200;
			try {
				URL siteURL = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) siteURL
						.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				threadNum ++;
				responseCode = connection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(new InputStreamReader(
							connection.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					// print result
					logger.info("Thread"+threadNum+":"+response.toString());
				} else {
					logger.info("API GET request not worked");
				}
			} catch (Exception e) {
				logger.error("Error response:"+e.getMessage());
			}
		}
	}
	
	private static int getRandomSleepTime() {
		Random randomGenerator = new Random();
		int randomSleepTime = 1;
		randomSleepTime = randomGenerator.nextInt(5);
		return randomSleepTime;
	}
}
