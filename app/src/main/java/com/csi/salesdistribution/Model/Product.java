package com.csi.salesdistribution.Model;



public class Product {
    String prod_id;
    String name;
    String vat;
    String tradeValue;
    String pack_size;
    String quantity;
    String tradePrice;
    String discount;
    String netAmount;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(String tradeValue) {
        this.tradeValue = tradeValue;
    }

    public String getPack_size() {
        return pack_size;
    }

    public void setPack_size(String pack_size) {
        this.pack_size = pack_size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }
}