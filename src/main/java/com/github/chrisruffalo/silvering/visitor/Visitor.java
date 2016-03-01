package com.github.chrisruffalo.silvering.visitor;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public interface Visitor<T> {

    VisitResult visit(final T instance);

}
