package bst;

import java.util.ArrayList;
import java.util.Comparator;


public class BinarySearchTree<E> {
  BinaryNode<E> root;  // Används också i BSTVisaulizer
  int size;            // Används också i BSTVisaulizer
  private Comparator<E> comp;
    
  	public static void main(String[] args) {
  		BSTVisualizer visualize = new BSTVisualizer("Tree", 600, 600);
  		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
  		
  			tree.add(3);
  			tree.add(2);
  			tree.add(6);
  			tree.add(7);
  			tree.add(-5);
  			tree.add(12);
  			tree.add(18);
  		
  		tree.rebuild();
  		tree.printTree();
  		visualize.drawTree(tree);
  	}
  
  
	/**
	 * Constructs an empty binary search tree.
	 */
	public BinarySearchTree() {
		root = null;
		size = 0; 
		comp = ((e1, e2) -> ((Comparable<E>) e1).compareTo(e2));
		
	}
	
	/**
	 * Constructs an empty binary search tree, sorted according to the specified comparator.
	 */
	public BinarySearchTree(Comparator<E> comp) {
		root = null;
		size = 0;
		this.comp = comp; 
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * @param x element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		if(root == null) {
			root = new BinaryNode<E>(x);
			size++;
			return true;
		}
		
		return add(root, x);
	}
	
	/**
	 * Recursive method to add
	 * @param x and n element to be inserted
	 * @return true if the the element was inserted
	 */
	private boolean add(BinaryNode<E> n,E x) {
		BinaryNode<E> node = new BinaryNode<E>(x);
		
		if(comp.compare(x, n.element) == 0) {
			return false;
		}
		
		//Left
		else if (comp.compare(x, n.element) < 0) {
			if(n.left == null) {
				n.left = node;
				size++;
			} else {
				return add(n.left, x);
			}
		}
		
		//Right 
		else if (comp.compare(x, n.element) > 0) {
			if (n.right == null) {
				n.right = node;
				size++;
			} else {
				return add(n.right, x);
			}
		} 
		
		return true; 
	}
	
	/**
	 * Computes the height of tree.
	 * @return the height of the tree
	 */
	public int height() {
		return height(root);
	}
	
	/**
	 * Recursive method, calculating the height
	 * @param n element to be inserted
	 * @return the height of the tree
	 */
	private int height(BinaryNode<E> n) {
		if (n == null) {
			return 0;
		} else {
			return 1 + Math.max(height(n.left), height(n.right));
		}
		
		
		
	}
	
	/**
	 * Returns the number of elements in this tree.
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Removes all of the elements from this list.
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * Print tree contents in inorder.
	 */
	public void printTree() {
		printTree(root);
	}
	
	/**
	 * recursive method, loops through biggest values, prints them, and then checks the smaller values.
	 */
	private void printTree(BinaryNode<E> n) {
		if (n != null) {
			printTree(n.left);
			System.out.println(n.element.toString());
			printTree(n.right);
		}
		
	}
	/** 
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		ArrayList<E> sorted = new ArrayList<>();
		toArray(root, sorted);
		root = buildTree(sorted, 0, sorted.size() - 1);

	}
	
	/*
	 * Adds all elements from the tree rooted at n in inorder to the list sorted.
	 */
	private void toArray(BinaryNode<E> n, ArrayList<E> sorted) {
		if (n != null) {
			toArray(n.left, sorted);
			sorted.add(n.element);
			toArray(n.right, sorted);
		}
		
	}
	
	/*
	 * Builds a complete tree from the elements from position first to 
	 * last in the list sorted.
	 * Elements in the list a are assumed to be in ascending order.
	 * Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(ArrayList<E> sorted, int first, int last) {
		if (first > last) {
			return null;
		}
		
		int mid = ((first + last) / 2);
		
		BinaryNode<E> n= new BinaryNode<E>(sorted.get(mid)); 
		
		n.left = buildTree(sorted, first, mid - 1);
		n.right = buildTree(sorted, mid + 1, last);
		
		return n;
	}
	


	static class BinaryNode<E> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}	
	}
	
}
