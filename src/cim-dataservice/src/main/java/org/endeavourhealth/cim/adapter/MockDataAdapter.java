package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.common.IDataAdapter;

import java.io.*;
import java.util.UUID;

public class MockDataAdapter implements IDataAdapter {
    public String getPatient(UUID patientId) {
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
}
