import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReactionTime {
    public static void main(String[] args) throws IOException {
        Crono crono = new Crono();
        DatagramSocket server = new DatagramSocket(20777);
        byte[] event = new byte[40];
        DatagramPacket packet = new DatagramPacket(event, event.length);
        server.receive(packet);
        byte[] data = packet.getData();
        Header header = new Header();
        header.loadInfo(data);
        Event eventLightsOut = new Event();
        while (!eventLightsOut.getEventCode().equals("LGOT")){
            server.receive(packet);
            data = packet.getData();
            eventLightsOut.loadEventCode(data);
        }
        crono.start();
        byte clutch = 100;
        while (clutch != 0){
            server.receive(packet);
            data = packet.getData();
            header.loadInfo(data);
            if(header.getID() == 6){
                
            }
        }
    }

    private static class Crono{
        private long initialTime;
        private long endTime;

        public void start(){
            initialTime = System.currentTimeMillis();
        }

        public void stop(){
            endTime = System.currentTimeMillis();

        }

        public long timePassed (){
            return endTime - initialTime;

        }

        public long getStart (){
            return initialTime;

        }

        public long getEnd (){
            return endTime;

        }
    }
}
