/**
 * Configurações do CKEditor.
 * 
 * @author Tomas.Godoi
 */
(function() {
	'use strict';

	angular.plataforma.run(function() {

		// Configurando o CKEditor

//		CKEDITOR.config.height = '842px';
//		CKEDITOR.config.width = '595px';
		
		/**
		 * Zerando o removeButtons, para não remover o sublinhado. (que está configurado para
		 * remover na configuração padrão do CKEditor)
		 */
		CKEDITOR.config.contentsCss = [CKEDITOR.config.contentsCss, '/application/plataforma/support/ckeditor/stfcontents.css'];
		CKEDITOR.plugins.addExternal('stfstyles', '/application/plataforma/support/ckeditor/plugins/stfstyles/');
		
		CKEDITOR.config.removeButtons = '';

		CKEDITOR.stylesSet.add('stf_styles', [ {
			name : 'Padrão',
			element : 'p',
			attributes : {
				'class' : 'stf-normal'
			},
			styles : {}
		}, {
			name : 'Citação 1',
			element : 'p',
			attributes : {
				'class' : 'stf-citacao1'
			},
			styles : {
				'margin-left' : '40px'
			}
		}, {
			name : 'Citação 2',
			element : 'p',
			attributes : {
				'class' : 'stf-citacao2'
			}
		}, {
			name : 'Citação 3',
			element : 'p',
			attributes : {
				'class' : 'stf-citacao3'
			}
		}, {
			name : 'Citação 4',
			element : 'p',
			attributes : {
				'class' : 'stf-citacao4'
			}
		}, {
			name : 'Citação 5',
			element : 'p',
			attributes : {
				'class' : 'stf-citacao5'
			}
		}, {
			name : 'Padrão Centralizado',
			element : 'p',
			attributes : {
				'class' : 'stf-padrao-centralizado'
			}
		}, {
			name : 'Emenda Monocrática',
			element : 'p',
			attributes : {
				'class' : 'stf-emenda-monocratica'
			}
		}, {
			name : 'Assinado Digitalmente',
			element : 'p',
			attributes : {
				'class' : 'stf-assinado-digitalmente'
			}
		}, ]);
	});
})();