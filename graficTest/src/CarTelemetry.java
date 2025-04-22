import java.util.Random;

public class CarTelemetry {
    private short speed;
    private byte gear;
    private short rpm;

    private Random random; // Per generare numeri casuali di esempio

    public CarTelemetry(int carIndex) {
        random = new Random();
        speed = 0;
        gear = 1;
        rpm = 1000;
    }

    // Metodi getter
    public short getSpeed() {
        return speed;
    }

    public byte getGear() {
        return gear;
    }

    public short getRpm() {
        return rpm;
    }

    // Metodo per aggiornare i valori
    public void updateValues() {
        speed = (short) random.nextInt(120);  // Velocità casuale tra 0 e 119 (short)
        gear = (byte) (random.nextInt(6) + 1); // Marcia casuale tra 1 e 6 (byte)
        rpm = (short) (random.nextInt(8000) + 1000); // RPM casuale tra 1000 e 9000 (short)
    }
}
