package SearchMinKey;

import SparseProject.SparseVector;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestTree {
    @Test
    public void testEdgeCases() {
        Tree t = new Tree();
        assertThrows(NoSuchElementException.class, () -> {
            t.searchOneHigherKeyThanInputKey(421, t.root.getLeft(), t.root.getRight());
        });
    }
}
