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

// Carrega o .bowerrc para identificar a localidade dele
var fs = require("fs");
var bowerdirectory =  JSON.parse(fs.readFileSync(".bowerrc", "utf8")).directory;

/**
 * NOTA
 *----------
 * Não utilizar './' no início dos caminhos referentes a watch. Devido ao comportamento
 * do gulp, novos arquivos não serão identificados no watch caso o caminho possua.
 */

// Exporta a configuração
module.exports = {
	// Raíz do servidor Web / Pasta de distribuição (será limpa em construções)
	webRoot: './dist',

	// SASS
	sass: {
		// Source
		source: [
			'./src/assets/sass/**/*.sass',
			'./src/assets-stf/sass/**/*.sass',
			'!./src/assets-stf/sass/partials/**/*.sass'
		],

		// Conteúdo que deve ser observado para recarregar páginas
		watch: [
			'src/assets/sass/**/*.sass',
			'src/assets-stf/sass/**/*.sass',
		],

		// Destino
		dest: './dist/assets/sass',
	},


	// LESS
	less: {
		// Source
		source: './src/assets/less/main.less',
		// Destino
		dest: './dist/assets/less',
	},

	// JADE
	jade: {
		// Source
		source: [
			'./src/assets-stf/jade/**/*.jade',
			'!./src/assets-stf/jade/partials/**/*.jade'
		],

		// Conteúdo que deve ser observado para recarregar páginas
		watch: [
			'src/assets-stf/jade/**/*.jade',
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
		source: './src/assets-stf/img/**/*',

		// Destino
		dest: './dist/assets/img',
		
		// Opções de minificação (https://github.com/sindresorhus/gulp-imagemin#options)
		minifyOptions: {
            progressive: true,
            interlaced: true
        }
	},

	// JAVASCRIPT
	js: {
		// Diretório que guarda arquivos JS
		directory: "./src/assets/js/",

		// Conteúdo que deve ser observado para recarregar páginas
		watch: [
			"src/assets/**/*.js",
            "src/assets-stf/**/*.js",
        ], 

        // Opções de minificação
        minifyOptions: {
            mangle: true
        },
	},

	// ASSETS
	assets: [
		// [Source, Destino]

		// Fontes e ícones
		['./src/assets/{icons,fonts}/**/*', './dist/assets'],

		// Bower
		[bowerdirectory + '/**', './dist/bower_components'],

		// Arquivos necessários para páginas específicas
		['./src/assets/js/altair_admin_common.js', './dist/assets/js'],
		['./src/assets/js/pages/*.js', './dist/assets/js/pages'],
		['./src/assets/js/custom/*.js', './dist/assets/js/custom'],
	],
};
