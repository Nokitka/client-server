package data;

public class Coordinates {

    private Integer x;

    private Float y; //Поле не может быть null

    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(){}

    
    /*public String toString() {
        return "Coordinates{" +
                "x=" + x +
                "; y=" + y +
                '}';
    }*/

    public String toCSVCoordinates() {
        return x + ", " + y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
