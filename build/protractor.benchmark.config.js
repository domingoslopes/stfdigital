/**
 * Configurações para o Protractor, usado para testes de benchmark e2e.
 * 
 * @author Tomas.Godoi
 */
'use strict';

var Jasmine2HtmlReporter = require('protractor-jasmine2-html-reporter');
var baseDir = 'src/main/resources/static';
var port = 443;

var path = require('path');

exports.config = {
	jasmineNodeOpts : {
		showColors : true,
		defaultTimeoutInterval : 600000
	},

	specs : [ baseDir + '/application/test/benchmark/**/{pattern}.benchmark.js' ],

	capabilities : {
		'browserName' : 'chrome',
		'chromeOptions': {
			prefs: {
				'download': {
					'prompt_for_download': false,
					'default_directory': path.resolve('/tmp')
				}
			}
		}
	},
	
	allScriptsTimeout: 300000,
	
	seleniumArgs : [ 
	    '-browserTimeout=100' 
	],
	
	baseUrl : 'https://digital.stf.jus.br:' + port,
	
	onPrepare: function() {
		browser.driver.manage().window().maximize();
		jasmine.getEnv().addReporter(new Jasmine2HtmlReporter({
			savePath : 'src/main/resources/static/application/test/e2e/results/',
			screenshotsFolder: 'screenshots',
			takeScreenshots: true,
			takeScreenshotsOnlyOnFailures: true
		}));
	}
};
