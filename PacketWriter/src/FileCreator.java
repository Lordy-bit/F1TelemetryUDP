import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FileCreator {
        public static void main(String[] args) throws IOException {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");


            DatagramSocket server = new DatagramSocket(20777);
            byte[] event = new byte[1464];
            DatagramPacket packet = new DatagramPacket(event, event.length);
            byte [] data;



            FileWriter file = new FileWriter("data.txt");
            file.flush();
            String currentFormattedTime = LocalTime.now().format(dtf);
            System.out.println(currentFormattedTime);
            Scanner sc = new Scanner(System.in);
            String time = sc.nextLine();

            while(!time.equals(currentFormattedTime)){
                server.receive(packet);
                data = packet.getData();
                for(byte b : data){
                    file.write(b);
                }
                currentFormattedTime = LocalTime.now().format(dtf);
            }
            file.close();
    }
}