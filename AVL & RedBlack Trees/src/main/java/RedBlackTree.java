import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;

public class RedBlackTree<T extends Comparable<T>> implements Tree<T>{
    private Node<T> root;

    @Override
    public void insert(T key) {
        Node<T> nodeToBeInsert = new Node<>(key);
        Node<T> tmpRoot;
        tmpRoot = insert(root, nodeToBeInsert);
        //After insertion, we should maintain red black tree properties
        if(tmpRoot != null){
            root = tmpRoot;
            redBlackRules(nodeToBeInsert);
        }
    }

    /**
     * @return the root node of the tree
     * */
    private Node<T> insert(Node<T> node, Node<T> nodeToBeInsert){
        if(node == null)
            return nodeToBeInsert;
        //case insert the node in the left subtree as leaf node
        if (nodeToBeInsert.getKey().compareTo(node.getKey()) < 0){
            Node<T> x = insert(node.getLeft() , nodeToBeInsert);
            if(x != null){
                node.setLeft(x);
                x.setParent(node);
            }
            else return null;
        }
        else if (nodeToBeInsert.getKey().compareTo(node.getKey()) > 0){
            Node<T> x = insert(node.getRight(), nodeToBeInsert);
            if(x != null){
                node.setRight(x);
                x.setParent(node);
            }
            else return null;
        }
        else {
            System.out.println("ERROR: value is already in the tree!");
            return null;
        }
        return node;
    }

    /**
     * maintain the red black properties while inserting a new node
     * @param node is the newly inserted node
     * */
    public void redBlackRules(Node<T> node){
        if (node!= root && node.getParent().getColor()==RED){
            //P red and node is not root
            Node<T> uncle=node.getUncle();
            Node<T> parent=node.getParent();
            Node<T> grandParent=node.getGrandParent();
            // In this case we have a red uncle, and we are inserting a red node as default,
            // so we will recolor uncle ,parent and grandparent
            if (uncle!=null && uncle.getColor()==RED)
                recolorUPG(node);
            // her GP and is left child
            else if (grandParent!=null && parent == grandParent.getLeft() )
                leftSubtreeHandling(node);
            else if (grandParent!=null && parent== grandParent.getRight())
                rightSubtreeHandling(node);
        }
        root.setColor(BLACK);
    }

    public void recolorUPG(Node<T> node){
        node.getUncle().flipColor();
        node.getParent().flipColor();
        node.getGrandParent().flipColor();
        redBlackRules(node.getGrandParent());
    }

    public void leftSubtreeHandling(Node<T> node){
        if (node==node.getParent().getRight()){
            rotateLeft(node.getParent());
            Node<T> temp =node;
            node=node.getLeft();
        }
        node.getParent().flipColor();
        if(node.getGrandParent() != null){
            node.getGrandParent().flipColor();
            rotateRight(node.getGrandParent());
        }
        if (node==node.getParent().getLeft())
            redBlackRules(node.getParent());
        else
            redBlackRules(node.getGrandParent());
    }

    public void rightSubtreeHandling(Node<T> node){
        if (node==node.getParent().getLeft()) {
            rotateRight(node.getParent());
            node=node.getRight();
        }
        node.getParent().flipColor();
        if(node.getGrandParent() != null){
            node.getGrandParent().flipColor();
            rotateLeft(node.getGrandParent());
        }
        if (node==node.getParent().getLeft())
            redBlackRules(node.getGrandParent());
        else
            redBlackRules(node.getParent());
    }

    @Override
    public boolean search(T key) {
        return search(root, key) != null;
    }

    /**
     * @return the node if the item is found; nil otherwise.
     * */
    private Node<T> search(Node<T> x, T key){
        if(x == null || x.getKey().compareTo(key)==0)
            return x;
        if(key.compareTo(x.getKey()) < 0)
            return search(x.getLeft(), key);
        else
            return search(x.getRight(), key);
    }

    @Override
    public void delete(T key){
        Node<T> nodeToDelete = search(root, key);
        delete(nodeToDelete);
    }

    public void delete(Node<T> nodeToDelete) {
        if (nodeToDelete == null) {
            System.out.println("ERROR: Word does not exist in the dictionary!");
            return;
        }
        //find its replacement
        //case: nodeToDelete is a leaf node
        if (nodeToDelete.isLeafNode()&&nodeToDelete.getColor()==RED){
            nodeToDelete.clear();
            return;
        } else if (nodeToDelete.isLeafNode()&&nodeToDelete.getColor()==BLACK) {
            handlingDoubleBlack(nodeToDelete);
            nodeToDelete.clear();
            return;
        }
        //case: nodeToDelete is not a leaf node
        Node<T> transplant;
        if (nodeToDelete.getLeft() == null)
            transplant = nodeToDelete.getRight();
        else if (nodeToDelete.getRight() == null)
            transplant = nodeToDelete.getLeft();
            //case: nodeToDelete has two children
        else
            transplant = successor(nodeToDelete);
        //case the node to transplant is red

        nodeToDelete.setKey(transplant.getKey());
        //case the transplant is red and leaf node
        if (transplant.isLeafNode()){
            if (transplant.getColor()==RED)
                transplant.clear();
            else {
                handlingDoubleBlack(transplant);
                transplant.clear();
            }
        }
        else
            delete(transplant);
    }

    private void handlingDoubleBlack(Node<T> node){
        Node<T> DB = node;
        if (root==DB){
            return;
        }
        if ( node.getParent().getColor()==RED && node.sibling()!=null &&node.isSiblingAndItsChildrenBlack()){
            DB.sibling().setColor(RED);
            DB.getParent().setColor(BLACK);
        }
        else if (node.getParent().getColor()==BLACK && node.sibling()!=null && node.isSiblingAndItsChildrenBlack()){
            DB.sibling().setColor(RED);
            DB=DB.getParent();
            handlingDoubleBlack(DB);
        }
        else if (DB.sibling()!=null && DB.sibling().getColor()==RED){
            //1.swap colors of sibling and parent
            DB.sibling().setColor(BLACK);
            DB.getParent().setColor(RED);
            //2.rotation in the direction of the DB
            if (DB.getParent().getLeft()==DB){
                rotateLeft(DB.getParent());
                //3.redo handling DB
                handlingDoubleBlack(DB);
            }
            else if (DB.getParent().getRight()==DB){
                rotateRight(DB.getParent());
                handlingDoubleBlack(DB);

            }
        }
        //case 5
        else if (DB.sibling()!=null && DB.sibling().getColor()==BLACK && DB.farChildIsBlackAndNearIsRedFromLeft()){
            DB.sibling().getLeft().setColor(BLACK);
            DB.sibling().setColor(RED);
            rotateRight(DB.sibling());
            handlingDoubleBlack(DB);
        }
        else if (DB.sibling()!=null && DB.sibling().getColor()==BLACK && DB.farChildIsBlackAndNearIsRedFromRight()){
            DB.sibling().getRight().setColor(BLACK);
            DB.sibling().setColor(RED);
            rotateLeft(DB.sibling());
            handlingDoubleBlack(DB);
        }
        else if (DB.sibling()!=null && DB.sibling().getColor()==BLACK && DB.farChildIsRedFromLeft()){
            Color temp=DB.sibling().getColor();
            DB.sibling().setColor(DB.getParent().getColor());
            DB.getParent().setColor(temp);
            DB.sibling().getRight().setColor(BLACK);
            rotateLeft(DB.getParent());
            return;
        }
        else if (DB.sibling()!=null && DB.sibling().getColor()==BLACK && DB.farChildIsRedFromRight()){
            Color temp=DB.sibling().getColor();
            DB.sibling().setColor(DB.getParent().getColor());
            DB.getParent().setColor(temp);
            DB.sibling().getLeft().setColor(BLACK);
            rotateRight(DB.getParent());
            return;
        }
    }

    private Node<T> successor(Node<T> x){
        if(x.getRight() != null)
            return minimum(x.getRight());
        Node<T> y = x.getParent();
        while (y != null && x == y.getRight() ){
            x = y;
            y = y.getParent();
        }
        return y;
    }

    @Override
    public T minimum() {
        return minimum(root).getKey();
    }

    private Node<T> minimum(Node<T> x){
        while(x.getLeft() != null)
            x = x.getLeft();
        return x;
    }

    @Override
    public T maximum() {
        return maximum(root).getKey();
    }

    private Node<T> maximum(Node<T> x){
        while(x.getRight() != null)
            x = x.getRight();
        return x;
    }

    @Override
    public void inOrderTraversal(){
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node<T> x){
        if(x == null) return;
        inOrderTraversal(x.getLeft());
        System.out.println(x.getKey());
        inOrderTraversal(x.getRight());
    }

    @Override
    public boolean isEmpty(){
        return root == null;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public int size(){
        return size(root);
    }

    private int size(Node<T> x){
        if(x == null) return 0;
        return 1 + size(x.getLeft()) + size(x.getRight());
    }

    ////////////////////////////////////*//////////////////////////////////////////////////
    /**
     * rotate with left child and update the heights
     * @return the new root
     * */
    private void rotateRight(Node<T> node){
        Node<T> leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        if (node.getLeft() != null){
            //here we will update the parent node only if there is a left child
            //get the child then assign the parent value
            node.getLeft().setParent(node);
        }
        leftChild.setRight(node);
        leftChild.setParent(node.getParent());//the parent of Leftchild will be the parent of node
        updateParent(node,leftChild);
        node.setParent(leftChild);//update the parent value

    }

    private void updateParent(Node<T> node, Node<T> temp) {
        if (node.getParent()==null)
            root=temp;
        else if (node==node.getParent().getLeft())
            node.getParent().setLeft(temp);
        else
            node.getParent().setRight(temp);

    }

    private void rotateLeft(Node<T> node){
        Node<T> rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        if (node.getRight() != null){
            //here we will update the parent node only if there is a left child
            //get the child then assign the parent value
            node.getRight().setParent(node);

        }
        rightChild.setLeft(node);
        rightChild.setParent(node.getParent());//the parent of Leftchild will be the parent of node
        updateParent(node, rightChild);
        node.setParent(rightChild);//update the parent value
    }

    public void batchInsert(String pathName) {
        try {
            File file = new File(pathName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                T word = (T) scanner.nextLine();
                insert(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occur while reading this file");
        }
    }

    public void batchDelete(String pathName){
        try {
            File file = new File(pathName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                T word = (T) scanner.nextLine();
                delete(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occur while reading this file");
        }
    }

    public Node<T> getRoot(){
        return this.root;
    }

    public void clear(){
        this.root = null;
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.insert(55);
        redBlackTree.insert(40);
        redBlackTree.insert(65);
        redBlackTree.insert(60);
        redBlackTree.insert(75);
        redBlackTree.insert(57);

        redBlackTree.delete(40);
    }

}
