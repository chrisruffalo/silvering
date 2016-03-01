package com.github.chrisruffalo.silvering.engine.marker;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public interface VisitMarker<T> {

    boolean wasAlreadyVisited(final T node);

    void markVisited(final T node);

}
