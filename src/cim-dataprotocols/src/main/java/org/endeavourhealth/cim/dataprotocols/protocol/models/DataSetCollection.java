package org.endeavourhealth.cim.dataprotocols.protocol.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(value = { "searchTerms" })
public class DataSetCollection {
    public static final String SCHEMA_VERSION = "1.0";

    private UUID id;
    private String name;
    private String description;
    private List<String> searchTerms;
    private List<UUID> dataSets;

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

    public List<String> getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }

    public List<UUID> getDataSets() {
        return dataSets;
    }

    public void setDataSets(List<UUID> dataSets) {
        this.dataSets = dataSets;
    }
}
