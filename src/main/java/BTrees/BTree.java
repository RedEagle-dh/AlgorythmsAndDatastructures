package BTrees;
class BTreeNode {
    int[] keys; // Array to store keys
    int t;  // Minimum degree (defines the range for number of keys)
    BTreeNode[] children; // Array of children
    int n;     // Current number of keys
    boolean isLeaf; // Is true when node is leaf. Otherwise false

    public BTreeNode(int t, boolean isLeaf) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.n = 0;
    }

    // Function to traverse all nodes in a subtree rooted with this node
    public void traverse() {
        // There are n keys and n+1 children, traverse through n keys and first n children
        int i = 0;
        for (i = 0; i < this.n; i++) {
            // If this is not leaf, then before printing key[i],
            // traverse the subtree rooted with child C[i].
            if (!this.isLeaf) {
                children[i].traverse();
            }
            System.out.print(keys[i] + " ");
        }

        // Print the subtree rooted with last child
        if (!isLeaf) {
            children[i].traverse();
        }
    }

    // Function to search key k in subtree rooted with this node
    public BTreeNode search(int k) {
        // Find the first key greater than or equal to k
        int i = 0;
        while (i < n && k > keys[i]) {
            i++;
        }

        // If the found key is equal to k, return this node
        if (i < n && keys[i] == k) {
            return this;
        }

        // If key is not found here and this is a leaf node
        if (isLeaf) {
            return null;
        }

        // Go to the appropriate child
        return children[i].search(k);
    }

    // Insert the key in the subtree rooted with this node
    void insertNonFull(int k) {
        // Initialize index as index of rightmost element
        int i = n - 1;

        // If this is a leaf node
        if (isLeaf) {
            // The following loop does two things:
            // a) Finds the location of new key to be inserted
            // b) Moves all greater keys to one place ahead
            while (i >= 0 && keys[i] > k) {
                keys[i + 1] = keys[i];
                i--;
            }

            // Insert the new key at found location
            keys[i + 1] = k;
            n = n + 1;
        } else { // If this node is not leaf
            // Find the child which is going to have the new key
            while (i >= 0 && keys[i] > k) {
                i--;
            }

            // See if the found child is full
            if (children[i + 1].n == 2 * t - 1) {
                // If the child is full, then split it
                splitChild(i + 1, children[i + 1]);

                // After split, the middle key of C[i] goes up and
                // C[i] is split into two. See which of the two
                // is going to have the new key
                if (keys[i + 1] < k) {
                    i++;
                }
            }
            children[i + 1].insertNonFull(k);
        }
    }

    // A utility function to split the child y of this node. i is index of y in child array C[].
    // The Child y must be full when this function is called
    void splitChild(int i, BTreeNode y) {
        // Create a new node which is going to store (t-1) keys of y
        BTreeNode z = new BTreeNode(y.t, y.isLeaf);
        z.n = t - 1;

        // Copy the last (t-1) keys of y to z
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        // Copy the last t children of y to z
        if (!y.isLeaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }

        // Reduce the number of keys in y
        y.n = t - 1;

        // Since this node is going to have a new child,
        // create space of new child
        for (int j = n; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }

        // Link the new child to this node
        children[i + 1] = z;

        // A key of y will move to this node. Find the location of
        // new key and move all greater keys one space ahead
        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }

        // Copy the middle key of y to this node
        keys[i] = y.keys[t - 1];

        // Increment count of keys in this node
        n = n + 1;
    }
}

class BTree {
    BTreeNode root; // Pointer to root node
    int t;  // Minimum degree

    // Constructor (initializes tree as empty)
    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    // function to traverse the tree
    public void traverse() {
        if (root != null) {
            root.traverse();
        }
    }

    // function to search a key in this tree
    public BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    // The main function that inserts a new key in this B-Tree
    public void insert(int k) {
        // If tree is empty
        if (root == null) {
            // Allocate memory for root
            root = new BTreeNode(t, true);
            root.keys[0] = k;  // Insert key
            root.n = 1;  // Update number of keys in root
        } else {  // If tree is not empty
            // If root is full, then tree grows in height
            if (root.n == 2 * t - 1) {
                // Allocate memory for new root
                BTreeNode s = new BTreeNode(t, false);

                // Make old root as child of new root
                s.children[0] = root;

                // Split the old root and move 1 key to the new root
                s.splitChild(0, root);

                // New root has two children now.  Decide which of the two
                // children is going to have new key
                int i = 0;
                if (s.keys[0] < k) {
                    i++;
                }
                s.children[i].insertNonFull(k);

                // Change root
                root = s;
            } else {  // If root is not full, call insertNonFull for root
                root.insertNonFull(k);
            }
        }
    }
}