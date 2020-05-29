
package io.swagger.model;
// FTT
public enum ModelUpdateStates {
    Ok, // Jobs can be procesed
    InProgress, // 503 wait until model updated
    Failed // 500 Bad, very bad..
}
