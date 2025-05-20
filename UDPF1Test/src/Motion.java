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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
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

    public float getWorldPositionX() {
        return m_worldPositionX;
    }

    public float getWorldPositionY() {
        return m_worldPositionY;
    }

    public float getWorldPositionZ() {
        return m_worldPositionZ;
    }

    public float getWorldVelocityX() {
        return m_worldVelocityX;
    }

    public float getWorldVelocityY() {
        return m_worldVelocityY;
    }

    public float getWorldVelocityZ() {
        return m_worldVelocityZ;
    }

    public short getWorldForwardDirX() {
        return m_worldForwardDirX;
    }

    public short getWorldForwardDirY() {
        return m_worldForwardDirY;
    }

    public short getWorldForwardDirZ() {
        return m_worldForwardDirZ;
    }

    public short getWorldRightDirX() {
        return m_worldRightDirX;
    }

    public short getWorldRightDirY() {
        return m_worldRightDirY;
    }

    public short getWorldRightDirZ() {
        return m_worldRightDirZ;
    }

    public float getGForceLateral() {
        return m_gForceLateral;
    }

    public float getGForceLongitudinal() {
        return m_gForceLongitudinal;
    }

    public float getGForceVertical() {
        return m_gForceVertical;
    }

    public double getGForceXY() {
        return Math.sqrt(m_gForceLateral*m_gForceLateral + m_gForceLongitudinal*m_gForceLongitudinal);
    }

    public float getGForceXY(int decimals) {
        return (float) Math.floor((Math.sqrt(m_gForceLateral * m_gForceLateral + m_gForceLongitudinal * m_gForceLongitudinal)*(Math.pow(10,decimals)))/Math.pow(10,decimals));
    }

    public double getGAngle(){
        return Math.acos(m_gForceLateral/getGForceXY());
    }

    public float getYaw() {
        return m_yaw;
    }

    public float getPitch() {
        return m_pitch;
    }

    public float getRoll() {
        return m_roll;
    }

    public static class MotionPlayer extends Motion{

        // Extra player car ONLY data
        private float m_suspensionPositionRL; // Note: All wheel arrays have the following order:
        private float m_suspensionPositionRR;
        private float m_suspensionPositionFL;
        private float m_suspensionPositionFR;

        private float m_suspensionVelocityRL; // RL, RR, FL, FR
        private float m_suspensionVelocityRR;
        private float m_suspensionVelocityFL;
        private float m_suspensionVelocityFR;

        private float m_suspensionAccelerationRL; // RL, RR, FL, FR
        private float m_suspensionAccelerationRR;
        private float m_suspensionAccelerationFL;
        private float m_suspensionAccelerationFR;

        private float m_wheelSpeedRL; // Speed of each wheel
        private float m_wheelSpeedRR;
        private float m_wheelSpeedFL;
        private float m_wheelSpeedFR;

        private float m_wheelSlipRL; // Slip ratio for each wheel
        private float m_wheelSlipRR;
        private float m_wheelSlipFL;
        private float m_wheelSlipFR;

        private float m_localVelocityX; // Velocity in local space
        private float m_localVelocityY; // Velocity in local space
        private float m_localVelocityZ; // Velocity in local space
        private float m_angularVelocityX; // Angular velocity x-component
        private float m_angularVelocityY; // Angular velocity y-component
        private float m_angularVelocityZ; // Angular velocity z-component
        private float m_angularAccelerationX; // Angular velocity x-component
        private float m_angularAccelerationY; // Angular velocity y-component
        private float m_angularAccelerationZ; // Angular velocity z-component
        private float m_frontWheelsAngle; // Current front wheels angle in radians


        public MotionPlayer(int index) {
            super(index);
        }

        @Override
        public void loadInfo(byte [] data) {
            super.loadInfo(data);

            m_suspensionPositionRL = toFloat(data, 1344);
            m_suspensionPositionRR = toFloat(data, 1348);
            m_suspensionPositionFL = toFloat(data, 1352);
            m_suspensionPositionFR = toFloat(data, 1356);
            m_suspensionVelocityRL = toFloat(data, 1360);
            m_suspensionVelocityRR = toFloat(data, 1364);
            m_suspensionVelocityFL = toFloat(data, 1368);
            m_suspensionVelocityFR = toFloat(data, 1372);
            m_suspensionAccelerationRL = toFloat(data, 1376);
            m_suspensionAccelerationRR = toFloat(data, 1380);
            m_suspensionAccelerationFL = toFloat(data, 1384);
            m_suspensionAccelerationFR = toFloat(data, 1388);
            m_wheelSpeedRL = toFloat(data, 1392);
            m_wheelSpeedRR = toFloat(data, 1396);
            m_wheelSpeedFL = toFloat(data, 1400);
            m_wheelSpeedFR = toFloat(data, 1404);
            m_wheelSlipRL = toFloat(data, 1408);
            m_wheelSlipRR = toFloat(data, 1412);
            m_wheelSlipFL = toFloat(data, 1416);
            m_wheelSlipFR = toFloat(data, 1420);
            m_localVelocityX = toFloat(data, 1424);
            m_localVelocityY = toFloat(data, 1428);
            m_localVelocityZ = toFloat(data, 1432);
            m_angularVelocityX = toFloat(data, 1436);
            m_angularVelocityY = toFloat(data, 1440);
            m_angularVelocityZ = toFloat(data, 1444);
            m_angularAccelerationX = toFloat(data, 1448);
            m_angularAccelerationY = toFloat(data, 1452);
            m_angularAccelerationZ = toFloat(data, 1456);
            m_frontWheelsAngle = toFloat(data, 1460);
        }

        public float getSuspensionPositionRL() {
            return m_suspensionPositionRL;
        }

        public float getSuspensionPositionRR() {
            return m_suspensionPositionRR;
        }

        public float getSuspensionPositionFL() {
            return m_suspensionPositionFL;
        }

        public float getSuspensionPositionFR() {
            return m_suspensionPositionFR;
        }

        public float getSuspensionVelocityRL() {
            return m_suspensionVelocityRL;
        }

        public float getSuspensionVelocityRR() {
            return m_suspensionVelocityRR;
        }

        public float getSuspensionVelocityFL() {
            return m_suspensionVelocityFL;
        }

        public float getSuspensionVelocityFR() {
            return m_suspensionVelocityFR;
        }

        public float getSuspensionAccelerationRL() {
            return m_suspensionAccelerationRL;
        }

        public float getSuspensionAccelerationRR() {
            return m_suspensionAccelerationRR;
        }

        public float getSuspensionAccelerationFL() {
            return m_suspensionAccelerationFL;
        }

        public float getSuspensionAccelerationFR() {
            return m_suspensionAccelerationFR;
        }

        public float getWheelSpeedRL() {
            return m_wheelSpeedRL;
        }

        public float getWheelSpeedRR() {
            return m_wheelSpeedRR;
        }

        public float getWheelSpeedFL() {
            return m_wheelSpeedFL;
        }

        public float getWheelSpeedFR() {
            return m_wheelSpeedFR;
        }

        public float getWheelSlipRL() {
            return m_wheelSlipRL;
        }

        public float getWheelSlipRR() {
            return m_wheelSlipRR;
        }

        public float getWheelSlipFL() {
            return m_wheelSlipFL;
        }

        public float getWheelSlipFR() {
            return m_wheelSlipFR;
        }

        public float getLocalVelocityX() {
            return m_localVelocityX;
        }

        public float getLocalVelocityY() {
            return m_localVelocityY;
        }

        public float getLocalVelocityZ() {
            return m_localVelocityZ;
        }

        public float getAngularVelocityX() {
            return m_angularVelocityX;
        }

        public float getAngularVelocityY() {
            return m_angularVelocityY;
        }

        public float getAngularVelocityZ() {
            return m_angularVelocityZ;
        }

        public float getAngularAccelerationX() {
            return m_angularAccelerationX;
        }

        public float getAngularAccelerationY() {
            return m_angularAccelerationY;
        }

        public float getAngularAccelerationZ() {
            return m_angularAccelerationZ;
        }

        public float getFrontWheelsAngle() {
            return m_frontWheelsAngle;
        }
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
