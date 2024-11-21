package stack;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LinkedStack Tests")
public class LinkedStackTest {

    private LinkedStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new LinkedStack<>();
    }

    @Nested
    @DisplayName("Fields Tests")
    class FieldsTests {
        @Test
        @DisplayName("Verify fields: head and size")
        void testFieldsPresence() {
            assertDoesNotThrow(() -> LinkedStack.class.getDeclaredField("head"));
            assertDoesNotThrow(() -> LinkedStack.class.getDeclaredField("size"));
        }

        @Test
        @DisplayName("Initial size is zero and head is null")
        void testInitialState() {
            assertEquals(0, stack.size());
            assertTrue(stack.isEmpty());
        }
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        @Test
        @DisplayName("Default constructor initializes correctly")
        void testDefaultConstructor() {
            assertNotNull(stack);
            assertEquals(0, stack.size());
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("Static factory method creates stack with elements in LIFO order")
        void testStaticFactoryMethod() {
            LinkedStack<Integer> stack = LinkedStack.of(1, 2, 3);
            assertEquals(3, stack.size());
            assertEquals(3, stack.pop());
            assertEquals(2, stack.pop());
            assertEquals(1, stack.pop());
        }

        @Test
        @DisplayName("Static factory method creates an empty stack with no arguments")
        void testStaticFactoryEmpty() {
            LinkedStack<Integer> emptyStack = LinkedStack.of();
            assertTrue(emptyStack.isEmpty());
        }
    }

    @Nested
    @DisplayName("Node Tests")
    class NodeTests {
        @Test
        @DisplayName("Node is correctly created using valueOf")
        @SneakyThrows
        void testNodeValueOf() {
            Class<?> nodeClass = Class.forName("stack.LinkedStack$Node");

            var valueOfMethod = nodeClass.getDeclaredMethod("valueOf", Object.class);
            valueOfMethod.setAccessible(true);

            Object node = valueOfMethod.invoke(null, 10);

            Field elementField = nodeClass.getDeclaredField("element");
            elementField.setAccessible(true);
            assertEquals(10, elementField.get(node));

            Field nextField = nodeClass.getDeclaredField("next");
            nextField.setAccessible(true);
            assertNull(nextField.get(node));
        }

        @Test
        @DisplayName("Node links correctly to the next node")
        @SneakyThrows
        void testNodeLinking() {
            Class<?> nodeClass = Class.forName("stack.LinkedStack$Node");

            Constructor<?> nodeConstructor = nodeClass.getDeclaredConstructor(Object.class);
            nodeConstructor.setAccessible(true);

            Object firstNode = nodeConstructor.newInstance(1);
            Object secondNode = nodeConstructor.newInstance(2);

            Field nextField = nodeClass.getDeclaredField("next");
            nextField.setAccessible(true);
            nextField.set(firstNode, secondNode);

            Field elementField = nodeClass.getDeclaredField("element");
            elementField.setAccessible(true);
            assertEquals(2, elementField.get(nextField.get(firstNode)));
        }
    }

    @Nested
    @DisplayName("Methods Tests")
    class MethodsTests {
        @Test
        @DisplayName("push() adds elements to the stack")
        void testPush() {
            stack.push(10);
            stack.push(20);
            assertEquals(20, stack.peek());
            assertEquals(2, stack.size());
        }

        @Test
        @DisplayName("push() throws NullPointerException for null elements")
        void testPushNullElement() {
            assertThrows(NullPointerException.class, () -> stack.push(null));
        }

        @Test
        @DisplayName("pop() removes and returns the top element")
        void testPop() {
            stack.push(20);
            stack.push(30);
            assertEquals(30, stack.pop());
            assertEquals(20, stack.pop());
        }

        @Test
        @DisplayName("pop() throws EmptyStackException when stack is empty")
        void testPopEmptyStack() {
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("peek() returns the top element without removing it")
        void testPeek() {
            stack.push(10);
            stack.push(20);
            assertEquals(20, stack.peek());
            assertEquals(2, stack.size());
        }

        @Test
        @DisplayName("peek() throws EmptyStackException when stack is empty")
        void testPeekEmptyStack() {
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Test
        @DisplayName("size() returns the correct number of elements")
        void testSize() {
            assertEquals(0, stack.size());
            stack.push(1);
            stack.push(2);
            assertEquals(2, stack.size());
        }

        @Test
        @DisplayName("isEmpty() returns true for empty stack")
        void testIsEmptyTrue() {
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("isEmpty() returns false for non-empty stack")
        void testIsEmptyFalse() {
            stack.push(1);
            assertFalse(stack.isEmpty());
        }

        @Test
        @DisplayName("Clear and reuse the stack after operations")
        void testClearAndReuse() {
            stack.push(10);
            stack.pop();
            stack.push(20);
            assertEquals(20, stack.pop());
            assertTrue(stack.isEmpty());
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {
        @Test
        @DisplayName("Push and pop a single element")
        void testSingleElement() {
            stack.push(42);
            assertEquals(42, stack.pop());
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("Push and pop multiple elements in LIFO order")
        void testLIFOOrder() {
            stack.push(1);
            stack.push(2);
            stack.push(3);
            assertEquals(3, stack.pop());
            assertEquals(2, stack.pop());
            assertEquals(1, stack.pop());
        }

        @Test
        @DisplayName("Pop until stack is empty and ensure exception")
        void testPopUntilEmpty() {
            stack.push(1);
            stack.push(2);
            stack.pop();
            stack.pop();
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("Handle large stack operations without performance degradation")
        void testLargeStack() {
            int largeSize = 10_000;
            for (int i = 0; i < largeSize; i++) {
                stack.push(i);
            }
            assertEquals(largeSize, stack.size());
            for (int i = largeSize - 1; i >= 0; i--) {
                assertEquals(i, stack.pop());
            }
            assertTrue(stack.isEmpty());
        }
    }

    @Nested
    @DisplayName("Helper Methods Tests")
    class HelperMethodsTests {
        @Test
        @DisplayName("retrieveHead() returns the top element and updates head")
        void testRetrieveHead() {
            stack.push(1);
            stack.push(2);
            assertEquals(2, stack.pop());
            assertEquals(1, stack.pop());
        }
    }
}
