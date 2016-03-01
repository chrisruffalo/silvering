package com.github.chrisruffalo.silvering.visitor;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class NavigationControlVisitor extends CollectingClassHierarchyVisitor {

    private VisitResult response;

    public NavigationControlVisitor(final VisitResult response) {
        this.response = response;
    }

    @Override
    public VisitResult visit(Class<?> instance) {
        super.visit(instance);
        return response;
    }
}
