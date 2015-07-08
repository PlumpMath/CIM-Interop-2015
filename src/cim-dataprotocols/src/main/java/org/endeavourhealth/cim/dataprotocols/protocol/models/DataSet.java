package org.endeavourhealth.cim.dataprotocols.protocol.models;

import org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.*;

import java.util.List;
import java.util.UUID;

/**
 * A predefined subset of data
 */
public class DataSet {
    public static final String SCHEMA_VERSION = "1.0";

    private UUID id;
    private String name;
    private String description;
    private List<ObservationCondition> observationConditions;
    private List<ProblemCondition> problemConditions;
    private List<IssueCondition> issueConditions;
    private List<EncounterCondition> encounterConditions;

    // TODO: DataSet: Cohort

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IssueCondition> getIssueConditions() {
        return issueConditions;
    }

    public void setIssueConditions(List<IssueCondition> issueConditions) {
        this.issueConditions = issueConditions;
    }

    public List<ObservationCondition> getObservationConditions() {
        return observationConditions;
    }

    public void setObservationConditions(List<ObservationCondition> observationConditions) {
        this.observationConditions = observationConditions;
    }

    public List<EncounterCondition> getEncounterConditions() {
        return encounterConditions;
    }

    public void setEncounterConditions(List<EncounterCondition> encounterConditions) {
        this.encounterConditions = encounterConditions;
    }

    public List<ProblemCondition> getProblemConditions() {
        return problemConditions;
    }

    public void setProblemConditions(List<ProblemCondition> problemConditions) {
        this.problemConditions = problemConditions;
    }
}
