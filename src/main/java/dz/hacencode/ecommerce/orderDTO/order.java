package dz.hacencode.ecommerce.orderDTO;

public class order {

     private int num, qte;
     private String barCode, productName;
     private double price, sousTotal;

     public order() {
     }

     public order(int num, int qte, String barCode, String productName, double price, double sousTotal) {
          this.num = num;
          this.qte = qte;
          this.barCode = barCode;
          this.productName = productName;
          this.price = price;
          this.sousTotal = sousTotal;

     }

     public int getNum() {
          return num;
     }

     public void setNum(int num) {
          this.num = num;
     }

     public int getQte() {
          return qte;
     }

     public void setQte(int qte) {
          this.qte = qte;
     }

     public String getBarCode() {
          return barCode;
     }

     public void setBarCode(String barCode) {
          this.barCode = barCode;
     }

     public String getProductName() {
          return productName;
     }

     public void setProductName(String productName) {
          this.productName = productName;
     }

     public double getPrice() {
          return price;
     }

     public void setPrice(double price) {
          this.price = price;
     }

     public double getSousTotal() {
          return sousTotal;
     }

     public void setSousTotal(double sousTotal) {
          this.sousTotal = sousTotal;
     }

}
