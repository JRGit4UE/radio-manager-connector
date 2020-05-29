
package io.swagger.model;
// FTT
public class ModelUpdateState {

    private ModelUpdateStates state;
    private ModelUpdateState() {
        state = ModelUpdateStates.Ok;
    }
    private static ModelUpdateState instance;


    public static ModelUpdateState getInstance(){
        if(ModelUpdateState.instance == null){
            ModelUpdateState.instance = new ModelUpdateState();
        }
        return ModelUpdateState.instance;
    }

    public synchronized ModelUpdateStates  getState(){
        return state;
    }
    public synchronized void setState(ModelUpdateStates s){
        state = s;
    }
}
