package org.endeavourhealth.cim.processor.subscription;

import java.util.UUID;

public class PollingSubscription {
    private final UUID subscriptionId;
    private final String odsCode;
    private final String message;

    public PollingSubscription(UUID subscriptionId, String odsCode, String message) {
        this.subscriptionId = subscriptionId;
        this.odsCode = odsCode;
        this.message = message;
    }
}
