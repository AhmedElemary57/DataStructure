import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AVL<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    public AVL(){
        this.root = null;
    }

    public AVL(T key){
        this.root = new Node<T>(key);
    }

    @Override
    public void insert(T key) {
        root = insert(root, key);
    }
    /**
     * @return the root node of the tree
     * */
    private Node<T> insert(Node<T> node, T key){
        if(node == null)
            return new Node<>(key, null, null, null);
        if (key.compareTo(node.getKey()) < 0){
            Node<T> x = insert(node.getLeft(), key);
            x.setParent(node);
            node.setLeft(x);
        }
        else if (key.compareTo(node.getKey()) > 0){
            Node<T> x = insert(node.getRight(), key);
            x.setParent(node);
            node.setRight(x);
        }
        else
            System.out.println("ERROR: Word already in the dictionary!");
        return balance(node);
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

    /**
     * @return true if the item is found; false otherwise.
     * */
    @Override
    public boolean search(T key) {
        return search(root, key) != null;
    }
    /**
     * @return the node if the item is found; nil otherwise.
     * */
    private Node<T> search(Node<T> x, T key){
        if(x == null || x.getKey().compareTo(key) == 0)
            return x;
        if(key.compareTo(x.getKey()) < 0)
            return search(x.getLeft(), key);
        else
            return search(x.getRight(), key);
    }

    @Override
    public void delete(T key) {
        //find the node to be deleted
        Node<T> nodeToDelete = search(root, key);
        if (nodeToDelete == null) {
            System.out.println("ERROR: Word does not exist in the dictionary!");
            return;
        }
        //find its replacement
        //case: nodeToDelete has one child
        Node<T> transplant;
        if (nodeToDelete.getLeft() == null)
            transplant = nodeToDelete.getRight();
        else if (nodeToDelete.getRight() == null)
            transplant = nodeToDelete.getLeft();
        //case: nodeToDelete has two children
        else
            transplant = successor(nodeToDelete);

        Node<T> criticalNode = null;
        if(transplant != null)//nodeToDelete.getParent();
            criticalNode = transplant.getParent();

        if(criticalNode == null)
            criticalNode = nodeToDelete.getParent();
        if(criticalNode != null && criticalNode == nodeToDelete)
            criticalNode = transplant;

        replace(nodeToDelete, transplant);

        Node<T> tmp = root;
        while(criticalNode != null){
            criticalNode = balanceAfterDeletion(criticalNode);
            tmp = criticalNode;
            criticalNode = criticalNode.getParent();
        }
        root = tmp;
    }

    /**
     * it replaces one subtree as a child of its parent with another subtree
     * replaces child u with subtree v
     * */
    private void replace(Node<T> u, Node<T> v){//2 1
        //case replace node with root
        if(u.getParent() == null){
            if(v == root.getRight()){
                if(v == null) {
                    root = null;
                    return;
                }
                else{
                    root.setKey(v.getKey());
                    root.setRight(v.getRight());
                }
            }else{
                 root.setKey(v.getKey());
                 v.getParent().setLeft(v.getRight());
                 v.getParent().setHeight(Math.max(height(v.getParent().getLeft()), height(v.getParent().getRight())) + 1);
            }
            root.setHeight(Math.max(height(root.getLeft()), height(root.getRight())) + 1);
            return;
        }

        if(u == u.getParent().getLeft())
            u.getParent().setLeft(v);
        else
            u.getParent().setRight(v);
        if(v != null) {
            v.setParent(u.getParent());
            if(v != u.getLeft() && v != u.getRight())
                v.setLeft(u.getLeft());
        }
    }
    /**
     * @return the minimum node in the right subtree
     * */
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

    /**
     * @return minimum node within the subtree rooted at node x
     * */
    private Node<T> minimum(Node<T> x){
        while(x.getLeft() != null)
            x = x.getLeft();
        return x;
    }

    @Override
    public T minimum() {
        return minimum(root).getKey();
    }

    /**
     * @return maximum node within the subtree rooted at node x
     * */
    private Node<T> maximum(Node<T> x){
        while(x.getRight() != null)
            x = x.getRight();
        return x;
    }

    @Override
    public T maximum() {
        return maximum(root).getKey();
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

    public int height(){
        return root == null ? -1 : root.getHeight();
    }

    private int height(Node<T> x){
        return x == null ? -1 : x.getHeight();
    }

    /**
     * doing the AVL property then updating the height of node x
     * */
    private Node<T> balance(Node<T> x){
        if(x == null) return x;
        int balanceFactor = height(x.getLeft()) - height(x.getRight());
        if(balanceFactor > 1){
            //left-left insertion
            int difference = height(x.getLeft().getLeft()) - height(x.getLeft().getRight());
            if(difference == 1 || difference == 0){
                x = rotateRight(x);
            }
            //left-right insertion
            else if(difference == -1){
                x = rotateLeftRight(x);
            }
        }
        else if(balanceFactor < -1){
            //right-right insertion
            int difference = height(x.getRight().getLeft()) - height(x.getRight().getRight());
            if(difference == -1 || difference == 0){
                x = rotateLeft(x);
            }
            //right-left insertion
            else if(difference == 1){
                x = rotateRightLeft(x);
            }
        }
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        return x;
    }

    private Node<T> balanceAfterDeletion(Node<T> criticalNode){
        Node<T> B = criticalNode;
        int criticalBalanceFactor = height(criticalNode.getLeft()) - height(criticalNode.getRight());
        //If node X present in the right sub-tree of criticalNode
        if(criticalBalanceFactor > 1){
            B = criticalNode.getLeft();
            int balanceFactor = height(B.getLeft()) - height(B.getRight());
            if(balanceFactor == 0 || balanceFactor == 1){
                B = rotateRight(criticalNode);
            }else if(balanceFactor == -1){
                criticalNode.setLeft( rotateLeft(B) );
                B = rotateRight(criticalNode);
            }
        }
        //If node X present in the left sub-tree of criticalNode
        else if(criticalBalanceFactor < -1){
            B = criticalNode.getRight();
            int balanceFactor = height(B.getLeft()) - height(B.getRight());
            if(balanceFactor == 0 || balanceFactor == -1){
                B = rotateLeft(criticalNode);
            }else if(balanceFactor == 1){
                criticalNode.setRight( rotateRight(B) );
                B = rotateLeft(criticalNode);
            }
        }
        B.setHeight(Math.max(height(B.getLeft()), height(B.getRight())) + 1);
        return B;
    }

    /**
     * rotate with left child and update the heights
     * @return the new root
     * */
    private Node<T> rotateRight(Node<T> k2){
        Node<T> k1 = k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);
        k1.setParent(k2.getParent());
        k2.setParent(k1);
        if(k2.getLeft() != null)
            k2.getLeft().setParent(k2);
        k2.setHeight(Math.max( height(k2.getLeft()), height(k2.getRight()) )  + 1);
        k1.setHeight(Math.max( height(k1.getLeft()), k2.getHeight() )  + 1);
        return k1;
    }

    private Node<T> rotateLeft(Node<T> k2){
        Node<T> k1 = k2.getRight();
        k2.setRight(k1.getLeft());
        k1.setLeft(k2);
        k1.setParent(k2.getParent());
        k2.setParent(k1);
        if(k2.getRight() != null)
            k2.getRight().setParent(k2);
        k2.setHeight(Math.max( height(k2.getLeft()), height(k2.getRight()) )  + 1);
        k1.setHeight(Math.max( k2.getHeight(), height(k1.getRight()) )  + 1);
        return k1;
    }

    private Node<T> rotateRightLeft(Node<T> k1){
        Node<T> k3 = k1.getRight();
        Node<T> k2 = k3.getLeft();
        k1.setRight(k2.getLeft());
        k3.setLeft(k2.getRight());
        k2.setParent(k1.getParent());
        k2.setLeft(k1);
        k2.setRight(k3);
        k3.setParent(k2);
        k1.setParent(k2);
        k3.setHeight(Math.max( height(k3.getLeft()), height(k3.getRight()) )  + 1);
        k1.setHeight(Math.max( height(k1.getLeft()), height(k1.getRight()) )  + 1);
        return k2;
    }

    private Node<T> rotateLeftRight(Node<T> k3){
        Node<T> k1 = k3.getLeft();
        Node<T> k2 = k1.getRight();
        k3.setLeft(k2.getRight());
        k1.setRight(k2.getLeft());
        k2.setParent(k3.getParent());
        k2.setLeft(k1);
        k2.setRight(k3);
        k1.setParent(k2);
        k3.setParent(k2);
        k3.setHeight(Math.max( height(k3.getLeft()), height(k3.getRight()) )  + 1);
        k1.setHeight(Math.max( height(k1.getLeft()), height(k1.getRight()) )  + 1);
        return k2;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public boolean isBalanced(){
        if(root == null) return true;

        return Math.abs(height(root.getLeft()) - height(root.getRight())) < 2;
    }

    public int size(){
        return size(root);
    }

    private int size(Node<T> x){
        if(x == null) return 0;
        return 1 + size(x.getLeft()) + size(x.getRight());
    }

    public Node<T> getRoot(){
        return this.root;
    }
    public void clear(){
        this.root = null;
    }

}
