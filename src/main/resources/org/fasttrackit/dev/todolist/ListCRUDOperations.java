package org.fasttrackit.dev.todolist;

import java.sql.*;
import java.util.Scanner;

public class ListCRUDOperations {

    /*public static void printMenu(){
        System.out.println("1.read");
        System.out.println("2.create");
        System.out.println("3.update");
        System.out.println("4.delete");
        System.out.println("0=exit");
    }*/

    /*public static void main(String[] args) {
        System.out.println("Hello database users! We are going to call DB from Java");
        int option;
        Scanner read = new Scanner(System.in);
        try {
            do {
                printMenu();

                System.out.print("option: "); //citste optiunea
                option=read.nextInt();
                //demo CRUD operations
                switch (option){
                    case 1: {
                        demoRead();
                        break;
                    }
                    case 2: {
                        demoCreate();
                        break;
                    }
                    case 3: {
                        demoUpdate();
                        break;
                    }
                    case 4: {
                        demoDelete();
                        break;
                    }
                    case 0: {
                        break;
                    }
                }
            } while(option!=0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public static void demoCreate(String item) throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariusList";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO \"ToDoList\" (item) VALUES (?)");
        pSt.setString(1, item);

        // 5. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }

    public static UserBean demoReadUser(String userName ) throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariusList";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        PreparedStatement pSt = conn.prepareStatement("SELECT name,id_user FROM \"Users\" where name=?");
        pSt.setString(1, userName);
        // 5. execute a prepared statement
        ResultSet rs = pSt.executeQuery();
        System.out.println("enter get user ");
        if (rs.next()) {
            System.out.println("user was found: "+rs.getString("name").trim());
            System.out.println("id: "+rs.getInt("id_user"));
            UserBean userBean = new UserBean();
            userBean.setId(rs.getInt("id_user"));
            userBean.setName(rs.getString("name").trim());
            return userBean;
        }
        return null;
    }

    public static String[] demoRead() throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariusList";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("SELECT item, \"isDone\" FROM \"ToDoList\" where \"isDone\"=false");

        // 6. iterate the result set and collecting values
        String[] result = new String[20];
        int i=0;
        boolean isDone;
        String value;
        while (rs.next()) {
            //isDone=rs.getBoolean("isDone");
            value=rs.getString("item").trim();
            //System.out.println(value + " = " + isDone);
            //if (!isDone) {
                result[i]=value;
                i++;
            //}
        }
        System.out.println("am iesit din while, result construction ended");

        // 7. close the objects
        rs.close();
        st.close();
        conn.close();
        return result;
    }

    /*private static void demoUpdate() throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariusList";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";
        String name;
        String phoneNo;
        Long key;
        Scanner read = new Scanner(System.in);
        System.out.print("name: ");
        name=read.nextLine();
        System.out.print("phone: ");
        phoneNo=read.nextLine();
        System.out.print("id to be updated: ");
        key=read.nextLong();

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("UPDATE agenda SET name=?, phone=? WHERE id_agenda=?"); //so we have 3 params
        pSt.setString(1, name);
        pSt.setString(2, phoneNo);
        pSt.setLong(3, key);

        // 5. execute a prepared statement
        int rowsUpdated = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }*/


    /*private static void demoDelete(String task) throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariusList";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("UPDATE \"ToDoList\" set isDone=? WHERE item=?");
        pSt.setString(1, task);
        pSt.setString(1, task);

        // 5. execute a prepared statement
        int rowsDeleted = pSt.executeUpdate();
        System.out.println(rowsDeleted + " rows were deleted.");
        
        // 6. close the objects
        pSt.close();
        conn.close();
    }*/

    public static void demoSetDone(String task) throws ClassNotFoundException, SQLException {
        System.out.println("task to be set as DONE: "+ task);

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariusList";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("UPDATE \"ToDoList\" SET \"isDone\"=? WHERE item=?");
        pSt.setBoolean(1, true);
        pSt.setString(2, task);

        // 5. execute a prepared statement
        int rowsDeleted = pSt.executeUpdate();
        System.out.println(task + " set to: DONE");

        // 6. close the objects
        pSt.close();
        conn.close();
    }

    public static UserBean createUser(String name) throws ClassNotFoundException, SQLException {
        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariusList";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO \"Users\" (name) VALUES (?)");
        pSt.setString(1, name);

        // 5. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();

        return demoReadUser(name);
    }
}

