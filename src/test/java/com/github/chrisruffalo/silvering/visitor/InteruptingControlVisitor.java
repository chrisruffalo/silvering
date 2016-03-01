package com.github.chrisruffalo.silvering.visitor;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class InteruptingControlVisitor extends NavigationControlVisitor {

    private int visitFirst;

    public InteruptingControlVisitor(final int visitFirst, final VisitResult response) {
        super(response);
        this.visitFirst = visitFirst;
    }

    @Override
    public VisitResult visit(Class<?> instance) {
        final VisitResult result = super.visit(instance);
        if(this.visitFirst-- < 1) {
            return result;
        } else {
            return VisitResult.CONTINUE;
        }
    }
}
