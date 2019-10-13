package com.example.samsung.myapplication;

public class DiscountInfo {

    private String Point; //적립 포인트
    private String Installment; //무이자
    private String ShipFee; //배송비

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }

    public String getInstallment() {
        return Installment;
    }

    public void setInstallment(String installment) {
        Installment = installment;
    }

    public String getShipFee() {
        return ShipFee;
    }

    public void setShipFee(String shipFee) {
        ShipFee = shipFee;
    }
}
