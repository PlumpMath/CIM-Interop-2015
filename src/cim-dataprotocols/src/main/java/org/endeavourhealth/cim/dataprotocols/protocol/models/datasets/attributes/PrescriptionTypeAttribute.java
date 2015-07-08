package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.attributes;

public class PrescriptionTypeAttribute {
    private boolean acute;
    private boolean repeat;
    private boolean past;

    public boolean isAcute() {
        return acute;
    }

    public void setAcute(boolean acute) {
        this.acute = acute;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }
}
