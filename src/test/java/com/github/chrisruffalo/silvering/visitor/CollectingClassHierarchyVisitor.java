package com.github.chrisruffalo.silvering.visitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
class CollectingClassHierarchyVisitor implements ClassHierarchyVisitor {
    private Set<Class<?>> visited;

    CollectingClassHierarchyVisitor() {
        this.visited = new HashSet<Class<?>>();
    }

    public VisitResult visit(Class<?> instance) {
        this.visited.add(instance);
        return VisitResult.CONTINUE;
    }

    Set<Class<?>> getVisited() {
        return Collections.unmodifiableSet(this.visited);
    }
}
