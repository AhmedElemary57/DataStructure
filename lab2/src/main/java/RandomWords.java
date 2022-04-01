import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class RandomWords {

    /**
     * generate a random string
     * */
    private String generateString(int n) {

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(str.length() * Math.random());
            sb.append(str.charAt(index));
        }

        return sb.toString();
    }

    /**
     * generate a text file of random n strings
     * */
    public void createFileOfWords(int numOfWords){
        Random random = new Random();
        try{
            File file = new File("w.txt");
            if (!file.exists())
                file.createNewFile();
            FileWriter fileWriter =
                    new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            while(numOfWords != 0){
                int charsNumber = random.nextInt(20) + 1;
                bw.write(generateString(charsNumber) + "\n");
                numOfWords--;
            }
            bw.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        RandomWords randomWords = new RandomWords();
        randomWords.createFileOfWords(100);
    }
}
