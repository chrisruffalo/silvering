package com.github.chrisruffalo.silvering.visitor;

import com.github.chrisruffalo.silvering.engine.ClassHierarchyTraverser;
import com.github.chrisruffalo.silvering.engine.config.ClassHierarchyTraversalProperty;
import com.github.chrisruffalo.silvering.engine.config.TraverserConfig;
import com.github.chrisruffalo.silvering.visitor.hierarchy.Branch;
import com.github.chrisruffalo.silvering.visitor.hierarchy.IChild;
import com.github.chrisruffalo.silvering.visitor.hierarchy.ICousin;
import com.github.chrisruffalo.silvering.visitor.hierarchy.IMain;
import com.github.chrisruffalo.silvering.visitor.hierarchy.IOffshoot;
import com.github.chrisruffalo.silvering.visitor.hierarchy.Root;
import com.github.chrisruffalo.silvering.visitor.hierarchy.Trunk;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author Chris Ruffalo
 */
public class ClassHierarchyVisitorTest {

    @Test
    public void testNulls() {
        final ClassHierarchyTraverser traverser = new ClassHierarchyTraverser(null);
        traverser.traverse(null, null);
    }

    @Test
    public void testNullsWithVisitor() {
        final ClassHierarchyTraverser traverser = new ClassHierarchyTraverser(null);
        final ClassHierarchyVisitor visitor = new CollectingClassHierarchyVisitor();
        traverser.traverse(null, visitor);
    }

    @Test
    public void testNullVisitorWithActuals() {
        final ClassHierarchyTraverser traverser = new ClassHierarchyTraverser();
        final ClassHierarchyVisitor visitor = null;
        traverser.traverse(Root.class, visitor);
    }

    @Test
    public void testNoTraverseSuperclass() {
        final TraverserConfig config = new TraverserConfig();
        config.set(ClassHierarchyTraversalProperty.TRAVERSE_SUPERCLASSES, false);

        this.executeTest(Branch.class, config, Branch.class, ICousin.class, IOffshoot.class, IMain.class);
    }

    @Test
    public void testBasicHierarchy() {
        final TraverserConfig config = new TraverserConfig();
        config.set(ClassHierarchyTraversalProperty.TRAVERSE_INTERFACES, false);

        this.executeTest(Trunk.class, config, Trunk.class, Root.class);
    }

    @Test
    public void testBasicHierarchyWithObject() {
        final TraverserConfig config = new TraverserConfig();
        config.set(ClassHierarchyTraversalProperty.INCLUDE_OBJECT_CLASS, true);
        config.set(ClassHierarchyTraversalProperty.TRAVERSE_INTERFACES, false);

        this.executeTest(Trunk.class, config, Trunk.class, Root.class, Object.class);
    }

    @Test
    public void testHierarchyWithInterfaces() {
        this.executeTest(Root.class, Root.class, IMain.class);
    }

    @Test
    public void testComplexHierarchyWithInterfaces() {
        this.executeTest(Branch.class, Branch.class, Trunk.class, Root.class, IOffshoot.class, ICousin.class, IChild.class, IMain.class);
    }

    @Test
    public void testNotVisitingExtendedInterfaces() {
        final TraverserConfig config = new TraverserConfig();
        config.set(ClassHierarchyTraversalProperty.TRAVERSE_EXTENDED_INTERFACES, false);

        this.executeTest(Branch.class, config, Branch.class, Trunk.class, Root.class, ICousin.class, IChild.class, IMain.class);
    }

    @Test
    public void testHaltingVisitor() {
        final NavigationControlVisitor visitor = new NavigationControlVisitor(VisitResult.HALT);
        final TraverserConfig config = new TraverserConfig();

        this.executeTest(Branch.class, visitor, config, Branch.class);
    }

    @Test
    public void testInterruptingHaltingVisitor() {
        final NavigationControlVisitor visitor = new InteruptingControlVisitor(2, VisitResult.HALT);
        final TraverserConfig config = new TraverserConfig();

        this.executeTest(Branch.class, visitor, config, Branch.class, Trunk.class, Root.class);
    }

    @Test
    public void testNoSubtreeVisitor() {
        final NavigationControlVisitor visitor = new NavigationControlVisitor(VisitResult.SKIP_SUBTREE);
        final TraverserConfig config = new TraverserConfig();

        this.executeTest(Branch.class, visitor, config, Branch.class);
    }

    @Test
    public void testNoSiblingsVisitor() {
        final NavigationControlVisitor visitor = new NavigationControlVisitor(VisitResult.SKIP_SIBLINGS);
        final TraverserConfig config = new TraverserConfig();

        this.executeTest(Branch.class, visitor, config, Branch.class, Trunk.class, Root.class, IMain.class);
    }

    /**
     * Internal method for code-reuse in testing. Performs default operation.
     *
     * @param start the start point of the test visit
     * @param expectedClassesOrInterfaces the classes that are expected to be visited
     */
    private void executeTest(final Class<?> start, final Class<?>... expectedClassesOrInterfaces) {
        this.executeTest(start, new TraverserConfig(), expectedClassesOrInterfaces);
    }

    /**
     * Internal method for code-reuse in testing. Performs operation as specified by the configuration provided.
     *
     * @param start the start point of the test visit
     * @param config the configuration of the traverser used during the visit
     * @param expectedClassesOrInterfaces the classes that are expected to be visited
     */
    private void executeTest(final Class<?> start, final TraverserConfig config, final Class<?>... expectedClassesOrInterfaces) {
        // create visitor
        final CollectingClassHierarchyVisitor visitor = new CollectingClassHierarchyVisitor();
        executeTest(start, visitor, config, expectedClassesOrInterfaces);
    }

    /**
     * Internal method for code-reuse in testing. Performs operation as specified by the configuration provided.
     *
     * @param start the start point of the test visit
     * @param visitor the visitor to use during the test
     * @param config the configuration of the traverser used during the visit
     * @param expectedClassesOrInterfaces the classes that are expected to be visited
     */
    private void executeTest(final Class<?> start, final CollectingClassHierarchyVisitor visitor, final TraverserConfig config, final Class<?>... expectedClassesOrInterfaces) {
        // can't have a null expectation
        Assert.assertNotNull("The expected values were null", expectedClassesOrInterfaces);

        // create traverser
        final ClassHierarchyTraverser traverser = new ClassHierarchyTraverser(config);

        // visit
        traverser.traverse(start, visitor);

        // get results
        final Set<Class<?>> visited = new HashSet<>(visitor.getVisited());

        // assert individual expectations
        for(final Class<?> expected : expectedClassesOrInterfaces) {
            Assert.assertTrue("The expected results must contain the class '" + expected.getName() + "'", visited.contains(expected));
            visited.remove(expected);
        }

        // ensure that all of the expected values were found
        if(!visited.isEmpty()) {
            final StringBuilder builder = new StringBuilder();
            builder.append("Unexpected class");
            if(visited.size() > 1) {
                builder.append("es");
            }
            builder.append(" found: ");
            boolean first = true;
            for(final Class<?> item : visited) {
                if(!first) {
                   builder.append(", ");
                }
                first = false;
                builder.append("'");
                builder.append(item.getName());
                builder.append("'");
            }
            Assert.fail(builder.toString());
        }
    }
}
