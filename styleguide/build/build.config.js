/**
 * Define o conjunto de variáveis que serão usadas pelo gulp ao executar
 * as tarefas de construção.
 * 
 * @author Leandro Rezende
 * 
 * @since 06.07.2015
 * @since 1.0.0
 */
'use strict';

module.exports = {
	// Raíz do servidor Web
	webRoot: './dist',

	// Bower
	bower: "dist/bower_components",

	// SASS
	sass: {
		// Source
		source: [
			'./src/assets/**/*.sass',
			'./src/assets-stf/**/*.sass',
		],
		// Destino
		dest: './dist/assets/css',
	},

	// JADE
	jade: {
		// Source
		source: [
			'./src/assets/jade/**/*.jade',
			'./src/assets-stf/jade/**/*.jade',
		],
		// Destino 
		dest: './dist'
	},

	// CSS
	css: {
		// Source
		source: [
			'./src/assets/css/**/*.css',
			'./src/assets-stf/css/**/*.css',
		],
		// Destino em ambiente de produção
		dest: './dist/assets/css',
		// Nome do arquivo concatenado
		concat: 'main.css',
		// Opções de minificação (https://github.com/jakubpawlowicz/clean-css#how-to-use-clean-css-api)
		minifyOptions: {

		}
	},

	// HTML
	html: {
		// Source
		source: [
			'./src/assets/html/**/*.html',
			'./src/assets-stf/html/**/*.html',
		],
		// Destino
		dest: './dist',
		// Opções de minificação (https://github.com/kangax/html-minifier#options-quick-reference)
		minifyOptions: {
			collapseWhitespace: true
		},
	},

	// IMAGENS
	image: {
		// Source
		source: [
			'./src/assets/img/**/*',
			'./src/assets-stf/img/**/*',
		],
		// Destino
		dest: './dist/assets/img'
	},

	// JAVASCRIPT
	js: {
		// Source
		source: './src/assets/js',
		// Destino
		dest: './dist/assets/js',
		// Nome do arquivo concatenado
		concat: 'common.js',
	},

	// ASSETS
	assets: {
		// Source
		source: [
			'./src/assets/{icons,fonts}/**/*'
		],
		// Destino
		dest: './dist/assets'
	}
};
