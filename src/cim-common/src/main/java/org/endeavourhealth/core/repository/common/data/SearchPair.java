package org.endeavourhealth.core.repository.common.data;

public class SearchPair {
    private final String searchTerm;
    private final String termType;

    public SearchPair(String termType, String searchTerm) {
        this.termType = termType;
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getTermType() {
        return termType;
    }
}
