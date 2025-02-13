class BTreeNode {
    int[] keys; // Array of keys
    int t; // Minimum degree (defines the range for number of keys)
    BTreeNode[] children; // Array of child pointers
    int n; // Current number of keys
    boolean isLeaf; // True if leaf node

    // Constructor
    BTreeNode(int t, boolean isLeaf) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.n = 0;
    }
}

class BTree {
    BTreeNode root; // Root of the B-tree
    int t; // Minimum degree

    // Constructor
    BTree(int t) {
        this.root = null;
        this.t = t;
    }

    // Function to traverse the B-tree
    void traverse() {
        if (root != null) {
            traverse(root);
        }
    }

    void traverse(BTreeNode node) {
        for (int i = 0; i < node.n; i++) {
            if (!node.isLeaf) {
                traverse(node.children[i]);
            }
            System.out.print(node.keys[i] + " ");
        }
        if (!node.isLeaf) {
            traverse(node.children[node.n]);
        }
    }

    // Function to search a key in the B-tree
    BTreeNode search(int key) {
        return (root == null) ? null : search(root, key);
    }

    BTreeNode search(BTreeNode node, int key) {
        int i = 0;
        while (i < node.n && key > node.keys[i]) {
            i++;
        }
        if (i < node.n && key == node.keys[i]) {
            return node; // Key found
        }
        if (node.isLeaf) {
            return null; // Key not found
        }
        return search(node.children[i], key); // Search in the child
    }

    // Function to insert a new key
    void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode newNode = new BTreeNode(t, false);
                newNode.children[0] = root;
                splitChild(newNode, 0);
                int i = 0;
                if (newNode.keys[0] < key) {
                    i++;
                }
                insertNonFull(newNode.children[i], key);
                root = newNode;
            } else {
                insertNonFull(root, key);
            }
        }
    }

    void insertNonFull(BTreeNode node, int key) {
        int i = node.n - 1;
        if (node.isLeaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            if (node.children[i].n == 2 * t - 1) {
                splitChild(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    void splitChild(BTreeNode parent, int i) {
        BTreeNode fullNode = parent.children[i];
        BTreeNode newNode = new BTreeNode(fullNode.t, fullNode.isLeaf);
        newNode.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            newNode.keys[j] = fullNode.keys[j + t];
        }
        if (!fullNode.isLeaf) {
            for (int j = 0; j < t; j++) {
                newNode.children[j] = fullNode.children[j + t];
            }
        }
        fullNode.n = t - 1;

        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = newNode;

        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
                parent.keys[i] = fullNode.keys[t - 1];
        parent.n++;
    }

    // Function to delete a key from the B-tree
    void delete(int key) {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }
        root = delete(root, key);
        if (root.n == 0) {
            root = root.isLeaf ? null : root.children[0];
        }
    }

    BTreeNode delete(BTreeNode node, int key) {
        int idx = findKey(node, key);

        if (idx < node.n && node.keys[idx] == key) {
            if (node.isLeaf) {
                return removeFromLeaf(node, idx);
            } else {
                return removeFromNonLeaf(node, idx);
            }
        } else {
            if (node.isLeaf) {
                System.out.println("The key " + key + " is not present in the tree.");
                return node;
            }

            boolean isLastChild = (idx == node.n);
            if (node.children[idx].n < t) {
                fill(node, idx);
            }

            if (isLastChild && idx > node.n) {
                node.children[idx - 1] = delete(node.children[idx - 1], key);
            } else {
                node.children[idx] = delete(node.children[idx], key);
            }
        }
        return node;
    }

    int findKey(BTreeNode node, int key) {
        int idx = 0;
        while (idx < node.n && node.keys[idx] < key) {
            idx++;
        }
        return idx;
    }

    BTreeNode removeFromLeaf(BTreeNode node, int idx) {
        for (int i = idx + 1; i < node.n; i++) {
            node.keys[i - 1] = node.keys[i];
        }
        node.n--;
        return node;
    }

    BTreeNode removeFromNonLeaf(BTreeNode node, int idx) {
        int key = node.keys[idx];

        if (node.children[idx].n >= t) {
            int predecessor = getPredecessor(node, idx);
            node.keys[idx] = predecessor;
            node.children[idx] = delete(node.children[idx], predecessor);
        } else if (node.children[idx + 1].n >= t) {
            int successor = getSuccessor(node, idx);
            node.keys[idx] = successor;
            node.children[idx + 1] = delete(node.children[idx + 1], successor);
        } else {
            merge(node, idx);
            node.children[idx] = delete(node.children[idx], key);
        }
        return node;
    }

    int getPredecessor(BTreeNode node, int idx) {
        BTreeNode current = node.children[idx];
        while (!current.isLeaf) {
            current = current.children[current.n];
        }
        return current.keys[current.n - 1];
    }

    int getSuccessor(BTreeNode node, int idx) {
        BTreeNode current = node.children[idx + 1];
        while (!current.isLeaf) {
            current = current.children[0];
        }
        return current.keys[0];
    }

    void fill(BTreeNode node, int idx) {
        if (idx != 0 && node.children[idx - 1].n >= t) {
            borrowFromPrev(node, idx);
        } else if (idx != node.n && node.children[idx + 1].n >= t) {
            borrowFromNext(node, idx);
        } else {
            if (idx != node.n) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }

    void borrowFromPrev(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx - 1];

        for (int i = child.n - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }

        if (!child.isLeaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
            child.children[0] = sibling.children[sibling.n];
        }

        child.keys[0] = node.keys[idx - 1];
        node.keys[idx - 1] = sibling.keys[sibling.n - 1];

        child.n++;
        sibling.n--;
    }

    void borrowFromNext(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx + 1];

                child.keys[child.n] = node.keys[idx];
        if (!child.isLeaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        node.keys[idx] = sibling.keys[0];

        sibling.n--;
        for (int i = 0; i < sibling.n; i++) {
            sibling.keys[i] = sibling.keys[i + 1];
        }
        if (!sibling.isLeaf) {
            for (int i = 0; i <= sibling.n; i++) {
                sibling.children[i] = sibling.children[i + 1];
            }
        }

        child.n++;
    }

    void merge(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx + 1];

        child.keys[t - 1] = node.keys[idx];

        for (int i = 0; i < sibling.n; i++) {
            child.keys[i + t] = sibling.keys[i];
        }

        if (!child.isLeaf) {
            for (int i = 0; i <= sibling.n; i++) {
                child.children[i + t] = sibling.children[i];
            }
        }

        for (int i = idx + 1; i < node.n; i++) {
            node.keys[i - 1] = node.keys[i];
        }

        for (int i = idx + 2; i <= node.n; i++) {
            node.children[i - 1] = node.children[i];
        }

        child.n += sibling.n + 1;
        node.n--;

        sibling = null; // Optional: Help garbage collection
    }

    // Main method to test the B-tree implementation
    public static void main(String[] args) {
        BTree bTree = new BTree(3); // A B-Tree with minimum degree 3

        // Insert keys
        bTree.insert(10);
        bTree.insert(20);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(12);
        bTree.insert(30);
        bTree.insert(7);
        bTree.insert(17);

        // Print the B-tree
        System.out.println("Traversal of the B-tree:");
        bTree.traverse();
        System.out.println();

        // Delete a key
        System.out.println("Deleting key 6:");
        bTree.delete(6);
        bTree.traverse();
        System.out.println();

        // Delete another key
        System.out.println("Deleting key 13 (not present):");
        bTree.delete(13);
        bTree.traverse();
        System.out.println();

        // Delete another key
        System.out.println("Deleting key 7:");
        bTree.delete(7);
        bTree.traverse();
        System.out.println();

        // Delete another key
        System.out.println("Deleting key 4 (not present):");
        bTree.delete(4);
        bTree.traverse();
        System.out.println();

        // Delete another key
        System.out.println("Deleting key 5:");
        bTree.delete(5);
        bTree.traverse();
        System.out.println();

        // Delete another key
        System.out.println("Deleting key 10:");
        bTree.delete(10);
        bTree.traverse();
        System.out.println();

        // Delete another key
        System.out.println("Deleting key 30:");
        bTree.delete(30);
        bTree.traverse();
        System.out.println();

        // Delete another key
        System.out.println("Deleting key 20:");
        bTree.delete(20);
        bTree.traverse();
        System.out.println();
    }
}
