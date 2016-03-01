package com.github.chrisruffalo.silvering.engine;

import com.github.chrisruffalo.silvering.engine.config.TraverserConfig;
import com.github.chrisruffalo.silvering.engine.marker.VisitMarker;
import com.github.chrisruffalo.silvering.model.Component;

import java.util.List;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class PropertyGraphTraverser extends AbstractTraverser<Component> {

    @Override
    protected VisitMarker<Component> createMarker(final TraverserConfig config) {
        return null;
    }

    @Override
    protected List<Component> childrenOf(final Component node) {
        return null;
    }
}
