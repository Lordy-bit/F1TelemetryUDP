import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Event {
    private String eventCode;

    public void loadEventCode(byte [] data){
        eventCode = "";
        for (int i=24; i < 28;i++){
            eventCode = eventCode + (char)data[i];
        }
    }

    public void printCode(){
        System.out.println(eventCode);
    }

    public String getEventCode(){
        return eventCode;
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


    public class FastestLap {
        private byte	vehicleIdx; // Vehicle index of car achieving fastest lap
        private float	lapTime;    // Lap time is in seconds

        private final String eventCode = "FTLP";


        public void loadInfo(byte [] data){
            vehicleIdx = data[28];
            lapTime = toFloat(data, 29);
        }

        public byte getVehicleIdx() {
            return vehicleIdx;
        }

        public float getLapTime() {
            return lapTime;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class Retirement{
        private byte   vehicleIdx; // Vehicle index of car retiring

        private final String eventCode = "RTMT";

        public void loadInfo(byte [] data){
            vehicleIdx = data [28];
        }

        public byte getVehicleIdx() {
            return vehicleIdx;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class TeamMateInPits{
        private byte   vehicleIdx; // Vehicle index of team mate

        private final String eventCode = "TMPT";

        public void loadInfo(byte [] data){
            vehicleIdx = data[28];
        }

        public byte getVehicleIdx() {
            return vehicleIdx;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class RaceWinner{
        private byte   vehicleIdx; // Vehicle index of the race winner

        private final String eventCode = "RCWN";

        public void loadInfo(byte [] data){
            vehicleIdx = data[28];
        }

        public byte getVehicleIdx() {
            return vehicleIdx;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class Penalty{
        private byte penaltyType;		    // Penalty type – see Appendices
        private byte infringementType;		// Infringement type – see Appendices
        private byte vehicleIdx;         	// Vehicle index of the car the penalty is applied to
        private byte otherVehicleIdx;    	// Vehicle index of the other car involved
        private byte time;               	// Time gained, or time spent doing action in seconds
        private byte lapNum;             	// Lap the penalty occurred on
        private byte placesGained;       	// Number of places gained by this

        private final String eventCode = "PENA";

        public void loadInfo(byte [] data){
            penaltyType = data[28];
            infringementType = data[29];
            vehicleIdx = data[30];
            otherVehicleIdx = data[31];
            time = data[32];
            lapNum = data[33];
            placesGained = data[34];
        }

        public byte getPenaltyType() {
            return penaltyType;
        }

        public byte getInfringementType() {
            return infringementType;
        }

        public byte getVehicleIdx() {
            return vehicleIdx;
        }

        public byte getOtherVehicleIdx() {
            return otherVehicleIdx;
        }

        public byte getTime() {
            return time;
        }

        public byte getLapNum() {
            return lapNum;
        }

        public byte getPlacesGained() {
            return placesGained;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class SpeedTrap{
        private byte vehicleIdx;		           // Vehicle index of the vehicle triggering speed trap
        private float speed;      		           // Top speed achieved in kilometres per hour
        private byte isOverallFastestInSession;    // Overall fastest speed in session = 1, otherwise 0
        private byte isDriverFastestInSession;     // Fastest speed for driver in session = 1, otherwise 0
        private byte fastestVehicleIdxInSession;   // Vehicle index of the vehicle that is the fastest
                                                   // in this session
        private float fastestSpeedInSession;       // Speed of the vehicle that is the fastest
                                                   // in this session

        private final String eventCode = "SPTP";

        public void loadInfo(byte [] data){
            vehicleIdx = data[28];
            speed = toFloat(data,29);
            isOverallFastestInSession = data[32];
            isDriverFastestInSession = data[33];
            fastestVehicleIdxInSession = data [34];
            fastestSpeedInSession = toFloat(data,35);
        }

        public byte getVehicleIdx() {
            return vehicleIdx;
        }

        public float getSpeed() {
            return speed;
        }

        public boolean getIsOverallFastestInSession() {
            if (isOverallFastestInSession == 1){
                return true;
            }else {
                return false;
            }
        }

        public boolean getIsDriverFastestInSession() {
            if (isDriverFastestInSession == 1){
                return true;
            }else {
                return false;
            }
        }

        public byte getFastestVehicleIdxInSession() {
            return fastestVehicleIdxInSession;
        }

        public float getFastestSpeedInSession() {
            return fastestSpeedInSession;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class StartLights{
        private byte numLights;			// Number of lights showing

        private final String eventCode = "STLG";

        public void loadInfo(byte [] data){
            numLights = data [28];
        }

        public byte getNumLights() {
            return numLights;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class DriveThroughPenaltyServed{
        private byte vehicleIdx;                 // Vehicle index of the vehicle serving drive through

        private final String eventCode = "DTSV";

        public void loadInfo(byte [] data){
            vehicleIdx = data[28];
        }

        public byte getVehicleIdx() {
            return vehicleIdx;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class StopGoPenaltyServed{
        private byte vehicleIdx;                 // Vehicle index of the vehicle serving stop go

        private final String eventCode = "SGSV";

        public void loadInfo(byte [] data){
            vehicleIdx = data[28];
        }

        public byte getVehicleIdx() {
            return vehicleIdx;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class Flashback{
        private int   flashbackFrameIdentifier;   // Frame identifier flashed back to
        private float flashbackSessionTime;       // Session time flashed back to

        private final String eventCode = "FLBK";

        public void loadInfo(byte [] data){
            flashbackFrameIdentifier = toInt(data,28);
            flashbackSessionTime = toFloat(data,32);
        }

        public int getFlashbackFrameIdentifier() {
            return flashbackFrameIdentifier;
        }

        public float getFlashbackSessionTime() {
            return flashbackSessionTime;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

    public class Buttons{
        private int m_buttonStatus;    // Bit flags specifying which buttons are being pressed
                                       // currently - see appendices

        private final String eventCode = "BUTN";

        public void loadInfo(byte [] data){
            m_buttonStatus = toInt(data,28);
        }

        public int getButtonStatus() {
            return m_buttonStatus;
        }

        public String getEventCode() {
            return eventCode;
        }
    }

}
