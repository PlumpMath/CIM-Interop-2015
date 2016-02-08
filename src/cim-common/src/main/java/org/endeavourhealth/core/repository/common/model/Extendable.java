package org.endeavourhealth.core.repository.common.model;

import org.endeavourhealth.core.utils.StreamExtension;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Additional content defined by data providers that is not currently modeled in the structured format
 */
public abstract class Extendable implements Serializable {
    protected List<Extension> extensions;

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public Extension getExtension(String key) {
        if (extensions == null)
            return null;

        return extensions
                .stream()
                .filter(e -> e.getKey().equals(key))
                .collect(StreamExtension.singleOrNullCollector());
    }

    public Extension addExtension(String key, String value) {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }

        Extension newExtension = new Extension();
        newExtension.setKey(key);
        newExtension.setValue(value);
        extensions.add(newExtension);

        return newExtension;
    }

    public void removeExtension(String key) {
        if (extensions == null) {
            return;
        }

        Extension extensionToRemove = getExtension(key);
        if (extensionToRemove != null) {
            extensions.remove(extensionToRemove);
        }
    }
}
