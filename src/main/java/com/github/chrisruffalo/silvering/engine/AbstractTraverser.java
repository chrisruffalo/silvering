package com.github.chrisruffalo.silvering.engine;

import com.github.chrisruffalo.silvering.engine.config.TraversalProperty;
import com.github.chrisruffalo.silvering.engine.config.TraverserConfig;
import com.github.chrisruffalo.silvering.engine.marker.VisitMarker;
import com.github.chrisruffalo.silvering.visitor.VisitResult;
import com.github.chrisruffalo.silvering.visitor.Visitor;

import java.util.List;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
abstract class AbstractTraverser<T> implements Traverser<T> {

    private final TraverserConfig config;

    /**
     * Create a non-null marker that can check and see if a node has
     * been visited before. If this is null expect an NPE.
     *
     * @param config the configuration in use by the traverser
     * @return a new marker for tracking the visit status of each item
     */
    protected abstract VisitMarker<T> createMarker(final TraverserConfig config);

    /**
     * Return all the children of the given node. This method should
     * never return a null value for node. If it does, expect to get
     * a NPE. The value of node should never be null.
     *
     * @param node the (non-null) node to look for the children of
     * @return a non-null list with sibling data if it exists.
     */
    protected abstract List<T> childrenOf(final T node);

    public AbstractTraverser() {
        this.config = new TraverserConfig();
    }

    public AbstractTraverser(final TraverserConfig config) {
        if(config == null) {
            this.config = new TraverserConfig();
        } else {
            this.config = config;
        }
    }

    public void traverse(final T instance, final Visitor<T> visitor) {
        if(visitor == null) {
            // todo: error or warning?
            return;
        }

        // create marker
        final VisitMarker<T> marker = this.createMarker(this.config);

        // continue traversal
        this.traverseInto(instance, visitor, marker);
    }

    private VisitResult traverseInto(final T instance, final Visitor<T> visitor, final VisitMarker<T> marker) {
        // can't visit or traverse into a null starting node
        if(instance == null) {
            return VisitResult.CONTINUE;
        }

        // check against recursive visit
        if(marker.wasAlreadyVisited(instance)) {
            return VisitResult.CONTINUE;
        }

        // visit node
        final VisitResult result = visitor.visit(instance);

        // mark as visited
        marker.markVisited(instance);

        // die if requested halt
        if(VisitResult.HALT.equals(result)) {
            return result;
        }

        // skip subtree if requested
        if(!VisitResult.SKIP_SUBTREE.equals(result)) {
            // get potential children, instance will never be null at this point
            // so we can dispense with the null check in childrenOf
            final List<T> children = this.childrenOf(instance);

            // traverse into all children
            for (final T child : children) {
                // do visit of current child and all subnodes
                final VisitResult childResult = this.traverseInto(child, visitor, marker);
                // process result
                if (VisitResult.HALT.equals(childResult) || VisitResult.SKIP_SIBLINGS.equals(childResult)) {
                    // the visitor indicated a halt and that it wants to stop visiting all nodes
                    return childResult;
                }
            }
        } else {
            return VisitResult.CONTINUE;
        }

        // the visitor did not request any halt and skip is relative to continue execution
        return result;
    }

    protected <V> V getConfig(final TraversalProperty<V> property) {
        return this.config.get(property);
    }
}
