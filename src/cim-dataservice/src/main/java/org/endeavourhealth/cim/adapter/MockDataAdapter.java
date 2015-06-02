package org.endeavourhealth.cim.adapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.endeavourhealth.cim.common.IDataAdapter;

import java.io.*;
import java.util.UUID;

public class MockDataAdapter implements IDataAdapter {
    public String getPatientByPatientId(UUID patientId) {
        StringBuilder sb = new StringBuilder();
        try {
        InputStream is = getClass().getClassLoader().getResourceAsStream("GetPatientResponse.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            return e.getMessage();
        }

        return sb.toString();
    }

    public String getPatientByNHSNumber(String nhsNumber) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            // TODO: Implement correct comms to mock CMS
            HttpGet request = new HttpGet("http://localhost:8081/GetEMISPatientByNhsNumber/");

            HttpResponse response = client.execute(request);

            StringBuilder responseData = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = rd.readLine()) != null) {
                responseData.append(line);
            }
            return responseData.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createCondition(String requestData) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            // TODO: Implement correct comms to mock CMS
            HttpPost request = new HttpPost("http://localhost:8081/AddEMISProblem/");
            HttpEntity entity = new ByteArrayEntity(requestData.getBytes("UTF-8"));
            request.setEntity(entity);

            HttpResponse response = client.execute(request);

            StringBuilder responseData = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = rd.readLine()) != null) {
                responseData.append(line);
            }
            return responseData.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
