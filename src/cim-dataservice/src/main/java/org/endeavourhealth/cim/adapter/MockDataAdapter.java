package org.endeavourhealth.cim.adapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:8081/GetEMISPatientByNhsNumber/");

        try {
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
