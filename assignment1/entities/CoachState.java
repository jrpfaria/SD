package assignment1.entities;

public enum CoachState {
    WAIT_FOR_REFEREE_COMMAND,
    ASSEMBLE_TEAM,
    WATCH_TRIAL;

    @Override
    public String toString()
    {
        switch(this)
        {
            case WAIT_FOR_REFEREE_COMMAND:
                return "WFRC";
            case ASSEMBLE_TEAM:
                return "ASST";
            case WATCH_TRIAL:
                return "WATR";
            default:
                throw new IllegalArgumentException();
        }
    }
}
