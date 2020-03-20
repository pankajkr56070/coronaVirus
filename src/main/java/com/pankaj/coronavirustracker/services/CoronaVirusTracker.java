package com.pankaj.coronavirustracker.services;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

@Service
public class CoronaVirusTracker {

    private  static String COVID_CONFIRMED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/archived_data/archived_time_series/time_series_2019-ncov-Confirmed.csv";
    private  static String COVID_DEATH_URL =    "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";

    @PostConstruct
    public void fetchConfirmedCases() throws IOException {
        OkHttpClient client = new OkHttpClient();
        URL url = new URL(COVID_CONFIRMED_URL);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        StringReader csvBodyReader = new StringReader(response.body().string());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        int totalConfirmedCases = 0;
        for (CSVRecord record : records) {
            System.out.print(record.get("Province/State") + " ");
            System.out.print(record.get("Country/Region") + " ");
            System.out.println(record.get(record.size() - 1) + " ");
            totalConfirmedCases += Integer.parseInt(record.get(record.size() - 1));
        }
        System.out.println("TOTAL CONFIRMED CASES: " + totalConfirmedCases);
    }



    @PostConstruct
    public void fetchDeadCases() throws IOException {
        OkHttpClient clientDeath = new OkHttpClient();
        URL url = new URL(COVID_DEATH_URL);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = clientDeath.newCall(request).execute();
        StringReader csvBodyReader = new StringReader(response.body().string());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        int totalDeathCases = 0;
        for (CSVRecord record : records) {
            System.out.print(record.get("Province/State") + " ");
            System.out.print(record.get("Country/Region") + " ");
            System.out.println(record.get(record.size() - 1) + " ");
            totalDeathCases += Integer.parseInt(record.get(record.size() - 1));
        }
        System.out.println("TOTAL DEATH CASES: " + totalDeathCases);
    }


}

