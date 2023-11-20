package SparseProject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckSparseVector {

    @Test
    public void testCreateInstance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SparseVector(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new SparseVector(0);
        });
    }

    @Test
    public void testSetAndGetElement() {
        SparseVector vector = new SparseVector(5);
        SparseVector vector1 = new SparseVector();

        assertEquals(0.0, vector1.getElement(0));
        assertEquals(0.0, vector1.getElement(1));

        vector.setElement(2, 3.0);
        assertEquals(3.0, vector.getElement(2));

        vector.setElement(0, 1.5);
        assertEquals(1.5, vector.getElement(0));
    }

    @Test
    public void testGetLength() {
        SparseVector vector = new SparseVector(8);
        SparseVector vector2 = new SparseVector();
        assertEquals(0, vector2.getLength());

        assertEquals(8, vector.getLength());

        vector.removeElement(4);
        assertEquals(7, vector.getLength());

    }

    @Test
    public void testRemoveElementThrowsException() {
        SparseVector vector = new SparseVector(5);
        SparseVector emptyVector = new SparseVector();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            vector.removeElement(10);
        });
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            vector.removeElement(-1);
        });

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            emptyVector.removeElement(0);
        });
    }

    @Test
    public void testSetElementThrowsException() {
        SparseVector vector = new SparseVector(5);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            vector.setElement(10, 2.0);
        });
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            vector.setElement(-1, 2.0);
        });
    }

    @Test
    public void testAdd() {
        SparseVector vector1 = new SparseVector(5);
        SparseVector vector2 = new SparseVector(5);

        SparseVector vector3 = new SparseVector();
        SparseVector vector4 = new SparseVector();

        vector3.add(vector4);
        assertEquals(0.0, vector1.getElement(0));

        vector1.setElement(2, 3.0);
        vector1.setElement(4, 1.0);

        vector2.setElement(1, 2.0);
        vector2.setElement(4, 2.0);

        vector1.add(vector2);

        assertEquals(3.0, vector1.getElement(2));
        assertEquals(2.0, vector1.getElement(1));
        assertEquals(3.0, vector1.getElement(4));
    }

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
