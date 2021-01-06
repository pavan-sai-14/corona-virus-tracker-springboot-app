package com.pavanreddy.corona_virus_tracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pavanreddy.corona_virus_tracker.model.LocationStats;

@Service
public class CoronaVirusDataService {
	
	private List<LocationStats> allStats=new ArrayList<LocationStats>();
	
	private final String url="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	@PostConstruct  //when the class instance is created this function will execute 
	@Scheduled(cron = "* * 1 * * *") //updating information daily
	public void fetchCoronaVirusData() throws IOException, InterruptedException
	{
		List<LocationStats> newStats=new ArrayList<LocationStats>();
		
		//process for fetching data from client side
		HttpClient httpClient=HttpClient.newHttpClient();
		
		//sending request 
		HttpRequest httpRequest=HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
		
		//this will send me RESPONSE
		HttpResponse<String> httpResponse=httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
		
		StringReader reader=new StringReader(httpResponse.body());
		
		//converting comma separated String data into separate fields by HEADERS 
		//RFC4180 removes empty spaces,if DEFAULT is used it will print null value also 
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		
		for (CSVRecord record : records) {
			LocationStats stats=new LocationStats();
		    stats.setState(record.get("Province/State")); 
		    stats.setCountry(record.get("Country/Region"));
		    int latest=Integer.parseInt(record.get(record.size()-1));
		    int privous=Integer.parseInt(record.get(record.size()-2));
		    stats.setTotalReportedCase(latest);
		    stats.setLatestReportedCase(latest-privous);
		    newStats.add(stats);
		}
		this.allStats.addAll(newStats);
	}

	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
}
