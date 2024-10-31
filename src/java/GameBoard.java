/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

    private List<Dot> dots = new ArrayList<>();
    Player[] players = new Player[4];

    public GameBoard() {
        //set the initial position of 4 players
        players[0] = new Player(1, 0, 0);
        players[1] = new Player(2, 44, 0);
        players[2] = new Player(3, 0, 44);
        players[3] = new Player(4, 44, 44);

        putDots();
    }

    //Randomly generate food points
    private void putDots() {
        Random rand = new Random();
        int dotCount = 20 + rand.nextInt(10);
        boolean[][] exists = new boolean[44][44];

        while (dotCount != 0) {
            int x = rand.nextInt(43) + 1;
            int y = rand.nextInt(43) + 1;

            //to make sure no duplicate dots are added
            if (!exists[x][y]) {
                dots.add(new Dot(Dot.colors[rand.nextInt(3)], x, y));
                exists[x][y] = true;
                dotCount--;
            }
        }
    }

    public void setPlayer(Player player, int index) {
        players[index] = player;
    }

    public Player getPlayer(int index) {
        return players[index];
    }

    //Create json of PLAYERS:
    public String getPlayerJson() {
        String str = "";
        for (Player p : players) {
            str += String.format("[\"P%d\",%d,%d,%d],", p.getPlayerId(), p.getScore(), p.getPositionX(), p.getPositionY());

        }
        str = str.substring(0, str.length() - 1);
        str += "]";
        return str;
    }

    //Create json of DOTS:
    public String getDotJson() {

        if (dots.isEmpty()) {
            return null;
        }

        String str = "[";
        for (int i = 0; i < dots.size() - 1; i++) {
            str += dots.get(i).toString() + ",";
        }
        str += dots.get(dots.size() - 1) + "]";
        return str;
    }

    //Combine json of PLAYERS and DOTS and create a new json object
    public String getResponseJson() {
        return String.format("{ \"DOTS\": %s, \"PLAYERS\":[ %s}", getDotJson(), getPlayerJson());
    }

    //Collision handling 
    //Score updating 
    //Updating the Game Grid
    public void updatePlayerLocation(int playerId, String key) {
    System.out.println("playerId=" + playerId + ", key=" + key);
        Player player = players[playerId - 1];
        player.move(Integer.parseInt(key));

        for (int i = 0; i < dots.size(); i++) {//Score incrementing
            Dot dot = dots.get(i);
            if (dot.getPositionX() == player.getPositionX()
                    && dot.getPositionY() == player.getPositionY()) {
                switch (dot.getColor()) {
                    case "R":
                        player.setScore(player.getScore() + 1);
                        break;
                    case "G":
                        player.setScore(player.getScore() + 2);
                        break;
                    case "B":
                        player.setScore(player.getScore() + 4);
                        break;

                }
                dots.remove(i);
            }
        }

        for (Player p : players) {//collision handling
            if (p.getPlayerId() == player.getPlayerId()) {
                continue;
            }

            if (p.getPositionX() == player.getPositionX()
                    && p.getPositionY() == player.getPositionY()) {
                player.setScore(player.getScore() - 3);
                p.setScore(p.getScore() - 3);//Reduce 3 marks when collision happens

                switch (p.getPlayerId()) {
                    case 1:
                        p.setPositionX(0);
                        p.setPositionY(0);
                        break;
                    case 2:
                        p.setPositionX(44);
                        p.setPositionY(0);
                        break;
                    case 3:
                        p.setPositionX(0);
                        p.setPositionY(44);
                        break;
                    case 4:
                        p.setPositionX(44);
                        p.setPositionY(44);
                        break;
                }

                switch (player.getPlayerId()) {
                    case 1:
                        player.setPositionX(0);
                        player.setPositionY(0);
                        break;
                    case 2:
                        player.setPositionX(44);
                        player.setPositionY(0);
                        break;
                    case 3:
                        player.setPositionX(0);
                        player.setPositionY(44);
                        break;
                    case 4:
                        player.setPositionX(44);
                        player.setPositionY(44);
                        break;
                }

            }
        }

    }

}
