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

            String currentFormattedTime = LocalTime.now().format(dtf);
            System.out.println("Current time:");
            System.out.println(currentFormattedTime);
            System.out.println("");

            Scanner sc = new Scanner(System.in);
            System.out.println("Time to end simulation:");
            String time = sc.nextLine();
            System.out.println("");

            System.out.println("File Name:");
            String fileName = sc.nextLine()+".txt";
            FileWriter file = new FileWriter(fileName);
            System.out.println("");
            file.flush();

            while(!time.equals(currentFormattedTime)){
                server.receive(packet);
                data = packet.getData();
                for(byte b : data){
                    file.write(b);
                }
                currentFormattedTime = LocalTime.now().format(dtf);
            }
            System.out.println("Simulation finished.");
            file.close();
    }
}