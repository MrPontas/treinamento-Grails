package teste

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class RelatorioCompraController {

    def index() {
        def parametros = lerParametros(params, true);

        parametros
    }

    def criarRelatorio(){
        def parametros = lerParametros(params, false);
        def totalFinal = 0;

        List relList = Compra.createCriteria().list() {
            if (parametros.dIni)
                ge("data", parametros.dIni)
            if (parametros.dFim)
                le("data", parametros.dFim)
        }
        for(compra in relList){
            totalFinal += compra.valorTotal
        }

        if(relList.size()==0) {
            flash.message = message(code: "default.relatorio.vazio.invalid");
            render(view: "index", model: parametros);
            return;
        }
        flash.message = message(code: "");
        Map map = new HashMap();
        map.put("D_INI", parametros.dIni);
        map.put("D_FIM", parametros.dFim);
        map.put("TOTAL_FINAL", totalFinal);



        try {
            if(params.getAt("_action_criarRelatorio").contains("PDF")) {
                map.put("PDF", true);
                (new CriarRelatorio(servletContext.getRealPath("/reports"), "relatorioCompras", relList, map, ExportFormat.PDF_FORMAT, response)).gerarRelatorio();
            }
            else {
                map.put("PDF", false);
                (new CriarRelatorio(servletContext.getRealPath("/reports"), "relatorioCompras", relList, map, ExportFormat.XLS_FORMAT, response)).gerarRelatorio();
            }
        }
        catch(Exception ex) {
            if(ex instanceof net.sf.jasperreports.governors.TimeoutGovernorException) {
                flash.message = message(code: 'default.timeException.relatorio.message', args: [message(code: 'relatorioClientes.label', default: 'Relatório')])
            }
            else {
                flash.message = message(code: ex.getMessage());
            }
            render(view: "index", model: parametros);
            return;
        }
    }

    def lerParametros(params, primeiroAcesso) {
        Date dIni, dFim;

        if(params.dIni != null && params.dFim.size() > 0){
            String dIniFormatada = params.dIni.replaceAll("-", "/") + " 00:00"
            dIni = new Date(dIniFormatada)
        }
        if(params.dFim != null && params.dFim.size() > 0){
            String dFimFormatada = params.dFim.replaceAll("-", "/") + " 23:59"
            dFim = new Date(dFimFormatada)
        }


        [dIni:dIni, dFim: dFim]
    }
}
