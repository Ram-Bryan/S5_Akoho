/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JRDataSource
 *  net.sf.jasperreports.engine.JREmptyDataSource
 *  net.sf.jasperreports.engine.JRException
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
 *  net.sf.jasperreports.engine.export.JRPdfExporter
 *  net.sf.jasperreports.engine.export.oasis.JROdtExporter
 *  net.sf.jasperreports.engine.export.ooxml.JRDocxExporter
 *  net.sf.jasperreports.engine.export.ooxml.JRPptxExporter
 *  net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
 *  net.sf.jasperreports.export.ExporterInput
 *  net.sf.jasperreports.export.ExporterOutput
 *  net.sf.jasperreports.export.ReportExportConfiguration
 *  net.sf.jasperreports.export.SimpleDocxReportConfiguration
 *  net.sf.jasperreports.export.SimpleExporterInput
 *  net.sf.jasperreports.export.SimpleOdtReportConfiguration
 *  net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
 *  net.sf.jasperreports.export.SimplePdfReportConfiguration
 *  net.sf.jasperreports.export.SimplePptxReportConfiguration
 *  net.sf.jasperreports.export.SimpleXlsxReportConfiguration
 */
package reporting;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.ReportExportConfiguration;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOdtReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimplePptxReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class ReportingUtils {
    private JasperPrint jasperPrint = null;

    public void PDF(String reportPath, List dataSource, Map param, OutputStream out) throws JRException, IOException {
        this.jasperPrint = this.fillReport(reportPath, dataSource, param);
        this.exportReport(this.jasperPrint, ReportType.PDF, out);
    }

    public void DOCX(String reportPath, List dataSource, Map param, OutputStream out) throws JRException, IOException {
        this.jasperPrint = this.fillReport(reportPath, dataSource, param);
        this.exportReport(this.jasperPrint, ReportType.DOCX, out);
    }

    public void XLSX(String reportPath, List dataSource, Map param, OutputStream out) throws JRException, IOException {
        this.jasperPrint = this.fillReport(reportPath, dataSource, param);
        this.exportReport(this.jasperPrint, ReportType.XLSX, out);
    }

    public void ODT(String reportPath, List dataSource, Map param, OutputStream out) throws JRException, IOException {
        this.jasperPrint = this.fillReport(reportPath, dataSource, param);
        this.exportReport(this.jasperPrint, ReportType.ODT, out);
    }

    public void PPT(String reportPath, List dataSource, Map param, OutputStream out) throws JRException, IOException {
        this.jasperPrint = this.fillReport(reportPath, dataSource, param);
        this.exportReport(this.jasperPrint, ReportType.PPT, out);
    }

    public void export(String reportPath, List dataSource, Map param, OutputStream out, ReportType reportType) throws JRException {
        this.jasperPrint = this.fillReport(reportPath, dataSource, param);
        this.exportReport(this.jasperPrint, reportType, out);
    }

    public void export(String reportPath, List dataSource, Map param, OutputStream out, int pageIndex) throws JRException {
        this.jasperPrint = this.fillReport(reportPath, dataSource, param);
        this.exportReportSpecPageStart(this.jasperPrint, out, pageIndex);
    }

    public JasperPrint fillReport(String reportPath, List dataSource, Map param) throws JRException {
        if (dataSource == null || dataSource.isEmpty()) {
            return JasperFillManager.fillReport((String)reportPath, (Map)param, (JRDataSource)new JREmptyDataSource());
        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource((Collection)dataSource);
        return JasperFillManager.fillReport((String)reportPath, (Map)param, (JRDataSource)beanCollectionDataSource);
    }

    public void exportReport(JasperPrint jasperPrint, ReportType reporType, OutputStream out) throws JRException {
        try {
            JRPdfExporter jRExporter = null;
            switch (reporType) {
                case PDF: {
                    jRExporter = new JRPdfExporter();
                    SimplePdfReportConfiguration configuration = new SimplePdfReportConfiguration();
                    jRExporter.setConfiguration((ReportExportConfiguration)configuration);
                    break;
                }
                case DOCX: {
                    jRExporter = new JRDocxExporter();
                    SimpleDocxReportConfiguration configurationDocx = new SimpleDocxReportConfiguration();
                    break;
                }
                case XLSX: {
                    jRExporter = new JRXlsxExporter();
                    SimpleXlsxReportConfiguration configurationXLS = new SimpleXlsxReportConfiguration();
                    configurationXLS.setDetectCellType(Boolean.valueOf(true));
                    configurationXLS.setCollapseRowSpan(Boolean.valueOf(false));
                    jRExporter.setConfiguration((ReportExportConfiguration)configurationXLS);
                    break;
                }
                case PPT: {
                    jRExporter = new JRPptxExporter();
                    SimplePptxReportConfiguration configurationPpt = new SimplePptxReportConfiguration();
                    break;
                }
                case ODT: {
                    jRExporter = new JROdtExporter();
                    SimpleOdtReportConfiguration simpleOdtReportConfiguration = new SimpleOdtReportConfiguration();
                }
            }
            jRExporter.setExporterInput((ExporterInput)new SimpleExporterInput(jasperPrint));
            jRExporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(out));
            jRExporter.exportReport();
        }
        catch (JRException jreException) {
            jreException.printStackTrace();
            throw jreException;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void exportReportSpecPageStart(JasperPrint jasperPrint, OutputStream out, int pageStart) throws JRException {
        try {
            JRPdfExporter jRExporter = new JRPdfExporter();
            SimplePdfReportConfiguration configuration = new SimplePdfReportConfiguration();
            configuration.setStartPageIndex(Integer.valueOf(pageStart));
            jRExporter.setConfiguration((ReportExportConfiguration)configuration);
            jRExporter.setExporterInput((ExporterInput)new SimpleExporterInput(jasperPrint));
            jRExporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(out));
            jRExporter.exportReport();
        }
        catch (JRException jreException) {
            jreException.printStackTrace();
            throw jreException;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String setName(ReportType reportType) {
        return null;
    }

    public static enum ReportType {
        PDF,
        DOCX,
        XLSX,
        ODT,
        PPT,
        XLS;

    }
}

