import com.mysql.jdbc.Connection;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/db_name";

    //  Database credentials
    static final String USER = "USER_NAME";
    static final String PASS = "Pass";

    static Connection conn = null;
    static Statement stmt = null;
    static  PreparedStatement preparedStmt = null;
    static ResultSet rs = null;

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            boolean st = true;

            while (st){
                System.out.println("1) Create Data");
                System.out.println("2) Read Data");
                System.out.println("3) Update Data");
                System.out.println("4) Delete Data");
                System.out.println("5) Exit");
                System.out.print("Choose: "); int ch = scan.nextInt();

                switch (ch) {
                    case 1:
                        insert_data();
                        break;
                    case 2:
                        read_data();
                        break;
                    case 3:
                        update_data();
                        break;
                    case 4:
                        delete_data();
                        break;
                    case 5:
                        st = false;
                        break;

                }
            }

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main

    public static void insert_data() throws SQLException {
        //STEP 4: Execute a query
        System.out.println("Inserting records into the table...");
        stmt = conn.createStatement();

        System.out.print("Enter Name: "); String name = scan.next();
        System.out.print("Enter E-mail: "); String email = scan.next();
        System.out.print("Enter Age: "); int age = scan.nextInt();

        String sql = "INSERT INTO users (name,age,mail)" + "VALUES (?,?,?)";
        preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString (1, name);
        preparedStmt.setInt(2, age);
        preparedStmt.setString(3, email);

        // execute the preparedstatement
        preparedStmt.execute();

        System.out.println("Inserted records into the table...");
        stmt.close();
        preparedStmt.close();
    }

    public static void read_data() throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM users");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "     " + rs.getString(2) + "     " + rs.getInt(3) + "        " + rs.getString(4));
        }
        rs.close();
        stmt.close();
    }

    public static void update_data() throws SQLException {
        //STEP 4: Execute a query
        System.out.println("Updating records into the table...");
        stmt = conn.createStatement();

        rs = stmt.executeQuery("SELECT * FROM users");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "     " + rs.getString(2) + "     " + rs.getInt(3) + "        " + rs.getString(4));
        }

        System.out.print("Choose ID: "); int chid = scan.nextInt();

        System.out.print("Enter Name: "); String name = scan.next();
        System.out.print("Enter E-mail: "); String email = scan.next();
        System.out.print("Enter Age: "); int age = scan.nextInt();

        String sql = "UPDATE users SET name = ?, age = ?, mail = ? WHERE id = ?";
        preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString (1, name);
        preparedStmt.setInt(2, age);
        preparedStmt.setString(3, email);
        preparedStmt.setInt(4, chid);

        // execute the preparedstatement
        preparedStmt.executeUpdate();
        stmt.close();
        preparedStmt.close();

        System.out.println("Updating records into the table...");
    }

    public static void delete_data() throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            System.out.println(rs.getInt(1) + "     " + rs.getString(2) + "     " + rs.getInt(3) + "        " + rs.getString(4));
        }

        System.out.print("Choose ID: "); int chid = scan.nextInt();

        String sql = "DELETE FROM users WHERE id = ?";
        preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setInt(1, chid);

        preparedStmt.execute();

        rs.close();
        preparedStmt.close();
        stmt.close();
    }
}//end JDBCExample
