/**
 * Configurações para o Protractor, usado para testes de benchmark e2e.
 * 
 * @author Tomas.Godoi
 */
'use strict';

var HtmlReporter = require('protractor-html-screenshot-reporter');
var baseDir = 'src/main/resources/static';
var port = 3000;

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
	    '-browserTimeout=60' 
	],
	
	baseUrl : 'https://127.0.0.1:' + port,
	
	onPrepare: function() {
		browser.driver.manage().window().maximize();
		jasmine.getEnv().addReporter(new HtmlReporter({
			baseDirectory : 'src/main/resources/static/application/test/benchmark/results',
			takeScreenShotsOnlyForFailedSpecs: true
		}));
	}
};
