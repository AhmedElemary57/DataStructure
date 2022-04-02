import java.sql.Array;

public class MergeSort {

    void merge(int []array,int left , int middle ,int right){
        int size1 = middle-left+1;
        int size2 = right-middle;

        int[] leftArr = new int[size1];
        int []  rightArr = new int[size2];

        for (int i = 0; i < size1; i++) {
             leftArr[i] = array[left+i];
        }
        for (int j = 0; j <size2 ; j++) {
            rightArr[j] = array[middle+1+j];
        }
        int i=0 ,j=0,k=left;

        while (i<size1 && j<size2){
            if (leftArr[i]<=rightArr[j]){
                array[k]=leftArr[i];
                i++;
            }
            else {
                array[k]=rightArr[j];
                j++;
            }
            k++;
        }

        while (i<size1){
            array[k]=leftArr[i];
            i++;
            k++;
        }
        while (j<size2){
            array[k]=rightArr[j];
            j++;
            k++;
        }
    }
    void sort(int []array ,int left ,int right){
        if (left < right){

            int middle = (left + right)/2;
            sort(array , left , middle);
            sort(array ,middle+1 ,  right);
            merge(array , left , middle , right);
        }


    }
    public  static void main(String[] args){
        int []a={10,88,97,544,987,87568,567576,5667567,7656,65456,1,4,22,11,0,-1,-47,-1000,1};
        MergeSort m=new MergeSort();
        m.sort(a,0,a.length-1);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);

        }


    }

}
