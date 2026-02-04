package Utils;

import com.selrom.db.DataUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanySettingUtil {

    private static final CompanySettingUtil instance = new CompanySettingUtil();
    private static final DataUtil dataUtil = new DataUtil(); // Singleton-style usage

    private CompanySettingUtil() {
        // Private constructor for Singleton
    }

    public static CompanySettingUtil getInstance() {
        return instance;
    }

    public boolean isDisplayBatch() {
        return "Batch".equalsIgnoreCase(getStringSetting("batch"));
    }

    public boolean isDisplayExp() {
        return getBooleanSetting("exp");
    }

    public boolean isDisplayMfg() {
        return getBooleanSetting("mfg");
    }

    public String getCompanyName() {
        return getStringSetting("cname");
    }

    // Reusable method to fetch String setting
    private String getStringSetting(String column) {
        String query = "SELECT " + column + " FROM setting_bill;";
        ResultSet resultSet = null;
        try {
            resultSet = dataUtil.doQuery(query);
            if (resultSet != null && resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
        }
        return "";
    }

    // Reusable method to fetch boolean setting
    private boolean getBooleanSetting(String column) {
        String query = "SELECT " + column + " FROM setting_bill;";
        ResultSet resultSet = null;
        try {
            resultSet = dataUtil.doQuery(query);
            if (resultSet != null && resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
        }
        return false;
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
