/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('DevolucaoController', function (PeticaoService, $state, $stateParams, $scope, messages) {
		var devolucao = this;
		
		var resource = $stateParams.resources[0];
		devolucao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		devolucao.recursos = [];
		devolucao.tiposDevolucao = [{id : 'REMESSA_INDEVIDA', nome : "Remessa Indevida"}, {id : 'TRANSITADO', nome : "Transitado"}, {id : 'BAIXADO', nome : "Baixado"}];
		devolucao.tipoDevolucao = '';
		devolucao.documento = '';
		
		devolucao.showEditor = false;
		devolucao.tipoEditor = 'CKEDITOR';
		$scope.urlTemplate = '';
		
		var toolbar = [
			{ name: 'clipboard', 'items' : ['Cut', 'Copy', 'Paste']},
			{ name: 'styles', 'items' : ['Styles']},
			{ name: 'basicstyles', 'items' : ['Bold', 'Italic', 'Underline']},
			{ name: 'advancedstyles', 'items' : ['Smallcaps', 'Marker']},
			{ name: 'formatting', 'items' : ['Indent', 'Outdent']},
			{ name: 'justify', 'items' : ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']},
			{ name: 'breaking', 'items' : ['PageBreak']},
		];
		
		var plugins = 'font,justify,stfstyles,indentblock,pagebreak';
		
		$scope.editorOptions = { language : 'pt-br',
			allowedContent : true,
			extraPlugins: plugins,
			toolbar : 'stf',
			toolbar_stf: toolbar,
			stylesSet: 'stf_styles',
			removeButtons: '',
//			height: '842px',
//			width: '595px'
			height: '500px',
			width: '210mm',
			indentClasses: ['stf-indent1', 'stf-indent2', 'stf-indent3', 'stf-indent4', 'stf-indent4'],
		};
		
		devolucao.document = {
//			src: 'https://eti078143:8443/' + PeticaoService.urlTemplateDevolucao(devolucao.tipoDevolucao, 'docx'),
			src: 'https://eti078143:8443/api/textos/1/conteudo.docx',
//			src: 'http://www.easychair.org/publications/easychair.docx',
//			src: 'https://www2.le.ac.uk/Members/davidwickins/old/test.docx/at_download/file',
//			src: 'http://calibre-ebook.com/downloads/demos/demo.docx',
			title: 'Editor'
		};
		
		devolucao.save = function() {
			console.log('save');
		};
		
		PeticaoService.consultarProcessoWorkflow(devolucao.peticaoId).then(function(data) {
			devolucao.processoWorkflowId = data;
		});
		
		$scope.$watch('devolucao.tipoDevolucao', function() {
			if (devolucao.tipoDevolucao != '') {
				//ckeditor
				PeticaoService.templateDevolucao(devolucao.tipoDevolucao)
					.then(function(template) {
						devolucao.documento = template;
						devolucao.showEditor = true;
					});
				//wodotexteditor
				$scope.urlTemplate = PeticaoService.urlTemplateDevolucao(devolucao.tipoDevolucao, 'odt');
			}
		});
		
		devolucao.validar = function() {
			var errors = null;
			if (devolucao.tipoDevolucao.length === 0) {
				errors = 'Você precisa selecionar <b>o tipo de devolução</b>.<br/>';
			}
			
			if (!angular.isNumber(devolucao.numeroOficio)) {
				errors += 'Você precisa informar <b>o número do ofício</b>.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			devolucao.recursos.push(new DevolverCommand(devolucao.peticaoId, devolucao.tipoDevolucao, devolucao.numeroOficio, devolucao.documento));
			return true;
		};
		
		devolucao.completar = function() {
			$state.go('visualizar.peticao', {peticaoId: devolucao.peticaoId});
			messages.success('Petição devolvida com sucesso.');
		};
		
		function DevolverCommand(peticaoId, tipoDevolucao, numeroOficio, documento) {
			var command = {};
			command.peticaoId = peticaoId;
			command.tipoDevolucao = tipoDevolucao; 
			command.numeroOficio = numeroOficio;
			command.documento = documento;
			return command;
		}
	});
})();
