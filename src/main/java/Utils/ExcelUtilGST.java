package Utils;

import com.selrom.db.DataUtil;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilGST {

    String dfrom;
    String dto;
    DataUtil util = null;
    int hmany = 2;

    public ExcelUtilGST(String dfrom, String dto) {
        this.dfrom = dfrom;
        this.dto = dto;
        util = new DataUtil();
    }

    public void GenerateGstrPurchaseWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "GSTR_Purchase.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook(); // Changed to XSSFWorkbook
        createGstrPurchaseSheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateGstrSalesWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "GSTR_Sales.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        createGstrSalesSheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateGstrPurchaseReturnWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "GSTR_Purchase_Return.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        createGstrPurchaseReturnSheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateGstrSalesReturnWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "GSTR_Sales_Return.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        createGstrSalesReturnSheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateHsnPurchaseWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "HSN_Purchase.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        createHsnPurchaseSheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateHsnSalesWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "HSN_Sales.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        createHsnSalesSheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateHsnPurchaseReturnWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "HSN_Purchase_Return.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        createHsnPurchaseReturnSheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateHsnSalesReturnWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "HSN_Sales_Return.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        createHsnSalesReturnSheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateSummaryWorkbook() {
        File outputFile = getSaveFileFromUser("Save File As", "GSTR_Summary.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        createSummarySheet(workbook);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerateGSTWorkbookComplete() {
        File outputFile = getSaveFileFromUser("Save File As", "GSTR_Workbook_Complete.xlsx");
        if (outputFile == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();

        // --- SALES FIRST ---
        createGstrSalesSheet(workbook);           // 1
        createGstrSalesReturnSheet(workbook);     // 2

        // --- PURCHASE NEXT ---
        createGstrPurchaseSheet(workbook);        // 3
        createGstrPurchaseReturnSheet(workbook);  // 4

        // --- HSN-WISE DETAIL ---
        createHsnSalesSheet(workbook);            // 5
        createHsnSalesReturnSheet(workbook);      // 6
        createHsnPurchaseSheet(workbook);         // 7
        createHsnPurchaseReturnSheet(workbook);   // 8

        // --- SUMMARY LAST ---
        createSummarySheet(workbook);             // 9

        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --------------------------- Sheet Methods ---------------------------
    public void createGstrPurchaseSheet(Workbook wb) {
        try {
            DateFormat dfIn = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat dfDb = new SimpleDateFormat("yyyy/MM/dd");
            DecimalFormat df2 = new DecimalFormat("#.##");

            Date nm = dfIn.parse(dfrom);
            Date nm1 = dfIn.parse(dto);
            String lk = dfDb.format(nm);
            String lk1 = dfDb.format(nm1);

            String[] headers = {
                "Sl.No.", "Supplier Name", "GSTIN", "Bill No", "Bill Date", "Tax %", "Value",
                "IGST", "CGST", "SGST", "CESS", "Tax Amount", "Total Value", "GRN"
            };

            List<Object[]> dataRows = new ArrayList<>();

            // Query distinct bills in date range
            String query = "SELECT DISTINCT cname, billno, DATE_FORMAT(bdate,'%d/%m/%Y') AS bdate_str, bdate, grn, ttype "
                    + "FROM purchase_items WHERE bdate BETWEEN '" + lk + "' AND '" + lk1 + "' ORDER BY bdate, grn";

            ResultSet r = util.doQuery(query);

            List<String> cnameList = new ArrayList<>();
            List<String> billnoList = new ArrayList<>();
            List<String> billdateList = new ArrayList<>();
            List<String> grnList = new ArrayList<>();
            List<String> ttypeList = new ArrayList<>();

            while (r != null && r.next()) {
                cnameList.add(r.getString(1));
                billnoList.add(r.getString(2));
                billdateList.add(r.getString(3));
                grnList.add(r.getString(5));
                ttypeList.add(r.getString(6));
            }

            int[] taxRates = {0, 5, 12, 18, 28};

            int serial = 1;
            for (int i = 0; i < billnoList.size(); i++) {
                String billno = billnoList.get(i);
                String cname = cnameList.get(i);
                String billdate = billdateList.get(i);
                String grn = grnList.get(i);
                String ttype = ttypeList.get(i);
                String tin = "";

                for (int taxp : taxRates) {
                    double value = 0, taxamt = 0, total = 0;

                    String sumq = "SELECT sum(sub), sum(taxamt), sum(total) FROM purchase_items "
                            + "WHERE bdate BETWEEN '" + lk + "' AND '" + lk1 + "' AND taxp='" + taxp + "' AND billno='" + billno + "'";
                    ResultSet r2 = util.doQuery(sumq);
                    while (r2 != null && r2.next()) {
                        value = r2.getDouble(1);
                        if (taxp != 0) {
                            taxamt = r2.getDouble(2);
                            total = r2.getDouble(3);
                        }
                    }

                    if (value > 0) {
                        double cgst = taxamt / 2;
                        double igst = taxamt;

                        double valf = Double.parseDouble(df2.format(value));
                        double taxamtf = Double.parseDouble(df2.format(taxamt));
                        double totalf = Double.parseDouble(df2.format(total));
                        double cgstf = Double.parseDouble(df2.format(cgst));
                        double igstf = Double.parseDouble(df2.format(igst));

                        String strVal = String.format("%.2f", valf);
                        String strTaxamt = String.format("%.2f", taxamtf);
                        String strTotal = String.format("%.2f", totalf);
                        String strCgst = String.format("%.2f", cgstf);
                        String strIgst = String.format("%.2f", igstf);

                        if (ttype.equalsIgnoreCase("Local")) {
                            strIgst = "0.00";
                        } else {
                            strCgst = "0.00";
                        }

                        if (tin.isEmpty()) {
                            String gstq = "SELECT DISTINCT gstno FROM vendor WHERE cname='" + cname.replace("'", "''") + "'";
                            ResultSet rgst = util.doQuery(gstq);
                            if (rgst != null && rgst.next()) {
                                tin = rgst.getString(1);
                                if (tin == null) {
                                    tin = "";
                                }
                            }
                        }

                        Object[] row = new Object[]{
                            serial,
                            (cname.equals(".") ? "Cash Bill" : cname),
                            tin,
                            billno,
                            billdate,
                            String.valueOf(taxp),
                            strVal,
                            strIgst,
                            strCgst,
                            strCgst, // SGST = CGST
                            "",
                            strTaxamt,
                            strTotal,
                            grn
                        };
                        dataRows.add(row);
                        serial++;
                    }
                }
            }

            // Calculate totals
            double nvalue = 0, ntotal = 0, ncgst = 0, nigst = 0, ntax = 0;
            for (Object[] row : dataRows) {
                nvalue += Double.parseDouble(row[6].toString());
                nigst += Double.parseDouble(row[7].toString());
                ncgst += Double.parseDouble(row[8].toString());
                ntax += Double.parseDouble(row[11].toString());
                ntotal += Double.parseDouble(row[12].toString());
            }

            String strVal = String.format("%.2f", nvalue);
            String strTaxamt = String.format("%.2f", ntax);
            String strTotal = String.format("%.2f", ntotal);
            String strCgst = String.format("%.2f", ncgst);
            String strIgst = String.format("%.2f", nigst);

            // Blank row, then Total row
            dataRows.add(new Object[headers.length]);
            dataRows.add(new Object[]{
                "", "TOTAL:" + (dataRows.size()), "", "", "", "", strVal, strIgst, strCgst, strCgst, "", strTaxamt, strTotal, ""
            });

            // Create sheet and populate using your reusable method
            Sheet sheet = wb.createSheet("GSTR Purchase");

            // Convert List<Object[]> to Object[][]
            Object[][] data = new Object[dataRows.size()][headers.length];
            for (int i = 0; i < dataRows.size(); i++) {
                data[i] = dataRows.get(i);
            }

            // Call your provided method to create the sheet with styling
            createSheetWithData(sheet, headers, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createGstrSalesSheet(Workbook wb) {
        try {
            Sheet sheet = wb.createSheet("GSTR Sales");

            // Headers for the Excel sheet
            String[] headers = {
                "Sl.No.", "Customer ID", "Customer Name", "Bill No", "Bill Date", "Tax %",
                "Value", "IGST", "CGST", "SGST", "CESS", "Tax Amount", "Total Value"
            };

            DateFormat dfIn = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat dfDb = new SimpleDateFormat("yyyy/MM/dd");

            // Assuming dfrom and dto are class-level variables in "dd/MM/yyyy" format
            Date nm = dfIn.parse(dfrom);
            Date nm1 = dfIn.parse(dto);
            String lk = dfDb.format(nm);
            String lk1 = dfDb.format(nm1);

            List<Object[]> dataRows = new ArrayList<>();

            // Updated SQL query using MAX and aggregation as in your updated load_report
            String query = "SELECT MAX(cid) AS cid, billno, DATE_FORMAT(MAX(dat),'%d/%m/%Y') AS dat, taxp, "
                    + "SUM(sub), SUM(taxamt), SUM(total), MAX(tax_type) AS tax_type, MAX(tax) AS tax "
                    + "FROM sales_items "
                    + "WHERE dat BETWEEN '" + lk + "' AND '" + lk1 + "' "
                    + "GROUP BY billno, taxp "
                    + "ORDER BY MAX(dat), billno, taxp";

            ResultSet r = util.doQuery(query);

            int serial = 1;
            if (r != null) {
                while (r.next()) {
                    String cid = r.getString("cid");
                    String billno = r.getString("billno");
                    String billdate = r.getString("dat");
                    String taxp = r.getString("taxp");
                    double value2 = r.getDouble(5);
                    double taxamt2 = r.getDouble(6);
                    double total2 = r.getDouble(7);
                    String tax_type = r.getString("tax_type");
                    String tax = r.getString("tax");

                    double cgst = 0, igst = 0;

                    // Apply Inclusive Model-II adjustment
                    if (tax_type != null && tax_type.equalsIgnoreCase("Inclusive Model-II")) {
                        value2 = value2 - taxamt2;
                    }

                    // Determine tax split based on tax location
                    if (tax != null && tax.equalsIgnoreCase("Local")) {
                        cgst = taxamt2 / 2;
                    } else {
                        igst = taxamt2;
                    }

                    // Format numbers with known decimal precision (hmany)
                    String value3 = String.format("%." + hmany + "f", value2);
                    String igst3 = String.format("%." + hmany + "f", igst);
                    String cgst3 = String.format("%." + hmany + "f", cgst);
                    String taxamt3 = String.format("%." + hmany + "f", taxamt2);
                    String total3 = String.format("%." + hmany + "f", total2);

                    // Placeholder for Customer Name—will resolve after processing all rows
                    dataRows.add(new Object[]{
                        serial,
                        cid,
                        ".", // Will update customer name later
                        billno,
                        billdate,
                        taxp,
                        value3,
                        igst3,
                        cgst3,
                        cgst3, // SGST same as CGST as per your logic
                        "", // CESS empty as original
                        taxamt3,
                        total3
                    });
                    serial++;
                }
            } else {
                System.err.println("ResultSet is null. Query might have failed:\n" + query);
            }

            // Fetch customer names and GSTIN for each cid and update the name column (index 2)
            for (int i = 0; i < dataRows.size(); i++) {
                Object[] row = dataRows.get(i);
                String cid = row[1].toString();
                String custQuery = "SELECT DISTINCT cname, gstno FROM cust WHERE cid='" + cid + "'";
                ResultSet rCust = util.doQuery(custQuery);
                String cname = ".";
                if (rCust != null && rCust.next()) {
                    cname = rCust.getString(1) != null ? rCust.getString(1) : ".";
                }
                if (cname.equals(".") || cname.equals("--")) {
                    cname = "Cash Bill";
                }
                row[2] = cname;
            }

            // Aggregate totals
            double nvalue = 0, ntax = 0, ntotal = 0, ncgst = 0, nigst = 0;
            for (Object[] row : dataRows) {
                nvalue += Double.parseDouble(row[6].toString());
                nigst += Double.parseDouble(row[7].toString());
                ncgst += Double.parseDouble(row[8].toString());
                ntax += Double.parseDouble(row[11].toString());
                ntotal += Double.parseDouble(row[12].toString());
            }

            String value3Total = String.format("%." + hmany + "f", nvalue);
            String taxamt3Total = String.format("%." + hmany + "f", ntax);
            String total3Total = String.format("%." + hmany + "f", ntotal);
            String cgst3Total = String.format("%." + hmany + "f", ncgst);
            String igst3Total = String.format("%." + hmany + "f", nigst);

            // Add a blank row as separator then total row
            dataRows.add(new Object[headers.length]); // blank row
            dataRows.add(new Object[]{
                "", "TOTAL:" + (dataRows.size() - 1), "", "", "", "",
                value3Total, igst3Total, cgst3Total, cgst3Total, "", taxamt3Total, total3Total
            });

            // Convert List<Object[]> to Object[][]
            Object[][] data = new Object[dataRows.size()][headers.length];
            for (int i = 0; i < dataRows.size(); i++) {
                data[i] = dataRows.get(i);
            }

            // Use your existing method to create styled Excel sheet
            createSheetWithData(sheet, headers, data);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in createGstrSalesSheet: " + e.getMessage());
        }
    }

    private void createGstrPurchaseReturnSheet(Workbook wb) {
        Sheet sheet = wb.createSheet("GSTR Purchase Return");

        String[] headers = {
            "Sl.No.", "Supplier Name", "GSTIN", "Bill No", "Bill Date",
            "Tax %", "Value", "IGST", "CGST", "SGST", "CESS",
            "Tax Amount", "Total Value", "GRN"
        };

        List<Object[]> dataList = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat("0.00");

        double totalValue = 0, totalTax = 0, totalCGST = 0, totalIGST = 0;

        try {
            Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String lk = new SimpleDateFormat("yyyy/MM/dd").format(fromDate);
            String lk1 = new SimpleDateFormat("yyyy/MM/dd").format(toDate);

            String summaryQuery = "SELECT DISTINCT cname, billno, DATE_FORMAT(bdate, '%d/%m/%Y'), grn, ttype,bdate "
                    + "FROM preturn_items "
                    + "WHERE bdate BETWEEN '" + lk + "' AND '" + lk1 + "' ORDER BY bdate, grn";
            ResultSet summaryRs = util.doQuery(summaryQuery);

            int serial = 1;

            while (summaryRs.next()) {
                String cname = summaryRs.getString(1);
                String billno = summaryRs.getString(2);
                String date = summaryRs.getString(3);
                String grn = summaryRs.getString(4);
                String ttype = summaryRs.getString(5);
                String gstin = "";

                ResultSet gstRs = util.doQuery("SELECT gstno FROM vendor WHERE cname = '" + cname + "'");
                if (gstRs.next()) {
                    gstin = gstRs.getString(1);
                }

                int[] taxRates = {0, 5, 12, 18, 28};

                for (int taxp : taxRates) {
                    String itemQuery = "SELECT SUM(sub), SUM(taxamt), SUM(total) FROM preturn_items "
                            + "WHERE bdate BETWEEN '" + lk + "' AND '" + lk1 + "' "
                            + "AND taxp = '" + taxp + "' AND billno = '" + billno + "'";
                    ResultSet itemRs = util.doQuery(itemQuery);

                    if (itemRs.next() && itemRs.getDouble(1) > 0) {
                        double sub = itemRs.getDouble(1);
                        double taxamt = itemRs.getDouble(2);
                        double total = itemRs.getDouble(3);
                        double cgst = taxamt / 2;
                        double igst = taxamt;
                        String cess = "";  // placeholder

                        // For Local transactions, IGST = 0
                        if (ttype.equalsIgnoreCase("Local")) {
                            igst = 0;
                        } else {
                            cgst = 0;
                        }

                        totalValue += total;
                        totalTax += taxamt;
                        totalCGST += cgst;
                        totalIGST += igst;

                        Object[] row = new Object[]{
                            serial++,
                            cname,
                            gstin,
                            billno,
                            date,
                            taxp + "",
                            df2.format(sub),
                            df2.format(igst),
                            df2.format(cgst),
                            df2.format(cgst),
                            cess,
                            df2.format(taxamt),
                            df2.format(total),
                            grn
                        };

                        dataList.add(row);
                    }
                }
            }

            // Add Total Row
            Object[] totalRow = new Object[]{
                "", "TOTAL:" + (serial - 1), "", "", "", "",
                df2.format(totalValue),
                df2.format(totalIGST),
                df2.format(totalCGST),
                df2.format(totalCGST),
                "",
                df2.format(totalTax),
                df2.format(totalValue + totalTax),
                ""
            };
            dataList.add(new Object[headers.length]); // Blank row before total
            dataList.add(totalRow);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert to 2D array
        Object[][] data = dataList.toArray(new Object[0][]);
        createSheetWithData(sheet, headers, data);
    }

    private void createGstrSalesReturnSheet(Workbook wb) {
        try {
            Sheet sheet = wb.createSheet("GSTR Sales Return");
            List<Object[]> dataList = new ArrayList<>();

            // Updated headers with GSTR-compliant fields
            String[] headers = {
                "S.No", "Invoice No", "Invoice Date", "Customer Name", "GSTIN",
                "Place of Supply", "Invoice Type", "Reverse Charge", "HSN/SAC",
                "Taxable Value", "Tax Rate (%)", "CGST", "SGST", "IGST",
                "Total Tax", "Total Amount"
            };

            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String lk = new SimpleDateFormat("yyyy/MM/dd").format(nm);
            String lk1 = new SimpleDateFormat("yyyy/MM/dd").format(nm1);

            int serial = 1;
            double totalTaxable = 0, totalTax = 0, totalAmount = 0;
            double totalCGST = 0, totalSGST = 0, totalIGST = 0;

            String query = "SELECT MAX(cid), billno, DATE_FORMAT(MIN(dat),'%d/%m/%Y'), taxp, "
                    + "SUM(sub), SUM(taxamt), SUM(total), MAX(tax_type), MAX(tax) "
                    + "FROM sreturn_items "
                    + "WHERE dat BETWEEN '" + lk + "' AND '" + lk1 + "' "
                    + "GROUP BY billno, taxp "
                    + "ORDER BY MIN(dat), billno, taxp";

            ResultSet r = util.doQuery(query);

            while (r.next()) {
                double taxable = r.getDouble(5);
                double taxAmt = r.getDouble(6);
                double total = r.getDouble(7);
                double taxRate = r.getDouble(4);
                String taxType = r.getString(8);
                String tax = r.getString(9);
                String billNo = r.getString(2);
                String date = r.getString(3);
                String cid = r.getString(1);

                if ("Inclusive Model-II".equalsIgnoreCase(taxType)) {
                    taxable -= taxAmt;
                }

                double cgst = 0, sgst = 0, igst = 0;
                if ("Local".equalsIgnoreCase(tax)) {
                    cgst = taxAmt / 2;
                    sgst = taxAmt / 2;
                } else {
                    igst = taxAmt;
                }

                // Fetch customer details
                String cname = "Cash Bill";
                String gstin = "";
                ResultSet r2 = util.doQuery("SELECT cname, gstno FROM cust WHERE cid='" + cid + "'");
                if (r2.next()) {
                    cname = r2.getString(1);
                    gstin = r2.getString(2);
                }

                // Derived fields
                String pos = gstin.length() >= 2 ? gstin.substring(0, 2) : "";
                String invoiceType = (gstin != null && !gstin.trim().isEmpty()) ? "B2B" : "B2C";
                String reverseCharge = "No"; // Adjust this if your logic requires it
                String hsnCode = ""; // Optional: get from product/item table if needed

                dataList.add(new Object[]{
                    serial++,
                    billNo,
                    date,
                    cname,
                    gstin,
                    pos,
                    invoiceType,
                    reverseCharge,
                    hsnCode,
                    String.format("%." + hmany + "f", taxable),
                    String.format("%." + hmany + "f", taxRate),
                    cgst > 0 ? String.format("%." + hmany + "f", cgst) : "",
                    sgst > 0 ? String.format("%." + hmany + "f", sgst) : "",
                    igst > 0 ? String.format("%." + hmany + "f", igst) : "",
                    String.format("%." + hmany + "f", taxAmt),
                    String.format("%." + hmany + "f", total)
                });

                totalTaxable += taxable;
                totalTax += taxAmt;
                totalAmount += total;
                totalCGST += cgst;
                totalSGST += sgst;
                totalIGST += igst;
            }

            // Empty row and totals row
            dataList.add(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
            dataList.add(new Object[]{
                "", "TOTAL", "", "", "", "", "", "", "",
                String.format("%." + hmany + "f", totalTaxable),
                "", // Tax rate
                String.format("%." + hmany + "f", totalCGST),
                String.format("%." + hmany + "f", totalSGST),
                String.format("%." + hmany + "f", totalIGST),
                String.format("%." + hmany + "f", totalTax),
                String.format("%." + hmany + "f", totalAmount)
            });

            // Convert List<Object[]> to Object[][]
            Object[][] dataArray = new Object[dataList.size()][];
            for (int i = 0; i < dataList.size(); i++) {
                dataArray[i] = dataList.get(i);
            }

            // Generate the sheet
            createSheetWithData(sheet, headers, dataArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createHsnPurchaseSheet(Workbook wb) {
        Sheet sheet = wb.createSheet("HSN Purchase");

        String[] headers = {
            "HSN Code", "Item Name", "Bill Nos", "Unit", "Quantity", "Total Amount",
            "Taxable Value", "IGST", "CGST", "SGST",
            "Total Tax", "Tax %", "Rate/Unit", "Tax/Unit", "Remarks"
        };

        List<Object[]> dataList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0." + "0".repeat(hmany));

        double grandTotalAmount = 0, grandTaxableValue = 0, totalIGST = 0, totalCGST = 0;

        try {
            Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String from = new SimpleDateFormat("yyyy/MM/dd").format(fromDate);
            String to = new SimpleDateFormat("yyyy/MM/dd").format(toDate);

            String query = "SELECT hsn, iname, "
                    + "GROUP_CONCAT(DISTINCT billno ORDER BY billno SEPARATOR ', ') AS billnos, "
                    + "SUM(quan) AS total_quantity, "
                    + "SUM(total) AS total_amount, "
                    + "SUM(sub) AS taxable_value, "
                    + "SUM(CASE WHEN ttype = 'Local' THEN taxamt ELSE 0 END) AS total_local_tax, "
                    + "SUM(CASE WHEN ttype <> 'Local' THEN taxamt ELSE 0 END) AS total_igst "
                    + "FROM purchase_items "
                    + "WHERE dat BETWEEN '" + from + "' AND '" + to + "' "
                    + "GROUP BY hsn, iname "
                    + "ORDER BY hsn, iname";

            ResultSet rs = util.doQuery(query);

            while (rs.next()) {
                String hsn = rs.getString("hsn");
                String iname = rs.getString("iname");
                String billNos = rs.getString("billnos");
                int quantity = rs.getInt("total_quantity");
                double totalAmount = rs.getDouble("total_amount");
                double taxableValue = rs.getDouble("taxable_value");
                double localTax = rs.getDouble("total_local_tax");
                double igst = rs.getDouble("total_igst");

                double cgst = localTax / 2.0;
                double sgst = cgst;
                double totalTax = igst + localTax;

                double taxPercent = (taxableValue != 0) ? (totalTax / taxableValue) * 100 : 0;
                double ratePerUnit = (quantity > 0) ? taxableValue / quantity : 0;
                double taxPerUnit = (quantity > 0) ? totalTax / quantity : 0;

                grandTotalAmount += totalAmount;
                grandTaxableValue += taxableValue;
                totalCGST += cgst;
                totalIGST += igst;

                Object[] row = new Object[]{
                    hsn,
                    iname,
                    billNos != null ? billNos : "",
                    "", // Unit (fill if available)
                    quantity,
                    df.format(totalAmount),
                    df.format(taxableValue),
                    df.format(igst),
                    df.format(cgst),
                    df.format(sgst),
                    df.format(totalTax),
                    df.format(taxPercent),
                    df.format(ratePerUnit),
                    df.format(taxPerUnit),
                    "" // Remarks
                };
                dataList.add(row);
            }
            rs.close();

            // Add totals row
            double totalTax = totalCGST * 2 + totalIGST;
            dataList.add(new Object[headers.length]); // Blank row
            dataList.add(new Object[]{
                "", "Total", "", "", "", df.format(grandTotalAmount),
                df.format(grandTaxableValue), df.format(totalIGST), df.format(totalCGST), df.format(totalCGST),
                df.format(totalTax), "", "", "", ""
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[][] data = dataList.toArray(new Object[0][]);
        createSheetWithData(sheet, headers, data);
    }

    private void createHsnSalesSheet(Workbook wb) {
        Sheet sheet = wb.createSheet("HSN Sales");

        String[] headers = {
            "HSN Code", "Item Name", "Bill Nos", "Unit", "Quantity", "Total Amount",
            "Taxable Value", "IGST", "CGST", "SGST",
            "Total Tax", "Tax %", "Rate/Unit", "Tax/Unit", "Remarks"
        };

        List<Object[]> dataList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0." + "0".repeat(hmany));

        double grandTotal = 0, grandValue = 0, totalIGST = 0, totalCGST = 0;

        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String start = new SimpleDateFormat("yyyy/MM/dd").format(startDate);
            String end = new SimpleDateFormat("yyyy/MM/dd").format(endDate);

            // Added GROUP_CONCAT for billnos
            String query = "SELECT hsn, iname, GROUP_CONCAT(DISTINCT billno ORDER BY billno SEPARATOR ', ') AS billnos, "
                    + "MAX(udes), SUM(quan), SUM(total), SUM(sub), MAX(tax_type), SUM(taxamt) "
                    + "FROM sales_items "
                    + "WHERE dat BETWEEN '" + start + "' AND '" + end + "' AND entry='purchase' "
                    + "GROUP BY hsn, iname "
                    + "ORDER BY hsn, iname";

            ResultSet rs = util.doQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String hsn = rs.getString("hsn");
                    String iname = rs.getString("iname");
                    String billNos = rs.getString("billnos");
                    String udes = rs.getString(4);
                    int quantity = (int) rs.getDouble(5);
                    double total = rs.getDouble(6);
                    double value = rs.getDouble(7);
                    String taxType = rs.getString(8);
                    double taxAmt = rs.getDouble(9);

                    if ("Inclusive Model-II".equalsIgnoreCase(taxType)) {
                        value -= taxAmt;
                    }

                    // Split tax IGST / CGST
                    double igst = 0, cgst = 0;
                    String taxQuery = "SELECT SUM(taxamt), tax "
                            + "FROM sales_items "
                            + "WHERE dat BETWEEN '" + start + "' AND '" + end + "' AND entry='purchase' "
                            + "AND hsn = '" + hsn + "' AND iname = '" + iname + "' GROUP BY tax";
                    ResultSet taxRs = util.doQuery(taxQuery);
                    if (taxRs != null) {
                        while (taxRs.next()) {
                            double tAmt = taxRs.getDouble(1);
                            String tType = taxRs.getString(2);
                            if ("Local".equalsIgnoreCase(tType)) {
                                cgst += tAmt / 2;
                            } else {
                                igst += tAmt;
                            }
                        }
                        taxRs.close();
                    }

                    double sgst = cgst;
                    double totalTax = igst + cgst + sgst;
                    double taxPercent = (value != 0) ? (totalTax / value) * 100 : 0;
                    double ratePerUnit = (quantity > 0) ? value / quantity : 0;
                    double taxPerUnit = (quantity > 0) ? totalTax / quantity : 0;

                    grandTotal += total;
                    grandValue += value;
                    totalIGST += igst;
                    totalCGST += cgst;

                    dataList.add(new Object[]{
                        hsn,
                        iname,
                        billNos != null ? billNos : "",
                        udes,
                        quantity,
                        df.format(total),
                        df.format(value),
                        df.format(igst),
                        df.format(cgst),
                        df.format(sgst),
                        df.format(totalTax),
                        df.format(taxPercent),
                        df.format(ratePerUnit),
                        df.format(taxPerUnit),
                        ""
                    });
                }
                rs.close();
            }

            double finalTax = totalIGST + 2 * totalCGST;
            dataList.add(new Object[headers.length]);
            dataList.add(new Object[]{
                "", "Total", "", "", "", df.format(grandTotal), df.format(grandValue),
                df.format(totalIGST), df.format(totalCGST), df.format(totalCGST),
                df.format(finalTax), "", "", "", ""
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in createHsnSalesSheet: " + e.getMessage());
        }

        Object[][] data = dataList.toArray(new Object[0][]);
        createSheetWithData(sheet, headers, data);
    }

    private void createHsnPurchaseReturnSheet(Workbook wb) {
        Sheet sheet = wb.createSheet("HSN Purchase Return");
        String[] headers = {
            "HSN Code", "Item Name", "Bill Nos", "Unit", "Quantity", "Total Amount",
            "Taxable Value", "IGST", "CGST", "SGST", "Total Tax", "Rate/Unit", "Tax/Unit", "Remarks"
        };

        List<Object[]> rows = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String start = new SimpleDateFormat("yyyy/MM/dd").format(sdf.parse(dfrom));
            String end = new SimpleDateFormat("yyyy/MM/dd").format(sdf.parse(dto));

            // Updated query to get comma-separated bill numbers (GROUP_CONCAT syntax for MySQL)
            String query = "SELECT hsn, iname, GROUP_CONCAT(DISTINCT billno ORDER BY billno SEPARATOR ', ') AS billnos, "
                    + "SUM(quan), SUM(total), SUM(sub) "
                    + "FROM preturn_items "
                    + "WHERE dat BETWEEN '" + start + "' AND '" + end + "' "
                    + "GROUP BY hsn, iname ORDER BY hsn, iname";

            ResultSet rs = util.doQuery(query);

            double totalAmt = 0, totalTaxable = 0, totalIGST = 0, totalCGST = 0, totalSGST = 0;

            while (rs != null && rs.next()) {
                String hsn = rs.getString("hsn");
                String iname = rs.getString("iname");
                String billNos = rs.getString("billnos");
                double quantity = rs.getDouble(4);
                double total = rs.getDouble(5);
                double taxable = rs.getDouble(6);

                // Tax split
                double igst = 0, cgst = 0, sgst = 0;
                String taxQuery = "SELECT SUM(taxamt), ttype FROM preturn_items "
                        + "WHERE dat BETWEEN '" + start + "' AND '" + end + "' "
                        + "AND hsn='" + hsn + "' AND iname='" + iname + "' GROUP BY ttype";
                ResultSet taxRS = util.doQuery(taxQuery);
                while (taxRS != null && taxRS.next()) {
                    double taxAmt = taxRS.getDouble(1);
                    String type = taxRS.getString(2);
                    if ("Local".equalsIgnoreCase(type)) {
                        cgst += taxAmt / 2;
                        sgst += taxAmt / 2;
                    } else {
                        igst += taxAmt;
                    }
                }
                if (taxRS != null) {
                    taxRS.close();
                }

                // Derived fields
                double ratePerUnit = quantity > 0 ? total / quantity : 0;
                double taxPerUnit = quantity > 0 ? (igst + cgst + sgst) / quantity : 0;
                double totalTax = igst + cgst + sgst;

                // Accumulate totals
                totalAmt += total;
                totalTaxable += taxable;
                totalIGST += igst;
                totalCGST += cgst;
                totalSGST += sgst;

                rows.add(new Object[]{
                    hsn,
                    iname,
                    billNos != null ? billNos : "",
                    "",
                    (int) quantity,
                    df.format(total),
                    df.format(taxable),
                    df.format(igst),
                    df.format(cgst),
                    df.format(sgst),
                    df.format(totalTax),
                    df.format(ratePerUnit),
                    df.format(taxPerUnit),
                    ""
                });
            }
            if (rs != null) {
                rs.close();
            }

            // Add total row
            rows.add(new Object[]{
                "", "Total", "", "", "",
                df.format(totalAmt),
                df.format(totalTaxable),
                df.format(totalIGST),
                df.format(totalCGST),
                df.format(totalSGST),
                df.format(totalIGST + totalCGST + totalSGST),
                "", "", ""
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert list to array
        Object[][] data = rows.toArray(new Object[0][]);
        createSheetWithData(sheet, headers, data);
    }

    private void createHsnSalesReturnSheet(Workbook wb) {
        Sheet sheet = wb.createSheet("HSN Sales Return");

        String[] headers = {
            "HSN Code", "Item Name", "Bill Nos", "Unit", "Quantity", "Taxable Value",
            "Rate of Tax (%)", "IGST", "CGST", "SGST", "Total Tax",
            "Total Amount", "Remarks"
        };

        List<Object[]> dataList = new ArrayList<>();

        try {
            Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String from = new SimpleDateFormat("yyyy/MM/dd").format(fromDate);
            Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String to = new SimpleDateFormat("yyyy/MM/dd").format(toDate);

            // Main query with GROUP_CONCAT to fetch comma separated bill numbers
            String query = "SELECT hsn, iname, GROUP_CONCAT(DISTINCT billno ORDER BY billno SEPARATOR ', ') AS billnos, udes, "
                    + "SUM(quan), SUM(sub), SUM(taxamt), taxp, tax_type "
                    + "FROM sreturn_items "
                    + "WHERE dat BETWEEN '" + from + "' AND '" + to + "' AND entry='purchase' "
                    + "GROUP BY hsn, iname, udes, taxp, tax_type "
                    + "ORDER BY hsn, iname";

            ResultSet r = util.doQuery(query);

            double totalValue = 0, totalTax = 0, totalAmount = 0;
            double totalIGST = 0, totalCGST = 0, totalSGST = 0;

            while (r != null && r.next()) {
                String hsn = r.getString("hsn");
                String iname = r.getString("iname");
                String billNos = r.getString("billnos");
                String udes = r.getString("udes");
                double qty = r.getDouble("SUM(quan)");
                double taxable = r.getDouble("SUM(sub)");
                double taxamt = r.getDouble("SUM(taxamt)");
                double taxRate = r.getDouble("taxp");
                String taxType = r.getString("tax_type");

                double igst = 0, cgst = 0, sgst = 0;

                // Determine tax split
                if ("Local".equalsIgnoreCase(taxType)) {
                    cgst = taxamt / 2;
                    sgst = taxamt / 2;
                } else {
                    igst = taxamt;
                }

                double total = taxable + taxamt;

                // Add to overall totals
                totalValue += taxable;
                totalTax += taxamt;
                totalAmount += total;
                totalIGST += igst;
                totalCGST += cgst;
                totalSGST += sgst;

                Object[] row = {
                    hsn,
                    iname,
                    billNos != null ? billNos : "",
                    udes,
                    (int) qty,
                    String.format("%." + hmany + "f", taxable),
                    String.format("%." + hmany + "f", taxRate),
                    String.format("%." + hmany + "f", igst),
                    String.format("%." + hmany + "f", cgst),
                    String.format("%." + hmany + "f", sgst),
                    String.format("%." + hmany + "f", taxamt),
                    String.format("%." + hmany + "f", total),
                    ""
                };

                dataList.add(row);
            }

            // Add total row
            dataList.add(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", ""});
            dataList.add(new Object[]{
                "", "Total", "", "", "",
                String.format("%." + hmany + "f", totalValue),
                "", // Rate empty in total row
                String.format("%." + hmany + "f", totalIGST),
                String.format("%." + hmany + "f", totalCGST),
                String.format("%." + hmany + "f", totalSGST),
                String.format("%." + hmany + "f", totalTax),
                String.format("%." + hmany + "f", totalAmount),
                ""
            });

            createSheetWithData(sheet, headers, dataList.toArray(new Object[0][]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSummarySheet(Workbook wb) {
        Sheet sheet = wb.createSheet("GSTR Summary");

        String[] headers = {
            "Category", "5% Tax", "12% Tax", "18% Tax", "28% Tax", "Cess", "Total", "Output/Input", "Net Payable"
        };
        List<Object[]> rows = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");

        try {
            Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String from = new SimpleDateFormat("yyyy/MM/dd").format(fromDate);
            Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String to = new SimpleDateFormat("yyyy/MM/dd").format(toDate);

            double s5 = 0, s12 = 0, s18 = 0, s28 = 0;
            double sr5 = 0, sr12 = 0, sr18 = 0, sr28 = 0;
            double p5 = 0, p12 = 0, p18 = 0, p28 = 0;
            double pr5 = 0, pr12 = 0, pr18 = 0, pr28 = 0;

            // Sales
            String query = "SELECT SUM(CASE WHEN taxp = 5 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 12 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 18 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 28 THEN taxamt ELSE 0 END) "
                    + "FROM sales_items WHERE dat BETWEEN '" + from + "' AND '" + to + "'";
            ResultSet r = util.doQuery(query);
            if (r.next()) {
                s5 = r.getDouble(1);
                s12 = r.getDouble(2);
                s18 = r.getDouble(3);
                s28 = r.getDouble(4);
            }

            // Sales Return
            query = "SELECT SUM(CASE WHEN taxp = 5 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 12 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 18 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 28 THEN taxamt ELSE 0 END) "
                    + "FROM sreturn_items WHERE dat BETWEEN '" + from + "' AND '" + to + "'";
            r = util.doQuery(query);
            if (r.next()) {
                sr5 = r.getDouble(1);
                sr12 = r.getDouble(2);
                sr18 = r.getDouble(3);
                sr28 = r.getDouble(4);
            }

            // Purchase
            query = "SELECT SUM(CASE WHEN taxp = 5 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 12 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 18 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 28 THEN taxamt ELSE 0 END) "
                    + "FROM purchase_items WHERE dat BETWEEN '" + from + "' AND '" + to + "'";
            r = util.doQuery(query);
            if (r.next()) {
                p5 = r.getDouble(1);
                p12 = r.getDouble(2);
                p18 = r.getDouble(3);
                p28 = r.getDouble(4);
            }

            // Purchase Return
            query = "SELECT SUM(CASE WHEN taxp = 5 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 12 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 18 THEN taxamt ELSE 0 END), "
                    + "SUM(CASE WHEN taxp = 28 THEN taxamt ELSE 0 END) "
                    + "FROM preturn_items WHERE dat BETWEEN '" + from + "' AND '" + to + "'";
            r = util.doQuery(query);
            if (r.next()) {
                pr5 = r.getDouble(1);
                pr12 = r.getDouble(2);
                pr18 = r.getDouble(3);
                pr28 = r.getDouble(4);
            }

            double stot = s5 + s12 + s18 + s28;
            double srtot = sr5 + sr12 + sr18 + sr28;
            double ptot = p5 + p12 + p18 + p28;
            double prtot = pr5 + pr12 + pr18 + pr28;

            double output = stot - srtot;
            double input = ptot - prtot;
            double net = output - input;

            // Row structure: Category | 5% | 12% | 18% | 28% | Cess | Total | Type | Net
            rows.add(new Object[]{"Sales", df.format(s5), df.format(s12), df.format(s18), df.format(s28), "0.00", df.format(stot), "Output", ""});
            rows.add(new Object[]{"(-) Sales Return", df.format(sr5), df.format(sr12), df.format(sr18), df.format(sr28), "0.00", df.format(srtot), "Output", ""});
            rows.add(new Object[]{"Output Tax", "", "", "", "", "", "", "", df.format(output)});
            rows.add(new Object[]{"", "", "", "", "", "", "", "", ""});

            rows.add(new Object[]{"Purchase", df.format(p5), df.format(p12), df.format(p18), df.format(p28), "0.00", df.format(ptot), "Input", ""});
            rows.add(new Object[]{"(-) Purchase Return", df.format(pr5), df.format(pr12), df.format(pr18), df.format(pr28), "0.00", df.format(prtot), "Input", ""});
            rows.add(new Object[]{"Input Tax", "", "", "", "", "", "", "", df.format(input)});
            rows.add(new Object[]{"", "", "", "", "", "", "", "", ""});

            rows.add(new Object[]{"GST SUMMARY", "", "", "", "", "", "", "", ""});
            rows.add(new Object[]{"Output Tax", df.format(output), "", "", "", "", "", "", ""});
            rows.add(new Object[]{"Input Tax", df.format(input), "", "", "", "", "", "", ""});
            rows.add(new Object[]{"Tax Payable", df.format(net), "", "", "", "", "", "", ""});

        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[][] data = rows.toArray(new Object[0][]);
        createSheetWithData(sheet, headers, data);
    }

    private void createSheetWithData(Sheet sheet, String[] headers, Object[][] data) {
        Workbook workbook = sheet.getWorkbook();

        // Create a header cell style: bold, colored background, centered text
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Create a standard cell style with borders and number formatting
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        // Create a number format for currency or values (optional)
        DataFormat format = workbook.createDataFormat();
        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(format.getFormat("#,##0.00")); // Format for numbers with 2 decimals

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Populate data rows
        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j);
                Object value = data[i][j];
                if (value instanceof Number) {
                    // Apply number formatting for numeric values
                    cell.setCellValue(((Number) value).doubleValue());
                    cell.setCellStyle(numberStyle); // Apply number formatting
                } else {
                    cell.setCellValue(value != null ? value.toString() : "");
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Optionally, adjust row heights if needed
        for (int i = 1; i <= data.length; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                int rowHeight = row.getHeight();
                row.setHeight((short) (rowHeight + 200)); // Increase row height if needed
            }
        }
    }

    public static File getSaveFileFromUser(String dialogTitle, String defaultFileName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setSelectedFile(new File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Append .xlsx if not already present
            if (!selectedFile.getName().toLowerCase().endsWith(".xlsx")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".xlsx");
            }

            // If file exists, auto-increment name
            selectedFile = getUniqueFile(selectedFile);

            return selectedFile;
        }

        return null; // User cancelled
    }

    private static File getUniqueFile(File file) {
        String name = file.getName();
        String parent = file.getParent();

        String baseName;
        String extension;

        int dotIndex = name.lastIndexOf('.');
        if (dotIndex > 0) {
            baseName = name.substring(0, dotIndex);
            extension = name.substring(dotIndex);
        } else {
            baseName = name;
            extension = "";
        }

        File uniqueFile = new File(parent, baseName + extension);
        int count = 1;

        while (uniqueFile.exists()) {
            uniqueFile = new File(parent, baseName + "(" + count + ")" + extension);
            count++;
        }

        return uniqueFile;
    }

}
