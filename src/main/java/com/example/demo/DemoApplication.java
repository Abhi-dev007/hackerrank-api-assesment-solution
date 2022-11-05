package com.example.demo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class DemoApplication {

	public static int avgPulseRate(String diagnosisName, int doctorId){
		int tot_pulse = 0;
		int cnt = 0;
		int page = 1;
		int tot_pages = 1;
		while(page<=tot_pages) {
			try {

				URL url = new URL("https://jsonmock.hackerrank.com/api/medical_records?page="+Integer.toString(page));

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();

				//Getting the response code
				int responsecode = conn.getResponseCode();

				if (responsecode != 200) {
					throw new RuntimeException("HttpResponseCode: " + responsecode);
				} else {

					String inline = "";
					Scanner scanner = new Scanner(url.openStream());

					//Write all the JSON data into a string using a scanner
					while (scanner.hasNext()) {
						inline += scanner.nextLine();
					}

					//Close the scanner
					scanner.close();

					//Using the JSON simple library parse the string into a json object
					JSONParser parse = new JSONParser();
					JSONObject data_obj = (JSONObject) parse.parse(inline);

					tot_pages = Integer.parseInt(data_obj.get("total_pages").toString());
					JSONArray data = (JSONArray) data_obj.get("data");

					for (int i = 0; i < data.size(); i++) {

						JSONObject new_obj = (JSONObject) data.get(i);
						JSONObject diagnosis = (JSONObject) new_obj.get("diagnosis");
						JSONObject doctor = (JSONObject) new_obj.get("doctor");
						JSONObject vitals = (JSONObject) new_obj.get("vitals");
						String id = doctor.get("id").toString();

						if (diagnosis.get("name").equals(diagnosisName) && id.equals(Integer.toString(doctorId))) {
							cnt++;
							String pulse = vitals.get("pulse").toString();
							tot_pulse += Integer.parseInt(pulse);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			page++;
		}
		if(cnt==0){
			return 0;
		}
		else
			return tot_pulse/cnt;
	}

	public static void main(String[] args) {
		String diagnosisName = "Pulmonary embolism";
		int doctorId = 2;
		System.out.println(avgPulseRate(diagnosisName, doctorId));
	}



}
