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
	proxy : 'localhost/stfdigital/styleguide/dist',
	assets : ['src/assets/js/**/*', 'src/assets/fonts/**/*'],
	images : 'src/assets/images/**/*',
	sass : 'src/assets/sass/**/*',
	jade : 'src/assets/jade/**/*',	
	sasscompiled : 'dist/assets-stf/css',
	htmlcompiled : 'dist/assets-stf/css',	
	dist : 'dist/',
    html : 'dist/*.html',
    jsvendor : 'dist/assets/js/**/*.js',
    css : 'dist/assets-stf/css/**/*.css',
    js : 'dist/assets-stf/js/**/*.js',  
    imagesstf : 'dist/assets-stf/images/**/*'   
};
