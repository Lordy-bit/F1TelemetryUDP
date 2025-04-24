import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Header header = new Header();
        DataReader reader = new DataReader("data.txt");
        byte[] data = reader.read(109019, 1000);

        header.loadInfo(data);
        header.printInfo();
    }
}

