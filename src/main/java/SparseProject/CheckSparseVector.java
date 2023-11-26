package SparseProject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckSparseVector {

    /**
     * Unit test suite for the {@link SparseVector} class constructor.
     * This test ensures that the constructor behaves as expected and handles edge cases appropriately.
     * The {@code SparseVector} class represents a vector with sparse data, optimized for memory efficiency.
     *
     * <p><b>Test Cases:</b></p>
     * <ul>
     *   <li>Attempts to create a {@code SparseVector} with a negative length, expecting an {@code IllegalArgumentException}.</li>
     *   <li>Attempts to create a {@code SparseVector} with a zero length, expecting an {@code IllegalArgumentException}.</li>
     *   <li>Creates a default {@code SparseVector} and verifies that its length is 0.</li>
     *   <li>Creates a {@code SparseVector} with a specified length of 5 and verifies the length.</li>
     *   <li>Creates a {@code SparseVector} with a specified length of 1 and verifies the length.</li>
     * </ul>
     *
     * <p><b>Test Assumptions:</b></p>
     * <ul>
     *   <li>The {@code SparseVector} class properly throws {@code IllegalArgumentException} for invalid input.</li>
     *   <li>The default constructor creates a {@code SparseVector} with length 0.</li>
     *   <li>The constructor with a specified length creates a {@code SparseVector} with the expected length.</li>
     * </ul>
     *
     * <p><b>Test Results:</b></p>
     * <p>
     * The test case uses JUnit's assertions to validate that the constructor behaves correctly.
     * It checks for exceptions in case of invalid input and verifies that the created vectors have the expected lengths.
     * </p>
     *
     * @result The test passes if the constructor handles invalid input appropriately, creates instances with correct lengths,
     * and does not throw unexpected exceptions.
     *
     * @see SparseVector
     * @see IllegalArgumentException
     */
    @Test
    public void testCreateInstance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SparseVector(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new SparseVector(0);
        });
        assertEquals(0, new SparseVector().getLength());
        assertEquals(5, new SparseVector(5).getLength());
        assertEquals(1, new SparseVector(1).getLength());
    }

    /**
     * Unit test suite for the {@link SparseVector} class's setElement and getElement methods.
     * This test ensures that the setElement and getElement methods behave as expected, handling various scenarios.
     * The {@code SparseVector} class represents a vector with sparse data, optimized for memory efficiency.
     *
     * <p><b>Test Cases:</b></p>
     * <ul>
     *   <li>Attempts to get an element from a newly created {@code SparseVector} at a valid index (1-based) and expects 0.0.</li>
     *   <li>Attempts to get an element from a newly created empty {@code SparseVector} and expects an {@code IllegalArgumentException}.</li>
     *   <li>Sets an element at index 3 in a {@code SparseVector}, gets the element, and verifies it matches the set value.</li>
     *   <li>Sets an element at index 1 in a {@code SparseVector}, gets the element, and verifies it matches the set value.</li>
     *   <li>Attempts to get an element from a {@code SparseVector} at an index with default value (0.0) and expects 0.0.</li>
     *   <li>Attempts to get an element from a {@code SparseVector} at an invalid index and expects an {@code IllegalArgumentException}.</li>
     * </ul>
     *
     * <p><b>Test Assumptions:</b></p>
     * <ul>
     *   <li>The default value for elements in a {@code SparseVector} is 0.0.</li>
     *   <li>Attempting to get an element from an empty {@code SparseVector} throws an {@code IllegalArgumentException}.</li>
     *   <li>The setElement method correctly sets the specified element in the {@code SparseVector} at the given index.</li>
     *   <li>The getElement method retrieves the correct element from the {@code SparseVector} at the specified index.</li>
     * </ul>
     *
     * <p><b>Test Results:</b></p>
     * <p>
     * The test case uses JUnit's assertions to validate that the setElement and getElement methods behave correctly.
     * It checks for the correct handling of valid and invalid indices, default values, and proper setting/getting of elements.
     * </p>
     *
     * @result The test passes if the setElement and getElement methods work as expected, throwing exceptions for invalid input,
     * and correctly setting/getting elements in the {@code SparseVector}.
     *
     * @see SparseVector
     * @see IllegalArgumentException
     */
    @Test
    public void testSetAndGetElement() {
        SparseVector v1 = new SparseVector(5);
        SparseVector v2 = new SparseVector();

        assertEquals(0.0, v1.getElement(1));

        assertThrows(IllegalArgumentException.class, () -> {
            v2.getElement(1);
        });

        v1.setElement(3, 3.0);
        assertEquals(3.0, v1.getElement(3));

        v1.setElement(1, 1.5);
        assertEquals(1.5, v1.getElement(1));

        assertEquals(0.0, v1.getElement(5));

        assertThrows(IllegalArgumentException.class, () -> {
            v1.getElement(6);
        });
    }

    /**
     * Unit test suite for the {@link SparseVector} class's getLength method.
     * This test ensures that the getLength method of the {@code SparseVector} class returns the correct length.
     * The {@code SparseVector} class represents a vector with sparse data, optimized for memory efficiency.
     *
     * <p><b>Test Cases:</b></p>
     * <ul>
     *   <li>Verifies that the length of a newly created empty {@code SparseVector} is 0.</li>
     *   <li>Verifies that the length of a newly created {@code SparseVector} with a specified length is correct.</li>
     *   <li>Verifies that removing an element from a {@code SparseVector} does not affect its reported length.</li>
     * </ul>
     *
     * <p><b>Test Assumptions:</b></p>
     * <ul>
     *   <li>The default length of an empty {@code SparseVector} is 0.</li>
     *   <li>The length of a {@code SparseVector} with a specified length matches the specified length.</li>
     *   <li>Removing an element from a {@code SparseVector} does not change the length of the vector.</li>
     * </ul>
     *
     * <p><b>Test Results:</b></p>
     * <p>
     * The test case uses JUnit's assertions to validate that the getLength method returns the correct length of the {@code SparseVector}.
     * It checks for the correct reporting of the length for empty and non-empty vectors, even after removing elements.
     * </p>
     *
     * @result The test passes if the getLength method correctly reports the length of the {@code SparseVector} in different scenarios.
     *
     * @see SparseVector
     */
    @Test
    public void testGetLength() {
        SparseVector vector = new SparseVector(8);
        SparseVector vector2 = new SparseVector();
        assertEquals(0, vector2.getLength());

        assertEquals(8, vector.getLength());

        vector.removeElement(4);
        assertEquals(8, vector.getLength());

    }

    /**
     * Unit test suite for the {@link SparseVector} class's removeElement method throwing exceptions.
     * This test ensures that the removeElement method of the {@code SparseVector} class throws appropriate exceptions for invalid input.
     * The {@code SparseVector} class represents a vector with sparse data, optimized for memory efficiency.
     *
     * <p><b>Test Cases:</b></p>
     * <ul>
     *   <li>Attempts to remove an element from a {@code SparseVector} at an index exceeding its length and expects an {@code IllegalArgumentException}.</li>
     *   <li>Attempts to remove an element from a {@code SparseVector} at a negative index and expects an {@code IllegalArgumentException}.</li>
     *   <li>Attempts to remove an element from an empty {@code SparseVector} and expects an {@code IllegalArgumentException}.</li>
     * </ul>
     *
     * <p><b>Test Assumptions:</b></p>
     * <ul>
     *   <li>Attempting to remove an element from a {@code SparseVector} at an index exceeding its length throws an {@code IllegalArgumentException}.</li>
     *   <li>Attempting to remove an element from a {@code SparseVector} at a negative index throws an {@code IllegalArgumentException}.</li>
     *   <li>Attempting to remove an element from an empty {@code SparseVector} throws an {@code IllegalArgumentException}.</li>
     * </ul>
     *
     * <p><b>Test Results:</b></p>
     * <p>
     * The test case uses JUnit's assertions to validate that the removeElement method throws the expected exceptions for invalid input.
     * It checks for proper handling of out-of-bounds indices and invalid removal attempts from an empty vector.
     * </p>
     *
     * @result The test passes if the removeElement method correctly throws {@code IllegalArgumentException} for invalid input.
     *
     * @see SparseVector
     * @see IllegalArgumentException
     */
    @Test
    public void testRemoveElementThrowsException() {
        SparseVector vector = new SparseVector(5);
        SparseVector emptyVector = new SparseVector();
        assertThrows(IllegalArgumentException.class, () -> {
            vector.removeElement(10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            vector.removeElement(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            emptyVector.removeElement(1);
        });
    }

    /**
     * Unit test suite for the {@link SparseVector} class's setElement method throwing exceptions.
     * This test ensures that the setElement method of the {@code SparseVector} class throws appropriate exceptions for invalid input.
     * The {@code SparseVector} class represents a vector with sparse data, optimized for memory efficiency.
     *
     * <p><b>Test Cases:</b></p>
     * <ul>
     *   <li>Attempts to set an element in a {@code SparseVector} at an index exceeding its length and expects an {@code IllegalArgumentException}.</li>
     *   <li>Attempts to set an element in a {@code SparseVector} at a negative index and expects an {@code IllegalArgumentException}.</li>
     *   <li>Attempts to set an element in a {@code SparseVector} at index 0 (invalid for sparse vectors) and expects an {@code IllegalArgumentException}.</li>
     *   <li>Attempts to set an element in a {@code SparseVector} at a valid index and expects no exception.</li>
     * </ul>
     *
     * <p><b>Test Assumptions:</b></p>
     * <ul>
     *   <li>Attempting to set an element in a {@code SparseVector} at an index exceeding its length throws an {@code IllegalArgumentException}.</li>
     *   <li>Attempting to set an element in a {@code SparseVector} at a negative index throws an {@code IllegalArgumentException}.</li>
     *   <li>Attempting to set an element in a {@code SparseVector} at index 0 (invalid for sparse vectors) throws an {@code IllegalArgumentException}.</li>
     *   <li>Setting an element in a {@code SparseVector} at a valid index does not throw an exception.</li>
     * </ul>
     *
     * <p><b>Test Results:</b></p>
     * <p>
     * The test case uses JUnit's assertions to validate that the setElement method throws the expected exceptions for invalid input.
     * It checks for proper handling of out-of-bounds indices and invalid attempts to set elements at index 0.
     * </p>
     *
     * @result The test passes if the setElement method correctly throws {@code IllegalArgumentException} for invalid input.
     *
     * @see SparseVector
     * @see IllegalArgumentException
     */
    @Test
    public void testSetElementThrowsException() {
        SparseVector vector = new SparseVector(5);
        assertThrows(IllegalArgumentException.class, () -> {
            vector.setElement(10, 2.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            vector.setElement(-1, 2.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            vector.setElement(0, 2.0);
        });
        assertDoesNotThrow(() -> {
            vector.setElement(1, 2.0);
        });
    }

    /**
     * Unit test suite for the {@link SparseVector} class's add method.
     * This test ensures that the add method of the {@code SparseVector} class performs vector addition correctly.
     * The {@code SparseVector} class represents a vector with sparse data, optimized for memory efficiency.
     *
     * <p><b>Test Cases:</b></p>
     * <ul>
     *   <li>Attempts to add two empty {@code SparseVector}s and expects an {@code IllegalArgumentException}.</li>
     *   <li>Attempts to add two {@code SparseVector}s with different lengths and expects an {@code IllegalArgumentException}.</li>
     *   <li>Attempts to add a {@code SparseVector} with an empty {@code SparseVector} and expects an {@code IllegalArgumentException}.</li>
     *   <li>Verifies that adding two non-empty {@code SparseVector}s performs vector addition correctly.</li>
     * </ul>
     *
     * <p><b>Test Assumptions:</b></p>
     * <ul>
     *   <li>Attempting to add two empty {@code SparseVector}s throws an {@code IllegalArgumentException}.</li>
     *   <li>Attempting to add two {@code SparseVector}s with different lengths throws an {@code IllegalArgumentException}.</li>
     *   <li>Attempting to add a {@code SparseVector} with an empty {@code SparseVector} throws an {@code IllegalArgumentException}.</li>
     *   <li>The add method of the {@code SparseVector} class correctly performs vector addition.</li>
     * </ul>
     *
     * <p><b>Test Results:</b></p>
     * <p>
     * The test case uses JUnit's assertions to validate that the add method throws the expected exceptions for invalid input.
     * It checks for proper handling of attempts to add empty vectors and vectors with different lengths.
     * Additionally, it verifies that the add method performs vector addition correctly for non-empty vectors.
     * </p>
     *
     * @result The test passes if the add method correctly handles invalid input and performs vector addition as expected.
     *
     * @see SparseVector
     * @see IllegalArgumentException
     */
    @Test
    public void testAdd() {
        SparseVector vector1 = new SparseVector(5);
        SparseVector vector2 = new SparseVector(5);

        SparseVector vector3 = new SparseVector();
        SparseVector vector4 = new SparseVector();

        SparseVector vector5 = new SparseVector(4);

        assertThrows(IllegalArgumentException.class, () -> {
            vector3.add(vector4);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            vector1.add(vector5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            vector1.add(vector3);
        });

        assertEquals(0.0, vector1.getElement(1));

        vector1.setElement(2, 3.0);
        vector1.setElement(4, 1.0);

        vector2.setElement(1, 2.0);
        vector2.setElement(4, 2.0);

        vector1.add(vector2);

        assertEquals(3.0, vector1.getElement(2));
        assertEquals(2.0, vector1.getElement(1));
        assertEquals(3.0, vector1.getElement(4));
    }

    /**
     * Unit test suite for the {@link SparseVector} class's equals method.
     * This test ensures that the equals method of the {@code SparseVector} class correctly determines vector equality.
     * The {@code SparseVector} class represents a vector with sparse data, optimized for memory efficiency.
     *
     * <p><b>Test Cases:</b></p>
     * <ul>
     *   <li>Verifies that two empty {@code SparseVector}s are considered equal.</li>
     *   <li>Verifies that two {@code SparseVector}s with different lengths are not considered equal.</li>
     *   <li>Verifies that two {@code SparseVector}s with the same length but different elements are not considered equal.</li>
     *   <li>Verifies that two {@code SparseVector}s with the same length and elements are considered equal.</li>
     * </ul>
     *
     * <p><b>Test Assumptions:</b></p>
     * <ul>
     *   <li>Two empty {@code SparseVector}s are considered equal.</li>
     *   <li>Two {@code SparseVector}s with different lengths are not considered equal.</li>
     *   <li>Two {@code SparseVector}s with the same length but different elements are not considered equal.</li>
     *   <li>Two {@code SparseVector}s with the same length and elements are considered equal.</li>
     * </ul>
     *
     * <p><b>Test Results:</b></p>
     * <p>
     * The test case uses JUnit's assertions to validate that the equals method correctly determines vector equality.
     * It checks for equality of empty vectors, vectors with different lengths, vectors with different elements, and vectors with the same length and elements.
     * </p>
     *
     * @result The test passes if the equals method correctly determines vector equality in different scenarios.
     *
     * @see SparseVector
     */
    @Test
    public void testEquals() {
        SparseVector vector1 = new SparseVector(5);
        SparseVector vector2 = new SparseVector(5);
        SparseVector vector3 = new SparseVector(4);

        assertTrue(vector1.equals(vector2));
        assertFalse(vector1.equals(vector3));

        vector1.setElement(2, 3.0);
        assertFalse(vector1.equals(vector2));

        vector2.setElement(2, 3.0);
        assertTrue(vector1.equals(vector2));
    }

}
