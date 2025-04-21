import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Event {
    private String eventCode;

    public void loadEventCode(byte [] data){
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

    public class FastestLap{
        private byte	vehicleIdx; // Vehicle index of car achieving fastest lap
        private float	lapTime;    // Lap time is in seconds


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
    }

    public class StartLights{
        byte numLights;			// Number of lights showing

        public void loadInfo(byte [] data){
            numLights = data [28];
        }

        public byte getNumLights() {
            return numLights;
        }
    }


}



