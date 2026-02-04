package com.selrom.utils;

import java.io.InputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

public class JasperReportCompiler {

    public static JasperReport compileReport(String jrxmlPath) throws JRException {
        InputStream reportStream = JasperReportCompiler.class.getResourceAsStream(jrxmlPath);
        if (reportStream == null) {
            throw new JRException("Report template not found: " + jrxmlPath);
        }
        return JasperCompileManager.compileReport(reportStream);
    }
}
