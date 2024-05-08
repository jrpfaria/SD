package commInfra;

/**
 * Type of the exchanged messages.
 * <p>
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public enum MessageType { // TODO
    // GeneralRepos    
    /**
     * SETNFIC - Set Log File Name
     */
    SETNFIC,
    /**
     * NFICDONE - Log File Name Done
     */
    NFICDONE,
    /*
     * STREFST - Start Referee State
     */
    STREFST,
    /**
     * STCOAST - Start Coach State
     */
    STCOAST,
    /**
     * STCONTST - Start Contestant State
     */
    STCONTST,
    /**
     * STCONTSTR - Start Contestant Strength
     */
    STCONTSTR,
    /**
     * ADDCONT - Add Contestant
     */
    ADDCONT,
    /**
     * RMCONT - Remove Contestant
     */
    RMCONT,
    /**
     * STRP - Set Rope Position
     */
    STRP,
    /**
     * STG - Start Game
     */
    STG,
    /**
     * EOG - End Of Game
     */
    EOG,
    /**
     * EOM - End Of Match
     */
    EOM,

    // RefereeSite
    /**
     * ANG - Announce New Game
     */
    ANG,
    /**
     * INFR - Inform Referee
     */
    INFR,
    /**
     * TRY - Teams Ready
     */
    TRY,

    // Playground
    /**
     * STT - Start Trial
     */
    STT,
    /**
     * WTC - Wait Trial Conclusion
     */
    WTC,
    /**
     * ATD - Assert Trial Decision
     */
    ATD,
    /**
     * DGW - Declare Game Winner
     */
    DGW,
    /**
     * ASTM - Assemble Team
     */
    ASTM,
    /**
     * WATL - Watch Trial
     */
    WATL,
    /**
     * FCA - Follow Coach Advice
     */
    FCA,
    /**
     * AD - Am Done
     */
    AD,
    /**
     * GR - Get Ready
     */
    GR,
    /**
     * SIT - Stand In Position
     */
    SIP,

    // ContestantsBench
    /**
     * CLT - Call Trial
     */
    CLT,
    /**
     * DMW - Declare Match Winner
     */
    DMW,
    /**
     * RVN - Review Notes
     */
    RVN,
    /**
     * WFRC - Wait For Referee Command
     */
    WFRC,
    /**
     * CLC - Call Contestants
     */
    CLC,
    /**
     * SAB - Seat At Bench
     */
    SAB,
    /**
     * SD - Seat Down
     */
    SD,

    // General Purpose
    /**
     * SHUT - Shutdown
     */
    SHUT,
    /**
     * SHUTDONE - Shutdown Acknowledgement
     */
    SHUTDONE,
    /**
     * SACK - Set Acknowledgement
     */
    SACK,
    /**
     * ACK - Acknowledgement
     */
    ACK,
    /**
     * ERR - Error
     */
    ERR
}