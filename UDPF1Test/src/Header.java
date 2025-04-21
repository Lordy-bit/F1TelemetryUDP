import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Header {
    private short    m_packetFormat;           // 2022
    private byte    m_gameMajorVersion;        // Game major version - "X.00"
    private byte    m_gameMinorVersion;        // Game minor version - "1.XX"
    private byte    m_packetVersion;           // Version of this packet type, all start from 1
    private byte    m_packetId;                // Identifier for the packet type, see below
    private long    m_sessionUID;              // Unique identifier for the session
    private float  m_sessionTime;              // Session timestamp
    private int    m_frameIdentifier;          // Identifier for the frame the data was retrieved on
    private byte    m_playerCarIndex;          // Index of player's car in the array
    private byte    m_secondaryPlayerCarIndex; // Index of secondary player's car in the array (splitscreen)
                                               // 255 if no second player


    //Header --> first 24 bytes of the packet
    public Header(){
    }

    public void loadInfo(byte [] data){
        m_packetFormat = toShort(data,0);  //0 & 1
        m_gameMajorVersion = data [2];
        m_gameMinorVersion = data [3];
        m_packetVersion = data [4];
        m_packetId = data [5];
        m_sessionUID = toLong(data,6);  //from 6 to 13
        m_sessionTime = toFloat(data,14);  //
        m_frameIdentifier = toInt(data,18);
        m_playerCarIndex = data [22];
        m_secondaryPlayerCarIndex = data [23];
    }

    public void printInfo(){
        System.out.println("Packet Format: "+m_packetFormat);
        System.out.println("Game Major Version: "+m_gameMajorVersion+".00");
        System.out.println("Game Minor Version: "+"1."+m_gameMinorVersion);
        System.out.println("Packet Version: "+m_packetVersion);
        System.out.println("Packet ID: "+m_packetId);
        System.out.println("Session UID: "+m_sessionUID);
        System.out.println("Session Time: "+m_sessionTime);
        System.out.println("Frame Identifier: "+m_frameIdentifier);
        System.out.println("Player Car Index: "+m_playerCarIndex);
        System.out.println("Secondary Player Car Index: "+m_secondaryPlayerCarIndex);
    }

    public void loadAndprintInfo(byte [] data){
        loadInfo(data);
        printInfo();
    }

    public byte getID(){
        return m_packetId;
    }

    public byte getPlayerIndex(){
        return m_playerCarIndex;
    }

    public static short toShort(byte [] data, int i){
        data = Arrays.copyOfRange(data,i,i+2);  // 1 2 3
        short[] shorts = new short[data.length/2];
        // to turn bytes to shorts as either big endian or little endian.
        ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
        return shorts[0];
    }

    public static float toFloat(byte [] data, int i){
        data = Arrays.copyOfRange(data,i,i+4);  // 1 2 3
        float[] floats = new float[data.length/4];
        // to turn bytes to shorts as either big endian or little endian.
        ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().get(floats);
        return floats[0];
    }

    public static int toInt(byte [] data, int i){
        data = Arrays.copyOfRange(data,i,i+4);  // 1 2 3
        int [] ints = new int[data.length/4];
        // to turn bytes to shorts as either big endian or little endian.
        ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().get(ints);
        return ints[0];
    }

    public static long toLong(byte [] data, int i){
        data = Arrays.copyOfRange(data,i,i+8);  // 1 2 3
        long [] longs = new long[data.length/8];
        // to turn bytes to shorts as either big endian or little endian.
        ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).asLongBuffer().get(longs);
        return longs[0];
    }



}

       //Car Telemetry Packet example(first 40 bytes with Header):
       // -27
       // 7
       // 1
       // 18
       // 1
       // 6
       // 80
       // -55
       // 39
       // 32
       // -97
       // 40
       // -92
       // 80
       // -12
       // 43
       // 46
       // 68
       // 28
       // 69
       // 0
       // 0
       // 0
       // -1
       // 18
       // 1
       // 0
       // 0
       // -128
       // 63
       // 88
       // -36
       // 44
       // -69
       // 0
       // 0
       // 0
       // 0
       // 0
       // 7