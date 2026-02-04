package pospack;

import com.selrom.db.DataUtil;

/**
 *
 * @author Selrom Software
 */
public class print_class_return {

    DataUtil util;

    void get_print(DataUtil util, String billno, String drive, String folder, String billformat) {
        switch (billformat) {
            case "Thermal GST":
                new print_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal GST MRP":
                new print_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal MRP Without GST":
                new print_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal Short Bill":
                new print_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal GST 4-Inch MRP":
                new print_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal 4-Inch Without GST":
                new print_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "A4":
                new print_a4().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal 2-Inch MRP":
                new print_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal GST Summary":
                new print_thermal_summary_return().Report(util, billno, drive, folder, billformat);
                break;
            default:
                new print_thermal_return().Report(util, billno, drive, folder, billformat);
                break;
        }
    }

}
