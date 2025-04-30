import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {
    private FileReader reader;

    DataReader(String file) throws FileNotFoundException {
        reader = new FileReader(file);
    }

    public byte[] read(int delay) throws IOException {
        byte[] data = new byte [1464];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte)reader.read();
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
