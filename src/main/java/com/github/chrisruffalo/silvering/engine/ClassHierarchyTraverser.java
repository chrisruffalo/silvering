package com.github.chrisruffalo.silvering.engine;

import com.github.chrisruffalo.silvering.engine.config.ClassHierarchyTraversalProperty;
import com.github.chrisruffalo.silvering.engine.config.TraverserConfig;
import com.github.chrisruffalo.silvering.engine.marker.ClassVisitMarker;
import com.github.chrisruffalo.silvering.engine.marker.VisitMarker;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class ClassHierarchyTraverser extends AbstractTraverser<Class<?>> {

    public ClassHierarchyTraverser() {
    }

    public ClassHierarchyTraverser(final TraverserConfig config) {
        super(config);
    }

    protected VisitMarker<Class<?>> createMarker(final TraverserConfig config) {
        return new ClassVisitMarker(config);
    }

    protected List<Class<?>> childrenOf(Class<?> node) {
        // the list of children if any are present
        final List<Class<?>> children = new LinkedList<Class<?>>();

        // if traversing superclasses is enabled
        if(this.getConfig(ClassHierarchyTraversalProperty.TRAVERSE_SUPERCLASSES)) {
            // add parent class if it exists
            final Class<?> parent = node.getSuperclass();
            if (node.getSuperclass() != null) {
                if (this.getConfig(ClassHierarchyTraversalProperty.INCLUDE_OBJECT_CLASS) || !Object.class.equals(parent)) {
                    children.add(parent);
                }
            }
        }

        // if traversing interfaces is enabled
        if(this.getConfig(ClassHierarchyTraversalProperty.TRAVERSE_INTERFACES)) {
            if(this.getConfig(ClassHierarchyTraversalProperty.TRAVERSE_EXTENDED_INTERFACES) || !node.isInterface()) {
                // add parent interfaces if present
                final Class<?>[] interfaces = node.getInterfaces();
                // the value of .getInterfaces() should never be null
                // and this is only a small optimization... could easily
                // be a foreach with multiple `.add` statements.
                if (interfaces.length > 0) {
                    final List<Class<?>> interfaceList = Arrays.asList(interfaces);
                    children.addAll(interfaceList);
                }
            }
        }

        return children;
    }
}
