public class StartLights extends Event{
    byte numLights;			// Number of lights showing

    public void loadInfo(byte [] data){
        numLights = data [28];
    }

    public byte getNumLights() {
        return numLights;
    }
}