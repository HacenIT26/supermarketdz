package dz.hacencode.ecommerce.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Status {
    private int statusID;
    private String statusName;

    public Status(int statusID, String statusName) {
        this.statusID = statusID;
        this.statusName = statusName;
    }

    public Status() {

    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    public Status getStatue(int statusID) {
        Connection con = connectDB.getconnection();
        Status status = new Status();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `statusID`, `statusName` FROM `status` WHERE `statusID` ='"+statusID+"'");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                status.setStatusID(s.getInt("statusID"));
                status.setStatusName(s.getString("statusName"));
            }
        } catch (SQLException ex) {

            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }
    public int createStatus(Status status) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO `status`(`statusID`, `statusName`) VALUES ('" + status.getStatusID()+ "',"
                    + "'" + status.getStatusName().replace("'", "''") + "'");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
    public int updateStatus(Status status) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("UPDATE `status` SET " +
                    "`statusName`='"+status.getStatusName().replace("'", "''") +"' where `userID`="+status.getStatusID()+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
    public int deleteStatus(int statusID) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("DELETE FROM `status` WHERE `statusID`="+statusID+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
}
