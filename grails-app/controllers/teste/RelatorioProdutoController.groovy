package teste

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class RelatorioProdutoController {

    def index() {
        def parametros = lerParametros(params, true);

        parametros
    }

    def criarRelatorio(){
        def parametros = lerParametros(params, false);

        println parametros
        List relList = Produto.createCriteria().list() {
            and{
                if (parametros.nome.size()>0)
                    like("nome", "%"+parametros.nome+"%")
                if (parametros.dIni)
                    ge("criadoEm", parametros.dIni)
                if (parametros.dFim)
                    le("criadoEm", parametros.dFim)
            }
        }

        if(relList.size()==0) {
            flash.message = message(code: "default.relatorio.vazio.invalid");
            render(view: "index", model: parametros);
            return;
        }
        flash.message = message(code: "");
        Map map = new HashMap();
        if(parametros.nome && parametros.nome.size() > 0)
            map.put("NOME", parametros.nome);
        map.put("D_INI", parametros.dIni);
        map.put("D_FIM", parametros.dFim);



        try {
            if(params.getAt("_action_criarRelatorio").contains("PDF")) {
                map.put("PDF", true);
                (new CriarRelatorio(servletContext.getRealPath("/reports"), "relatorioProdutos", relList, map, ExportFormat.PDF_FORMAT, response)).gerarRelatorio();
            }
            else {
                map.put("PDF", false);
                (new CriarRelatorio(servletContext.getRealPath("/reports"), "relatorioProdutos", relList, map, ExportFormat.XLS_FORMAT, response)).gerarRelatorio();
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
        String nome;
        Date dIni, dFim;

        try{nome = params.nome}catch(Exception e){}
        //2021-01-09 --> 2021/01/09 00:00
        if(params.dIni != null && params.dFim.size() > 0){
            String dIniFormatada = params.dIni.replaceAll("-", "/") + " 00:00"
            dIni = new Date(dIniFormatada)
        }
        if(params.dFim != null && params.dFim.size() > 0){
            String dFimFormatada = params.dFim.replaceAll("-", "/") + " 23:59"
            dFim = new Date(dFimFormatada)
        }


        [dIni:dIni, dFim: dFim, nome: nome]
    }
}
