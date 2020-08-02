package com.ninhhoangcuong.coronavirustracker.service;

import com.ninhhoangcuong.coronavirustracker.model.LocationStats;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    @Autowired
    Environment environment;
    @Getter
    private List<LocationStats> confirmStats = new ArrayList<>();
    @Getter
    private List<LocationStats> recoveredStats = new ArrayList<>();
    @Getter
    private List<LocationStats> deathsStats = new ArrayList<>();
    @PostConstruct
    @Scheduled(cron = "0 0 12 * * ?")
    public void fetchVirusData() throws IOException, InterruptedException {
        this.confirmStats = getReport("VIRUS_DATA_URL");
        this.recoveredStats = getReport("RECOVERED_GLOBAL_URL");
        this.deathsStats = getReport("DEATHS_GLOBAL_URL");
    }
    private List<LocationStats> getReport(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(environment.getProperty(url)))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> csvRecords =  CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        return convertCsvApiToList(csvRecords);
    }

    private List<LocationStats> convertCsvApiToList(Iterable<CSVRecord> records) throws IOException {
        List<LocationStats> newStats = new ArrayList<>();
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get(0));
            locationStats.setCountry(record.get("Country/Region"));
            int csvRecordLatestTotalCase = getLatestTotalCase(record);
            int lastestTotalCase = Integer.parseInt(record.get(csvRecordLatestTotalCase));
            int prevDayCase = Integer.parseInt(record.get(csvRecordLatestTotalCase-1));
            locationStats.setLatestTotalCase(lastestTotalCase);
            locationStats.setDiffFromPrevDay(lastestTotalCase-prevDayCase);
            newStats.add(locationStats);
        }
        return newStats;
    }


    private int getLatestTotalCase(CSVRecord record){
        int csvRecord = record.size()-1;
        String valueLastest = record.get(csvRecord);
        //get lastest update
        while (csvRecord>0&&StringUtils.isEmpty(valueLastest)){
            csvRecord--;
            valueLastest = record.get(csvRecord);
        }
        return csvRecord;
    }
}
