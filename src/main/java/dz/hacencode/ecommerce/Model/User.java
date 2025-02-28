package dz.hacencode.ecommerce.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private int userID, roleID;
    private String username;
    private String passwords;
    private Date TimeStamp;

    public User(int userID, int roleID, String username, String passwords, Date timeStamp) {
        this.userID = userID;
        this.roleID = roleID;
        this.username = username;
        this.passwords = passwords;
        TimeStamp = timeStamp;
    }

    public User() {

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }
    public User getUser(int userID) {
        Connection con = connectDB.getconnection();
        User user = new User();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `userID`, `username`, `passwords`, `roleID`, `TimeStamp` FROM `users` WHERE userID='"+userID+"'");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                user.setUserID(s.getInt("userID"));
                user.setUsername(s.getString("username"));
                user.setPasswords(s.getString("passwords"));
            }
        } catch (SQLException ex) {

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }
    public int createUser(User user) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO `users`(`username`, `passwords`, `roleID`, `TimeStamp`) VALUES ('" + user.getUsername().replace("'", "''") + "',"
                    + "'" + user.getPasswords().replace("'", "''") + "','" + user.getRoleID() + "',"
                    + "'" + user.getTimeStamp()+ "')");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
    public int updateUser(User user) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("UPDATE `users` SET " +
                    "`username`='"+user.getUsername().replace("'", "''")+"'," +
                    "`passwords`='"+user.getPasswords().replace("'", "''")+"'" +
                    " where `userID`="+user.getUserID()+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
    public int deleteUser(int userID) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("DELETE FROM `users` WHERE `userID`="+userID+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
}
