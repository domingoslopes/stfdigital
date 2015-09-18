/**
 * Configurações para o Protractor, usado para testes end-to-end (e2e).
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 06.07.2015
 * @since 1.0.0
 */
'use strict';

var HtmlReporter = require('protractor-html-screenshot-reporter');
var baseDir = 'src/main/resources/static';
var port = 3000;

exports.config = {
	jasmineNodeOpts : {
		showColors : true,
		defaultTimeoutInterval : 30000
	},

	specs : [ baseDir + '/application/test/e2e/**/*.scenario.js' ],

	capabilities : {
		'browserName' : 'chrome'
	},
	
	seleniumArgs : [ 
	    '-browserTimeout=60' 
	],
	
	baseUrl : 'http://127.0.0.1:' + port,
	
	onPrepare: function() {
		browser.driver.manage().window().maximize();
		jasmine.getEnv().addReporter(new HtmlReporter({
			baseDirectory : 'src/main/resources//static/application/test/e2e/results',
			takeScreenShotsOnlyForFailedSpecs: true
		}));
	}
};
