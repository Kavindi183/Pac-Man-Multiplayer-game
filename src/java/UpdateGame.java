/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/game", "/UpdateGame"})
public class UpdateGame extends HttpServlet {

    static int playerCount = 0;

    GameBoard game;

    @Override
    public void init() throws ServletException {
        game = new GameBoard();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/event-stream;charset=UTF-8");

        try (final PrintWriter out = response.getWriter()) {
            //new session is created only when the session is new
            //playerCount is kept the number of players connected
            if (request.getSession().isNew()) {
                if (playerCount < 4) {
                    request.getSession().setAttribute("player", game.players[playerCount++]);
                }
            }

            while (!Thread.interrupted()) {
                synchronized (this) {

                    out.println("data: " + game.getResponseJson());
                    out.println();
                    out.flush();
                    wait();
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        synchronized (this) {
            String key = request.getParameter("keypress");

            Player player = (Player) request.getSession().getAttribute("player");
            //Game starts only when 4 players are connected
            if (playerCount == 4) {
                game.updatePlayerLocation(player.getPlayerId(), key);

                notifyAll();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
