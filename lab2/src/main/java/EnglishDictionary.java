import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EnglishDictionary {
    private RedBlackTree<String> dictionary;

    public EnglishDictionary(){
        dictionary = new RedBlackTree<>();
    }

    public void load(String pathName) {
        try {
            File file = new File(pathName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                this.dictionary.insert(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occur while reading this file");
        }
    }

    public int size(){
        return dictionary.size();
    }

    public void insertWord(String word){
        dictionary.insert(word);
    }

    public boolean lookUp(String word){
        return dictionary.search(word);
    }

    public void remove(String word){
        dictionary.delete(word);
    }

    public int batchLookUp(String pathName){
        int count = 0;
        try {
            File file = new File(pathName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                if(dictionary.search(word)){
                    System.out.println(word + ": " + "YES");
                    count++;
                }
                else{
                    System.out.println(word + ": " + "NO");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occur while reading this file");
        }
        return count;
    }

    public void batchDelete(String pathName){
        try {
            File file = new File(pathName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                dictionary.delete(word);
                System.out.println(word + ": " + "Deleted");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occur while reading this file");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnglishDictionary dictionary = new EnglishDictionary();
        System.out.println("""
                the available operations are:
                 (load)    spaced by dictionary path
                 (size)
                 (insert)  spaced by word name
                 (look-up) spaced by word name
                 (remove)  spaced by word name
                 (batch-lookups)   spaced by queries path
                 (batch-deletions) spaced by queries path
                 (exit) to exit the program""");
        String input = scanner.nextLine();
        String[] arr = input.split("\\s+");
        do{
            switch (arr[0]){
                case "load":
                    dictionary.load(arr[1]);
                    break;
                case "size":
                    System.out.println("Size: " + dictionary.size());
                    break;
                case "insert":
                    dictionary.insertWord(arr[1]);
                    break;
                case "look-up":
                    if(dictionary.lookUp(arr[1]))
                        System.out.println("YES");
                    else
                        System.out.println("NO");
                    break;
                case "remove":
                    dictionary.remove(arr[1]);
                    break;
                case "batch-lookups":
                    System.out.println("Number of words: " + dictionary.batchLookUp(arr[1]));
                    break;
                case "batch-deletions":
                    dictionary.batchDelete(arr[1]);
                    break;
                default:
                    System.out.println("Try again");
            }
            input = scanner.nextLine();
            arr = input.split("\\s+");
        }while (!arr[0].equals("exit"));
    }
}
