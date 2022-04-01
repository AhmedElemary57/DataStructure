public interface Tree <T extends Comparable<T>>{
    Node<T> getRoot();
    void clear();
    void insert(T key);
    boolean search(T key);
    void delete(T key);
    T minimum();
    T maximum();
    boolean isEmpty();
    int height();
    void inOrderTraversal();
    int size();
}
