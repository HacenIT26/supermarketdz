package dz.hacencode.ecommerce.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Category {
    private int categoryID;
    private String categoryName;

    public Category(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public Category() {

    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Set<Category> getAllCategory() {
        Connection con = connectDB.getconnection();
        Set<Category> categories = new HashSet<>();
        Category category;
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `categoryID`," +
                    " `categoryName` FROM `category`");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                category = new Category();
                category.setCategoryID(s.getInt("categoryID"));
                category.setCategoryName(s.getString("categoryName"));
                categories.add(category);
            }

        } catch (SQLException ex) {

            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categories;
    }
    public Category getCategory(int categoryID) {
        Connection con = connectDB.getconnection();
        Category category = new Category();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT `categoryID`," +
                    " `categoryName` FROM `category` WHERE categoryID='"+categoryID+"'");
            ResultSet s =pr.executeQuery();
            while(s.next()){
                category.setCategoryID(s.getInt("categoryID"));
                category.setCategoryName(s.getString("categoryName"));
            }
        } catch (SQLException ex) {

            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }

        return category;
    }
    public int createCategory(Category category) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO `category`(`categoryName`) VALUES" +
                    " ('" + category.getCategoryName().replace("'", "''") +")");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
    public int deleteCategory(int categoryID) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("DELETE FROM `category`  WHERE `categoryID`="+categoryID+"");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
}
