package stack;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(value = ClassOrderer.OrderAnnotation.class)
@DisplayName("LinkedStack Tests")
public class LinkedStackTest {

    private LinkedStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new LinkedStack<>();
    }

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class NodeTypeTests {

        @Order(1)
        @Test
        void nodeClassIsDeclared() {
            final var nodeClass = getNodeClass();
            assertNotNull(nodeClass, "Node class is not declared inside LinkedStack<T>");

        }

        @Order(2)
        @Test
        void nodeClassIsStatic() {
            final var nodeClass = getNodeClass();
            final var isStatic = Modifier.isStatic(nodeClass.getModifiers());

            assertTrue(isStatic, "Node class isn't static");

        }

        @Order(3)
        @Test
        void nodeClassIsGeneric() {
            final var nodeClass = getNodeClass();
            final var typeParameters = nodeClass.getTypeParameters();

            assertEquals(1, typeParameters.length, "Node isn't generic or declares more generic parameters than needed");
            assertEquals("T", typeParameters[0].getName(), "Genetic type is not T");

        }

        @Order(4)
        @Test
        void nodeClassHasGenericElementField() {
            final var nodeClass = getNodeClass();
            final var elementField = assertDoesNotThrow(() -> nodeClass.getDeclaredField("element"), "Node doesn't declare 'element' field");

            final var isPrivate = Modifier.isPrivate(elementField.getModifiers());
            final var isPackagePrivate = !Modifier.isPrivate(elementField.getModifiers())
                                         && !Modifier.isProtected(elementField.getModifiers())
                                         && !Modifier.isPublic(elementField.getModifiers());

            assertTrue(isPrivate || isPackagePrivate, "Node<T>#element has neither private nor package-private visibility");
            assertEquals("T", elementField.getGenericType().getTypeName(), "Node<T>#element isn't of type 'T'");

        }

        @Order(5)
        @Test
        void nodeClassHasGenericNextNodeField() {
            final var nodeClass = getNodeClass();
            final var nextField = assertDoesNotThrow(() -> nodeClass.getDeclaredField("next"), "Node doesn't declare 'next' field");

            final var isPrivate = Modifier.isPrivate(nextField.getModifiers());
            final var isPackagePrivate = !Modifier.isPrivate(nextField.getModifiers())
                                         && !Modifier.isProtected(nextField.getModifiers())
                                         && !Modifier.isPublic(nextField.getModifiers());
            final var fieldTypeIsNode = nextField.getGenericType().getTypeName()
                    .endsWith("$Node<T>");

            assertTrue(isPrivate || isPackagePrivate, "Node<T>#next has neither private nor package-private visibility");
            assertTrue(fieldTypeIsNode, "Node<T>#next isn't of type 'Node<T>'");

        }

        private Class<?> getNodeClass() {
            return Arrays.stream(LinkedStack.class.getDeclaredClasses())
                    .filter(c -> c.getSimpleName().equals("Node"))
                    .findAny()
                    .orElse(null);
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class FieldsTests {
        // TODO test linked stack fields
    }


    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ConstructorTest {

        @Test
        @Order(1)
        void linkedStackInitializedEmpty() throws IllegalAccessException {
            final var linkedStack = assertDoesNotThrow(() -> new LinkedStack<Integer>(), "Threw during creation");

            final var head = getHead();
            final var size = getSize();

            head.setAccessible(true);
            size.setAccessible(true);

            assertNull(head.get(linkedStack), "Newly created linked stack's 'head' is not null");
            assertEquals(0, size.get(linkedStack), "Newly created linked stack's 'size' is not 0");
        }
    }

    private Field getHead() {
        try {
            return LinkedStack.class.getDeclaredField("head");
        } catch (NoSuchFieldException e) {
            return Assertions.fail(e);
        }
    }

    private Field getSize() {
        try {
            return LinkedStack.class.getDeclaredField("size");
        } catch (NoSuchFieldException e) {
            return Assertions.fail(e);
        }
    }

}
