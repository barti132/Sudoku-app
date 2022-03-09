package pl.bartoszsredzinski.sudokuapp;

/**
 * Coordinates
 *
 * @author Bartosz Średziński
 * created on 09.03.2022
 */
public class Coordinates{
    private int x;
    private int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }
}
