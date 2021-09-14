function createViewButtonFnc(select){   
    if ( !($( "#"+select.attr('name').replace(/\./g, "\\.").replace(/\[/g, "\\[").replace(/\]/g, "\\]")+"view" ).length) ){
        if ((select.attr('visualiza')==="false") || (select.attr('visualiza')==="nada")){
            return ;
        }
        
        $( "<a>" )
           .attr( "type", "button")
          .attr( "id", select.attr('name')+"view" )
          .attr( "href", select.attr('servidor')+((select.attr('controllerDomain')==null)?select.attr('controller'):select.attr('controllerDomain'))+"/"+"edit/"+select.val()+((select.attr('ieEndereco')==null)?"":'?ieEndereco=true') )
          .attr( "tabIndex", -1 )
          .prependTo( select.next( ".input-group" ).children( ".input-group-btn" ) )
          .addClass( "btn-padrao btn btn-default" )
          .append( "<i class='fa fa-eye'></i>" )
          .attr( "title", "Visualizar registro" )
          .attr( "target","_blank")
          .tooltip()
          .button({text: false});
    }
}

function preencherSelect(id, idSelect, urlGet, camposLabel, camposValue, campoValor){
    camposLabel=camposLabel.split(",");
    camposValue=(camposValue)?camposValue.split(","):camposLabel;
    idSelect = idSelect.replace(/\./g,'\\.').replace(/\[/g, "\\[").replace(/\]/g, "\\]");
    var select = $("#"+idSelect);
    $.ajax({        
        url: urlGet,
        type: "POST",
        dataType: "json",
        async: false,
        data: {
            searchId: true,
            search: id,
            idReferencia: select.attr("idReferencia"),
            idReferencia2: select.attr("idReferencia2"),
            idReferencia3: select.attr("idReferencia3"),
            idReferencia4: select.attr("idReferencia4")
        },
        success: function( data ) {
            if (data.length>0) {
                var item = data[0];
                select.find('option').remove().end()
                .append($('<option>').text(
                        function(){
                            var retorno;
                            $.each(camposLabel, function( index, value ) {
                                var values = value.split('.'), valueItem;
                                if(values.length===1)
                                    valueItem = item[values[0]];
                                else if(values.length===2) 
                                    valueItem = item[values[0]][values[1]];
                                else if(values.length===3) 
                                    valueItem = item[values[0]][values[1]][values[2]];

                                if (index===0)
                                    retorno = valueItem;
                                else if (valueItem!==null && valueItem!=="")
                                    retorno = retorno + " | " + valueItem;
                            });
                            return retorno;
                        }
                    )
                    .attr('value', item[campoValor])
                    .attr( "selected", "selected" )
                );
                var valueInput="";
                $.each(camposValue, function( index, value ) {
                    var values = value.split('.'), valueItem;
                    if(values.length===1)
                        valueItem = item[values[0]];
                    else if(values.length===2) 
                        valueItem = item[values[0]][values[1]];
                    else if(values.length===3) 
                        valueItem = item[values[0]][values[1]][values[2]];

                    if (index===0)
                        valueInput = valueItem;
                    else if (valueItem!==null && valueItem!=="")
                        valueInput = valueInput + " | " + valueItem;
                });
                $("#autocomplete-"+idSelect+"-input").val(valueInput);                
                select.trigger('change');
                $( "#"+select.attr('name').replace(/\./g, "\\.").replace(/\[/g, "\\[").replace(/\]/g, "\\]")+"view" ).remove();
                if (select.attr('adiciona')==="true")
                    $( "#"+select.attr('name').replace(/\./g, "\\.").replace(/\[/g, "\\[").replace(/\]/g, "\\]")+"add" ).remove();
                createViewButtonFnc(select);
            }
        }
    });
}

function controlarDialog(iframe, nomeController, url, urlPadrao){
    url = url.toString();
   
    var vecURL = url.split("/");
        //Se foi para o List fecha o dialog e retorna para o create
    if (vecURL[vecURL.length-1]==="list" || vecURL[vecURL.length-2]==="list"){
        if ( vecURL[vecURL.length-1]!=="list" ){
            preencherSelect(vecURL[vecURL.length-1], $(iframe).attr("idSelect"), $(iframe).attr("urlGet"), $(iframe).attr("camposLabel"), $(iframe).attr("camposValue"), $(iframe).attr("campoValor"));
        }
        $( "#dialog-form-"+nomeController ).dialog( "close" );
        //$( iframe ).attr("src", urlPadrao+"/create");
    }
    //Remove item de layout
    $( iframe ).contents().find('#header').hide();
    $( iframe ).contents().find('#left-panel').hide();
    $( iframe ).contents().find('.demo').hide();
    $( iframe ).contents().find('#main').attr("id","mainHide");
    $( iframe ).contents().find('#ribbon').hide();
    $( iframe ).contents().find('#content > .row').hide();
    $( iframe ).contents().find('.well').hide();
    $( iframe ).contents().find('.page-footer').hide();
    $( iframe ).contents().find('div#content').css("padding","5px 15px");
    $( iframe ).contents().find('div#wid-id-create').css("margin-bottom","0");
    
    $(iframe.contentWindow).on('beforeunload', function(){
        $(iframe).hide();
        $( "#dialog-form-"+nomeController )
        .append('<div id="textoBloqueio" bloqueioItem="'+nomeController+'"><h1 class="loadingMessageForm bounceIn animated"> Salvando <span class="particle particle--c"></span><span class="particle particle--a"></span><span class="particle particle--b"></span></h1></div>')
    });
    $( iframe ).contents().find('head').append('<style type="text/css">body #fc_widget { display: none; }</style>');
    
    //Desbloqueia a tela
    $( "div[bloqueioItem='"+nomeController+"']" ).remove();
    $(iframe).show();
}
    
(function( $ ) {  
    $.widget( "craw.combobox", {
      _create: function() {         
        this.wrapper = $( "<div>" ).addClass("input-group")
          .insertAfter( this.element );

  
        if (this.element.attr('inputSearch')==="true")
            this.wrapper.addClass( "input-icon input-icon-right" );
        else
            this.wrapper.addClass( "craw-combobox" );
        
        var url = this.element.attr('servidor')+this.element.attr('controller')+"/"+this.element.attr('action');
        var camposLabel = this.element.attr('camposLabel').split(",");
        var camposValue;
        if (this.element.attr('camposValue'))
            camposValue = this.element.attr('camposValue').split(",");  
        else 
            camposValue = camposLabel;  
        
        this.element.hide();
        this._createAutocomplete(url, camposLabel, camposValue, this.element.attr('campoValor'), this.element.attr('limit'), this.element.attr('class'));
        
        this.buttons = $( "<span>" ).addClass("input-group-btn").appendTo( this.wrapper );

        if(!(this.element.attr('semBotao')==="true"))
            this._createShowAllButton();
        else
            this.wrapper.removeClass("input-group");
        
        var selected = this.element.children( ":selected" ),
          value = selected.val() ? selected.text() : "";
        
        if (value!=="")
              this._createViewButton();
        else
              this._createAddButton();
      },
             
      _createAutocomplete: function(url, camposLabel, camposValue, campoValor, limit, className) {
        var selected = this.element.children( ":selected" ),
          value = selected.val() ? selected.text() : "";
        var idReferencia = this.element.attr('idReferencia'), idReferencia2 = this.element.attr('idReferencia2'), idReferencia3 = this.element.attr('idReferencia3'), idReferencia4 = this.element.attr('idReferencia4');
        var autofocus = this.element.prop('autofocus');
        var positionAux = {};
        if (this.element.attr('inputSearch')==="true")
            positionAux = { my : "right top", at: "right bottom" };
        
        var idSelect = this.element.attr("id");
        this.input = $( "<input>" )
          .appendTo( this.wrapper )
          .val( value )
          .attr( "type", "text" )
          .attr( "id", "autocomplete-"+this.element.attr("id")+"-input" )
          .attr( "title", "" )
          .attr( "url", url )
          .attr( "limit", limit )
          .attr( "campoValor", campoValor )
          .attr( "placeholder", this.element.attr('placeholder') )
          .attr( "disabled", this.element.attr('disabled') )
          .addClass( className )
          .removeClass( "comboboxautocomplete" )
          .mouseup(function(){
            $(this).select();
          })
          .autocomplete({
            delay: 0,
            minLength: 0,
            position: positionAux,
            autoFocus: (autofocus===true),
            source: function(request, response) {
                $.ajax({
                    url: url,
                    type: "POST",
                    dataType: "json",
                    data: {
                        max: limit,
                        searchId: false,
                        search: request.term,
                        idReferencia: idReferencia,
                        idReferencia2: idReferencia2,
                        idReferencia3: idReferencia3,
                        idReferencia4: idReferencia4
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: function(){
                                    var retorno;
                                    $.each(camposLabel, function( index, value ) {
                                        var values = value.split('.');
                                        var valueItem;
                                        if(values.length===1)
                                            valueItem = item[values[0]];
                                        else if(values.length===2) 
                                            valueItem = item[values[0]][values[1]];
                                        else if(values.length===3) 
                                            valueItem = item[values[0]][values[1]][values[2]];

                                        if (index===0)
                                            retorno = valueItem;
                                        else if (valueItem!==null && valueItem!=="")
                                            retorno = retorno + " | " + valueItem;
                                    });
                                    return retorno;
                                },
                                value: function(){
                                    var retorno;
                                    $.each(camposValue, function( index, value ) {
                                        var values = value.split('.');
                                        var valueItem;
                                        if(values.length===1)
                                            valueItem = item[values[0]];
                                        else if(values.length===2) 
                                            valueItem = item[values[0]][values[1]];
                                        else if(values.length===3) 
                                            valueItem = item[values[0]][values[1]][values[2]];
                                        
                                        if (index===0)
                                            retorno = valueItem;
                                        else if (valueItem!==null && valueItem!=="")
                                            retorno = retorno + " | " + valueItem;
                                    });
                                    var inputCombo = $("#autocomplete-"+idSelect+"-input");
                                    if (inputCombo.hasClass("auto-width-combo"))
                                        inputCombo.attr('style', 'width: '+(((retorno.length+1)*8)+20)+'px !important');
                                    return retorno;
                                },
                                option: item
                            };
                        }));
                    }
                });
            }
        }).tooltip({
          tooltipClass: "ui-state-highlight"
        });
        
        if (autofocus===true) {
            this.input.keypress(function(e){
                if ( e.which === 13 ){
                    e.preventDefault();
                    return false;
                }
            });
        }
        
        //Troca o redirecionamento do label
        $( "label[for='"+this.element.attr("id")+"']" ).attr("for",("autocomplete-"+this.element.attr("id")+"-input"));
        //Coloca a lista de item 10000 para frete
        $( ".ui-autocomplete.ui-front.ui-menu.ui-widget.ui-widget-content.ui-corner-all" ).css("z-index","10000");
        //Ajusta tamanho se for o caso
        if (this.input.hasClass("auto-width-combo") && this.input.val().trim()!=="")
                this.input.attr('style', 'width: '+(((this.input.val().length+1)*8)+20)+'px !important');
        
        this._on( this.input, {
          autocompleteselect: function( event, ui ) {
            ui.item.option.selected = true;
            
            var select = this.element;
            $.ajax({          
                url: url,
                type: "POST",
                dataType: "json",
                async: false,
                data: {
                    searchId: true,
                    search: ui.item.option.id,
                    idReferencia: this.element.attr("idReferencia"),
                    idReferencia2: this.element.attr("idReferencia2"),
                    idReferencia3: this.element.attr("idReferencia3"),
                    idReferencia4: this.element.attr("idReferencia4")
                },
                success: function( data ) {
                    $.each(data, function(i, item) {
                            select
                            .find('option')
                            .remove()
                            .end()
                            .append($('<option>')
                            .text(
                            function(){
                                var retorno;
                                $.each(camposLabel, function( index, value ) {
                                    var values = value.split('.');
                                    var valueItem;
                                    if(values.length===1)
                                        valueItem = item[values[0]];
                                    else if(values.length===2) 
                                        valueItem = item[values[0]][values[1]];
                                    else if(values.length===3) 
                                        valueItem = item[values[0]][values[1]][values[2]];
                                    
                                    if (index===0)
                                        retorno = valueItem;
                                    else if (valueItem!==null && valueItem!=="")
                                        retorno = retorno + " | " + valueItem;
                                });
                                return retorno;
                            })
                            .attr('value', item[campoValor])
                            .attr( "selected", "selected" )
                            );
                    });
                }
            });
            
            this._trigger( "select", event, {
              item: ui.item.option
            });

            this.element.trigger('change');
            this._removeViewButton();
            if (this.element.attr('adiciona')==="true")
                this._removeAddButton();
            this._createViewButton();
          },
 
          autocompletechange: "_removeIfInvalid",
          
          blur: function(){
                if (this.input.val()==="" && this.element.val()!=='null'){
                    this.element.find('option').remove().end();
                    this.element.append($('<option>').text('').attr('value', 'null').attr( "selected", "selected" ));
                    this._removeViewButton();
                    this._createAddButton();
                    this.element.trigger('change');                    
                    if (this.input.hasClass("auto-width-combo"))
                        this.input.removeAttr('style');
                }
            }
        });
      },

      _createViewButton: function() {
          createViewButtonFnc(this.element);
      },
              
      _createAddButton: function() {
        if (this.element.attr('adiciona')==="true" && $("#"+(this.element.attr('name')+"add").replace(/\./g,'\\.').replace(/\[/g, "\\[").replace(/\]/g, "\\]")).length===0) {
            var nomeServidor = this.element.attr('servidor');
            var nomeControllerList = this.element.attr('controller');
            var nomeController = ((this.element.attr('controllerDomain')==null)?this.element.attr('controller'):this.element.attr('controllerDomain'));
            var label = this.element.attr('label');
            var select = this.element;
            var disabled = ((this.element.attr('disabled')==="true" || this.element.attr('disabled')==="disabled")?true:false);
            $( "<a>" )
              .attr( "id", this.element.attr('name')+"add" )
              .attr( "tabIndex", -1 )
              .attr( "title", "Clique para cadastrar!" )
              .attr( "disabled", disabled)
              .tooltip()
              .addClass( "btn-padrao btn btn-default"+((disabled)?" disabled":"") )
              .append( "<i class='fa fa-plus'></i>" )
              .prependTo( this.buttons )
              .button({text: false})
              .click(function() {
                    if (!$( "#dialog-form-"+nomeController ).length){
                        $("body").append('<div id="dialog-form-'+nomeController+'" class="dialog-combocraw" title="Dialog Simple Title"></div>');

                        var wWidth = $(window).width();
                        var wHeight = $(window).height();
                        $("#dialog-form-"+nomeController).dialog({
				autoOpen : false,
				modal : true,
                                width: wWidth * 0.8,
				height: wHeight * 0.8,
                                draggable: false,
                                resizable: false,
				title : $(label).text(),
                                close: function () { 
                                    $('.ui-widget-overlay').unbind('click');
                                    $(this).remove();
                                },
                                open: function (event, ui) { 
                                    $(this).css('overflow', 'hidden'); 
                                    $('.ui-widget-overlay').bind('click', overlayclickclose);
                                }
			});
                        
                        function overlayclickclose() {
                            $("#dialog-form-"+nomeController).dialog('close');
                        }

                        $( "#dialog-form-"+nomeController )
                            .append('<div id="textoBloqueio" bloqueioItem="'+nomeController+'"><h1 class="loadingMessageForm bounceIn animated"> Carregando <span class="particle particle--c"></span><span class="particle particle--a"></span><span class="particle particle--b"></span></h1></div>')
                            .append("<iframe style='display:none;' src='"+nomeServidor+nomeController+"/create' width='100%' height='100%' style='border:0px;' onLoad='controlarDialog(this, \""+nomeController+"\", this.contentWindow.location, \""+nomeServidor+nomeController+"\");'></iframe>");
                    } 
                    $( "#dialog-form-"+nomeController+" iframe" )
                    .attr("idSelect",select.attr('id'))
                    .attr("urlGet",nomeServidor+nomeControllerList+"/"+select.attr('action'))
                    .attr("camposLabel",select.attr('camposLabel'))
                    .attr("camposValue",select.attr('camposValue'))
                    .attr("campoValor",select.attr('campoValor'));

                    $( "#dialog-form-"+nomeController ).dialog( "open" );
              });
        }
      },
              
      _removeAddButton: function() {
          $( "#"+this.element.attr('name').replace(/\./g, "\\.").replace(/\[/g, "\\[").replace(/\]/g, "\\]")+"add" ).remove();
      },
              
      _removeViewButton: function() {
          $( "#"+this.element.attr('name').replace(/\./g, "\\.").replace(/\[/g, "\\[").replace(/\]/g, "\\]")+"view" ).remove();
      },

      _createShowAllButton: function() {
        var input = this.input,
          wasOpen = false;
       
        var num;
        if (input.attr("limit"))
            num = input.attr("limit");
        else
            num = 10;
        var nomeServidor = this.element.attr('servidor');
        var disabled = ((input.attr('disabled')==="true" || input.attr('disabled')==="disabled")?true:false);
        $( "<button>" )
          .appendTo( this.buttons )
          .attr( "type", "button")
          .attr( "id", this.element.attr('name')+'buttonAll' )
          .attr( "tabIndex", -1 )
          .attr( "disabled", disabled )
          .addClass( "btn-padrao btn btn-default"+((disabled)?" disabled":"")+(((input.hasClass("project-selector")))?" project-selector":"") )
          .append( "<i class='select-seta'></i>" )
          .attr( "rel", "tooltip" )
          .attr( "data-original-title", (((input.hasClass("aw-select-main")))?"":"Mostrar os "+num+" primeiros registros") )
          .tooltip()
          .mousedown(function() {
                wasOpen = input.autocomplete( "widget" ).is( ":visible" );
          })
          .click(function() {
            input.focus();
 
            // Close if already visible
            if ( wasOpen ) {
                return;
            }
 
            // Pass empty string as value to search for, displaying all results
            input.autocomplete( "search", "" );
          });
      },
 
      _removeIfInvalid: function( event, ui ) {
   
        // Selected an item, nothing to do
        if ( ui.item ) {
          return;
        }
        
        // Remove invalid value
        if (this.input.val()!==""){
            this.element.find('option').remove().end();
            this.element.append($('<option>').text('').attr('value', 'null').attr( "selected", "selected" ));
            this._removeViewButton();
            this._createAddButton();
            this.element.trigger('change'); //Change para limpar tudo
            
            if (!this.input.hasClass("aw-select-main")){
                this.input
                  .tooltip( "destroy" )
                  .attr( "title", this.input.val().toUpperCase() + " não é um registro válido!" )
                  .tooltip( "show" )
                  .val( "" );
            } else
                this.input.val( "" );
      
            this._delay(function() {
                this.input.tooltip( "destroy" ).attr( "title", "" );
            }, 2500 );
            
            this.input.data( "ui-autocomplete" ).term = "";
        }
        
      },
 
      _destroy: function() {
        this.wrapper.remove();
        this.element.show();
      }
    });
    
  })( jQuery );

