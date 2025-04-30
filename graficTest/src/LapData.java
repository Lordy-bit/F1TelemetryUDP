import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class LapData {
    private int index;                               //index of this specific car in the array

    private int      m_lastLapTimeInMS;	       	     // Last lap time in milliseconds
    private int      m_currentLapTimeInMS; 	         // Current time around the lap in milliseconds
    private short    m_sector1TimeInMS;              // Sector 1 time in milliseconds
    private short    m_sector2TimeInMS;              // Sector 2 time in milliseconds
    private float    m_lapDistance;		             // Distance vehicle is around current lap in metres – could
                                                     // be negative if line hasn’t been crossed yet
    private float    m_totalDistance;		         // Total distance travelled in session in metres – could
                                                     // be negative if line hasn’t been crossed yet
    private float    m_safetyCarDelta;               // Delta in seconds for safety car
    private byte     m_carPosition;   	             // Car race position
    private byte     m_currentLapNum;		         // Current lap number
    private byte     m_pitStatus;            	     // 0 = none, 1 = pitting, 2 = in pit area
    private byte     m_numPitStops;            	     // Number of pit stops taken in this race
    private byte     m_sector;               	     // 0 = sector1, 1 = sector2, 2 = sector3
    private byte     m_currentLapInvalid;    	     // Current lap invalid - 0 = valid, 1 = invalid
    private byte     m_penalties;            	     // Accumulated time penalties in seconds to be added
    private byte     m_warnings;                     // Accumulated number of warnings issued
    private byte     m_numUnservedDriveThroughPens;  // Num drive through pens left to serve
    private byte     m_numUnservedStopGoPens;        // Num stop go pens left to serve
    private byte     m_gridPosition;         	     // Grid position the vehicle started the race in
    private byte     m_driverStatus;         	     // Status of driver - 0 = in garage, 1 = flying lap
                                                     // 2 = in lap, 3 = out lap, 4 = on track
    private byte     m_resultStatus;                 // Result status - 0 = invalid, 1 = inactive, 2 = active
                                                     // 3 = finished, 4 = didnotfinish, 5 = disqualified
                                                     // 6 = not classified, 7 = retired
    private byte     m_pitLaneTimerActive;     	     // Pit lane timing, 0 = inactive, 1 = active
    private short    m_pitLaneTimeInLaneInMS;   	 // If active, the current time spent in the pit lane in ms
    private short    m_pitStopTimerInMS;        	 // Time of the actual pit stop in ms
    private byte     m_pitStopShouldServePen;   	 // Whether the car should serve a penalty at this stop


    //global
    private byte	 m_timeTrialPBCarIdx; 	         // Index of Personal Best car in time trial (255 if invalid)
    private byte	 m_timeTrialRivalCarIdx; 	     // Index of Rival car in time trial (255 if invalid)


    //functional
    private int      m_bestLapTimeInMS;              //best lap registered
    private int      m_optimalLapTimeMS;             //optimal lap obtained by adding the best sectors together
    private short    m_sector3TimeInMS;
    private short    m_bestSector1TimeInMS;
    private short    m_bestSector2TimeInMS;
    private short    m_bestSector3TimeInMS;
    private short    lastS1;
    private short    lastS2;


    public LapData (int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }  //not recommended



    public void loadInfo(byte [] data){
        int mul = 43*index;

        m_lastLapTimeInMS = toInt(data,24+mul);

        if(m_bestLapTimeInMS == 0 || m_lastLapTimeInMS < m_bestLapTimeInMS && m_lastLapTimeInMS > 0){
            m_bestLapTimeInMS = m_lastLapTimeInMS;
        }

        m_currentLapTimeInMS = toInt(data,28+mul);

        m_sector1TimeInMS = (short) Math.abs(toShort(data,32+mul));

        if(m_sector1TimeInMS != 0){
            lastS1 = m_sector1TimeInMS;
        }

        if(m_bestSector1TimeInMS == 0 && lastS1 >= 0 || lastS1 < m_bestSector1TimeInMS && lastS1 > 0){
            m_bestSector1TimeInMS = lastS1;
        }

        m_sector2TimeInMS = (short)Math.abs(toShort(data, 34+mul));

        if(m_sector2TimeInMS != 0){
            lastS2 = m_sector2TimeInMS;
        }

        if(m_bestSector2TimeInMS == 0 && lastS2 >= 0 || lastS2 < m_bestSector2TimeInMS && lastS2 > 0){
            m_bestSector2TimeInMS = lastS2;
        }

        m_lapDistance = toFloat(data,36+mul);
        m_totalDistance = toFloat(data,40+mul);
        m_safetyCarDelta = toFloat(data,44+mul);
        m_carPosition = data[48+mul];
        m_currentLapNum = data[49+mul];
        m_pitStatus = data[50+mul];
        m_numPitStops = data[51+mul];
        m_sector = data[52+mul];
        m_currentLapInvalid = data[53+mul];
        m_penalties = data[54+mul];
        m_warnings = data[55+mul];
        m_numUnservedDriveThroughPens = data[56+mul];
        m_numUnservedStopGoPens = data[57+mul];
        m_gridPosition = data[58+mul];
        m_driverStatus = data[59+mul];
        m_resultStatus = data[60+mul];
        m_pitLaneTimerActive = data[61+mul];
        m_pitLaneTimeInLaneInMS = toShort(data,62+mul);
        m_pitStopTimerInMS = toShort(data,64);
        m_pitStopShouldServePen = data[66+mul];

        m_timeTrialPBCarIdx = data[970];
        m_timeTrialRivalCarIdx = data[971];


        if(m_sector == 0 && lastS1 > 0 && lastS2 > 0){
            m_sector3TimeInMS = (short)(m_lastLapTimeInMS-(lastS1+lastS2));
        }

        if(m_bestSector3TimeInMS == 0 && m_sector3TimeInMS >= 0 || m_sector3TimeInMS < m_bestSector3TimeInMS && m_sector3TimeInMS > 0){
            m_bestSector3TimeInMS = m_sector3TimeInMS;
        }

        if(m_bestSector1TimeInMS > 0 && m_bestSector2TimeInMS > 0 && m_bestSector3TimeInMS > 0) {
            m_optimalLapTimeMS = m_bestSector1TimeInMS + m_bestSector2TimeInMS + m_bestSector3TimeInMS;
        }
    }

    public void clear(){
        m_bestLapTimeInMS = 0;
        m_optimalLapTimeMS = 0;
        m_bestSector1TimeInMS = 0;
        m_bestSector2TimeInMS = 0;
        m_bestSector3TimeInMS = 0;
        lastS1 = 0;
        lastS2 = 0;
        m_sector3TimeInMS = 0;
    }

    public int getLastLapTimeInMS() {
        return m_lastLapTimeInMS;
    }

    public int getCurrentLapTimeInMS() {
        return m_currentLapTimeInMS;
    }

    public short getSector1TimeInMS() {
        return m_sector1TimeInMS;
    }

    public short getSector2TimeInMS() {
        return m_sector2TimeInMS;
    }

    public short getSector3TimeInMS() {
        return m_sector3TimeInMS;
    }

    public float getLapDistance() {
        return m_lapDistance;
    }

    public float getTotalDistance() {
        return m_totalDistance;
    }

    public float getSafetyCarDelta() {
        return m_safetyCarDelta;
    }

    public byte getCarPosition() {
        return m_carPosition;
    }

    public byte getCurrentLapNum() {
        return m_currentLapNum;
    }

    public byte getPitStatus() {
        return m_pitStatus;
    }

    public byte getNumPitStops() {
        return m_numPitStops;
    }

    public byte getSector() {
        return m_sector;
    }

    public boolean isCurrentLapInvalid(){
        if(m_currentLapInvalid == 1){
            return true;
        }
        return false;
    }

    public byte getPenalties() {
        return m_penalties;
    }

    public byte getWarnings() {
        return m_warnings;
    }

    public byte getNumUnservedDriveThroughPens() {
        return m_numUnservedDriveThroughPens;
    }

    public byte getNumUnservedStopGoPens() {
        return m_numUnservedStopGoPens;
    }

    public byte getGridPosition() {
        return m_gridPosition;
    }

    public byte getDriverStatus() {
        return m_driverStatus;
    }

    public byte getResultStatus() {
        return m_resultStatus;
    }

    public byte getPitLaneTimerActive() {
        return m_pitLaneTimerActive;
    }

    public short getPitLaneTimeInLaneInMS() {
        return m_pitLaneTimeInLaneInMS;
    }

    public short getPitStopTimerInMS() {
        return m_pitStopTimerInMS;
    }

    public boolean pitStopShouldServePen() {
        if(m_pitStopShouldServePen == 1){
            return true;
        }
        return false;
    }

    public byte getTimeTrialPBCarIdx() {
        return m_timeTrialPBCarIdx;
    }

    public byte getTimeTrialRivalCarIdx() {
        return m_timeTrialRivalCarIdx;
    }

    public int getBestLapTimeInMS() {
        return m_bestLapTimeInMS;
    }

    public int getOptimalLapTimeMS() {
        return m_optimalLapTimeMS;
    }

    public short getBestSector1TimeInMS() {
        return m_bestSector1TimeInMS;
    }

    public short getBestSector2TimeInMS() {
        return m_bestSector2TimeInMS;
    }

    public short getBestSector3TimeInMS() {
        return m_bestSector3TimeInMS;
    }

    public short getLastS1() {
        return lastS1;
    }

    public short getLastS2() {
        return lastS2;
    }

    public String timeToString(int timeInMS){
        int m = timeInMS/60000;
        float s = (float) (timeInMS - (60000 * m)) /1000;
        String timeStr = m+":";
        if(s<10){
            timeStr = timeStr+"0";
        }
        return timeStr+s;
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
