/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 /*This class is created to generate a single dot*/
public class Dot {

    public static String[] colors = {"R", "G", "B"};
    private String color;
    private int positionX;
    private int positionY;

    public Dot(String color, int positionX, int positionY) {
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public String getColor() {
        return color;
    }

    public String toString() {
        return String.format("[\"%s\", %d, %d]", color, positionX, positionY);
    }
}
