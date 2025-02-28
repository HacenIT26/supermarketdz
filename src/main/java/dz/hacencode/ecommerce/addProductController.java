package dz.hacencode.ecommerce;

import com.jfoenix.controls.JFXComboBox;
import dz.hacencode.ecommerce.Model.Category;
import dz.hacencode.ecommerce.Model.Produit;
import dz.hacencode.ecommerce.Model.connectDB;
import dz.hacencode.ecommerce.orderDTO.order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;

public class addProductController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private JFXComboBox<String> _category;
    private Set<Category> categoryList;
    private Category categorySelected;
    @FXML
    private TextField _bareCode;
    @FXML
    private TextField _productName;
    @FXML
    private TextArea _productDesc;
    @FXML
    private TextField _price;
    @FXML
    private TextField _quantity;
    private int productIDBarreCode;
    @FXML
    private TableView<Produit> _tableProduct;
    @FXML
    private TableColumn<Produit, Integer> productID;
    @FXML
    private TableColumn<Produit, Integer> barcode;
    @FXML
    private TableColumn<Produit, String> productName;
    @FXML
    private TableColumn<Produit, Double> price;
    @FXML
    private TableColumn<Produit, Integer> quantity;

    private List<Produit> produits;
    ObservableList<Produit> produitList = FXCollections.observableArrayList();

    @FXML
    private ImageView myImageView;
    private Image image;
    private File selectedFile;
    private FileInputStream fis;

    @FXML
    private ImageView barCodeImageView;
    private Image bareCodeImage;


    @FXML
    private Label img;
    private ImageIcon format = null;
    String filename = null;
    byte[] person_image = null;


//    Image myImage = new Image(getClass().getResourceAsStream("shrek2.jpg"));
//    @FXML
//    public void displayImage() {
//        myImageView.setImage(myImage);
//    }
@FXML
private void selectCategory(ActionEvent event) {
    String categSelected =  _category.getValue();
    for (Category category : categoryList) {
        if(category.getCategoryName().equals(categSelected)){
            categorySelected = category;
        }
    }
    _bareCode.setText(String.valueOf(categorySelected.getCategoryID()));
}
    @FXML
    private void focusInput(KeyEvent event) {

        if(event.getCode() == KeyCode.ENTER && _bareCode.isFocused()){
            _productName.requestFocus();
        }else if(event.getCode() == KeyCode.ENTER && _productName.isFocused()){
            _productDesc.requestFocus();
        }else if(event.getCode() == KeyCode.ENTER && _productDesc.isFocused()){
            _price.requestFocus();
        }else if(event.getCode() == KeyCode.ENTER && _price.isFocused()){
            _quantity.requestFocus();
        }else if(event.getCode() == KeyCode.ENTER && _quantity.isFocused()){
            _bareCode.requestFocus();
        }
    }

    @FXML
    private void UploadImageActionPerformed(ActionEvent event) throws FileNotFoundException {
        Stage stage = null;
        FileChooser fil_chooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPGPNG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG","*.JPEG","*.jpeg","PNG files (*.png)", "*.PNG");
        fil_chooser.getExtensionFilters().addAll(extFilterJPGPNG);

        selectedFile = fil_chooser.showOpenDialog(null);

        filename = selectedFile.getAbsolutePath();

        image = new Image(new FileInputStream(filename));
        fis = new FileInputStream (filename);

        myImageView.setImage(image);

    }
    @FXML
    private void saveProduct(ActionEvent event) throws FileNotFoundException {
        Produit produit = new Produit();
        produit.setCategoryID(categorySelected.getCategoryID());
        produit.setBarcode(_bareCode.getText());
        produit.setProductName(_productName.getText());
        produit.setDescription(_productDesc.getText());
        produit.setPrice(Double.parseDouble(_price.getText()));
        produit.setQuantity(Integer.parseInt(_quantity.getText()));
        produit.setImage(fis);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Product");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to save this product");
        // Setting the Button's action
        Optional<ButtonType> result =  alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int i = produit.createProduit(produit,selectedFile);
            System.out.println( i);
            if(i == 1){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Product");
                alert1.setHeaderText(null);
                alert1.setContentText("Product added successfuly!");
                alert1.showAndWait();
            }
        }


    }

    @FXML
    private void getAllProduct(ActionEvent event) throws FileNotFoundException {
        Produit produit = new Produit();
        produits = produit.getAllProduct();
        produits.forEach(produit1 -> produitList.add(produit1));
        _tableProduct.setItems(produitList);

    }
    @FXML
    private void selectPhoto(MouseEvent event) throws FileNotFoundException {
        Produit produit = new Produit();
        produit = _tableProduct.getSelectionModel().getSelectedItem();
        productIDBarreCode = produit.getProductID();
        myImageView.setImage(null);

//        byte[] byteArray = produit.getImage();
//        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
//        BufferedImage read = ImageIO.read(in);
//        image.setImage(SwingFXUtils.toFXImage(read, null));

        image = new Image(produit.getImage());
        myImageView.setFitWidth(350);
        myImageView.setFitHeight(300);
        myImageView.setImage(image);
        _bareCode.setText(produit.getBarcode());
        _productName.setText(produit.getProductName());
        _productDesc.setText(produit.getDescription());
        _price.setText(String.valueOf(produit.getPrice()));
        _quantity.setText(String.valueOf(produit.getQuantity()));
    }

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) {
        EAN13Bean barcodeGenerator = new EAN13Bean();
        BitmapCanvasProvider canvas =
                new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }

    @FXML
    private void generateBareCode(ActionEvent event) throws FileNotFoundException {

        String barCode =  _bareCode.getText();
        if(_bareCode.getText().length() < 12){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("ERROR");
            alert1.setHeaderText(null);
            alert1.setContentText("Number of digits must be equal 12 !");
            alert1.showAndWait();
        }else {
            BufferedImage bufferedImage = generateEAN13BarcodeImage(barCode);
            WritableImage wr = null;
            if (bufferedImage != null) {
                wr = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
                PixelWriter pw = wr.getPixelWriter();
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        pw.setArgb(x, y, bufferedImage.getRGB(x, y));
                    }
                }
            }
            barCodeImageView.setImage(wr);
        }

    }
    @FXML
    private void printBareCode(ActionEvent event){
        Map<String, Object> parameters = new HashMap<String, Object>();
        try {
            Connection con = connectDB.getconnection();

            //InputStream in = new FileInputStream( new File("../../Print/ticketPaiement.jrxml"));
            InputStream is = DashBoard.class.getResourceAsStream("/resources/print/barrecode.jrxml");

            URL res = getClass().getClassLoader().getResource("barrecode.jrxml");
            File file = Paths.get(res.toURI()).toFile();
            String absolutePath = file.getPath();

            JasperDesign jd = JRXmlLoader.load(absolutePath);

            parameters.put("productID", productIDBarreCode);

            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            //JasperReport jr = JasperCompileManager.compileReport("/print/ticketPaiement.jrxml");
            List<JasperPrint> jpList = new ArrayList<>();
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parameters, con);
            JasperPrint jp2 = JasperFillManager.fillReport(jasperReport, parameters, con);
            jpList.add(jp);
            jpList.add(jp2);
            jpList.forEach(jasperPrint ->  JasperViewer.viewReport(jasperPrint, false));

        } catch (JRException ex) {
            //  Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Dialog");
            alert2.setContentText("" + ex);
            System.out.println(ex);

            alert2.show();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void newProduct(ActionEvent actionEvent){

        _bareCode.setText("");

         _productName.setText("");

         _productDesc.setText("");

         _price.setText("");

         _quantity.setText("");
    }
    @FXML
    private void generateNewCodeBarre(ActionEvent actionEvent){
    if(categorySelected == null){

    }else{
        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(100000000);
        int rand_int2 = rand.nextInt(1000);

        // Print random integers
        System.out.println("Random Integers: "+rand_int1);
        System.out.println("Random Integers: "+rand_int2);
        _bareCode.setText(String.valueOf(categorySelected.getCategoryID()+"123"+rand_int1));
    }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Category category = new Category();
        categoryList =  category.getAllCategory();
        categoryList.forEach(category1 -> _category.getItems().add((category1.getCategoryName())));
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        _tableProduct.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _bareCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    _bareCode.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (_bareCode.getText().length() > 12) {
                    String s = _bareCode.getText().substring(0, 12);
                    _bareCode.setText(s);
                }
            }
        });

        _quantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    _quantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
