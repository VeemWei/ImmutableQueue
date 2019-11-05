import com.chenstek.ImmutableQueue;
import com.chenstek.Queue;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

public class ImmutableQueueTest {

    private final List<Integer> integerInputs = Arrays.asList(1, 2, 3, 4, 5);

    @Test
    public void enQueueShouldReturnNewQueue() {

        Queue<Integer> queue = new ImmutableQueue<>();
        for (Integer i : integerInputs) {
            Queue<Integer> prevQueue = queue;
            queue = queue.enQueue(i);
            assertNotSame(prevQueue, queue);
        }
        assertNotNull(queue.head());
        assertFalse(queue.isEmpty());

    }

    @Test
    public void deQueueShouldReturnNewQueue(){

        Queue<Integer> queue = new ImmutableQueue<>();
        for (Integer i : integerInputs){
            queue = queue.enQueue(i);
        }
        assertNotNull(queue.head());
        while (!queue.isEmpty()){
            Queue<Integer> prevQueue = queue;
            queue = queue.deQueue();
            assertNotSame(prevQueue, queue);
        }
        assertNull(queue.head());
        assertTrue(queue.isEmpty());

    }

    @Test
    public void fifoQueueOrderValidation(){

        Queue<Integer> queue = new ImmutableQueue<>();
        for (Integer i : integerInputs){
            queue = queue.enQueue(i);
        }
        for (Integer i : integerInputs){
            assertEquals(queue.head(), i);
            queue = queue.deQueue();
        }
        assertNull(queue.head());
        assertTrue(queue.isEmpty());

    }

    @Test
    public void enQueueValueValidation() {

        Queue<Integer> queue = new ImmutableQueue<>();
        for (Integer i : integerInputs) {
            queue = queue.enQueue(i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(queue.head(), integerInputs.get(0));

    }

    @Test
    public void deQueueValueValidation() {

        Queue<Integer> queue = new ImmutableQueue<>();
        for (Integer i : integerInputs) {
            queue = queue.enQueue(i);
        }
        queue = queue.deQueue();
        assertFalse(queue.isEmpty());
        assertNotSame(queue.head(), integerInputs.get(0));
        assertEquals(queue.head(), integerInputs.get(1));

    }

    @Test
    public void enQueueNull() {

        Queue<Integer> queue = new ImmutableQueue<>();
        queue = queue.enQueue(null);
        assertNull(queue.head());
        assertTrue(queue.isEmpty());
        queue = queue.enQueue(null).enQueue(1);
        assertNotNull(queue.head());
        assertFalse(queue.isEmpty());
        assertNotSame(queue.head(), null);
        assertEquals(queue.head(), new Integer(1));

    }

    @Test
    public void enQueueComplexDateStructure() {

        Queue<SampleObject> queue = new ImmutableQueue<>();
        SampleObject item1 = new SampleObject("Item 1");
        queue = queue.enQueue(item1);
        assertNotNull(queue.head());
        assertFalse(queue.isEmpty());
        assertEquals(queue.head(), item1);
        assertEquals(queue.head(), new SampleObject("Item 1"));

        SampleObject item2 = new SampleObject("Item 2");
        assertNotEquals(queue.head(), item2);
        assertNotEquals(queue.head(), new SampleObject("Item 2"));

        queue = queue.enQueue(null).enQueue(item2);
        assertNotNull(queue.head());
        assertFalse(queue.isEmpty());
        assertNotSame(queue.head(), null);

        queue = queue.deQueue();
        assertEquals(queue.head(), item2);

    }
}
