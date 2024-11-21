# **Test Plan for LinkedStack**

## **Overview**

The LinkedStack class represents a Last-In-First-Out (LIFO) stack implemented using a singly linked list. To thoroughly test its behavior and internal structure, the use of the Reflection API is necessary. This approach ensures that both public methods and private components are verified for correctness and reliability. The class offers multiple operations to manage the stack effectively. Below is a structured breakdown of what can be tested:

---

## **1. Fields and Class Test**
### **Objective**
Verify the presence, types, and access modifiers of fields in the `LinkedStack` and `Node` classes.

### **What to Test**
- **LinkedStack Fields**:
    - `head`: Ensure it is private and of type `Node<T>`.
    - `size`: Ensure it is private and of type `int` with a default value of `0`.
- **Node Class**:
    - Ensure the `Node` class is static and generic.
  - **Node Fields**:
      - `element`: Ensure it is private or package-private and of type `T`.
      - `next`: Ensure it is private or package-private and of type `Node<T>`.

---

## **2. Constructor Test**
### **Objective**
Test the behavior of the constructors in the `LinkedStack` and `Node` classes.

### **What to Test**
- **LinkedStack Constructor**:
    - Verify that the `LinkedStack` is initialized with `head == null` and `size == 0`.

- **Node Constructor**:
    - Test the private `Node` constructor to ensure it initializes `element` and leaves `next` as `null`.

- **Node Static Factory Method**:
    - Verify `Node.valueOf(T element)` correctly creates a `Node` with the given element.

---

## **3. Static Factory Method Test**
### **Objective**
Test the static `LinkedStack.of` method for creating stacks from provided elements.

### **What to Test**
- Ensure the method creates a new `LinkedStack` with all the provided elements.
- Verify that the stack is constructed in LIFO order (last element becomes the `head`).
- Handle edge cases:
    - Empty input (`LinkedStack.of()` creates an empty stack).

---

## **4. Methods Test**
### **Objective**
Test the core methods of the `LinkedStack` class for correctness and edge cases.

---

### **What to Test**

#### **push(T element)**
- **Description**: Add an element to the stack.
- **Verify**:
  - The new element becomes the `head`.
  - The size of the stack increments by 1.
  - A `NullPointerException` is thrown if `element` is `null`.

---

#### **pop()**
- **Description**: Remove and return the top element from the stack.
- **Verify**:
  - The `head` updates to the next node.
  - The size decrements by 1.
  - The returned element is the previous `head`'s element.
  - An `EmptyStackException` is thrown when attempting to pop from an empty stack.

---

#### **size()**
- **Description**: Return the number of elements in the stack.
- **Verify**:
  - It accurately reflects the number of elements in the stack at any time.

---

#### **isEmpty()**
- **Description**: Check if the stack is empty.
- **Verify**:
  - It returns `true` when the stack is empty (`head == null`).
  - It returns `false` when the stack has elements.

---

#### **peek()**
- **Description**: Return the top element of the stack without removing it.
- **Verify**:
  - The `head` remains unchanged.
  - The returned element is the current `head`'s element.
  - The size of the stack does not change.
  - An `EmptyStackException` is thrown when attempting to peek an empty stack.

---

## **5. Edge Cases**
### **Objective**
Ensure robustness of the `LinkedStack` implementation in unusual or boundary scenarios.

### **What to Test**
- **push() with null element**:
    - Verify that `Objects.requireNonNull` prevents adding a `null` element.
- **pop() on an empty stack**:
    - Ensure `EmptyStackException` is thrown.
- **LinkedStack.of() with no arguments**:
    - Verify that the created stack is empty (`isEmpty()` returns `true`).

---

## **6. Node Integrity Test**
### **Objective**
Validate the behavior and structure of the `Node` class when used within the `LinkedStack`.

### **What to Test**
- Ensure `Node.next` correctly links to the next element after `push()`.
- Verify that `Node.element` retains its assigned value.

---

## **7. Internal Helper Method Test**
### **Objective**
Test the private `retrieveHead()` method for correctness.

### **What to Test**
- Ensure it retrieves the `element` of the current `head` and updates `head` to point to the next node.

---
