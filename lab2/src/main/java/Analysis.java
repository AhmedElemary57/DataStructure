import java.util.LinkedList;

public class Analysis {
    AVL<String> avl = new AVL<>();
    RedBlackTree<String> redBlackTree = new RedBlackTree<>();
    RandomWords randomWords = new RandomWords();
    long startTime, endTime, totalTime;

    public void analyzeInsertionAVL(){
        startTime = System.currentTimeMillis();
        avl.batchInsert("w.txt");
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("total time for insertion in AVL = "+totalTime);
        avl.clear();
    }
    public void analyzeInsertionRedBlack(){
        startTime = System.currentTimeMillis();
        redBlackTree.batchInsert("w.txt");
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("total time for insertion in RedBlack Tree = "+totalTime);
        redBlackTree.clear();
    }
    public void analyzeInsertion(int n){
        randomWords.createFileOfWords(n);
        analyzeInsertionAVL();
        analyzeInsertionRedBlack();
    }
    public void analyzeDeletionAVL(){
        startTime = System.currentTimeMillis();
        avl.batchDelete("w.txt");
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("total time for deletion in AVL = "+totalTime);
        avl.clear();
    }
    public void analyzeDeletionRedBlack(){
        startTime = System.currentTimeMillis();
        redBlackTree.batchDelete("w.txt");
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("total time for deletion in redBlack Tree = "+totalTime);
        redBlackTree.clear();
    }
    public void analyzeDeletion(int n){
        randomWords.createFileOfWords(n);
        avl.batchInsert("w.txt");
        System.out.println(avl.isEmpty());
        analyzeDeletionAVL();
        redBlackTree.batchInsert("w.txt");
        System.out.println(redBlackTree.isEmpty());
        analyzeDeletionRedBlack();
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.analyzeInsertion(10);
        analysis.analyzeInsertion(100);
        analysis.analyzeInsertion(1000);

        analysis.analyzeDeletion(10);
        analysis.analyzeDeletion(100);
        analysis.analyzeDeletion(1000);

    }
}
