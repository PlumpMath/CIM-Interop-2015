package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.attributes;

public class ProblemTypeAttribute {
    private boolean acute;
    private boolean significant;
    private boolean minor;

    public boolean isAcute() {
        return acute;
    }

    public void setAcute(boolean acute) {
        this.acute = acute;
    }

    public boolean isSignificant() {
        return significant;
    }

    public void setSignificant(boolean significant) {
        this.significant = significant;
    }

    public boolean isMinor() {
        return minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
    }
}
