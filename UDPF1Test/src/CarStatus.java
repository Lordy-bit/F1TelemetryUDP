import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class CarStatus {
    private int    index;

    private byte   m_tractionControl;           // Traction control - 0 = off, 1 = medium, 2 = full
    private byte   m_antiLockBrakes;            // 0 (off) - 1 (on)
    private byte   m_fuelMix;                   // Fuel mix - 0 = lean, 1 = standard, 2 = rich, 3 = max
    private byte   m_frontBrakeBias;            // Front brake bias (percentage)
    private byte   m_pitLimiterStatus;          // Pit limiter status - 0 = off, 1 = on
    private float  m_fuelInTank;                // Current fuel mass
    private float  m_fuelCapacity;              // Fuel capacity
    private float  m_fuelRemainingLaps;         // Fuel remaining in terms of laps (value on MFD)
    private short  m_maxRPM;                    // Cars max RPM, point of rev limiter
    private short  m_idleRPM;                   // Cars idle RPM
    private byte   m_maxGears;                  // Maximum number of gears
    private byte   m_drsAllowed;                // 0 = not allowed, 1 = allowed
    private short  m_drsActivationDistance;     // 0 = DRS not available, non-zero - DRS will be available
                                                // in [X] metres
    private byte   m_actualTyreCompound;        // F1 Modern - 16 = C5, 17 = C4, 18 = C3, 19 = C2, 20 = C1
                                                // 7 = inter, 8 = wet
                                                // F1 Classic - 9 = dry, 10 = wet
                                                // F2 – 11 = super soft, 12 = soft, 13 = medium, 14 = hard
                                                // 15 = wet
    private byte   m_visualTyreCompound;        // F1 visual (can be different from actual compound)
                                                // 16 = soft, 17 = medium, 18 = hard, 7 = inter, 8 = wet
                                                // F1 Classic – same as above
                                                // F2 ‘19, 15 = wet, 19 – super soft, 20 = soft
                                                // 21 = medium , 22 = hard
    private byte   m_tyresAgeLaps;              // Age in laps of the current set of tyres
    private byte   m_vehicleFiaFlags;           // -1 = invalid/unknown, 0 = none, 1 = green
                                                // 2 = blue, 3 = yellow, 4 = red
    private float  m_ersStoreEnergy;            // ERS energy store in Joules
    private byte   m_ersDeployMode;             // ERS deployment mode, 0 = none, 1 = medium
                                                // 2 = hotlap, 3 = overtake
    private float  m_ersHarvestedThisLapMGUK;   // ERS energy harvested this lap by MGU-K
    private float  m_ersHarvestedThisLapMGUH;   // ERS energy harvested this lap by MGU-H
    private float  m_ersDeployedThisLap;        // ERS energy deployed this lap
    private byte   m_networkPaused;             // Whether the car is paused in a network game


    public CarStatus(int index){
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void loadInfo(byte [] data){
        int mul = 47*index;

        m_tractionControl = data[24+mul];
        m_antiLockBrakes = data[25+mul];
        m_fuelMix = data[26+mul];
        m_frontBrakeBias = data[27+mul];
        m_pitLimiterStatus = data[28+mul];
        m_fuelInTank = toFloat(data, 29+mul);
        m_fuelCapacity = toFloat(data, 33+mul);
        m_fuelRemainingLaps = toFloat(data, 37+mul);
        m_maxRPM = toShort(data,41+mul);
        m_idleRPM = toShort(data, 43+mul);
        m_maxGears = data[45+mul];
        m_drsAllowed = data[46+mul];
        m_drsActivationDistance = toShort(data, 47+mul);
        m_actualTyreCompound = data[49+mul];
        m_visualTyreCompound = data[50+mul];
        m_tyresAgeLaps = data[51+mul];
        m_vehicleFiaFlags = data[52+mul];
        m_ersStoreEnergy = toFloat(data,53+mul);
        m_ersDeployMode = data[57+mul];
        m_ersHarvestedThisLapMGUK = toFloat(data, 58+mul);
        m_ersHarvestedThisLapMGUH = toFloat(data, 62+mul);
        m_ersDeployedThisLap = toFloat(data, 66+mul);
        m_networkPaused = data[70+mul];
    }

    public byte getTractionControl() {
        return m_tractionControl;
    }


    public boolean getAntiLockBrakes() {
        if(m_antiLockBrakes == 1) {
            return true;
        }
        return false;
    }


    public byte getM_fuelMix() {
        return m_fuelMix;
    }


    public byte getFrontBrakeBias() {
        return m_frontBrakeBias;
    }


    public boolean getPitLimiterStatus() {
        if(m_pitLimiterStatus == 1) {
            return true;
        }
        return false;
    }


    public float getFuelInTank() {
        return m_fuelInTank;
    }


    public float getFuelCapacity() {
        return m_fuelCapacity;
    }


    public float getFuelRemainingLaps() {
        return m_fuelRemainingLaps;
    }


    public short getMaxRPM() {
        return m_maxRPM;
    }


    public short getIdleRPM() {
        return m_idleRPM;
    }


    public byte getMaxGears() {
        return m_maxGears;
    }


    public boolean getDrsAllowed() {
        if(m_drsAllowed == 1) {
            return true;
        }
        return false;
    }


    public short getDrsActivationDistance() {
        return m_drsActivationDistance;
    }


    public byte getActualTyreCompound() {
        return m_actualTyreCompound;
    }


    public String getActualTyreCompoundStr(){
        if (m_actualTyreCompound == 16){
            return "C5";
        }
        if (m_actualTyreCompound == 17){
            return "C4";
        }
        if (m_actualTyreCompound == 18){
            return "C3";
        }
        if (m_actualTyreCompound == 19){
            return "C2";
        }
        if (m_actualTyreCompound == 20){
            return "C1";
        }
        if (m_actualTyreCompound == 7){
            return "I";
        }
        if (m_actualTyreCompound == 8 || m_actualTyreCompound == 10 || m_actualTyreCompound == 15){
            return "W";
        }
        if (m_actualTyreCompound == 9){
            return "D";
        }
        if (m_actualTyreCompound == 11){
            return "SS";
        }
        if (m_actualTyreCompound == 12){
            return "S";
        }
        if (m_actualTyreCompound == 13){
            return "M";
        }
        if (m_actualTyreCompound == 14){
            return "H";
        }
        return "";
    }


    public byte getVisualTyreCompound() {
        return m_visualTyreCompound;
    }


    public String getVisualTyreCompoundStr(){
        if (m_visualTyreCompound == 16 || m_visualTyreCompound == 12 || m_visualTyreCompound == 20){
            return "S";
        }
        if (m_visualTyreCompound == 17 || m_visualTyreCompound == 13 || m_visualTyreCompound == 21){
            return "M";
        }
        if (m_visualTyreCompound == 18 || m_visualTyreCompound == 14 || m_visualTyreCompound == 22){
            return "H";
        }
        if (m_visualTyreCompound == 7){
            return "I";
        }
        if (m_visualTyreCompound == 8 || m_visualTyreCompound == 10 || m_visualTyreCompound == 15){
            return "W";
        }
        if (m_visualTyreCompound == 9){
            return "D";
        }
        if (m_visualTyreCompound == 11 || m_visualTyreCompound == 19){
            return "SS";
        }
        return "";
    }


    public byte getTyresAgeLaps() {
        return m_tyresAgeLaps;
    }


    public byte getVehicleFiaFlags() {
        return m_vehicleFiaFlags;
    }


    public float getErsStoreEnergy() {
        return m_ersStoreEnergy;
    }

    public double getErsStoreEnergyPercent(int decimals) {
        return (double)((int)(((m_ersStoreEnergy/10000)/4)*(10*decimals)))/(10*decimals);
    }


    public byte getErsDeployMode() {
        return m_ersDeployMode;
    }


    public float getErsHarvestedThisLapMGUK() {
        return m_ersHarvestedThisLapMGUK;
    }


    public float getErsHarvestedThisLapMGUH() {
        return m_ersHarvestedThisLapMGUH;
    }


    public float getErsDeployedThisLap() {
        return m_ersDeployedThisLap;
    }


    public double getErsDeployedThisLapPercent(int decimals) {
        return (double)((int)(((m_ersDeployedThisLap/10000)/4)*(10*decimals)))/(10*decimals);
    }


    public boolean getNetworkPaused() {
        if(m_networkPaused == 1){
            return true;
        }
        return false;
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
