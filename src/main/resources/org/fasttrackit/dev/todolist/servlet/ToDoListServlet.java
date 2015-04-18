package org.fasttrackit.dev.todolist.servlet;


import org.fasttrackit.dev.todolist.ListCRUDOperations;
import org.fasttrackit.dev.todolist.MyListOfToDoMock;
import org.fasttrackit.dev.todolist.ToDoBean;
import org.fasttrackit.dev.todolist.UserBean;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by condor on April 04, 2015
 * FastTrackIT, 2015
 */


/* didactic purposes

Some items are intentionally not optimized or not coded in the right way

FastTrackIT 2015

*/

public class ToDoListServlet extends HttpServlet {

    private static final String ACTION = "action";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String DONE_ACTION = "done";
    private static final String ID_TASK = "id";
    private static final String VALUE_NEWTASK = "value";

    public void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("service called...");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        String action = request.getParameter(ACTION);
        if (action != null && action.equals(LIST_ACTION)) {
            try {
                listAction(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (action != null && action.equals(ADD_ACTION)) {
            try {
                addAction(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (action != null && action.equals(DONE_ACTION)) {
            try {
                doneAction(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private int getUserIdFomSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        UserBean userBean = (UserBean) session.getAttribute(UserServlet.USER);

        return userBean.getId();
    }
    private void listAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {

        System.out.println("list action");

        int userid = getUserIdFomSession(request);

        List<ToDoBean> l = ListCRUDOperations.demoRead(userid);

        // put the list in a json
        JsonObjectBuilder jObjBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();
        for (ListIterator<ToDoBean> iterator = l.listIterator(); iterator.hasNext(); ) {
            ToDoBean element = iterator.next();
            if (!element.isDone()) {
                System.out.print(element.getId() + ":");
                System.out.println(element.getWhatToDo());
                jArrayBuilder.add(Json.createObjectBuilder()
                                .add("name", element.getWhatToDo())
                                .add("done", false)
                                .add("id", element.getId())
                );

            }
        }
        jObjBuilder.add("tasks", jArrayBuilder);
        JsonObject jSonFinal = jObjBuilder.build();

        //System.out.println("json pe list: " + jSonFinal.toString());

        returnJsonResponse(response, jSonFinal.toString());
        System.out.println("end list action");
    }


    private void doneAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {

        System.out.println("enter pe done");

        String idS = request.getParameter(ID_TASK);
        int id = Integer.parseInt(idS);
        int userid = getUserIdFomSession(request);
        try {
            ListCRUDOperations.demoSetDone(id, userid);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("i am done");
    }

    private void addAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {

        System.out.println("add action");

        String value = request.getParameter(VALUE_NEWTASK);

        int userid = getUserIdFomSession(request);

        ListCRUDOperations.demoCreate(value, userid);

        System.out.println("now I am done");

    }

    /**/
    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }
}
