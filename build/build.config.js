/**
 * Define o conjunto de variáveis que serão usadas pelo gulp ao executar
 * as tarefas de construção.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 06.07.2015
 * @since 1.0.0
 */
'use strict';

module.exports = {
	port : 3000,
	tmp : 'src/main/resources/static/tmp',
	dist : 'build/dist',
	base : 'src/main/resources/static',
	tpl : 'src/main/resources/static/application/**/*.tpl.html',
	mainScss : 'src/main/resources/static/theme/custom/css/style.scss',
	scss : 'src/main/resources/static/theme/custom/css/**/*.scss',
	js : [ 
		'src/main/resources/static/application/**/*.js',
		'!src/main/resources/static/application/test/unit-results/**/*.js'
	],
	index : 'src/main/resources/static/index.html',
	assets : 'src/main/resources/static/theme/assets/**',
	images : 'src/main/resources/static/theme/assets/images/**/*',
	banner : [
		'/**', ' * <%= pkg.name %> - <%= pkg.description %>',
		' * @version v<%= pkg.version %>', 
		' * @link <%= pkg.homepage %>',
		' * @license <%= pkg.license %>', ' */', '' 
	]
	.join('\n')
};
