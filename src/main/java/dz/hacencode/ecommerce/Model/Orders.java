package dz.hacencode.ecommerce.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Orders {

    private int orderID, userID, statusID ;
    private double totalPrice;
    private String date_updated;

    public Orders(int orderID, int userID, int statusID, double totalPrice, String date_updated) {
        this.orderID = orderID;
        this.userID = userID;
        this.statusID = statusID;
        this.totalPrice = totalPrice;
        this.date_updated = date_updated;
    }

    public Orders() {

    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }
    public List<Orders> getAllOrders() {
        Connection con = connectDB.getconnection();
        List<Orders> ordersList = new ArrayList<>();
        Orders orders;
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `orderID`, " +
                    "`userID`, `statusID`, `totalPrice`, `date_updated` FROM `orders`");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                orders = new Orders();
                orders.setUserID(s.getInt("userID"));
                orders.setOrderID(s.getInt("orderID"));
                orders.setStatusID(s.getInt("statusID"));
                orders.setTotalPrice(s.getDouble("totalPrice"));
                orders.setDate_updated(s.getString("date_updated"));
                ordersList.add(orders);
            }
        } catch (SQLException ex) {

            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ordersList;
    }
    public Orders getOrders(int orderID) {
        Connection con = connectDB.getconnection();
        Orders orders = new Orders();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `orderID`, " +
                    "`userID`, `statusID`, `totalPrice`, `date_updated` FROM `orders` WHERE orderID='"+orderID+"'");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                orders.setUserID(s.getInt("userID"));
                orders.setOrderID(s.getInt("orderID"));
                orders.setStatusID(s.getInt("statusID"));
                orders.setTotalPrice(s.getDouble("totalPrice"));
                orders.setDate_updated(s.getString("date_updated"));
            }
        } catch (SQLException ex) {

            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orders;
    }
    public Orders getLastOrders() {
        Connection con = connectDB.getconnection();
        Orders orders = new Orders();
        try {
            PreparedStatement pr = con.prepareStatement(" SELECT max(`orderID`) as lastOrder FROM `orders`");
            ResultSet s =pr.executeQuery();
            while(s.next()){

                orders.setOrderID(s.getInt("lastOrder"));
            }
        } catch (SQLException ex) {

            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orders;
    }


    public int createOrders(Orders orders) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO `orders`(`userID`, `statusID`, `totalPrice`, `date_updated`) VALUES " +
                    "('" + orders.getUserID() + "','" + orders.getStatusID() + "',"
                    + "'" + orders.getTotalPrice()+ "', '" + orders.getDate_updated()+ "')");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }

    public int deleteOrders(int orderID) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("DELETE FROM `orders` WHERE `orderID`="+orderID+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
}
