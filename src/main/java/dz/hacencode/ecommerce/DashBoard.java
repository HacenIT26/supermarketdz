package dz.hacencode.ecommerce;

import dz.hacencode.ecommerce.Model.*;
import dz.hacencode.ecommerce.orderDTO.order;
import dz.hacencode.ecommerce.orderDTO.orderPause;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.tools.ant.util.ResourceUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashBoard implements Initializable {
    //page sell operation
    @FXML
    private AnchorPane sell_operation;
    @FXML
    private TextField searchBar;
    @FXML
    ListView<String> listView = new ListView<String>();
    private int orderID;
    @FXML
    private TableView<order> Table_order;
    @FXML
    private TableColumn<order, Integer> num;
    @FXML
    private TableColumn<order, String> barcode;
    @FXML
    private TableColumn<order, String> productName;
    @FXML
    private TableColumn<order, Integer> qte;
    @FXML
    private TableColumn<order, Double> price;
    @FXML
    private TableColumn<order, Double> sousTotal;
    @FXML
    TableColumn<order, Boolean> actionCol = new TableColumn<>("Action");
    ObservableList<order> orderList = FXCollections.observableArrayList();
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private ScrollBar scrollBar;
    @FXML
    private GridPane grid;
    private MyListener myListener;


    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private Label total;
    @FXML
    private Label priceProduct;
    private List<Produit> produits;
    private int numOrder;
    ObservableList<orderPause> orderPauses = FXCollections.observableArrayList();
    private List<order> orderListToPause = new ArrayList<>();
    private Set<Double> sousTotalList;
    private Produit produitSelected;
    private double sousTotalClc;
    private double payment;
    File img1 = new File("/img/dore1.png");
    File img2 = new File("/img/dore2.png");
    private final Image DORE1  = new Image("https://png.pngtree.com/png-clipart/20190629/ourlarge/pngtree-minimalis-modern-classic-door-png-image_1528743.jpg");
    private final Image DORE2  = new Image("https://png.pngtree.com/png-clipart/20190629/ourlarge/pngtree-minimalis-modern-classic-door-png-image_1528743.jpg");
    private final Image IMAGE_RUBY  = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");
    private final Image IMAGE_APPLE  = new Image("http://findicons.com/files/icons/832/social_and_web/64/apple.png");
    private final Image IMAGE_VISTA  = new Image("http://antaki.ca/bloom/img/windows_64x64.png");
    private final Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");
    private final Image IMAGE_TWITTER2 = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");

    private Image[] listOfImages = {DORE1, DORE2 ,IMAGE_RUBY, IMAGE_APPLE, IMAGE_VISTA, IMAGE_TWITTER,IMAGE_TWITTER2};



    //page list of sells
    @FXML
    private AnchorPane sell_list;
    private final Locale myLocale = Locale.getDefault(Locale.Category.FORMAT);
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("EEEE, dd.MM.uuuu", Locale.FRENCH);
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Orders> Table_sells;
    @FXML
    private TableColumn<Orders, Integer> orderNum;
    @FXML
    private TableColumn<Orders, Double> total_sell;
    @FXML
    private TableColumn<Orders, String> date_sell;
    ObservableList<Orders> sellList = FXCollections.observableArrayList();

    @FXML
    private TableView<Orders_detail> Table_sells_Detail;
    @FXML
    private TableColumn<Orders_detail, Double> productDetail;
    @FXML
    private TableColumn<Orders_detail, String> quantityDetail;
    ObservableList<Orders_detail> sellListDetail = FXCollections.observableArrayList();


    @FXML
    private AnchorPane addProduct;
    @FXML
    private GridPane gridAddProuct;
    @FXML
private void searchProduct(KeyEvent event){

if (event.getCode() == KeyCode.BACK_SPACE){
    listView.getItems().clear();
    listView.setItems(list);
}else {
    ObservableList<String> result = FXCollections.observableArrayList();
    for (String part : list) {
        if (part.contains(searchBar.getText())) {
            result.add(part);
        }
    }

    listView.getItems().clear();
    listView.setItems(result);
}


}
    @FXML
    protected void getProduct(ActionEvent actionEvent){
        Produit produit = new Produit();
        produits = produit.getAllProduct();


        ObservableList<String> items = FXCollections.observableArrayList (
                "RUBY", "APPLE", "VISTA", "TWITTER","TWITTER2");

        produits.forEach(produit1 -> list.add(produit1.getProductName()));
        listView.setItems(list);


        listView.setCellFactory(param -> new ListCell<>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(name.equals("MacBook Pro"))
                        imageView.setImage(listOfImages[0]);
                    else if(name.equals("MacBook Pro"))
                        imageView.setImage(listOfImages[1]);
                    else if(name.equals("Iphone X"))
                        imageView.setImage(listOfImages[2]);
                    else if(name.equals("Samsung Galaxy s9"))
                        imageView.setImage(listOfImages[3]);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });

        myListener = new MyListener() {
            @Override
            public void onClickListener(Produit produit) {
                priceProduct.setText("prix : "+produit.getPrice() + " DA");
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
                valueFactory.setValue(1);
                spinner.setValueFactory(valueFactory);

                Produit finalGetProduct = produit;
                produitSelected = produit;
                spinner.valueProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                        sousTotalClc = finalGetProduct.getPrice() *spinner.getValue();

                    }
                });
            }
        };
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                Image image = new Image(produits.get(i).getImage());
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i),myListener, image);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tap1(ActionEvent event){

        qte.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<order, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<order, Integer> event) {
                order orderSelected = Table_order.getSelectionModel().getSelectedItem();
                orderSelected.setQte(7);

                order _order = event.getRowValue();
                _order.setQte(event.getNewValue());

                _order.setSousTotal(_order.getQte() * _order.getPrice());
                orderList.set(Table_order.getSelectionModel().getSelectedIndex(),_order).setSousTotal(_order.getQte() * _order.getPrice());
                Table_order.setItems(orderList);
                payment = 0;
                double _payment = 0;
                for(int i1 = 0; i1 < orderList.size(); i1++) {
                    _payment += orderList.get(i1).getSousTotal();

                    //payment =  DoubleStream.of(orderList.get(i1).getSousTotal()).sum();
                }
                payment = _payment;

                total.setText( payment+ " DA" );
            }
        });
    }
    @FXML
    protected void productSelect(MouseEvent mouseEvent){
        String productName = listView.getSelectionModel().getSelectedItem();
        Produit getProduct = null;
        for (Produit produit : produits) {
            if(produit.getProductName().equals(productName)){
                produitSelected = produit;
                getProduct = produit;
            }
        }
        priceProduct.setText("prix : "+getProduct.getPrice() + " DA");

    }
    @FXML
    protected void operation(ActionEvent actionEvent){
        int i = 0;
        i= i +1;
        order order = new order(i,spinner.getValue(),"0001111122222",produitSelected.getProductName(),produitSelected.getPrice(),sousTotalClc);
        orderList.add(order);
        Table_order.setItems(orderList);
        double _payment = 0;
        for(int i1 = 0; i1 < orderList.size(); i1++) {
            _payment += orderList.get(i1).getSousTotal();

            //payment =  DoubleStream.of(orderList.get(i1).getSousTotal()).sum();
        }
        payment = _payment;

        total.setText( payment+ " DA" );
    }

    @FXML
    protected void enterKey(KeyEvent actionEvent){

        if(actionEvent.getCode() == KeyCode.ENTER){
            int i = 0;
            i= i +1;
            order _order1 = new order(i,spinner.getValue(),"0001111122222",produitSelected.getProductName(),produitSelected.getPrice(),sousTotalClc);
            orderListToPause.add(_order1);
            orderList.add(_order1);
            Table_order.setItems(orderList);
            double _payment = 0;
            for(int i1 = 0; i1 < orderList.size(); i1++) {
                _payment += orderList.get(i1).getSousTotal();

                //payment =  DoubleStream.of(orderList.get(i1).getSousTotal()).sum();
            }
            payment = _payment;

            total.setText( payment+ " DA" );
        }

    }

    @FXML
    protected void pauseOrder(ActionEvent actionEvent){
        numOrder+=1;
        orderPause _orderPause1 = new orderPause(numOrder,payment,orderListToPause) ;
        orderPauses.add(_orderPause1);
        System.out.println(orderPauses.size());
        orderPauses.forEach(orderPause -> System.out.println(orderPause.getNumOrder()));
        Table_order.getItems().clear();
        total.setText("");
    }


    @FXML
    protected void pay(ActionEvent actionEvent){
        Orders order = new Orders();
        Orders lastOrder = order.getLastOrders();

        if(!Table_order.getItems().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Payment");
        alert.setHeaderText(null);
        alert.setContentText("Would you like continue!");
        // Setting the Button's action
        Optional<ButtonType> result =  alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            orderID = lastOrder.getOrderID();
            orderID+=1;
            Payment payment1 = new Payment();
            LocalDate myObj = LocalDate.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String getDate = myObj.format(myFormatObj);
            //order

            order.setStatusID(2);
            order.setUserID(5);
            order.setOrderID(orderID);
            order.setDate_updated(getDate);
            order.setTotalPrice(payment);
            order.createOrders(order);
            //Orders_detail
            Orders_detail orders_detail = new Orders_detail();
            for (order order1 : orderList) {
                for (Produit produit : produits) {
                    if(produit.getProductName().equals(order1.getProductName())){
                        produitSelected = produit;
                    }
                }

                orders_detail.setProductID(produitSelected.getProductID());
                orders_detail.setQuantity(order1.getQte());
                orders_detail.setOrderID(orderID);
                orders_detail.createOrders_detail(orders_detail);
            }
            //payment
            payment1.setDatetime(getDate);
            payment1.setTotalPrice(payment);
            payment1.setShippingMethod("cash");
            payment1.setOrderID(orderID);
            payment1.setUserID(5);
            payment1.createPayment(payment1);
            total.setText("");
            Table_order.getItems().clear();
        }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("your order is empty");
            // Setting the Button's action
            alert.showAndWait();
        }
    }
    @FXML
    private void print(ActionEvent actionEvent){
        Map<String, Object> parameters = new HashMap<String, Object>();
        try {
            Connection con = connectDB.getconnection();

            //InputStream in = new FileInputStream( new File("../../Print/ticketPaiement.jrxml"));
            InputStream is = DashBoard.class.getResourceAsStream("/resources/print/ticketPaiement.jrxml");

            URL res = getClass().getClassLoader().getResource("ticketPaiement.jrxml");
            File file = Paths.get(res.toURI()).toFile();
            String absolutePath = file.getPath();

            JasperDesign jd = JRXmlLoader.load(absolutePath);

            parameters.put("orderID", orderID);

            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            //JasperReport jr = JasperCompileManager.compileReport("/print/ticketPaiement.jrxml");
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parameters, con);
            JasperViewer.viewReport(jp, false);

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
    //get all sells
    @FXML
    protected void getAllSells(ActionEvent actionEvent){
        Orders orders = new Orders();
        List<Orders> ordersList = orders.getAllOrders();

        Orders_detail orders_detail = new Orders_detail();
        List<Orders_detail> ordersDetailList = orders_detail.getAllOrders_detail();


        ordersList.forEach(orders1 -> sellList.add(orders1));
        ordersDetailList.forEach(ordersDetail -> sellListDetail.add(ordersDetail));


        Table_sells.setItems(sellList);
        Table_sells_Detail.setItems(sellListDetail);
    }



    @FXML
    protected void getSellOperationStage(ActionEvent actionEvent){
        sell_operation.setVisible(true);
        sell_list.setVisible(false);
        addProduct.setVisible(false);
    }
    @FXML
    protected void getSellListStage(ActionEvent actionEvent){
        sell_operation.setVisible(false);
        sell_list.setVisible(true);
        addProduct.setVisible(false);
    }

    @FXML
    protected void getAddProductStage(ActionEvent actionEvent) throws IOException {
        gridAddProuct.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addProduct.fxml"));
       AnchorPane anchorPane= fxmlLoader.load();
        gridAddProuct.add(anchorPane,0,1);
        sell_operation.setVisible(false);
        sell_list.setVisible(false);
        addProduct.setVisible(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableColumn<order, Boolean> col_Delete = new TableColumn<>("Action");
        col_Delete.setSortable(false);
        col_Delete.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<order, Boolean>, ObservableValue<Boolean>>(){
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<order, Boolean> p){
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        col_Delete.setCellFactory(new Callback<TableColumn<order, Boolean>, TableCell<order, Boolean>>(){
            @Override
            public TableCell<order, Boolean> call(TableColumn<order, Boolean> p){
                return new ButtonDelete(Table_order);
            }
        });
        Table_order.getColumns().add(col_Delete);

        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        barcode.setCellValueFactory(new PropertyValueFactory<>("barCode"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        qte.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        qte.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<order, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<order, Integer> event) {

                order _order = event.getRowValue();
               _order.setQte(event.getNewValue());
               _order.setSousTotal(_order.getQte() * _order.getPrice());
               orderList.set(Table_order.getSelectionModel().getSelectedIndex(),_order).setSousTotal(_order.getQte() * _order.getPrice());
              Table_order.setItems(orderList);

                double _payment = 0;
                for(int i1 = 0; i1 < orderList.size(); i1++) {
                    _payment += orderList.get(i1).getSousTotal();

                    //payment =  DoubleStream.of(orderList.get(i1).getSousTotal()).sum();
                }
                payment = _payment;

                total.setText( payment+ " DA" );
            }
        });
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        sousTotal.setCellValueFactory(new PropertyValueFactory<>("sousTotal"));
        Table_order.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                String productName = listView.getSelectionModel().getSelectedItem();
                Produit getProduct = null;
                for (Produit produit : produits) {
                    if(produit.getProductName().equals(productName)){
                        produitSelected = produit;
                        getProduct = produit;
                    }
                }

                System.out.println(getProduct.getPrice());

                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
                valueFactory.setValue(1);
                spinner.setValueFactory(valueFactory);

                Produit finalGetProduct = getProduct;
                spinner.valueProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                        sousTotalClc = finalGetProduct.getPrice() *spinner.getValue();

                    }
                });


            }
        });

        /// //////////////

        orderNum.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        total_sell.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        date_sell.setCellValueFactory(new PropertyValueFactory<>("date_updated"));
        Table_sells.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        productDetail.setCellValueFactory(new PropertyValueFactory<>("productID"));
        quantityDetail.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        Table_sells_Detail.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }


    // Define the button cell
    private class ButtonDelete extends TableCell<order, Boolean>{
        final Button delButton = new Button("Delete");
        ButtonDelete(final TableView<order> tblView) {
            delButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t){

                    orderList.remove(getIndex());
                    double _payment =0;
                    for(int i1 = 0; i1 < orderList.size(); i1++) {
                        _payment += orderList.get(i1).getSousTotal();

                        //payment =  DoubleStream.of(orderList.get(i1).getSousTotal()).sum();
                    }
                    payment = _payment;
                    total.setText( payment+ " DA" );
                }
            });
        }
        // Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty){
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(delButton);
            }else{
                setGraphic(null);
                setText("");
            }
        }
    }
}



