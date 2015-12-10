/**
 * Configurações para o Karma Runner, usado para testes unitários
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 06.07.2015
 * @since 1.0.0
 */
'use strict';

var baseDir = 'src/main/resources/static';

module.exports = {

	// Definindo a lista de arquivos quer serão carregados no browser durante os testes...
	files : [
	 	baseDir + '/vendor/jquery/dist/jquery.js',
		baseDir + '/vendor/angular/angular.js',
		baseDir + '/vendor/angular-cookies/angular-cookies.js',
		baseDir + '/vendor/angular-mocks/angular-mocks.js',
		baseDir + '/vendor/angular-sanitize/angular-sanitize.js',
		baseDir + '/vendor/angular-ui-router/release/angular-ui-router.js',
		baseDir + '/vendor/ui-router-extras/release/ct-ui-router-extras.js',
		baseDir + '/vendor/angular-ui-select2/src/select2.js',
		baseDir + '/vendor/select2/select2.js',
		baseDir + '/vendor/angular-file-upload/dist/angular-file-upload.min.js',
		baseDir + '/vendor/moment/min/moment-with-locales.min.js',
		baseDir + '/vendor/angular-moment/angular-moment.min.js',		
	    baseDir + '/vendor/d3/d3.js',
	    baseDir + '/vendor/nvd3/build/nv.d3.js',
	    baseDir + '/vendor/angular-nvd3/dist/angular-nvd3.js',
	    baseDir + '/vendor/checklist-model/checklist-model.js',
	    baseDir + '/vendor/sockjs/sockjs.js',
	    baseDir + '/vendor/stomp-websocket/lib/stomp.js',
	    baseDir + '/vendor/ngMask/dist/ngMask.min.js',
	    baseDir + '/theme/assets/pages/js/pages.js',
	    baseDir + '/theme/assets/plugins/jquery-validation/js/jquery.validate.min.js',
		baseDir + '/application/plataforma/**/*.module.js', 
		baseDir + '/application/plataforma/**/*.js', 
		baseDir + '/application/autuacao/**/*.js', 
		baseDir + '/tmp/*.js',
		baseDir + '/application/test/**/*.module.js',
		baseDir + '/application/test/unit/**/{pattern}.spec.js' 
	],

	frameworks : [ 'jasmine' ],

	plugins : [ 
		'karma-chrome-launcher', 
		'karma-phantomjs-launcher',
		'karma-jasmine', 
		/*'karma-coverage',*/ // Comentado para facilitar o debug
		'karma-html-reporter',
		'karma-mocha-reporter' 
	],

	preprocessors : {
		'**/src/main/resources/static/application/**/*.js' : 'coverage'
	},

	reporters : [ 'mocha', 'html'/*, 'coverage'*/ ],

	coverageReporter : {
		type : 'html',
		dir : baseDir + '/application/test/unit/results/coverage',
		file : 'coverage.html'
	},

	htmlReporter : {
		outputDir : baseDir + '/application/test/unit/results/html'
	},

	logLevel : 'info',

	urlRoot : '/__test/',

	browsers : [ 'PhantomJS' ]
};
