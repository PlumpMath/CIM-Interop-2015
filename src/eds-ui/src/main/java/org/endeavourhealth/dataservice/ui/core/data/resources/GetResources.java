package org.endeavourhealth.dataservice.ui.core.data.resources;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.endeavourhealth.common.repository.ehr.data.AllergyIntoleranceRepository;
import org.endeavourhealth.common.repository.ehr.data.ConditionRepository;
import org.endeavourhealth.common.repository.ehr.data.EncounterRepository;
import org.endeavourhealth.common.repository.ehr.data.ObservationRepository;
import org.endeavourhealth.common.repository.ehr.model.AllergyIntolerance;
import org.endeavourhealth.common.repository.ehr.model.Condition;
import org.endeavourhealth.common.repository.ehr.model.Encounter;
import org.endeavourhealth.common.repository.ehr.model.Observation;


/**
 * Servlet implementation class GetResources
 */

@WebServlet(name = "GetResources", urlPatterns = {"/Resources/GetResources", "/Resources/GetResources.do"})
@MultipartConfig

public class GetResources extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GetResources() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        try {
            UUID patientId = UUID.fromString("11af0e7f-be18-431e-9be9-fd1adb2f0742");

            String resourceType = "";
            if (request.getParameter("resource_type")!=null)
                resourceType =  (String) request.getParameter("resource_type");

            String serviceId = "";
            if (request.getParameter("service_id")!=null)
                serviceId =  (String) request.getParameter("service_id");

            String status = "";
            if (request.getParameter("status")!=null)
                status =  (String) request.getParameter("status");

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = "";

            switch (resourceType) {
                case "allergy_intolerance":
                    AllergyIntoleranceRepository allergyIntoleranceRepository = new AllergyIntoleranceRepository();
                    List<AllergyIntolerance> allergyIntoleranceResources = null;
                    if (!serviceId.equals(""))
                        allergyIntoleranceResources = allergyIntoleranceRepository.getAllergyIntolerancesByPatientServiceId(patientId,UUID.fromString(serviceId));
                    else
                        allergyIntoleranceResources = allergyIntoleranceRepository.getAllergyIntolerancesByPatientId(patientId);

                    json = ow.writeValueAsString(allergyIntoleranceResources);
                    break;
                case "condition":
                    ConditionRepository conditionRepository = new ConditionRepository();
                    List<Condition> conditionResources = null;
                    if (!serviceId.equals(""))
                        conditionResources = conditionRepository.getConditionsByPatientServiceId(patientId,UUID.fromString(serviceId));
                    else
                    if (!status.equals(""))
                        conditionResources = conditionRepository.getConditionsByPatientStatus(patientId,status);
                    else
                        conditionResources = conditionRepository.getConditionsByPatientId(patientId);
                    json = ow.writeValueAsString(conditionResources);
                    break;
                case "observation":
                    ObservationRepository observationRepository = new ObservationRepository();
                    List<Observation> observationResources = null;
                    if (!serviceId.equals(""))
                        observationResources = observationRepository.getObservationsByPatientServiceId(patientId, UUID.fromString(serviceId));
                    else
                        observationResources = observationRepository.getObservationsByPatientId(patientId);
                    json = ow.writeValueAsString(observationResources);
                    break;
                case "encounter":
                    EncounterRepository encounterRepository = new EncounterRepository();
                    List<Encounter> encounterResources = null;
                    if (!serviceId.equals(""))
                        encounterResources = encounterRepository.getEncountersByPatientServiceId(patientId, UUID.fromString(serviceId));
                    else
                        encounterResources = encounterRepository.getEncountersByPatientId(patientId);
                    json = ow.writeValueAsString(encounterResources);
                    break;
            }

            writer.println(json);

        }
        catch (Exception e) {
            e.printStackTrace(writer);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        writer.close();
    }
}
