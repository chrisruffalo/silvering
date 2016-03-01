package com.github.chrisruffalo.silvering.visitor;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public enum VisitResult {

    /**
     * Continue normally visiting each element.
     */
    CONTINUE,

    /**
     * Even if a subtree exists of the current node, do not visit it.
     */
    SKIP_SUBTREE,

    /**
     * If siblings exist, skip them.
     */
    SKIP_SIBLINGS,

    /**
     * Stop visiting all nodes and end the traversal process.
     */
    HALT

}
