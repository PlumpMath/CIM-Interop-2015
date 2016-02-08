package org.endeavourhealth.cim.transform.common;

import org.hl7.fhir.instance.model.Bundle;

public class BundleProperties {

    private Bundle.BundleType bundleType = Bundle.BundleType.SEARCHSET;
    private String bundleId;
    private String baseUri;

    public Bundle.BundleType getBundleType() {
        return bundleType;
    }

    public BundleProperties setBundleType(Bundle.BundleType bundleType) {
        this.bundleType = bundleType;
        return this;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public BundleProperties setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public String getBundleId() {
        return bundleId;
    }

    public BundleProperties setBundleId(String bundleId) {
        this.bundleId = bundleId;
        return this;
    }
}
