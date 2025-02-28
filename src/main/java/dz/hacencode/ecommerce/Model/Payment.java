package dz.hacencode.ecommerce.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Payment {
    private int paymentID, userID, orderID;
    private double totalPrice;
    private String shippingMethod;
    private String datetime;

    public Payment(int paymentID, int userID, int orderID, double totalPrice, String shippingMethod, String datetime) {
        this.paymentID = paymentID;
        this.userID = userID;
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.shippingMethod = shippingMethod;
        this.datetime = datetime;
    }

    public Payment() {
    }
    public int getPaymentID() {
        return paymentID;
    }
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getShippingMethod() {
        return shippingMethod;
    }
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Payment getPayment(int paymentID) {
        Connection con = connectDB.getconnection();
        Payment payment = new Payment();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `paymentID`, `userID`, `orderID`," +
                    " `totalPrice`, `shippingMethod`, `datetime` FROM `payment` where `paymentID` ='"+paymentID+"'");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                payment.setPaymentID(s.getInt("paymentID"));
                payment.setUserID(s.getInt("userID"));
                payment.setOrderID(s.getInt("orderID"));
                payment.setTotalPrice(s.getDouble("totalPrice"));
                payment.setShippingMethod(s.getString("shippingMethod"));
                payment.setDatetime(s.getString("datetime"));
            }
        } catch (SQLException ex) {

            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return payment;
    }
    public int createPayment(Payment payment) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO `payment`(`userID`, `orderID`, `totalPrice`, `shippingMethod`, `date_payment`) VALUES " +
                    "(" + payment.getUserID()+ "," +payment.getOrderID() + "," + payment.getTotalPrice() + ",'" +
                    payment.getShippingMethod().replace("'", "''") + "','" +
                    payment.getDatetime() +"')");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
    public int deletePayment(int paymentID) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("DELETE FROM `payment` WHERE `paymentID`="+paymentID+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
}
