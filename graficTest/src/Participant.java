public class Participant {
    byte      m_aiControlled;            // Whether the vehicle is AI (1) or Human (0) controlled
    byte      m_driverId;		         // Driver id - see appendix, 255 if network human
    byte      m_networkId;		         // Network id – unique identifier for network players
    byte      m_teamId;                  // Team id - see appendix
    byte      m_myTeam;                  // My team flag – 1 = My Team, 0 = otherwise
    byte      m_raceNumber;              // Race number of the car
    byte      m_nationality;             // Nationality of the driver
    String       m_name;               //[48] Name of participant in UTF-8 format – null terminated
                                         // Will be truncated with … (U+2026) if too long
    byte      m_yourTelemetry;           // The player's UDP setting, 0 = restricted, 1 = public

    int index;                           //index of this participant
    byte m_numActiveCars;

    public Participant(int index){
        this.index = index;
    }

    public void loadInfo(byte [] data){
        int mul = 56 * index;
        m_numActiveCars = data[24];
        m_aiControlled = data [25+mul];
        m_driverId = data [26+mul];
        m_networkId = data [27+mul];
        m_teamId = data [28+mul];
        m_myTeam = data [29+mul];
        m_raceNumber = data [30+mul];
        m_nationality = data [31+mul];
        m_name = "";
        for (int i= 32; i < 80; i++){
            if(data[i+mul] != 0) {
                m_name = m_name + (char) data[i + mul];
            }
        }
        m_yourTelemetry = data[80+mul];
    }

    public void printInfo(){
        System.out.println("AI controlled: "+m_aiControlled);
        System.out.println("Driver ID: "+m_driverId);
        System.out.println("Network ID: "+m_networkId);
        System.out.println("Team ID: "+m_teamId);
        System.out.println("My team: "+m_myTeam);
        System.out.println("Race number: "+m_raceNumber);
        System.out.println("Name: "+m_name);
        System.out.println("Telemetry: "+m_yourTelemetry);
    }

}
