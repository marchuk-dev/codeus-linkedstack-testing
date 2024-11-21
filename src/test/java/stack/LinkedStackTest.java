package stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("LinkedStack Tests")
public class LinkedStackTest {

    private LinkedStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new LinkedStack<>();
    }

    @ParameterizedTest
    @MethodSource("linkedStackFieldsCorrectResults")
    @DisplayName("Test fields in LinkedStack.class")
    public void testFieldsInLinkedStack(Class<?> classType, int modifier, Field field) {
        assertEquals(classType, field.getType(), String.format("Field %s type mismatch", field.getName()));
        assertEquals(modifier, field.getModifiers(), String.format("Field %s should be private", field.getName()));
    }

    private static Stream<Arguments> linkedStackFieldsCorrectResults() throws NoSuchFieldException {
        Class<LinkedStack> linkedStackClass = LinkedStack.class;
        Class<LinkedStack.Node> nodeClass = LinkedStack.Node.class;

        return Stream.of(
                Arguments.of(Named.named("LinkedStack.Node.class, Modifier.PRIVATE -> head", LinkedStack.Node.class), Modifier.PRIVATE, linkedStackClass.getDeclaredField("head")),
                Arguments.of(Named.named("int.class, Modifier.PRIVATE -> size", int.class), Modifier.PRIVATE, linkedStackClass.getDeclaredField("size"))
        );
    }

    @ParameterizedTest
    @MethodSource("nodeFieldsCorrectResults")
    @DisplayName("Test next field in Node.class")
    public void testNodeElementField(String classTypeName, Field field) throws NoSuchFieldException {
        Class<LinkedStack.Node> nodeClass = LinkedStack.Node.class;
        Field next = nodeClass.getDeclaredField("next");

        assertEquals(classTypeName, field.getGenericType().getTypeName(), String.format("Field %s type mismatch", next));
        assertTrue(!Modifier.isPrivate(field.getModifiers())
                && !Modifier.isProtected(field.getModifiers())
                && !Modifier.isPublic(field.getModifiers()));
    }

    private static Stream<Arguments> nodeFieldsCorrectResults() throws NoSuchFieldException {
        Class<LinkedStack.Node> nodeClass = (Class<LinkedStack.Node>) getNodeClass();

        return Stream.of(
                Arguments.of(Named.named("T -> element", "T"), nodeClass.getDeclaredField("element")),
                Arguments.of(Named.named("stack.LinkedStack$Node<T> -> next", "stack.LinkedStack$Node<T>"), nodeClass.getDeclaredField("next"))
        );
    }

    private static Class<?> getNodeClass() {
        return Arrays.stream(LinkedStack.class.getDeclaredClasses())
                .filter(c -> c.getSimpleName().equals("Node"))
                .findAny()
                .orElse(null);
    }


}
