package com.example.samsung.myapplication;

public class Option {

    private String productCode; //상품코드
    private String productDetailUrl; //상세url
    private String productImage; //상품이미지
    private String optionOrder; //옵션번호
    private String optionTitle; //상세 정보 이름
    private String optionValue; //상세 정보 값
    private String optionPrice;
    private int changeValue=0;

    @Override
    public String toString() {
        //    return "Product [productCode=" + productCode + ", productName=" + productName + ", productImage=" + productImage
        //            + ", productDetailUrl=" + productDetailUrl + ", productPrice=" + productPrice + "]";
        String str1 = optionTitle;
        String[] words = str1.split(",");
        String str2 =  optionValue;
        String[] words2 = str2.split(",");

        String str ="\n가격 : "+ optionPrice+"\n";
        for(int i=0;i<words.length;i++){
            str+=words[i]+" : "+words2[i]+"\n";
        }
        //  Log.d("채윤이",this.getProductDetailUrl());
        return "상품코드"+productCode+""+str+"옵션번호:"+optionOrder+"\n";
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    public String getProductDetailUrl() {
        return productDetailUrl;
    }
    public void setProductDetailUrl(String productDetailUrl) {
        this.productDetailUrl = productDetailUrl;
    }

    public void setOptionOrder(String optionOrder) {
        this.optionOrder = optionOrder;
    }
    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }
    public String getOptionOrder() {
        return optionOrder;
    }
    public String getOptionTitle() {
        return optionTitle;
    }

    public String getOptionValue() {
        return optionValue;
    }
    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
    public String getOptionPrice() {
        return optionPrice;
    }
    public void setOptionPrice(String optionPrice) {
        this.optionPrice = optionPrice;
    }

}
