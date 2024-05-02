package commInfra;

public enum MessageType { // TODO
    // GeneralRepos
    SETNFIC, // Set Log File Name 
    NFICDONE, // Log File Name Done
    STREFST, // Start Referee State
    STCOAST, // Start Coach State
    STCONTST, // Start Contestant State
    STCONTSTR, // Start Contestant Strength
    ADDCONT, // Add Contestant
    RMCONT, // Remove Contestant
    STRP, // Set Rope Position
    STG, // Start Game
    EOG, // End Of Game
    EOM, // End Of Match

    // RefereeSite
    ANG, // Announce New Game
    INFR, // Inform Referee
    TRY, // Teams Ready

    // Playground
    STT, // Start Trial
    WTC, // Wait Trial Conclusion
    ATD, // Assert Trial Decision
    DGW, // Declare Game Winner
    ASTM, // Assemble Team
    WATL, // Watch Trial
    FCA, // Follow Coach Advice
    AD, // Am Done
    GR, // Get Ready
    SIP, // Stand In Position

    // ContestantsBench
    CLT, // Call Trial
    DMW, // Declare Match Winner
    RVN, // Review Notes
    WFRC, // Wait For Referee Command
    CLC, // Call Contestants
    SAB, // Seat At Bench
    SD, // Seat Down
    
    // General Purpose
    SHUT,
    SHUTDONE,
    SACK,
    ACK
}