package dz.hacencode.ecommerce;

import dz.hacencode.ecommerce.Model.Produit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;


public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img = new ImageView();
    private Produit produit;
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(produit);
    }


    private MyListener myListener;

    public void setData(Produit produit, MyListener myListener, Image setImage) {
        this.produit = produit;
        this.myListener = myListener;
        nameLabel.setText(produit.getProductName());
        priceLable.setText(produit.getPrice() +" DA");
       // Image image = new Image(setImage);
        img.setImage(setImage);

    }
}
