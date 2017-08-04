/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /*This class is created to generate players*/
public class Player {

    private final int playerId;
    private int positionX;
    private int positionY;
    private int score;

    public Player(int playerId, int positionX, int positionY) {
        this.playerId = playerId;
        this.positionX = positionX;
        this.positionY = positionY;

    }

    //According to the key stroke x,y cordinates of a particular player is updated
    public void move(int keystroke) {
        switch (keystroke) {
            case 37: // Arrow Left
                if (positionX > 0) {
                    positionX--;
                } else if (positionX == 0) {
                    positionX = 44;
                }
                break;
            case 38://Arrow Down
                if (positionY > 0) {
                    positionY--;
                } else if (positionY == 0) {
                    positionY = 44;
                }
                break;
            case 39://Arrow Right
                if (positionX < 44) {
                    positionX++;
                } else if (positionX == 44) {
                    positionX = 0;
                }
                break;
            case 40://Arrow Up
                if (positionY < 44) {
                    positionY++;
                } else if (positionY == 44) {
                    positionX = 0;
                }
                break;
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String toString() {
        return String.format("[\"P%d\",%d,%d,%d]", playerId, score, positionX, positionY);
    }

}
