/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
//import net.sf.jasperreports.engine.export.JRHtmlExporter;
//import net.sf.jasperreports.engine.export.JRRtfExporter;
//import net.sf.jasperreports.engine.export.JRTextExporter;
//import net.sf.jasperreports.engine.export.JRXmlExporter;
//import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
//import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author cristian
 */
public enum ExportFormat {

    PDF_FORMAT("application/pdf", "pdf", false),
//    HTML_FORMAT("text/html", "html", true),
//    XML_FORMAT("text/xml", "xml", false),
    CSV_FORMAT("text/csv", "csv", false),
    XLS_FORMAT("application/vnd.ms-excel", "xls", false);
//    RTF_FORMAT("text/rtf", "rtf", false),
//    TEXT_FORMAT("text/plain", "txt", true),
//    ODT_FORMAT("application/vnd.oasis.opendocument.text", "odt", false),
//    ODS_FORMAT("application/vnd.oasis.opendocument.spreadsheetl", "ods", false),
//    DOCX_FORMAT("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx", false),
//    XLSX_FORMAT("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx", false),
//    PPTX_FORMAT("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx", false);
    private String mimeTyp;
    private String extension;
    private boolean inline;

    private ExportFormat(String mimeTyp, String extension, boolean inline) {
        this.mimeTyp = mimeTyp;
        this.extension = extension;
        this.inline = inline;
    }

    public static JRExporter getExporter(ExportFormat format) {
        switch (format) {
            case PDF_FORMAT:
                return new JRPdfExporter();
//            case HTML_FORMAT:
//                return new JRHtmlExporter();
//            case XML_FORMAT:
//                return new JRXmlExporter();
            case CSV_FORMAT:
                return new JRCsvExporter();
            case XLS_FORMAT:
                return new JRXlsExporter();
//            case RTF_FORMAT:
//                return new JRRtfExporter();
//            case TEXT_FORMAT:
//                return new JRTextExporter();
//            case ODT_FORMAT:
//                return new JROdtExporter();
//            case ODS_FORMAT:
//                return new JROdsExporter();
//            case DOCX_FORMAT:
//                return new JRDocxExporter();
//            case XLSX_FORMAT:
//                return new JRXlsxExporter();
//            case PPTX_FORMAT:
//                return new JRPptxExporter();
            default:
                return new JRPdfExporter();
        }
    }

    public String getMimeTyp() {
        return mimeTyp;
    }

    public void setMimeTyp(String mimeTyp) {
        this.mimeTyp = mimeTyp;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean isInline() {
        return inline;
    }

    public void setInline(boolean inline) {
        this.inline = inline;
    }
}