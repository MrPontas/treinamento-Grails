package teste;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.governors.TimeoutGovernor;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

public class CriarRelatorio {
//Variáveis --------------------------------------------------------------------
    private String path;
    private String name;
    private List data; 
    private Map parameters;
    private ExportFormat format;
    private ByteArrayOutputStream output;
    private HttpServletResponse response;

//Método Construtor ------------------------------------------------------------
    public CriarRelatorio() {
    }

    public CriarRelatorio(String path, String name, List data, Map parameters, ExportFormat format, HttpServletResponse response) {
        this.path = path;
        this.name = name;
        this.data = data;
        this.parameters = parameters;
        this.format = format;
        this.response = response;
        output = new ByteArrayOutputStream();
    }

    public CriarRelatorio(String path, String name, List data, Map parameters, ExportFormat format) {
        this.path = path;
        this.name = name;
        this.data = data;
        this.parameters = parameters;
        this.format = format;
        output = new ByteArrayOutputStream();
    }

//Métodos ----------------------------------------------------------------------
    public void construirRelatorio() throws Exception {
        //Configurações para EXCEL
        //Alterado desta maneira para poder definir o tempo máximo da geração do relatório
        JasperReport jr = (JasperReport) JRLoader.loadObject(new File(this.path + "/" + this.name + ".jasper"));
        jr.setProperty(TimeoutGovernor.PROPERTY_TIMEOUT, "150000");//150000
        jr.setProperty(TimeoutGovernor.PROPERTY_TIMEOUT_ENABLED, "true");

        //Configurações para EXCEL
        if(this.format.equals(ExportFormat.XLS_FORMAT) || this.format.equals(ExportFormat.CSV_FORMAT)) {
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
            jr.setProperty(JRTextField.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");//Imprime todo o texto mesmo que passe o tamanho do campo
        }
        else {
            jr.setProperty(JRTextField.PROPERTY_TRUNCATE_AT_CHAR, "true");//Quebra por letra e não por palavra que é o padrão
        }

        JasperPrint print = JasperFillManager.fillReport(jr, this.parameters, new JRBeanCollectionDataSource(this.data));

        JRExporter exporter = ExportFormat.getExporter(this.format);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, this.output);
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");

        //Configurações para EXCEL
        if(this.format.equals(ExportFormat.XLS_FORMAT)) {
            exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            print.getPropertiesMap().setProperty("net.sf.jasperreports.export.xls.wrap.text", "false");
        }
        else if(this.format.equals(ExportFormat.CSV_FORMAT)) {//CSV
            exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ";");
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "cp1252");
        }

        //Exporta o Relatório
        exporter.exportReport();
    }

    public void gerarRelatorio() throws Exception {
        this.construirRelatorio();

        this.response.setContentType(this.format.getMimeTyp());
        this.response.setCharacterEncoding("UTF-8");

        Cookie myCookie = new Cookie("downloadToken", "123");
        myCookie.setMaxAge(600);
        myCookie.setPath("/");
        this.response.addCookie(myCookie);

        //Se for diferente de PDF_FORMAT manda salvar
        if(this.format.equals(ExportFormat.PDF_FORMAT)) {
            this.response.setHeader("Content-disposition", "filename=" + this.name.replace("relatorio", "") + "." + this.format.getExtension());
        }
        else {
            this.response.setHeader("Content-disposition", "attachment;filename=" + this.name.replace("relatorio", "") + "." + this.format.getExtension());
        }

        this.response.getOutputStream().write(this.output.toByteArray());
        this.response.getOutputStream().flush();
    }

     public JasperPrint construirRelatorioJasperPrint() throws Exception{
        //Alterado desta maneira para poder definir o tempo máximo da geração do relatório
        JasperReport jr = (JasperReport) JRLoader.loadObject(new File(this.path + "/" + this.name + ".jasper"));
        jr.setProperty(TimeoutGovernor.PROPERTY_TIMEOUT, "150000");//150000
        jr.setProperty(TimeoutGovernor.PROPERTY_TIMEOUT_ENABLED, "true");

        //Configurações para EXCEL
        if(this.format.equals(ExportFormat.XLS_FORMAT)) {
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
            jr.setProperty(JRTextField.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");//Imprime todo o texto mesmo que passe o tamanho do campo
        }
        else {
            jr.setProperty(JRTextField.PROPERTY_TRUNCATE_AT_CHAR, "true");//Quebra por letra e não por palavra que é o padrão
        }

        JasperPrint print;
        if (this.data instanceof JRXmlDataSource)
            print = JasperFillManager.fillReport(jr, this.parameters, (JRXmlDataSource) this.data);
        else 
            print = JasperFillManager.fillReport(jr, this.parameters, new JRBeanCollectionDataSource((List) this.data));
        //Configurações para EXCEL
        if(this.format.equals(ExportFormat.XLS_FORMAT)) {
            print.getPropertiesMap().setProperty("net.sf.jasperreports.export.xls.wrap.text", "false");
        }

        return print;
    }

    public byte[] gerarRelatorioBytes() throws Exception {
        this.construirRelatorio();

        return this.output.toByteArray();
    }

//Sets e Gets ------------------------------------------------------------------
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }  

    public ExportFormat getFormat() {
        return format;
    }

    public void setFormat(ExportFormat format) {
        this.format = format;
    }

    public ByteArrayOutputStream getOutput() {
        return output;
    }

    public void setOutput(ByteArrayOutputStream output) {
        this.output = output;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}