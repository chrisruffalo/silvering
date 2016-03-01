package com.github.chrisruffalo.silvering.engine.config;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class ClassHierarchyTraversalProperty<T> extends TraversalProperty<T> {

    /**
     * Should the visitor traverse all the way to the root Object class or exclude it? If this value is 'true' then the visitor
     * will be given the opportunity to visit the {@link Object} class.
     */
    public static ClassHierarchyTraversalProperty<Boolean> INCLUDE_OBJECT_CLASS = new ClassHierarchyTraversalProperty<>("silvering.class.includeObject", false);

    /**
     * Instructs the traverser to traverse through superclasses of the object. If this value is false then the visitor will not be able
     * to visit any super classes of the starting node class.
     */
    public static ClassHierarchyTraversalProperty<Boolean> TRAVERSE_SUPERCLASSES = new ClassHierarchyTraversalProperty<>("silvering.class.traverseSuperclasses", true);

    /**
     * Instructs the traverser to traverse through interfaces that are implemented by the starting node. If this value is false then the visitor
     * will not visit any interface implemented by the starting node class.
     */
    public static ClassHierarchyTraversalProperty<Boolean> TRAVERSE_INTERFACES = new ClassHierarchyTraversalProperty<>("silvering.class.traverseInterfaces", true);

    /**
     * Instructs the traverser to traverse through the parent interfaces of implemented interfaces. If this value is fale then the visitor will
     * visit implemented interfaces but <em>not</em> the interfaces the implemented interfaces extend.
     */
    public static ClassHierarchyTraversalProperty<Boolean> TRAVERSE_EXTENDED_INTERFACES = new ClassHierarchyTraversalProperty<>("silvering.class.traverseExtendedInterfaces", true);

    private ClassHierarchyTraversalProperty(final String key, final T defaultValue) {
        super(key, defaultValue);
    }
}
