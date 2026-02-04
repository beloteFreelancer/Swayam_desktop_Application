package pospack;

import com.selrom.db.DataUtil;

/**
 *
 * @author Selrom Software
 */
public class print_class {

    DataUtil util;

    void get_print(DataUtil util, String billno, String drive, String folder, String billformat) {
        switch (billformat) {
            // New Standard Names
            case "Sales 3-Inch (Thermal)":
            case "Sales 3-Inch MRP (Thermal)":
            case "Sales 3-Inch Short (Thermal)":
            case "Sales 3-Inch NoGST (Thermal)":
            case "Sales 4-Inch (Thermal)":
            case "Sales 4-Inch MRP (Thermal)":
            case "Sales 2-Inch MRP (Thermal)":
            case "Sales A4 (4 Copies)":
                new print_thermal().Report(util, billno, drive, folder, billformat);
                break;
            case "Sales A4":
                new print_a4().Report(util, billno, drive, folder, billformat);
                break;
            case "Sales A5":
                new print_a4_half().Report(util, billno, drive, folder, billformat);
                break;

            // Legacy / Existing Support
            case "Thermal GST":
            case "Thermal GST MRP":
            case "Thermal GST MRP-II":
            case "Thermal MRP Without GST":
            case "Thermal Short Bill":
            case "Thermal GST 4-Inch MRP":
            case "Thermal 4-Inch Without GST":
            case "Thermal 2-Inch MRP":
            case "A4 (4 Copies)":
            case "Dot Matrix USB":
                new print_thermal().Report(util, billno, drive, folder, billformat);
                break;

            case "A4":
                new print_a4().Report(util, billno, drive, folder, billformat);
                break;

            case "Thermal GST (Customized)":
                new print_thermal_customized().Report(util, billno, drive, folder, billformat);
                break;
            case "Thermal GST Summary":
                new print_thermal_summary().Report(util, billno, drive, folder, billformat);
                break;

            // Redirect consolidated legacy A5 formats to the standard handler
            case "A4-Half":
            case "A4-Half_II":
            case "A4-Half_IV":
            case "A4-Half_V":
                new print_a4_half().Report(util, billno, drive, folder, billformat);
                break;

            case "A4 (2 Copies)":
                new print_a4_2_Copies().Report(util, billno, drive, folder, billformat, "Original For Recipient");
                new print_a4_2_Copies().Report(util, billno, drive, folder, billformat, "Duplicate For Transporter");
                break;

            default:
                new print_thermal().Report(util, billno, drive, folder, billformat);
                break;
        }
    }

}
