package org.fasttrackit.dev.todolist;



import java.sql.SQLException;
import java.util.*;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.fasttrackit.dev.todolist.ListCRUDOperations;

import javax.xml.bind.SchemaOutputResolver;

/**
 * Created by condor on April 04, 2015
 * FastTrackIT, 2015
 */
public class MyListOfToDoMock {

    private static MyListOfToDoMock m;

    public static MyListOfToDoMock getInstance(int userid) throws SQLException, ClassNotFoundException {
        System.out.println("get instance...");
        System.out.println("m=null "+ m==null);
       // if(m==null) {
            m=new MyListOfToDoMock();
            m.generateInitialList(userid);
        //}
       return m;
    }


    private List<ToDoBean> toDoList = new ArrayList<ToDoBean>();
    private int id;

    private void generateInitialList(int userid) throws SQLException, ClassNotFoundException {
        System.out.println("Initializing...");
        toDoList = ListCRUDOperations.demoRead(userid);
    };

   public void addItem(String value, int userid) throws SQLException, ClassNotFoundException {
       toDoList.add(new ToDoBean(value,false, userid));
       ListCRUDOperations list = new ListCRUDOperations();
       list.demoCreate(value, userid);
        }

    public void doneItem(int index) {
        for (ListIterator<ToDoBean> iter = toDoList.listIterator(); iter.hasNext(); ) {
            ToDoBean element = iter.next();
            if (element.getId()==index) {
                element.setDone(true);
                iter.set(element);
            }
        }
    }


    public void printList() {
        for (ListIterator<ToDoBean> iter = toDoList.listIterator(); iter.hasNext(); ) {
            ToDoBean element = iter.next();
            if (!element.isDone()) {
                System.out.print(element.getId() + ":");
                System.out.println(element.getWhatToDo());
            }

        }
    }

    public List getList(int userid) throws SQLException, ClassNotFoundException {
        return ListCRUDOperations.demoRead(userid);
    }

}