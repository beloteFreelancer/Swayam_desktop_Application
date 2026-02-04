package estimatepack;

import com.selrom.db.DataUtil;
import estimatepack.print_estimate_thermal_return;

/**
 *
 * @author Selrom Software
 */
public class print_class_estimate_return {

    DataUtil util;

    void get_print(DataUtil util, String billno, String drive, String folder, String billformat) {
        switch (billformat) {
            case "Thermal":
                new print_estimate_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal GST":
                new print_estimate_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal MRP":
                new print_estimate_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal Short Bill":
                new print_estimate_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal 4-Inch":
                new print_estimate_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal 4-Inch MRP":
                new print_estimate_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "A4":
                new print_estimate_a4_return().Report(util, billno, drive, folder, billformat);
                break;
            case "A5":
                new print_estimate_a4_return().Report(util, billno, drive, folder, billformat);
                break;

            default:
                new print_estimate_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
        }
    }

}
