/**
 * Disponibiliza as tasks necessárias à compilação do sass para
 * a versão final do frontend, otimizado para produção.
 * 
 * @author Leandro Rezende
 * 
 * @since 03.09.2015
 * @since 1.0.0
 * 
 * 0. Varáveis genéricas
 * 1. JAVASCRIPT
 * 2. CSS
 * 3. IMAGES
 * 4. HTML
 * 5. ESTRUTURAIS
 */

/***************************
 * 0. Varáveis genéricas
 **************************/

/**
 * Auxiliar de caminhos
 */
var path = require('path');

/**
 * Auxiliar de streams
 */
var merge = require('merge-stream');

/**
 * Deleta diretórios/arquivos
 */
var del = require('del');

/**
 * Obtém variáveis gerais para as tarefas do gulp
 */
var config = require('./build/build.config.js');

/**
 * Define o plugins para usar qualquer tipo de plugin gulp
 */
var gulp = require('gulp'),
    plugins = require("gulp-load-plugins")({
        pattern: ['gulp-*', 'gulp.*', '*'],
        replaceString: /\bgulp[\-.]/
    });

/**
 * Define o browserSync para atualizar browser ao editar arquivos
 */
var browserSync = require('browser-sync').create();

/**
 * Define o chalk para colorir a tela do prompt de comando
 */
var chalk = require('chalk');
var chalk_error = chalk.bold.red;

/**
 * Ambiente de produção ou desenvolvimento
 */
var production = false;

/***************************
 * 1. JAVASCRIPT
 **************************/

// 1.1. - js
// Minifica e concatena os javascripts
gulp.task('js', function () {
    return plugins.runSequence('js:common', 'js:page-specific');
});

// 1.2. js:common
// Gera arquivos JS comuns a várias páginas
gulp.task('js:common', function() {
    return gulp.src(config.js.common_source)
        .pipe(plugins.concat(config.js.concat))
        .on('error', function(err) {
            console.log(chalk_error(err.message));
            this.emit('end');
        })
        .pipe(gulp.dest(config.js.dest));
});

// 1.3. js:page-specific
gulp.task('js:page-specific', function() {
    return gulp.src(config.js.page_specific_source)
        .on('error', function(err) {
            console.log(chalk_error(err.message));
            this.emit('end');
        })
        .pipe(gulp.dest(function(file) {
            var fullPath = path.resolve(__dirname, config.js.directory);
            var baseDestPath = path.resolve(__dirname, config.js.dest);
            var finalPath = path.resolve(baseDestPath, file.path.replace(fullPath + path.sep, ''));
            
            return path.dirname(finalPath);
        }));
});

/***************************
 * 2. CSS
 **************************/

// 2.1 - css
// Copia arquivos CSS source em dist
gulp.task('css', function() {
    var source = config.css.source;

    var stream = gulp.src(config.css.source)
        .pipe(plugins.size({
            title: 'css'
        }))
        .pipe(gulp.dest(config.css.dest))
});

// 2.2 - sass
// Transforma SASS em CSS
gulp.task('sass', function() {
    return gulp.src(config.sass.source)
        .pipe(plugins.sass())
        .on('error', plugins.sass.logError)
        .pipe(plugins.size({
            title: 'sass'
        }))
        .pipe(gulp.dest(config.sass.dest));
});

// 2.3 - less
// Transforma LESS em CSS
gulp.task('less', function() {
    return gulp.src(config.less.source)
        .pipe(plugins.size({
            title: 'less'
        }))
        .pipe(plugins.less())
        .pipe(gulp.dest(config.less.dest));
});

/***************************
 * 3. IMAGENS
 **************************/
// 3.1. images
// Otimiza as imagens, em ambiente de produção
gulp.task('images', function() {
    stream = gulp.src(config.image.source);

    if (production) {
        stream = stream.pipe(plugins.imagemin(config.image.minifyOptions));
    }
    
    return stream
        .pipe(plugins.size({
            title: 'images'
        }))
        .pipe(gulp.dest(config.image.dest));
});

/***************************
 * 4. HTML
 **************************/

// 4.1. jade
// Processa os códigos JADE
gulp.task('jade', function() {
    var stream = gulp.src(config.jade.source)
        .pipe(plugins.jade())

    if (production) {
        stream = stream
            .pipe(plugins.usemin({
                path: path.resolve(__dirname, config.jade.dest),
                js: [plugins.uglify(config.js.minifyOptions), plugins.rev],
                css: [plugins.minifyCss(config.css.minifyOptions), 'concat']
            }))
            .on('error', function(err) { 
                console.log(err.toString());
                this.emit("end");
            });
    }

    return stream.pipe(gulp.dest(config.jade.dest));
});

// 4.2. html
// Minifica os HTMLs (ambiente de produção, somente)
gulp.task('html', function() {
    var stream = gulp.src(config.html.source);

    if (production) {
        stream = stream
            .pipe(plugins.htmlMinifier(config.html.minifyOptions))
            .pipe(plugins.usemin({
                path: path.resolve(__dirname, config.html.dest),
                js: [plugins.uglify(config.js.minifyOptions), plugins.rev],
                css: [plugins.minifyCss(config.css.minifyOptions), 'concat']
            }));
    }

    return stream.pipe(gulp.dest(config.html.dest));
});

/***************************
 * 5. OUTROS
 **************************/

// 5.1. assets
// Copia assets necessários e que não passam por algum tipo de processamento
gulp.task('assets', function(callback) {
    var streams = [];

    for (index in config.assets) {
        asset = config.assets[index];
        
        var stream = gulp.src(asset[0])
            .pipe(gulp.dest(asset[1]));

        streams.push(stream);
    }

    return merge.apply(merge, streams);
});

/***************************
 * 6. ESTRUTURAIS
 **************************/

// 6.1. default
// Constrói arquivos e inicia servidor web em ambiente de desenvolvimento
gulp.task('default', function(callback) {
    return plugins.runSequence('build', 'serve', callback);
});

// 6.2.a. production
// Entra no ambiente de produção
gulp.task('production', function() {
    production = true;
});

// 6.2.b. dist
// Alias da task production, por conveniência
gulp.task('dist', ['production']);

// 6.3. clean
// Limpa arquivos distribuição
gulp.task('clean', function() {
    return del(config.webRoot);
});

// 6.4. build
// Constrói todos os arquivos no ambiente selecionado
gulp.task('build', function(callback) {
    return plugins.runSequence(['sass', 'less'], ['js', 'css', 'images', 'assets'], 'jade', 'html', callback);
});

// 6.5. release
// Faz a release de produção de todos os arquivos
gulp.task('release', function(callback) {
    return plugins.runSequence('production', 'build', callback);
});

// 6.6. serve
// Inicia um servidor Web e escuta por mudanças
gulp.task('serve', function() {
    // Inicia o servidor BrowserSync
    browserSync.init({
        // http://www.browsersync.io/docs/options/#option-server
        server: {
            baseDir: config.webRoot
        },
        // http://www.browsersync.io/docs/options/#option-notify
        notify: false
    });
});

// 6.7. watch
// Escuta por mudanças, roda tarefas necessárias e recarrega o navegador
gulp.task('watch', function(callback) {
    gulp.watch(config.sass.source, generateRunCallback('sass', browserSync.reload));
    gulp.watch(config.less.source, generateRunCallback('less', browserSync.reload));
    gulp.watch(config.jade.source, generateRunCallback('jade', browserSync.reload));
    gulp.watch(config.css.source, generateRunCallback('css', browserSync.reload));
    gulp.watch(config.html.source, generateRunCallback('html', browserSync.reload));
    gulp.watch(config.image.source, generateRunCallback('images', browserSync.reload));
    gulp.watch(config.js.source, generateRunCallback('js', browserSync.reload));
    gulp.watch(config.assets.source, generateRunCallback('assets', browserSync.reload));

    callback();
});

/***************************
 * 7. SUPORTE
 **************************/

// 7.1. generateRunCallback
// Roda uma tarefa e, em sequência (síncrono), chama um callback
function generateRunCallback(/** tasks, callback **/) {
    var args = arguments;
    return function() {
        plugins.runSequence.apply(plugins.runSequence, args);
    }
}