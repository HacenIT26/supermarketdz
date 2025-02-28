package dz.hacencode.ecommerce.orderDTO;

import java.util.List;

public class orderPause {

    private int numOrder;
    private double totalForPayment;
    private List<order> orderList;

    public orderPause() {
    }

    public orderPause(int numOrder, double totalForPayment, List<order> orderList) {
        this.numOrder = numOrder;
        this.totalForPayment = totalForPayment;
        this.orderList = orderList;
    }

    public int getNumOrder() {
        return numOrder;
    }

    public void setNumOrder(int numOrder) {
        this.numOrder = numOrder;
    }

    public double getTotalForPayment() {
        return totalForPayment;
    }

    public void setTotalForPayment(double totalForPayment) {
        this.totalForPayment = totalForPayment;
    }

    public List<order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<order> orderList) {
        this.orderList = orderList;
    }
}
