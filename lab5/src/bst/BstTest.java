package bst;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BstTest {
	
	private BinarySearchTree<Integer> tree;
	private BinarySearchTree<Integer> tree2;
	private BinarySearchTree<String> tree3;
	
	@BeforeEach
	void setUp() throws Exception {
		tree = new BinarySearchTree<Integer>();
		
		//Constructor w/ parameter
		tree2 = new BinarySearchTree<Integer>();
		
		tree3 = new BinarySearchTree<String>();
	}

	@AfterEach
	void tearDown() throws Exception {
		tree = null;
		tree2 = null;
		tree3 = null;
	}

	@Test
	void testHeight() {
		assertTrue(tree.add(1));
		assertTrue(tree.add(3));
		assertTrue(tree.add(5));
		assertTrue(tree.add(2));
		assertFalse(tree.add(3));
		
		//Checks the tree w/ parameter
		assertTrue(tree2.add(1));
		assertTrue(tree2.add(2));
		assertTrue(tree2.add(3));
		assertFalse(tree2.add(2));
		
		//Other element (String)
		assertTrue(tree3.add("jag"));
		assertTrue(tree3.add("gillar"));
		assertTrue(tree3.add("programmering"));
		assertFalse(tree3.add("gillar"));
		
	}
	
	@Test
	void testSize() {
		assertTrue(tree.add(1));
		assertTrue(tree.add(3));
		assertTrue(tree.add(5));
		assertTrue(tree.add(2));
		
		//checks double
		assertFalse(tree.add(3));
		
		assertEquals(4, tree.size());
		
		//Checks the tree w/ parameter
		assertTrue(tree2.add(1));
		assertTrue(tree2.add(2));
		assertTrue(tree2.add(3));
		assertFalse(tree2.add(2));
		assertEquals(3, tree2.size());
		
		//Other element (String)
		assertTrue(tree3.add("jag"));
		assertTrue(tree3.add("gillar"));
		assertTrue(tree3.add("programmering"));
		assertFalse(tree3.add("gillar"));
		assertEquals(3, tree3.size());
	}

	@Test
	void testClear() {
		tree.add(1);
		tree.add(5);
		tree.add(20);
		
		tree.clear();
		
		assertEquals(0, tree.size());
	}
}
