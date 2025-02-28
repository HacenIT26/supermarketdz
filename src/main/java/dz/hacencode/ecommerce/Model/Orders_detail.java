package dz.hacencode.ecommerce.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Orders_detail {

    private int orderID, productID, quantity;

    public Orders_detail(int orderID, int productID, int quantity) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public Orders_detail() {

    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Orders_detail> getAllOrders_detail() {
        Connection con = connectDB.getconnection();
        List<Orders_detail> ordersDetailList = new ArrayList<>();

        Orders_detail orders_detail;
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `orderID`, `productID`, `quantity` FROM `orders_detail`");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                orders_detail = new Orders_detail();
                orders_detail.setOrderID(s.getInt("orderID"));
                orders_detail.setProductID(s.getInt("productID"));
                orders_detail.setQuantity(s.getInt("quantity"));
                ordersDetailList.add(orders_detail);
            }
        } catch (SQLException ex) {

            Logger.getLogger(Orders_detail.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ordersDetailList;
    }
    public Orders_detail getOrders_detail(int orderID) {
        Connection con = connectDB.getconnection();
        Orders_detail orders_detail = new Orders_detail();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `orderID`, `productID`, `quantity` FROM `orders_detail` WHERE orderID='"+orderID+"'");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                orders_detail.setOrderID(s.getInt("orderID"));
                orders_detail.setProductID(s.getInt("productID"));
                orders_detail.setQuantity(s.getInt("quantity"));
            }
        } catch (SQLException ex) {

            Logger.getLogger(Orders_detail.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orders_detail;
    }
    public int createOrders_detail(Orders_detail ordersDetail) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO `orders_detail`(`orderID`, `productID`, `quantity`) VALUES " +
                    "('" + ordersDetail.getOrderID() + "','" + ordersDetail.getProductID() + "',"
                    + "'" + ordersDetail.getQuantity()+ "')");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Orders_detail.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }

    public int deleteOrders_detail(int orderID) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("DELETE FROM `orders_detail` WHERE `orderID`="+orderID+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Orders_detail.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
}
