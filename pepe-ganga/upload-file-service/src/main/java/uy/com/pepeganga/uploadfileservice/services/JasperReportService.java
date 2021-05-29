package uy.com.pepeganga.uploadfileservice.services;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class JasperReportService {

    private static final String BASE_PATH_REPORT = "/reports/";

    public byte[] exportReport(ExportType exportType, String path, String fileExportName, List<?> collection) throws JRException {
        byte[] bytes = new byte[0];

            JasperReport jasperReport = getJasperReport(path);
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(collection);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, source);
            switch (exportType){
                case EXCEL:
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
                        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                        xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                        xlsxExporter.setExporterOutput(output);
                        SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
                        reportConfigXLS.setSheetNames(new String[] { fileExportName });
                        xlsxExporter.setConfiguration(reportConfigXLS);
                        xlsxExporter.exportReport();
                        bytes = byteArray.toByteArray();
                        output.close();
                    break;
                case PDF:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + exportType);
            }
            return bytes;

    }

    private JasperReport getJasperReport(String path) throws JRException {
        InputStream input = getClass().getResourceAsStream(BASE_PATH_REPORT.concat(path));
        return JasperCompileManager.compileReport(input);
    }

    public enum ExportType {
        EXCEL,
        PDF
    }
}
