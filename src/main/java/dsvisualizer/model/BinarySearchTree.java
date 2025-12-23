package dsvisualizer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree data structure.
 */
public class BinarySearchTree {
    public static class TreeNode {
        public int data;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode root;
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Insert value into BST.
     */
    public boolean insert(int value) {
        if (search(value) != null) {
            throw new IllegalArgumentException("Duplicate value not allowed");
        }
        root = insertRecursive(root, value);
        size++;
        return true;
    }

    /**
     * Recursive insert helper.
     */
    private TreeNode insertRecursive(TreeNode node, int value) {
        if (node == null) {
            return new TreeNode(value);
        }
        if (value < node.data) {
            node.left = insertRecursive(node.left, value);
        } else {
            node.right = insertRecursive(node.right, value);
        }
        return node;
    }

    /**
     * Delete value from BST.
     */
    public boolean delete(int value) {
        if (search(value) == null) {
            return false;
        }
        root = deleteRecursive(root, value);
        size--;
        return true;
    }

    /**
     * Recursive delete helper.
     */
    private TreeNode deleteRecursive(TreeNode node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.data) {
            node.left = deleteRecursive(node.left, value);
        } else if (value > node.data) {
            node.right = deleteRecursive(node.right, value);
        } else {
            // Node with no children
            if (node.left == null && node.right == null) {
                return null;
            }
            // Node with one child
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // Node with two children
            TreeNode successor = findMin(node.right);
            node.data = successor.data;
            node.right = deleteRecursive(node.right, successor.data);
        }
        return node;
    }

    /**
     * Search for node with value.
     */
    public TreeNode search(int value) {
        return searchRecursive(root, value);
    }

    /**
     * Recursive search helper.
     */
    private TreeNode searchRecursive(TreeNode node, int value) {
        if (node == null) {
            return null;
        }
        if (value == node.data) {
            return node;
        }
        if (value < node.data) {
            return searchRecursive(node.left, value);
        }
        return searchRecursive(node.right, value);
    }

    /**
     * Find minimum node in subtree.
     */
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * In-order traversal.
     */
    public List<Integer> inorderTraversal() {
        List<Integer> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }

    /**
     * Pre-order traversal.
     */
    public List<Integer> preorderTraversal() {
        List<Integer> result = new ArrayList<>();
        preorderRecursive(root, result);
        return result;
    }

    /**
     * Post-order traversal.
     */
    public List<Integer> postorderTraversal() {
        List<Integer> result = new ArrayList<>();
        postorderRecursive(root, result);
        return result;
    }

    private void inorderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderRecursive(node.left, result);
        result.add(node.data);
        inorderRecursive(node.right, result);
    }

    private void preorderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.data);
        preorderRecursive(node.left, result);
        preorderRecursive(node.right, result);
    }

    private void postorderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderRecursive(node.left, result);
        postorderRecursive(node.right, result);
        result.add(node.data);
    }

    /**
     * Get root node.
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * Get current size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Check if tree is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clear the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }
}
