import java.util.Arrays;

public class MaxHeap implements MaxHeapIF{
    private int[] A;
    private int length;
    private int heapSize;

    public MaxHeap(){
        this.length = 3;
        A = new int[length];
    }

    public MaxHeap(int length){
        this.length = length;
        A = new int[length];
    }

    public void maxHeapify(int[] A, int i){
        int largest;
        int l = leftChildIndex(i);
        int r = rightChildIndex(i);
        if(l < heapSize && A[l] > A[i]){
            largest = l;
        }else largest = i;
        if(r < heapSize && A[r] > A[largest]){
            largest = r;
        }
        if(largest != i){
            swap(A, i, largest);
            maxHeapify(A, largest);
        }
    }

    public void buildMaxHeap(int[] A){
        heapSize = A.length;
        for(int i = (A.length/2) - 1; i >= 0; i--)
            maxHeapify(A, i);
    }

    public void maxHeapInsert(int node){
        if(heapSize == length){
            System.out.println("Error: overflow!");
            return;
        }

        A[heapSize] = node;
        //fix the max heap property
        int i = heapSize;
        while(i > 0 && A[parentIndex(i)] < A[i]){
            swap(A, i, parentIndex(i));
            i =  parentIndex(i);
        }
        heapSize++;
    }

    public int extractMax(){
        if(heapSize == 0)
            return -1;

        if(heapSize == 1){
            heapSize--;
            return A[heapSize];
        }
        int max = A[0];
        A[0] = A[--heapSize];
        maxHeapify(A, 0);
        return max;
    }

    public void heapSort(int[] A){
        buildMaxHeap(A);
        for(int i = A.length-1; i>=1; i--){
            swap(A, 0, i);
            heapSize--;
            maxHeapify(A, 0);
        }
    }

    @Override
    public String toString(){
        String output = "";
        for (int i = 0; i < heapSize; i++) {
            output += A[i] + " ";
        }
        return output;
    }

    private int parentIndex(int i){
        return (i - 1)>> 1;
    }
    private int leftChildIndex(int i){
        return (i << 1) + 1;
    }
    private int rightChildIndex(int i){
        return (i << 1) + 2;
    }
    private void swap(int[]A, int i, int largest){
        int tmp = A[i];
        A[i] = A[largest];
        A[largest] = tmp;
    }

    public static void main(String[] args) {
        MaxHeap heap = new MaxHeap(10);
        heap.maxHeapInsert(3);
        heap.maxHeapInsert(4);
        heap.maxHeapInsert(9);
        heap.maxHeapInsert(5);
        heap.maxHeapInsert(2);

        System.out.println(heap.extractMax());
        System.out.println(heap.toString());
        System.out.println(heap.extractMax());
        System.out.println(heap.toString());
    }
}
