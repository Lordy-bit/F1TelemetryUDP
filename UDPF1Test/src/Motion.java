import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Motion {
    private int index;

    private float   m_worldPositionX;      // World space X position
    private float   m_worldPositionY;      // World space Y position
    private float   m_worldPositionZ;      // World space Z position
    private float   m_worldVelocityX;      // Velocity in world space X
    private float   m_worldVelocityY;      // Velocity in world space Y
    private float   m_worldVelocityZ;      // Velocity in world space Z
    private short   m_worldForwardDirX;    // World space forward X direction (normalised)
    private short   m_worldForwardDirY;    // World space forward Y direction (normalised)
    private short   m_worldForwardDirZ;    // World space forward Z direction (normalised)
    private short   m_worldRightDirX;      // World space right X direction (normalised)
    private short   m_worldRightDirY;      // World space right Y direction (normalised)
    private short   m_worldRightDirZ;      // World space right Z direction (normalised)
    private float   m_gForceLateral;       // Lateral G-Force component
    private float   m_gForceLongitudinal;  // Longitudinal G-Force component
    private float   m_gForceVertical;      // Vertical G-Force component
    private float   m_yaw;                 // Yaw angle in radians
    private float   m_pitch;               // Pitch angle in radians
    private float   m_roll;                // Roll angle in radians

    public Motion (int index){
        this.index = index;
    }

    public void loadInfo(byte [] data){
        int mul = 60 * index;

        m_worldPositionX = toFloat(data,24+mul);
        m_worldPositionY = toFloat(data,28+mul);
        m_worldPositionZ = toFloat(data, 32+mul);
        m_worldVelocityX = toFloat(data, 36+mul);
        m_worldVelocityY = toFloat(data, 40+mul);
        m_worldVelocityZ = toFloat(data, 44+mul);
        m_worldForwardDirX = toShort(data, 48+mul);
        m_worldForwardDirY = toShort(data, 50+mul);
        m_worldForwardDirZ = toShort(data, 52+mul);
        m_worldRightDirX = toShort(data, 54+mul);
        m_worldRightDirY = toShort(data, 56+mul);
        m_worldRightDirZ = toShort(data, 58+mul);
        m_gForceLateral = toFloat(data, 60+mul);
        m_gForceLongitudinal = toFloat(data, 64+mul);
        m_gForceVertical = toFloat(data, 68+mul);
        m_yaw = toFloat(data, 72+mul);
        m_pitch = toFloat(data, 76+mul);
        m_roll = toFloat(data, 80+mul);
    }

    public void loadGforcesInfo(byte [] data){
        int mul = 60 * index;

        m_gForceLateral = toFloat(data, 60+mul);
        m_gForceLongitudinal = toFloat(data, 64+mul);
        m_gForceVertical = toFloat(data, 68+mul);
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
