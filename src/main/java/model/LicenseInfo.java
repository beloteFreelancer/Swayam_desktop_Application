/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class LicenseInfo {

    public String status;
    public String version;
    public String hardDiskId;
    public String motherBoardId;
    public String entryNumber;
    public String businessName;
    public String userName;
    public String password;
    public String licenseKey;
    public String date;
    public LicenseInfo(String status, String version, String hardDiskId, String motherBoardId,
            String entryNumber, String businessName, String userName, String password,
            String licenseKey, String date) {
        this.status = status;
        this.version = version;
        this.hardDiskId = hardDiskId;
        this.motherBoardId = motherBoardId;
        this.entryNumber = entryNumber;
        this.businessName = businessName;
        this.userName = userName;
        this.password = password;
        this.licenseKey = licenseKey;
        this.date = date;
    }
}
