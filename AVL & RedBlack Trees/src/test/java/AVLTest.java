import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTest {
    AVL<Integer> avl = new AVL<>();

    void setAvl(){
    }


    @Test
    void insert() {
        avl.insert(3);
        avl.insert(2);
        avl.insert(1);
        assertTrue(avl.isBalanced());
        assertEquals(1, avl.height());
        avl.insert(4);
        assertTrue(avl.isBalanced());
        assertEquals(2, avl.height());
        avl.insert(5);
        assertTrue(avl.isBalanced());
        assertEquals(2, avl.height());
        avl.insert(6);
        assertTrue(avl.isBalanced());
        assertEquals(2, avl.height());
        avl.insert(7);
        assertTrue(avl.isBalanced());
        assertEquals(2, avl.height());
        avl.insert(10);
        assertTrue(avl.isBalanced());
        assertEquals(3, avl.height());
    }

    @Test
    void insertionTwo(){
        avl.insert(50);
        avl.insert(40);
        avl.insert(60);
        avl.insert(30);
        avl.insert(45);
        avl.insert(49);
        assertTrue(avl.isBalanced());
    }

    @Test
    void deleteCaseOne() {
        avl.insert(4);
        avl.insert(6);
        avl.insert(3);
        avl.insert(2);
        avl.delete(6);
        assertTrue(avl.isBalanced());
    }
    @Test
    void deleteCaseTwo() {
        avl.insert(2);
        avl.insert(3);
        avl.insert(4);
        avl.insert(1);
        avl.delete(1);
        assertTrue(avl.isBalanced());
    }
    @Test
    void deleteCaseThree() {
        avl.insert(50);
        avl.insert(40);
        avl.insert(60);
        avl.insert(30);
        avl.insert(45);
        avl.insert(55);
        avl.insert(10);
        assertEquals(3, avl.height());
        avl.delete(55);
        assertEquals(2, avl.height());
        assertTrue(avl.isBalanced());
    }

    @Test
    void deleteCaseFour() {
        avl.insert(50);
        avl.insert(40);
        avl.insert(60);
        avl.insert(30);
        avl.insert(45);
        avl.insert(55);
        avl.insert(49);
        assertEquals(3, avl.height());
        avl.delete(55);
        assertEquals(2, avl.height());
        assertTrue(avl.isBalanced());
    }

    @Test
    @DisplayName("search for keys")
    void search() {
//        assertTrue(avl.search("a"));
//        assertTrue(avl.search("c"));
//        assertFalse(avl.search("g"));
//        assertFalse(avl.search("k"));
    }

    @Test
    void minimum() {
        //assertEquals("a", avl.minimum());
    }

    @Test
    void successor() {
    }

    @Test
    void inOrderTraversal() {
    }

    @Test
    void isEmpty() {
        assertFalse(avl.isEmpty());
    }
}