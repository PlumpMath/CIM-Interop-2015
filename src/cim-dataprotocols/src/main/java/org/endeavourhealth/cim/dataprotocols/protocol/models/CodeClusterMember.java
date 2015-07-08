package org.endeavourhealth.cim.dataprotocols.protocol.models;

public class CodeClusterMember {
    private long codeId;
    private String term;
    private boolean includeChildren;

    public long getCodeId() { return codeId; }
    public void setCodeId(long codeId) { this.codeId = codeId; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public boolean isIncludeChildren() { return includeChildren; }
    public void setIncludeChildren(boolean includeChildren) { this.includeChildren = includeChildren; }
}
