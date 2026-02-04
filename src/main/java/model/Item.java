/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Utils.CompanySettingUtil;

public class Item {

    private int ino;                 // Primary key
    private String barcode;         // Unique
    private String iname;
    private String iname1;
    private double prate;
    private int taxp;
    private double mrp;
    private double rprice;
    private double wprice;
    private String cat;
    private String manu;
    private String hsn;
    private String udes;
    private double minstock;
    private double maxstock;
    private String rack;
    private double disp;
    private double ostock;
    private String batch;
    private String size;
    private String mfg;
    private String exp;
    private String companyname;

    public Item() {
    }

    public Item(int ino, String barcode, String iname, String iname1, double prate, int taxp, double mrp, double rprice, double wprice, String cat, String manu, String hsn, String udes, double minstock, double maxstock, String rack, double disp, double ostock, String batch, String size, String mfg, String exp) {
        this.ino = ino;
        this.barcode = barcode;
        this.iname = iname;
        this.iname1 = iname1;
        this.prate = prate;
        this.taxp = taxp;
        this.mrp = mrp;
        this.rprice = rprice;
        this.wprice = wprice;
        this.cat = cat;
        this.manu = manu;
        this.hsn = hsn;
        this.udes = udes;
        this.minstock = minstock;
        this.maxstock = maxstock;
        this.rack = rack;
        this.disp = disp;
        this.ostock = ostock;
        this.batch = batch;
        this.size = size;
        this.mfg = mfg;
        this.exp = exp;
    }

    public int getIno() {
        return ino;
    }

    public void setIno(int ino) {
        this.ino = ino;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getIname1() {
        return iname1;
    }

    public void setIname1(String iname1) {
        this.iname1 = iname1;
    }

    public double getPrate() {
        return prate;
    }

    public void setPrate(double prate) {
        this.prate = prate;
    }

    public int getTaxp() {
        return taxp;
    }

    public void setTaxp(int taxp) {
        this.taxp = taxp;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getRprice() {
        return rprice;
    }

    public void setRprice(double rprice) {
        this.rprice = rprice;
    }

    public double getWprice() {
        return wprice;
    }

    public void setWprice(double wprice) {
        this.wprice = wprice;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getManu() {
        return manu;
    }

    public void setManu(String manu) {
        this.manu = manu;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getUdes() {
        return udes;
    }

    public void setUdes(String udes) {
        this.udes = udes;
    }

    public double getMinstock() {
        return minstock;
    }

    public void setMinstock(double minstock) {
        this.minstock = minstock;
    }

    public double getMaxstock() {
        return maxstock;
    }

    public void setMaxstock(double maxstock) {
        this.maxstock = maxstock;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public double getDisp() {
        return disp;
    }

    public void setDisp(double disp) {
        this.disp = disp;
    }

    public double getOstock() {
        return ostock;
    }

    public void setOstock(double ostock) {
        this.ostock = ostock;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMfg() {
        return mfg;
    }

    public void setMfg(String mfg) {
        this.mfg = mfg;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCompanyname() {
        if (companyname != null && !companyname.isEmpty()) {
            return companyname;
        }
        return CompanySettingUtil.getInstance().getCompanyName();
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

}
