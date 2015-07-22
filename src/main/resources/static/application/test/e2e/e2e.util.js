/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
(function() {
	'use strict';
	var fs = require('fs');

	exports.takeScreenshot = function(browser, filename) {
		browser.takeScreenshot().then(function(png) {
			fs.writeFileSync('src/main/resources/static/application/test/e2e/results/' + filename + '.png', png, 'base64');
		});
	};
})();
