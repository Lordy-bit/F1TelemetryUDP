public class FastestLap extends Event{
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
