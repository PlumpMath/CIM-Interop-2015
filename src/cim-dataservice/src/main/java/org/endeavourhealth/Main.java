package org.endeavourhealth;

import org.endeavourhealth.common.repository.ehr.data.ConditionRepository;
import org.endeavourhealth.common.repository.ehr.model.Condition;
import org.endeavourhealth.core.repository.common.data.DbClient;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.*;

/**
 * Created by darren on 28/10/15.
 */
public class Main {

    public static void main (String[] args) {
        try {


            /* add a new condition for a patient */

            UUID patientId = UUID.fromString("11af0e7f-be18-431e-9be9-fd1adb2f0742");
            UUID conditionId = UUID.randomUUID();
            UUID serviceId = UUID.fromString("8484e105-332e-4b1a-9614-406c7c84662b");
            Date effectiveDateTime = new Date();
            Map<String, String> metaData = new HashMap<String, String>();
            metaData.put("code","82272006");
            metaData.put("status", "past");
            metaData.put("severity", "Moderate");
            metaData.put("resource_version", "1.0.1");
            String entryData = "{testing}";
            Date lastUpdated = new Date();

            ConditionRepository conditionRepository = new ConditionRepository();

            Condition addCondition = new Condition();
            addCondition.setConditionId(conditionId);
            addCondition.setPatientId(patientId);
            addCondition.setServiceId(serviceId);
            addCondition.setEffectiveDateTime(effectiveDateTime);
            addCondition.setMetaData(metaData);
            addCondition.setEntryData(entryData);
            addCondition.setLastUpdated(lastUpdated);

            conditionRepository.add(addCondition);

            /* retrieve all resources for a patient */

            List<Condition> conditions = conditionRepository.getConditionsByPatientId(patientId);

            for (Condition condition : conditions) {
                System.out.println(condition.getConditionId());
                System.out.println(condition.getEntryData());

            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        } finally {
            DbClient.getInstance().close();
        }
    }

}
