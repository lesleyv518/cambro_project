package com.cambro.app.model;

import android.graphics.Bitmap;

import com.parse.ParseFile;

public class FresFit {

    String objectId;
    String name;
    String temperature;
    String humidity;
    String tip;
    String container;
    String link;
    String productImage;
    ParseFile image;
    Bitmap dImg;
    String txtColor;
    String ml;
    String productDescription;
    String lidDescription;
    String lidCode;
    String productCode;
    String oz;
    String size;
    String qt;
    String panDepth;
    String metricPanDepth;
    String metricQT;
    private String dimension;

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }

    public Bitmap getdImg() {
        return dImg;
    }

    public void setdImg(Bitmap dImg) {
        this.dImg = dImg;
    }

    public String getTxtColor() {
        return txtColor;
    }

    public void setTxtColor(String txtColor) {
        this.txtColor = txtColor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getMl() {
        return ml;
    }

    public void setMl(String ml) {
        this.ml = ml;
    }

    public String getLidCode() {
        return lidCode;
    }

    public void setLidCode(String lidCode) {
        this.lidCode = lidCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getOz() {
        return oz;
    }

    public void setOz(String oz) {
        this.oz = oz;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLidDescription() {
        return lidDescription;
    }

    public void setLidDescription(String lidDescription) {
        this.lidDescription = lidDescription;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getPanDepth() {
        return panDepth;
    }

    public void setPanDepth(String panDepth) {
        this.panDepth = panDepth;
    }

    public String getMetricPanDepth() {
        return metricPanDepth;
    }

    public void setMetricPanDepth(String metricPanDepth) {
        this.metricPanDepth = metricPanDepth;
    }

    public String getMetricQT() {
        return metricQT;
    }

    public void setMetricQT(String metricQT) {
        this.metricQT = metricQT;
    }
}
