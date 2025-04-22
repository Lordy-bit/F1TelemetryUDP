public class SpeedTrap extends Event{
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