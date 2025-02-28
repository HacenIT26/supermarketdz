module dz.hacencode.ecommerce {
    requires javafx.fxml;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires com.jfoenix;
    requires javafx.controls;
    requires barcode4j;
    requires ant;
    requires jasperreports;

    opens dz.hacencode.ecommerce to javafx.fxml;
    exports dz.hacencode.ecommerce;
    exports dz.hacencode.ecommerce.Model;
    exports dz.hacencode.ecommerce.orderDTO;
    opens dz.hacencode.ecommerce.orderDTO to javafx.fxml;
}