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
	proxy : 'localhost/stfdigital/styleguide/tpl',
	assets : ['src/assets/js/**/*', 'src/assets/fonts/**/*'],
	images : 'src/assets/images/**/*',
	sass : 'src/assets/sass/**/*',
	sasscompiled : 'tpl/assets-stf/css',
	dist : 'tpl/',
    html : 'tpl/*.html',
    jsvendor : 'tpl/assets/js/**/*.js',
    css : 'tpl/assets-stf/css/**/*.css',
    js : 'tpl/assets-stf/js/**/*.js',  
    imagesstf : 'tpl/assets-stf/images/**/*'   
};
