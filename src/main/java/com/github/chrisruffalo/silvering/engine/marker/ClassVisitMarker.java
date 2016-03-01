package com.github.chrisruffalo.silvering.engine.marker;

import com.github.chrisruffalo.silvering.engine.config.TraverserConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class ClassVisitMarker implements VisitMarker<Class<?>> {

    private final TraverserConfig config;

    private Set<Class<?>> visitedClasses;

    public ClassVisitMarker(final TraverserConfig config) {
        this.config = config;
        this.visitedClasses = new HashSet<Class<?>>();
    }

    public boolean wasAlreadyVisited(Class<?> node) {
        return this.visitedClasses.contains(node);
    }

    public void markVisited(Class<?> node) {
        this.visitedClasses.add(node);
    }
}
