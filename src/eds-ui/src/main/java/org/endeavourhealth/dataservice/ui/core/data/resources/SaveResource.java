package org.endeavourhealth.dataservice.ui.core.data.resources;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.endeavourhealth.common.repository.ehr.data.AllergyIntoleranceRepository;
import org.endeavourhealth.common.repository.ehr.data.ConditionRepository;
import org.endeavourhealth.common.repository.ehr.data.EncounterRepository;
import org.endeavourhealth.common.repository.ehr.data.ObservationRepository;
import org.endeavourhealth.common.repository.ehr.model.AllergyIntolerance;
import org.endeavourhealth.common.repository.ehr.model.Condition;
import org.endeavourhealth.common.repository.ehr.model.Encounter;
import org.endeavourhealth.common.repository.ehr.model.Observation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Servlet implementation class SaveCondition
 */

@WebServlet(name = "SaveResource", urlPatterns = {"/Resources/SaveResource", "/Resources/SaveResource.do"})
@MultipartConfig

public class SaveResource extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SaveResource() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String json = br.readLine();

            String resourceType = "";

            if (request.getParameter("resource_type")!=null)
                resourceType =  (String) request.getParameter("resource_type");


            ObjectMapper mapper = new ObjectMapper();

            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "sheaves-dev")
                    .put("node.name", "sheaves-node-1")
                    .put("network.host", "127.0.0.1")
                    .put("transport.tcp.port", "9300")
                    .put("client.transport.sniff", true)
                    .build();

            Client client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            String entryData = "";

            switch (resourceType) {
                case "allergy_intolerance":
                    AllergyIntolerance addAllergyIntolerance = mapper.readValue(json, AllergyIntolerance.class);
                    AllergyIntoleranceRepository allergyIntoleranceRepository = new AllergyIntoleranceRepository();
                    allergyIntoleranceRepository.add(addAllergyIntolerance);

                    entryData =
                            "{\"allergy_intolerance_id\":\""+addAllergyIntolerance.getAllergyIntoleranceId()+"\","+
                                    "\"patient_id\":\""+addAllergyIntolerance.getPatientId().toString()+"\","+
                                    "\"service_id\":\""+addAllergyIntolerance.getServiceId().toString()+"\","+
                                    "\"effective_datetime\":\""+new DateTime( addAllergyIntolerance.getEffectiveDateTime(), DateTimeZone.UTC )+"\","+
                                    "\"last_updated\":\""+new DateTime( addAllergyIntolerance.getLastUpdated(), DateTimeZone.UTC )+"\","+
                                    addAllergyIntolerance.getEntryData().substring(1);

                    indexDocument(client, "ehr", "allergy_intolerance", addAllergyIntolerance.getAllergyIntoleranceId().toString(),
                            entryData
                    );
                    break;
                case "condition":
                    Condition addCondition = mapper.readValue(json, Condition.class);
                    ConditionRepository conditionRepository = new ConditionRepository();
                    conditionRepository.add(addCondition);

                    entryData =
                            "{\"condition_id\":\""+addCondition.getConditionId()+"\","+
                                    "\"patient_id\":\""+addCondition.getPatientId().toString()+"\","+
                                    "\"service_id\":\""+addCondition.getServiceId().toString()+"\","+
                                    "\"effective_datetime\":\""+new DateTime( addCondition.getEffectiveDateTime(), DateTimeZone.UTC )+"\","+
                                    "\"last_updated\":\""+new DateTime( addCondition.getLastUpdated(), DateTimeZone.UTC )+"\","+
                                    addCondition.getEntryData().substring(1);

                    indexDocument(client, "ehr", "condition", addCondition.getConditionId().toString(),
                            entryData
                    );
                    break;
                case "observation":
                    Observation addObservation = mapper.readValue(json, Observation.class);
                    ObservationRepository observationRepository = new ObservationRepository();
                    observationRepository.add(addObservation);

                    entryData =
                            "{\"observation_id\":\""+addObservation.getObservationId()+"\","+
                                    "\"patient_id\":\""+addObservation.getPatientId().toString()+"\","+
                                    "\"service_id\":\""+addObservation.getServiceId().toString()+"\","+
                                    "\"effective_datetime\":\""+new DateTime( addObservation.getEffectiveDateTime(), DateTimeZone.UTC )+"\","+
                                    "\"last_updated\":\""+new DateTime( addObservation.getLastUpdated(), DateTimeZone.UTC )+"\","+
                                    addObservation.getEntryData().substring(1);

                    indexDocument(client, "ehr", "observation", addObservation.getObservationId().toString(),
                            entryData
                    );
                    break;
                case "encounter":
                    Encounter addEncounter = mapper.readValue(json, Encounter.class);
                    EncounterRepository encounterRepository = new EncounterRepository();
                    encounterRepository.add(addEncounter);

                    entryData =
                            "{\"encounter_id\":\""+addEncounter.getEncounterId()+"\","+
                                    "\"patient_id\":\""+addEncounter.getPatientId().toString()+"\","+
                                    "\"service_id\":\""+addEncounter.getServiceId().toString()+"\","+
                                    "\"effective_datetime\":\""+new DateTime( addEncounter.getEffectiveDateTime(), DateTimeZone.UTC )+"\","+
                                    "\"last_updated\":\""+new DateTime( addEncounter.getLastUpdated(), DateTimeZone.UTC )+"\","+
                                    addEncounter.getEntryData().substring(1);

                    indexDocument(client, "ehr", "encounter", addEncounter.getEncounterId().toString(),
                            entryData
                    );
                    break;
            }

            client.close();

        }
        catch (Exception e) {
            e.printStackTrace(writer);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        writer.close();

    }

    public static void indexDocument(Client client, String index, String type, String id,
                                     String entryData) {
        try {
            IndexResponse response = client.prepareIndex(index, type, id)
                    .setSource(entryData)
                    .get();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
