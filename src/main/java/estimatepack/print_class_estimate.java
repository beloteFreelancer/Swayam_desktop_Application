package estimatepack;

import com.selrom.db.DataUtil;

/**
 *
 * @author Selrom Software
 */
public class print_class_estimate {


    void get_print(DataUtil util, String billno, String drive, String folder, String billformat) {
        switch (billformat) {
            case "":
            case "Thermal":
                new print_estimate_thermal().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal MRP":
                new print_estimate_thermal().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal Short Bill":
                new print_estimate_thermal().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal 4-Inch":
                new print_estimate_thermal().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal 4-Inch MRP":
                new print_estimate_thermal().Report(util, billno, drive, folder, billformat);
                break;
            case "A4":
                new print_estimate_a4().Report(util, billno, drive, folder, billformat);
                break;
            case "A5":
                new print_estimate_a4().Report(util, billno, drive, folder, billformat);
                break;

            default:
                new print_estimate_a4().Report(util, billno, drive, folder, billformat);
                break;
        }
    }

}
