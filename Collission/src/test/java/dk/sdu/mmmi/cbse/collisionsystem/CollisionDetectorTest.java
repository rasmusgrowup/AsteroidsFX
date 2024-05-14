package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for CollisionDetector.
 */
public class CollisionDetectorTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CollisionDetectorTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CollisionDetectorTest.class );
    }

    /**
     * Test if collision detection works.
     */
    public void testCollisionDetection() {
        // Create two entities
        Entity entity1 = new Entity();
        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(10);

        Entity entity2 = new Entity();
        entity2.setX(0);
        entity2.setY(0);
        entity2.setRadius(10);

        // Create CollisionDetector
        CollisionDetector collisionDetector = new CollisionDetector();

        // Check if entities collide
        Boolean result = collisionDetector.collides(entity1, entity2);

        // Assert that the entities collide
        assertTrue(result);
    }
}
