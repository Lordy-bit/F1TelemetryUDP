import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class CarTelemetry {
    private int index;
    // Note: All wheel arrays have the following order:
    // RL, RR, FL, FR


    private short     m_speed;                        // Speed of car in kilometres per hour
    private float     m_throttle;                     // Amount of throttle applied (0.0 to 1.0)
    private float     m_steer;                        // Steering (-1.0 (full lock left) to 1.0 (full lock right))
    private float     m_brake;                        // Amount of brake applied (0.0 to 1.0)
    private byte      m_clutch;                       // Amount of clutch applied (0 to 100)
    private byte      m_gear;                         // Gear selected (1-8, N=0, R=-1)
    private short     m_engineRPM;                    // Engine RPM
    private byte      m_drs;                          // 0 = off, 1 = on
    private byte      m_revLightsPercent;             // Rev lights indicator (percentage)
    private short     m_revLightsBitValue;            // Rev lights (bit 0 = leftmost LED, bit 14 = rightmost LED)

    private short     m_brakesTemperatureRL;          // Brakes temperature (celsius)
    private short     m_brakesTemperatureRR;
    private short     m_brakesTemperatureFL;
    private short     m_brakesTemperatureFR;

    private byte      m_tyresSurfaceTemperatureRL;    // Tyres surface temperature (celsius)
    private byte      m_tyresSurfaceTemperatureRR;
    private byte      m_tyresSurfaceTemperatureFL;
    private byte      m_tyresSurfaceTemperatureFR;

    private byte      m_tyresInnerTemperatureRL;     // Tyres inner temperature (celsius)
    private byte      m_tyresInnerTemperatureRR;
    private byte      m_tyresInnerTemperatureFL;
    private byte      m_tyresInnerTemperatureFR;

    private short     m_engineTemperature;           // Engine temperature (celsius)

    private float     m_tyresPressureRL;            // Tyres pressure (PSI)
    private float     m_tyresPressureRR;
    private float     m_tyresPressureFL;
    private float     m_tyresPressureFR;

    private byte      m_surfaceTypeRL;               // Driving surface, see appendices
    private byte      m_surfaceTypeRR;
    private byte      m_surfaceTypeFL;
    private byte      m_surfaceTypeFR;

    //functional
    private float prev_steer;

    public CarTelemetry (int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void loadInfo(byte [] data){
        int mul = 60*index;

        m_speed = toShort(data,24+mul);
        m_throttle = toFloat(data,26+mul);
        prev_steer = m_steer;
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


    public short getSpeed(){
        return m_speed;
    }

    public float getThrottle(){
        return m_throttle;
    }

    public byte getThrottlePercent(){
        return (byte)(m_throttle * 100);
    }

    public float getSteer(){
        return m_steer;
    }

    public double getSteerInDeg() {
        return Math.acos(m_steer);
    }

    public double getSteerDiff(){
        return prev_steer-m_steer;
    }

    public double getSteerDiffInDeg(){return Math.acos(getSteerDiff());}

    public float getBrake(){
        return m_brake;
    }

    public byte getBrakePercent(){
        return (byte)(m_brake * 100);
    }

    public byte getClutch(){
        return m_clutch;
    }

    public byte getGear() {
        return m_gear;
    }

    public short getEngineRPM() {
        return m_engineRPM;
    }

    public boolean DRSisOn() {
        if(m_drs == 1){
            return true;
        }
        return false;
    }

    public byte getRevLightsPercent(){
        return m_revLightsPercent;
    }

    public short getRevLightsBitValue(){
        return m_revLightsBitValue;
    }



    public short getBrakesTemperatureRL(){
        return m_brakesTemperatureRL;
    }

    public short getBrakesTemperatureRR(){
        return m_brakesTemperatureRR;
    }

    public short getBrakesTemperatureFL(){
        return m_brakesTemperatureFL;
    }

    public short getBrakesTemperatureFR(){
        return m_brakesTemperatureFR;
    }



    public byte getTyresSurfaceTemperatureRL() {
        return m_tyresSurfaceTemperatureRL;
    }

    public byte getTyresSurfaceTemperatureRR() {
        return m_tyresSurfaceTemperatureRR;
    }

    public byte getTyresSurfaceTemperatureFL() {
        return m_tyresSurfaceTemperatureFL;
    }

    public byte getTyresSurfaceTemperatureFR() {
        return m_tyresSurfaceTemperatureFR;
    }



    public byte getTyresInnerTemperatureRL() {
        return m_tyresInnerTemperatureRL;
    }

    public byte getTyresInnerTemperatureRR() {
        return m_tyresInnerTemperatureRR;
    }

    public byte getTyresInnerTemperatureFL() {
        return m_tyresInnerTemperatureFL;
    }

    public byte getTyresInnerTemperatureFR() {
        return m_tyresInnerTemperatureFR;
    }




    public short getEngineTemperature() {
        return m_engineTemperature;
    }



    public float getM_tyresPressureRL() {
        return m_tyresPressureRL;
    }   //not troncated

    public float getM_tyresPressureRR() {
        return m_tyresPressureRR;
    }

    public float getM_tyresPressureFL() {
        return m_tyresPressureFL;
    }

    public float getM_tyresPressureFR() {
        return m_tyresPressureFR;
    }

    public float getTyresPressureRL(){
        int m = (int)(m_tyresPressureRL*10);
        return (float)(m/10);
    }                              //troncated

    public float getTyresPressureRR(){
        int m = (int)(m_tyresPressureRR*10);
        return (float)(m/10);
    }

    public float getTyresPressureFL(){
        int m = (int)(m_tyresPressureFL*10);
        return (float)(m/10);
    }

    public float getTyresPressureFR(){
        int m = (int)(m_tyresPressureFR*10);
        return (float)(m/10);
    }



    public byte getM_surfaceTypeRL() {
        return m_surfaceTypeRL;
    }

    public byte getM_surfaceTypeRR() {
        return m_surfaceTypeRR;
    }

    public byte getM_surfaceTypeFL() {
        return m_surfaceTypeFL;
    }

    public byte getM_surfaceTypeFR() {
        return m_surfaceTypeFR;
    }

    public boolean innerTempsInWindow(int min, int max){
        for (byte b : new byte[]{m_tyresInnerTemperatureFL,m_tyresInnerTemperatureFR,m_tyresInnerTemperatureRL,m_tyresInnerTemperatureRR}){
            if (b < min && b > max){
                return false;
            }
        }
        return true;
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
