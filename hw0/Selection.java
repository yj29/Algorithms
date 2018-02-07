package hw0;

/**
 * Created by YASH on 2/6/16.
 */
public enum Selection {
    X,
    Y,
    Z;

    Selection(){

    }
    private  boolean correct;
    public void setCorrect(boolean correct){
        this.correct = correct;
    }
}
