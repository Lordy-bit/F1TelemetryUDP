import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {
    private BufferedReader reader;

    DataReader(String file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
    }

    public byte[] read(int line, int delay) throws IOException {
            byte[] data = new byte [1464];
            int lineIndex = 0;
            while (lineIndex != line){
                reader.readLine();
                lineIndex ++;
            }
            String textLine = reader.readLine();
            for (int i = 0; i < textLine.length(); i++){
                data [i] = (byte)textLine.charAt(i);
            }
            sleep(delay);
            return data;
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
