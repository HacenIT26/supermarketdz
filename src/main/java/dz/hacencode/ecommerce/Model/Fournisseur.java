/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.hacencode.ecommerce.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hsn
 */
public class Fournisseur {

    String Code_four, RS_four, Adrs_four, Télé_four;

    public Fournisseur() {
    }

    public Fournisseur(String Code_four, String RS_four, String Adrs_four, String Télé_four) {
        this.Code_four = Code_four;
        this.RS_four = RS_four;
        this.Adrs_four = Adrs_four;
        this.Télé_four = Télé_four;
    }

    public String getCode_four() {
        return Code_four;
    }

    public void setCode_four(String Code_four) {
        this.Code_four = Code_four;
    }

    public String getRS_four() {
        return RS_four;
    }

    public void setRS_four(String RS_four) {
        this.RS_four = RS_four;
    }

    public String getAdrs_four() {
        return Adrs_four;
    }

    public void setAdrs_four(String Adrs_four) {
        this.Adrs_four = Adrs_four;
    }

    public String getTélé_four() {
        return Télé_four;
    }

    public void setTélé_four(String Télé_four) {
        this.Télé_four = Télé_four;
    }


    public int ajouter_BC(Fournisseur four) {
        Connection con = connectDB.getconnection();
        int i = 0;
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO `fournisseur`(`Code_F`, `RS_F`, "
                    + "`Adr_F`, `Tel_F`) VALUES ('" + four.getCode_four().replace("'", "''") + "',"
                    + "'" + four.getRS_four().replace("'", "''") + "','" + four.getAdrs_four().replace("'", "''") + "',"
                    + "'" + four.getTélé_four().replace("'", "''") + "')");
            i = pr.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(Fournisseur.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }

    public Vector<Fournisseur> getallFournisseur() {
        Connection con = connectDB.getconnection();
        Vector<Fournisseur> v = new Vector<>();
        try {
            PreparedStatement pr = con.prepareStatement("select*from fournisseur");
            ResultSet s = pr.executeQuery();
            Fournisseur four;
            while (s.next()) {
                four = new Fournisseur();
                four.setCode_four(s.getString("Code_F"));
                four.setRS_four(s.getString("RS_F"));
                four.setAdrs_four(s.getString("Adr_F"));
                four.setTélé_four(s.getString("Tel_F"));
                v.add(four);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Fournisseur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }

    public Vector<String> getallFournisseurR() {
        Connection con = connectDB.getconnection();
        Vector<String> v = new Vector<>();
        try {
            PreparedStatement pr = con.prepareStatement("select*from fournisseur");
            ResultSet s = pr.executeQuery();
            String four;
            while (s.next()) {
                four = s.getString("RS_F");
                v.add(four);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Fournisseur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }

    public Vector<Fournisseur> rechercheFournisseur(String data) {
        Connection con = connectDB.getconnection();
        Vector<Fournisseur> v = new Vector<>();
        try {
            PreparedStatement pr = con.prepareStatement("select*from fournisseur where "
                    + "Code_F like '%" + data + "%' or RS_F like '%" + data + "%' or Adr_F like '%" + data + "%' or Tel_F like '%" + data + "%'");
            ResultSet s = pr.executeQuery();
            Fournisseur four;
            while (s.next()) {
                four = new Fournisseur();
                four.setCode_four(s.getString("Code_F"));
                four.setRS_four(s.getString("RS_F"));
                four.setAdrs_four(s.getString("Adr_F"));
                four.setTélé_four(s.getString("Tel_F"));
                v.add(four);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Fournisseur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    
       public Fournisseur getFour(String RS_F){
          Connection con = connectDB.getconnection();
            Fournisseur four = new Fournisseur();
          try {
              PreparedStatement pr = con.prepareStatement("select*from fournisseur where RS_F='"+RS_F+"'");
            ResultSet s =pr.executeQuery();
           
            while(s.next()){   
            four.setCode_four(s.getString("Code_F"));
            four.setRS_four(s.getString("RS_F"));
            four.setAdrs_four(s.getString("Adr_F"));
            four.setTélé_four(s.getString("Tel_F"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Fournisseur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return four;   
    }

}
