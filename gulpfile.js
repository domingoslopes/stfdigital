/**
 * Disponibiliza as tasks necessárias à execução dos testes e à construção
 * da versão final do frontend, otimizado para produção.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 06.07.2015
 * @since 1.0.0
 */
'use strict';

var config = require('./build/build.config.js');
var karmaConfig = require('./build/karma.config.js');
var protractorConfig = require('./build/protractor.config.js');
var gulp = require('gulp');
var bower = require('gulp-bower');
var gulpNgConfig = require('gulp-ng-config');
var $ = require('gulp-load-plugins')();
var runSequence = require('run-sequence');
var browserSync = require('browser-sync');
var reload = browserSync.reload;
var modRewrite = require('connect-modrewrite');
var pkg = require('./package');
var karma = require('karma').server;
var del = require('del');
var _ = require('lodash');
/* jshint camelcase:false */
var webdriverStandalone = require('gulp-protractor').webdriver_standalone;
var webdriverUpdate = require('gulp-protractor').webdriver_update;

/**
 * Atualizando o webdriver. Essa task será usada pela task 'e2e'
 */
gulp.task('webdriver:update', webdriverUpdate);

/**
 * Roda os testes unitário sempre que houve uma modificação no arquivos
 */
gulp.task('tdd', function(cb) {
	karma.start(_.assign({}, karmaConfig, {
		singleRun: false,
		action: 'watch',
		browsers: ['PhantomJS']
	}), cb);
});

/**
 * Otimiza as imagens e as coloca na pasta 'dist'
 */
gulp.task('images', function() {
	return gulp.src(config.images)
	.pipe($.imagemin({
		progressive: true,
		interlaced: true
	}))
	.pipe(gulp.dest(config.dist + '/assets/images'))
	.pipe($.size({
		title: 'images'
	}));
});

/**
 * Gera os templates generate angular usando html2js
 */
gulp.task('templates', function() {
  return gulp.src(config.tpl)
    .pipe($.changed(config.tmp))
    .pipe($.html2js({
      outputModuleName: 'templates',
      base: 'src/main/resources/static',
      useStrict: true
    }))
    .pipe($.concat('templates.js'))
    .pipe(gulp.dest(config.tmp))
    .pipe($.size({
      title: 'templates'
    }));
});

/**
 * Gera os arquivos 'css' a partir dos fontes 'scss'
 */
gulp.task('sass', function() {
	return gulp.src(config.mainScss)
		.pipe($.sass())
		.on('error', $.sass.logError)
		.pipe(gulp.dest(config.tmp))
		.pipe($.size({
			title: 'sass'
	}));
});

/**
 * Executa as tasks necessárias para produzir a versão final na pasta 'dist'.
 */
gulp.task('build:dist', ['clean'], function(cb) {
	runSequence(['jshint', 'build', 'copy', 'copy:assets', 'images', 'test:unit'], 'html', cb);
});

/**
 * Instala as dependências configuradas via Bower.
 */
gulp.task('bower', function() {
	return bower();
});

gulp.task('properties', function () {
	var profile = process.env.NODE_ENV || 'development';
	return gulp.src('src/main/resources/properties.json')
		.pipe(gulpNgConfig('properties', {
			environment: profile
		}))
		.pipe(gulp.dest(config.tmp))
});

/**
 * Executa as tasks necessárias para produzir a versão para desenvolvimento.
 */
gulp.task('build', ['clean'], function(cb) {
	runSequence(['bower', 'properties', 'sass', 'templates'], cb);
});

/**
 * Gera os arquivos css 'minifieds', 2 arquivos js, altera os nomes para que sejam únicos e gera os sourcemaps
 */
gulp.task('html', function() {
	var assets = $.useref.assets({
		searchPath: '{build,src/main/resources/static}'
	});
	
	return gulp.src(config.index)
		.pipe(assets)
		.pipe($.sourcemaps.init())
		.pipe($.if('**/*main.js', $.ngAnnotate()))
		.pipe($.if('*.js', $.uglify({
			mangle: false,
		})))
		.pipe($.if('*.css', $.csso()))
		.pipe($.if(['**/*main.js', '**/*custom.css'], $.header(config.banner, {
			pkg: pkg
		})))
		.pipe($.rev())
		.pipe(assets.restore())
		.pipe($.useref())
		.pipe($.revReplace())
		.pipe($.if('*.html', $.minifyHtml({
			empty: true
		})))
		.pipe($.sourcemaps.write())
		.pipe(gulp.dest(config.dist))
		.pipe($.size({
			title: 'html'
		}));
});

/** 
 * Copia os arquivos da pasta 'assets' para a basta 'dist'
 */
gulp.task('copy:assets', function() {
	return gulp.src(config.assets, {
		dot: true
		})
		.pipe(gulp.dest(config.dist + '/assets'))
		.pipe($.size({
			title: 'copy:assets'
		}));
});

/** 
 * Copia os outros arquivos do frontend para a basta 'dist'
 */
gulp.task('copy', function() {
	return gulp.src([
		config.base + '/*',
			'!' + config.base + '/*.html',
			'!' + config.base + '/src',
			'!' + config.base + '/test'
		])
		.pipe(gulp.dest(config.dist))
		.pipe($.size({
			title: 'copy'
		}));
});

/**
 * Remove os arquivos dos diretórios temporários
 */
gulp.task('clean', del.bind(null, [config.dist, config.tmp]));

/**
 * Usamos JSHint, para nos ajudar a detectar erros e problemas em pontencial
 * no nosso código JavaScript
 */
gulp.task('jshint', function() {
  return gulp.src(config.js)
    .pipe(reload({
      stream: true,
      once: true
    }))
    .pipe($.jshint())
    .pipe($.jshint.reporter('jshint-stylish'))
    .pipe($.if(!browserSync.active, $.jshint.reporter('fail')));
});

//-----------------------------------------------------------------------------
// Taks Públicas...

/**
 * Task default. Apenas executa a task 'serve' que nenhuma outra task informada.
 */
gulp.task('default', ['serve']);

/**
 * Apenas executa os testes unitáriios.
 */
gulp.task('test:unit', ['build'], function(cb) {
	karma.start(_.assign({}, karmaConfig, {
		singleRun: true
	}), cb);
});

/**
 * Executa os teste e2e usado Protractor.
 */
gulp.task('test:e2e', ['webdriver:update'], function() {
	
	return gulp.src(protractorConfig.config.specs)
		.pipe($.protractor.protractor({
			configFile: 'build/protractor.config.js'
		}))
		.on('error', function(e) {
			throw e;
		});
});

/**
 * Executa a aplicação com a task 'serve', monitora alterações nos arquivos e refaz os testes.
 */
gulp.task('serve:tdd', function(cb) {
	runSequence(['serve', 'tdd'], cb);
});

/**
 * Construe, executa a aplicação e monitora alterações nos arquivos atualizando os 
 * todos navegadores abertos.
 */
gulp.task('serve', ['build'], function() {
	browserSync({
		notify: false,
		logPrefix: pkg.name,
		server: {
			baseDir : config.base,
			middleware: [modRewrite(config.rewritePattern)],
			port : 3000
		}
	});
	
	gulp.watch(config.index, reload);
	gulp.watch(config.scss, ['sass', reload]);
	gulp.watch(config.js, ['jshint']);
	gulp.watch(config.tpl, ['templates', reload]);
	gulp.watch(config.assets, reload);
});

/**
 * Roda a aplicação a partir da versão final disponibilizada na pasta 'dist'
 */
gulp.task('serve:dist', ['build:dist'], function() {
	browserSync({
		notify: false,
		server: {
			baseDir : config.dist,
			middleware: [modRewrite(config.rewritePattern)]
		}
	});
});
