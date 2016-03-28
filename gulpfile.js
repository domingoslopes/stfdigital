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
var protractorBenchmarkConfig = require('./build/protractor.benchmark.config.js');
var gulp = require('gulp');
var bower = require('gulp-bower');
var shell = require('gulp-shell');
var gulpNgConfig = require('gulp-ng-config');
var $ = require('gulp-load-plugins')();
var runSequence = require('run-sequence');
var browserSync = require('browser-sync');
var reload = browserSync.reload;
var historyApiFallback = require('connect-history-api-fallback');
var pkg = require('./package');
var karma = require('karma').server;
var del = require('del');
var _ = require('lodash');
var argv = require('yargs').argv;

var webdriverStandalone = require('gulp-protractor').webdriver_standalone;
var webdriverUpdate = require('gulp-protractor').webdriver_update;

var replacePattern = function(source) {
	var pattern = "*"; // Pattern padrão
	if (argv.pattern) {
		pattern = argv.pattern;
	}
	return source.map(function(val) {
		return val.replace("{pattern}", pattern);
	});
};

/**
 * Atualizando o webdriver. Essa task será usada pela task 'e2e'
 */
gulp.task('webdriver:update', webdriverUpdate);

/**
 * Roda os testes unitário sempre que houve uma modificação no arquivos
 */
gulp.task('tdd', function(cb) {
	karmaConfig.files = replacePattern(karmaConfig.files);
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

gulp.task('js-doc', shell.task([
	'jsdoc ' + 
	'-c node_modules/angular-jsdoc/common/conf.json ' +
	'-t node_modules/angular-jsdoc/angular-template ' +
	'-d docs ' +
	'./README.md ' +
	'-r ' + config.base + '/application'
]));

/** 
 * Copia os arquivos da pasta 'assets' para a basta 'dist'ls
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
	karmaConfig.files = replacePattern(karmaConfig.files);
	karma.start(_.assign({}, karmaConfig, {
		singleRun: true
	}), cb);
});

/**
 * Executa os teste e2e usado Protractor.
 */
gulp.task('test:e2e', ['webdriver:update'], function() {
	protractorConfig.config.specs = replacePattern(protractorConfig.config.specs);
	// Monta o objeto de configuração para o protractor, que vai sobreescrever a url ou não
	// dependendo da existência do parâmetro --e2eBaseUrl passado para o gulp
	var protractorConfigObject = {
		configFile: 'build/protractor.config.js',
		args: []
	};
	if (argv.e2eBaseUrl) {
		protractorConfigObject.args.push('--baseUrl');
		protractorConfigObject.args.push(argv.e2eBaseUrl);
	}
	if (argv.e2eSeleniumAddress) {
		protractorConfigObject.args.push('--seleniumAddress');
		protractorConfigObject.args.push(argv.e2eSeleniumAddress);
	}
	if (argv.e2eFilesDirPath) {
		protractorConfigObject.args.push('--params.filesDirPath');
		protractorConfigObject.args.push(argv.e2eFilesDirPath);
	}
	return gulp.src(protractorConfig.config.specs)
		.pipe($.protractor.protractor(protractorConfigObject))
		.on('error', function(e) {
			throw e;
		});
});

/**
 * Executa os teste benchmark e2e usando Protractor.
 */
gulp.task('test:benchmark', ['webdriver:update'], function() {
	protractorBenchmarkConfig.config.specs = replacePattern(protractorBenchmarkConfig.config.specs);
	return gulp.src(protractorBenchmarkConfig.config.specs)
		.pipe($.protractor.protractor({
			configFile: 'build/protractor.benchmark.config.js'
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
	var bs = browserSync({
		notify: false,
		logPrefix: pkg.name,
		startPath: "/login",
		proxy: {
			target: "https://localhost:8443",
			ws: true
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
			baseDir : config.dist
		}
	});
});
