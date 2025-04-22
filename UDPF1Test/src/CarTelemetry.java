import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class CarTelemetry {
    // Note: All wheel arrays have the following order:
    // RL, RR, FL, FR


    short    m_speed;                        // Speed of car in kilometres per hour
    float     m_throttle;                    // Amount of throttle applied (0.0 to 1.0)
    float     m_steer;                       // Steering (-1.0 (full lock left) to 1.0 (full lock right))
    float     m_brake;                       // Amount of brake applied (0.0 to 1.0)
    byte     m_clutch;                       // Amount of clutch applied (0 to 100)
    byte      m_gear;                        // Gear selected (1-8, N=0, R=-1)
    short    m_engineRPM;                    // Engine RPM
    byte     m_drs;                          // 0 = off, 1 = on
    byte     m_revLightsPercent;             // Rev lights indicator (percentage)
    short    m_revLightsBitValue;            // Rev lights (bit 0 = leftmost LED, bit 14 = rightmost LED)

    short    m_brakesTemperatureRL;          // Brakes temperature (celsius)
    short    m_brakesTemperatureRR;
    short    m_brakesTemperatureFL;
    short    m_brakesTemperatureFR;

    byte     m_tyresSurfaceTemperatureRL;    // Tyres surface temperature (celsius)
    byte     m_tyresSurfaceTemperatureRR;
    byte     m_tyresSurfaceTemperatureFL;
    byte     m_tyresSurfaceTemperatureFR;

    byte     m_tyresInnerTemperatureRL;     // Tyres inner temperature (celsius)
    byte     m_tyresInnerTemperatureRR;
    byte     m_tyresInnerTemperatureFL;
    byte     m_tyresInnerTemperatureFR;

    short    m_engineTemperature;           // Engine temperature (celsius)

    float     m_tyresPressureRL;            // Tyres pressure (PSI)
    float     m_tyresPressureRR;
    float     m_tyresPressureFL;
    float     m_tyresPressureFR;

    byte     m_surfaceTypeRL;               // Driving surface, see appendices
    byte     m_surfaceTypeRR;
    byte     m_surfaceTypeFL;
    byte     m_surfaceTypeFR;




    public void loadInfo(byte [] data, int index){
        int mul = 60*index;

        m_speed = toShort(data,24+mul);
        m_throttle = toFloat(data,26+mul);
        m_steer = toFloat(data,30+mul);
        m_brake = toFloat(data,34+mul);
        m_clutch = data[38];
        m_gear = data[39];
        m_engineRPM = toShort(data, 40+mul);
        m_drs = data[42+mul];
        m_revLightsPercent = data[43+mul];
        m_revLightsBitValue = toShort(data,44+mul);

        m_brakesTemperatureRL = toShort(data,46+mul);
        m_brakesTemperatureRR = toShort(data,48+mul);
        m_brakesTemperatureFL = toShort(data,50+mul);
        m_brakesTemperatureFR = toShort(data,52+mul);

        m_tyresSurfaceTemperatureRL = data[54+mul];
        m_tyresSurfaceTemperatureRR = data[55+mul];
        m_tyresSurfaceTemperatureFL = data[56+mul];
        m_tyresSurfaceTemperatureFR = data[57+mul];

        m_tyresInnerTemperatureRL = data[58+mul];
        m_tyresInnerTemperatureRR = data[59+mul];
        m_tyresInnerTemperatureFL = data[60+mul];
        m_tyresInnerTemperatureFR = data[61+mul];

        m_engineTemperature = toShort(data,62+mul);

        m_tyresPressureRL = toFloat(data,64+mul);
        m_tyresPressureRR = toFloat(data,68+mul);
        m_tyresPressureFL = toFloat(data,72+mul);
        m_tyresPressureFR = toFloat(data,76+mul);

        m_surfaceTypeRL = data[80];
        m_surfaceTypeRR = data[81];
        m_surfaceTypeFL = data[82];
        m_surfaceTypeFR = data[83];

    }


    public void printInfo(){
        System.out.println("Speed: "+m_speed);
        System.out.println("Throttle: "+m_throttle);
        System.out.println("Steer: "+m_steer);
        System.out.println("Brake: "+m_brake);
        System.out.println("Clutch: "+m_clutch);
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
