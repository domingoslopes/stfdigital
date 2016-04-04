/**
 * Configurações para o Protractor, usado para testes end-to-end (e2e).
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 06.07.2015
 * @since 1.0.0
 */
'use strict';

var Jasmine2HtmlReporter = require('protractor-jasmine2-html-reporter');
var SpecReporter = require('jasmine-spec-reporter');
var baseDir = 'src/main/resources/static';
var port = 3000;

exports.config = {
	jasmineNodeOpts : {
		showColors : true,
		defaultTimeoutInterval : 40000,
		print: function() {}
	},

	specs : [ baseDir + '/application/test/e2e/**/{pattern}.scenario.js' ],

	capabilities : {
		'browserName' : 'chrome'
	},
	
	seleniumArgs : [ 
	    '-browserTimeout=60' 
	],
	
	baseUrl : 'https://127.0.0.1:' + port,
	
	onPrepare: function() {
		browser.driver.manage().window().maximize();
		browser.getCapabilities().then(function() {
			jasmine.getEnv().addReporter(new SpecReporter({displayStacktrace: 'all'}));
			jasmine.getEnv().addReporter(new Jasmine2HtmlReporter({
				savePath : 'src/main/resources/static/application/test/e2e/results/',
				screenshotsFolder: 'screenshots',
				takeScreenshots: true,
				takeScreenshotsOnlyOnFailures: true
			}));
		});
	}
};
