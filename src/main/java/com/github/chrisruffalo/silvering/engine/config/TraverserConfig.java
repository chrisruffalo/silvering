package com.github.chrisruffalo.silvering.engine.config;

import java.util.HashMap;
import java.util.Objects;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class TraverserConfig {

    private final HashMap<String, Object> configurationValues;

    public TraverserConfig() {
        this.configurationValues = new HashMap<>();
    }

    public <T> T get(TraversalProperty<T> property) {
        if(!this.configurationValues.containsKey(property.getKey())) {
            return property.getDefaultValue();
        }

        final Object value = this.configurationValues.get(property.getKey());
        return property.as(value);
    }

    public <T> void set(final TraversalProperty<T> property, final T value) {
        this.configurationValues.put(property.getKey(), value);
    }

}
