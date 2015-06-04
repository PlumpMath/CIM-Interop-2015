package org.endeavourhealth.cim.processor.subscription;

import java.util.UUID;

public class PollingSubscription {
    public final UUID subscriptionId;
    public final String odsCode;
    public final String message;

    public PollingSubscription(UUID subscriptionId, String odsCode, String message) {
        this.subscriptionId = subscriptionId;
        this.odsCode = odsCode;
        this.message = message;
    }
}
