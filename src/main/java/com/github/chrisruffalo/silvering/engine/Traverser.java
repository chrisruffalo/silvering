package com.github.chrisruffalo.silvering.engine;

import com.github.chrisruffalo.silvering.visitor.Visitor;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public interface Traverser<T> {

    void traverse(final T instance, final Visitor<T> visitor);

}
