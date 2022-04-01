public interface MaxHeapIF {
    void maxHeapify(int[] A, int i);
    void buildMaxHeap(int[] A);
    void maxHeapInsert(int i);
    int extractMax();
    void heapSort(int[] A);
}
