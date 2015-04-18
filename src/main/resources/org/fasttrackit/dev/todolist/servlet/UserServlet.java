package org.fasttrackit.dev.todolist.servlet;

import org.fasttrackit.dev.todolist.ListCRUDOperations;
import org.fasttrackit.dev.todolist.UserBean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by Darius on 4/18/2015.
 */
public class UserServlet extends HttpServlet {

    private static final String ACTION = "action";
    private static final String SET_ACTION = "set";
    private static final String VALUE = "value";
    public static final String USER = "user";

    public void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("user service called...");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession(true);

        String action = request.getParameter(ACTION);
        if (action != null && action.equals(SET_ACTION)) {
            try {
                setUserAction(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void setUserAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        HttpSession session = request.getSession(true);
        String name = request.getParameter(VALUE);
        UserBean userBean = ListCRUDOperations.demoReadUser(name);
        if (userBean == null) {
            System.out.println("enter pe create user");
            userBean=ListCRUDOperations.createUser(name);
        }
        session.setAttribute(USER, userBean);//saving user onto session
    }
}
