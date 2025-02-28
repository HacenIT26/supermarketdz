/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.hacencode.ecommerce.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hsn
 */
public class Produit {

    private int productID, categoryID, orderID;
    private String barcode;
    private String productName;
    private String description;
    private InputStream image;
    private double price;
    private int quantity;

    public Produit(int productID, int categoryID, int orderID, String barcode, String productName, String description, InputStream image, double price, int quantity) {
        this.productID = productID;
        this.categoryID = categoryID;
        this.orderID = orderID;
        this.barcode = barcode;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public Produit() {

    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public List<Produit> getAllProduct() {
        Connection con = connectDB.getconnection();
        List<Produit> produits = new ArrayList<>();
        Produit produit;
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `productID`, `categoryID`, `productName`, `barcode`," +
                    "`description`, `image`, `price`, `quantity` FROM `product`");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                produit = new Produit();
                Blob image = s.getBlob("image");
                InputStream imgFile = image.getBinaryStream();
                produit.setProductID(s.getInt("productID"));
                produit.setCategoryID(s.getInt("categoryID"));
                produit.setBarcode(s.getString("barcode"));
                produit.setProductName(s.getString("productName"));
                produit.setDescription(s.getString("description"));
                produit.setImage(imgFile);
                produit.setPrice(s.getDouble("price"));
                produit.setQuantity(s.getInt("quantity"));
                produits.add(produit);
            }
        } catch (SQLException ex) {

            Logger.getLogger(Produit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return produits;
    }

    public Produit getProduct(int productID) {
        Connection con = connectDB.getconnection();
        Produit produit = new Produit();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `productID`, `categoryID`, `productName`,`barcode`," +
                    "`description`, `image`, `price`, `quantity` FROM `product` where `productID` ='"+productID+"'");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                Blob image = s.getBlob("image");
                InputStream imgFile = image.getBinaryStream();
                produit.setProductID(s.getInt("productID"));
                produit.setCategoryID(s.getInt("categoryID"));
                produit.setBarcode(s.getString("barcode"));
                produit.setProductName(s.getString("productName"));
                produit.setDescription(s.getString("description"));
                produit.setImage(imgFile);
                produit.setPrice(s.getDouble("price"));
                produit.setQuantity(s.getInt("quantity"));
            }
        } catch (SQLException ex) {

            Logger.getLogger(Produit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return produit;
    }
    public int createProduit(Produit produit ,  File file) throws FileNotFoundException {
        Connection con = connectDB.getconnection();
        int i = 0;
        FileInputStream input = new FileInputStream(file);
        try {
//            PreparedStatement pr = con.prepareStatement("INSERT INTO `product`(`categoryID`, `productName`,`barcode`, " +
//                    "`description`, `image`, `price`, `quantity`) VALUES ('" + produit.getCategoryID()+ "','"+produit.getBarcode()+"',"
//                    + "'" + produit.getProductName().replace("'", "''") + "'," +
//                    "'"+produit.getDescription().replace("'", "''") +"',"+input+","+produit.getPrice() +","+produit.getQuantity()+")");
//            i = pr.executeUpdate();


            PreparedStatement pr = con.prepareStatement("INSERT INTO `product`(`categoryID`, `productName`,`barcode`, " +
                    "`description`, `image`, `price`, `quantity`) VALUES (?,?,?,?,?,?,?)");
            pr.setInt(1, produit.getCategoryID());
            pr.setString(2, produit.getProductName());
            pr.setString(3, produit.getBarcode());
            pr.setString(4, produit.getDescription());
            pr.setBinaryStream(5,input);
//            pr.setBytes(1, pdfData);
            pr.setDouble(6, produit.getPrice());
            pr.setInt(7, produit.getQuantity());
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Produit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
    public int updateProduit(Produit produit) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("UPDATE `product` SET " +
                    "`categoryID`="+ produit.getCategoryID()+","+
                    "`productName`="+ produit.getProductName()+",`description`="+ produit.getDescription()+"," +
                    "`image`="+ produit.getImage()+",`price`="+ produit.getPrice()+",`quantity`="+ produit.getQuantity()+" " +
                    "WHERE `productID`="+produit.getProductID()+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Produit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
    public int deleteProduit(int productID) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("DELETE FROM `product` WHERE `productID`="+productID+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Produit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
}
