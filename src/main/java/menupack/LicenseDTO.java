package menupack;

import java.util.Date;

public class LicenseDTO {
    private String status;
    private Date date;
    private String version;
    private String hardDiskId;
    private String motherBoardId;
    private String cid;
    private String cname;
    private String uname;
    private String eno;
    private Date vdate;
    private Date userValidDate;
    private String licenseToken;
    private String pass;

    // Raw Encrypted values for Syncing back to local DB
    private String encStatus;
    private String encVersion;
    private String encHard;
    private String encMother;
    private String encPass;

    public LicenseDTO() {
    }

    // Getters and Setters for Logic
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getHardDiskId() { return hardDiskId; }
    public void setHardDiskId(String hardDiskId) { this.hardDiskId = hardDiskId; }

    public String getMotherBoardId() { return motherBoardId; }
    public void setMotherBoardId(String motherBoardId) { this.motherBoardId = motherBoardId; }

    public String getCid() { return cid; }
    public void setCid(String cid) { this.cid = cid; }

    public String getCname() { return cname; }
    public void setCname(String cname) { this.cname = cname; }

    public String getUname() { return uname; }
    public void setUname(String uname) { this.uname = uname; }

    public String getEno() { return eno; }
    public void setEno(String eno) { this.eno = eno; }

    public Date getVdate() { return vdate; }
    public void setVdate(Date vdate) { this.vdate = vdate; }

    public Date getUserValidDate() { return userValidDate; }
    public void setUserValidDate(Date userValidDate) { this.userValidDate = userValidDate; }

    public String getLicenseToken() { return licenseToken; }
    public void setLicenseToken(String licenseToken) { this.licenseToken = licenseToken; }

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    // Getters and Setters for Encrypted Data (for Sync)
    public String getEncStatus() { return encStatus; }
    public void setEncStatus(String encStatus) { this.encStatus = encStatus; }

    public String getEncVersion() { return encVersion; }
    public void setEncVersion(String encVersion) { this.encVersion = encVersion; }

    public String getEncHard() { return encHard; }
    public void setEncHard(String encHard) { this.encHard = encHard; }

    public String getEncMother() { return encMother; }
    public void setEncMother(String encMother) { this.encMother = encMother; }

    public String getEncPass() { return encPass; }
    public void setEncPass(String encPass) { this.encPass = encPass; }
}
