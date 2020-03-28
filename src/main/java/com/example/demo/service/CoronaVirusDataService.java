package com.example.demo.service;

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
import org.thymeleaf.util.StringUtils;

import com.example.demo.model.LocationStats;

@Service
public class CoronaVirusDataService {
	
	//private static String CORONA_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private static String CORONA_DATA_URL ="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	private List<LocationStats> allStats = new ArrayList<LocationStats>();
	
	@PostConstruct
	@Scheduled(cron="* * 1 * * *")
	private void fetchVirusData() throws IOException, InterruptedException {
		List<LocationStats> newStats = new ArrayList<LocationStats>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(CORONA_DATA_URL))
				.build();
		
		HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		//System.out.println(httpResponse.body());
		
		StringReader reader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {
			LocationStats locationStat = new LocationStats();
			locationStat.setState(record.get("Province/State"));
			locationStat.setCountry(record.get("Country/Region"));
			
			int latestCases = Integer.parseInt(record.get(record.size()-1));
			int previousDayCases = Integer.parseInt(record.get(record.size()-2));
			
			/*if(!StringUtils.isEmpty(Integer.parseInt(latestCases))){
				locationStat.setLatestCaseTotal(Integer.parseInt(record.get(record.size()-1)));	
			}*/
			locationStat.setLatestCaseTotal(latestCases);
			locationStat.setDiffFromPrevDay(latestCases-previousDayCases);	
			
		   // String name = record.get("Name");
			//System.out.println(locationStat);
			newStats.add(locationStat);
		}
		
		this.allStats = newStats;
		      
	}

	public List<LocationStats> getAllStats() {
		return allStats;
	}

	public void setAllStats(List<LocationStats> allStats) {
		this.allStats = allStats;
	}
	
	
	
	
}
