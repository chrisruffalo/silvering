package com.github.chrisruffalo.silvering.engine.config;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class PropertyGraphTraversalProperty<T> extends TraversalProperty<T> {

    /**
     * Access only Java "bean" properties.
     */
    public static final int PROPERTY = 1;
    /**
     * Access all methods.
     */
    public static final int METHOD = 2;
    /**
     * Access all fields.
     */
    public static final int FIELD = 4;

    /**
     * How the contents of an object are visited. Allows the traversal configuration determine how
     * the object should be accessed. This gives a rich model for controlling the way that properties/fields are
     * accessed without constraining the user too much.
     */
    public static PropertyGraphTraversalProperty<Integer> OBJECT_ACCESS = new PropertyGraphTraversalProperty<>("silvering.graph.access", PROPERTY);

    /**
     * Determines if the behavior of the traverser is to access private components. If this is set to false then no attempt will be made to access private
     * class components. If the option is true then the traverser will visit private components.
     */
    public static PropertyGraphTraversalProperty<Boolean> ACCESS_PRIVATE = new PropertyGraphTraversalProperty<>("silvering.graph.accessPrivate", false);

    /**
     * Determines if the behavior of the traverser is to access protected components. If this is set to false then no attempt will be made to access protected
     * class components. If the option is true then the traverser will visit protected components.
     */
    public static PropertyGraphTraversalProperty<Boolean> ACCESS_PROTECTED = new PropertyGraphTraversalProperty<>("silvering.graph.accessProtected", false);

    /**
     * Determines if the behavior of the traverser is to access public components. If this is set to false then no attempt will be made to access public
     * class components. If the option is true then the traverser will visit public components.
     */
    public static PropertyGraphTraversalProperty<Boolean> ACCESS_PUBLIC = new PropertyGraphTraversalProperty<>("silvering.graph.accessPublic", true);

    /**
     * If this value is set to 'true' then an error will be thrown when an access error is encountered. If it is false (the default behavior) the offending
     * access will be skipped and the traverser will continue to visit other components.
     */
    public static PropertyGraphTraversalProperty<Boolean> THROW_ERROR_ON_ACCESS_FAILURE = new PropertyGraphTraversalProperty<>("silvering.graph.throwError", false);

    private PropertyGraphTraversalProperty(final String key, final T defaultValue) {
        super(key, defaultValue);
    }
}
