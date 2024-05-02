package commInfra;

public enum MessageType { // TODO
    // GeneralRepos
    SETNFIC,
    NFICDONE,

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
    SIP, // Stand In Position
    GR, // Get Ready
    AD, // Am Done
    
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
