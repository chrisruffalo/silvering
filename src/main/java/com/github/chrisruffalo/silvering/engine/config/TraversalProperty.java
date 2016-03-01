package com.github.chrisruffalo.silvering.engine.config;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class TraversalProperty<T> {

    /**
     * Visit the start node. If this is true then the starting node will be the first node visited. If this setting is false
     * then the first node visited will be the first child of the start node (if one exists).
     */
    public static TraversalProperty<Boolean> VISIT_STARTING_NODE = new TraversalProperty<>("silvering.visitStartNode", true);

    /**
     * Should the children of a node be sorted before access
     */
    public static TraversalProperty<Boolean> SORT_CHILDREN_BEFORE_VISIT = new TraversalProperty<>("silvering.sortChildren", false);

    /**
     * Concrete list of classes to include
     */
    public static TraversalProperty<Class<?>[]> INCLUDE_CLASSES = new TraversalProperty<>("silvering.includeClasses", null);

    /**
     * Concrete list of classes to exclude
     */
    public static TraversalProperty<Class<?>[]> EXCLUDE_CLASSES = new TraversalProperty<>("silvering.excludeClasses", null);

    // todo: match by pattern, exclude by pattern

    private final String key;

    private final T defaultValue;

    TraversalProperty(final String key, final T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    String getKey() {
        return this.key;
    }

    T getDefaultValue() {
        return defaultValue;
    }

    @SuppressWarnings(value = {"unchecked"})
    T as(Object value) {
        return (T)value;
    }
}
