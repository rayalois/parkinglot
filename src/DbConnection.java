/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author iannoh
 */
import java.awt.Component;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class DbConnection {
    Connection conn;
    String url = "jdbc:mysql://localhost/parkinglot";
    PreparedStatement stm;
    Statement stmt;
    String query;
    boolean logins;
    private static String user;
    private static String pass;
    private Component btnLogin;
    public DbConnection() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,"root","");
            System.out.println("DB connected");
            
        }catch(java.lang.ClassNotFoundException e){
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
    }
    public void saveUserData(String fname, String othernames,
            String dl, String idnumber, String password, String cpassword)
            throws SQLException{
        String user_sql = "INSERT INTO users(fName, othernames, dl, idnumber,"
                + " userName, passWord) VALUES(?,?,?,?,?,?)";
        String username = fname+dl;
        try (PreparedStatement saveuser_stm = conn.prepareStatement(user_sql)) {
            saveuser_stm.setString(1, fname);
            saveuser_stm.setString(2, othernames);
            saveuser_stm.setString(3, dl);
            saveuser_stm.setString(4, idnumber);
            saveuser_stm.setString(5, username);
            saveuser_stm.setString(6, password);
            boolean rs;
            rs = saveuser_stm.execute();
            System.out.print(rs);
            JOptionPane.showMessageDialog(null,"Saved successfully. Your new "
                   + " username is: "+username);
        }
        stm.close();
        conn.close();
    }  
    public boolean loginService(String username, String password){
        try {
            
            String user,pass;
            logins = false;
            query = "SELECT username, password FROM users;";
            stmt = (Statement)conn.createStatement();
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                user = rs.getString("username");
                pass = rs.getString("password");
                if(user.equals(username)){
                    if(pass.equals(password)){
                     logins = true;
                     JOptionPane.showMessageDialog(btnLogin, "Log in successful!");
                    } 
                }
            }
            logins = false;
            //JOptionPane.showMessageDialog(btnLogin, "Incorrect password and/or username.");
            //return logins;
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return logins;    
    
    }
    
}
