/**
 * Disponibiliza as tasks necessárias à compilação do sass para
 * a versão final do frontend, otimizado para produção.
 * 
 * @author Leandro Rezende
 * 
 * @since 03.09.2015
 * @since 1.0.0
 * 
 * 1. VARIÁVEIS E FERRAMENTAS
 * 2. CSS
 * 3. IMAGENS
 * 4. HTML
 * 5. ASSETS
 * 6. ESTRUTURAIS
 */

/********************************************************************************
 * 1. VARIÁVEIS E FERRAMENTAS
 ********************************************************************************/

// 1.1. Ferramentas úteis para as tasks

// Gulp
var gulp = require('gulp');
// Ferramentas de caminhos/diretórios
var path = require('path');
// Faz merge de streams
var merge = require('merge-stream');
// Deleta arquivos
var del = require('del');
// Eyecandies para o terminal (cor, negrito, etc)
var chalk = require('chalk');
// Sincronização entre arquivos e visualização no navegador
var browserSync = require('browser-sync').create();

// 1.2. Obtém variáveis gerais para as tarefas do gulp
var config = require('./build/build.config.js');

// 1.3. Define o plugins para usar qualquer tipo de plugin gulp
var plugins = require("gulp-load-plugins")({
        pattern: ['gulp-*', 'gulp.*', '*'],
        replaceString: /\bgulp[\-.]/
    });

// 1.4. Define o chalk para colorir a tela do prompt de comando
var chalk_error = chalk.bold.red;

// 1.5. Ambiente de produção ou desenvolvimento
var production = false;

/********************************************************************************
 * 2. CSS
 ********************************************************************************/

// 2.1. css
// Copia arquivos CSS source em dist
gulp.task('css', function() {
    var source = config.css.source;

    var stream = gulp.src(config.css.source)
        .pipe(plugins.size({
            title: 'css'
        }))
        .pipe(gulp.dest(config.css.dest))
});

// 2.2. sass
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

// 2.3. less
// Transforma LESS em CSS
gulp.task('less', function() {
    return gulp.src(config.less.source)
        .pipe(plugins.size({
            title: 'less'
        }))
        .pipe(plugins.less())
        .pipe(gulp.dest(config.less.dest));
});

/********************************************************************************
 * 3. IMAGENS
 ********************************************************************************/

// 3.1. images
// Otimiza as imagens em ambiente de produção
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

/********************************************************************************
 * 4. HTML
 ********************************************************************************/

// 4.1. jade
// Processa os códigos JADE
gulp.task('jade', function() {
    var stream = gulp.src(config.jade.source)
        .pipe(plugins.jade())
        .on('error', function(err) { 
            console.log(err.toString());
            this.emit("end");
        });

    if (production) {
        stream = stream.pipe(plugins.usemin(useminConfig(path.resolve(__dirname, config.jade.dest))))
    }

    return stream.pipe(gulp.dest(config.jade.dest));
});

// 4.2. html
// Minifica os HTMLs (ambiente de produção, somente)
gulp.task('html', function() {
    var stream = gulp.src(config.html.source)
        

    if (production) {
        stream = stream
            .pipe(plugins.htmlMinifier(config.html.minifyOptions))
            //.pipe(plugins.usemin(useminConfig(path.resolve(__dirname, config.html.dest))))
    }

    return stream.pipe(gulp.dest(config.html.dest));
});

/********************************************************************************
 * 5. ASSETS
 ********************************************************************************/

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

/********************************************************************************
 * 6. ESTRUTURAIS
 ********************************************************************************/

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
    return plugins.runSequence(['sass', 'less'], ['css', 'images', 'assets'], 'jade', 'html', callback);
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
            baseDir: config.webRoot,
            routes: {
                "/altair-template": "template"
            }
        },
        // http://www.browsersync.io/docs/options/#option-notify
        notify: false
    });
});

// 6.7. watch
// Escuta por mudanças, roda tarefas necessárias e recarrega o navegador
gulp.task('watch', function(callback) {
    // Tarefas que devem rodar também as tarefas de JADE e HTML após modificadas em modo de produção
    var htmlChainTasks = {
        'sass': config.sass.watch,
        'less': config.less.source,
        'css': config.less.source,
        'assets': config.js.watch
    }

    for (task in htmlChainTasks) {
        src = htmlChainTasks[task];

        if (production) {
            gulp.watch(src, generateRunCallback(task, ['jade', 'html'], browserSync.reload));
        } else {
            gulp.watch(src, generateRunCallback(task, browserSync.reload));
        }
    }

    gulp.watch(config.jade.watch, generateRunCallback('jade', browserSync.reload));
    gulp.watch(config.html.source, generateRunCallback('html', browserSync.reload));
    gulp.watch(config.image.source, generateRunCallback('images', browserSync.reload));
    gulp.watch(config.assets.source, generateRunCallback('assets', browserSync.reload));

    callback();
});

/***************************
 * 7. SUPORTE
 **************************/

// 7.1. generateRunCallback
// Gera uma tarefa de runSequence
function generateRunCallback(/** tasks, callback **/) {
    var args = arguments;
    return function() {
        plugins.runSequence.apply(plugins.runSequence, args);
    }
}

// 7.2. useminConfig
// Gera configuração do gulp-usemin
function useminConfig(directory) {
    return {
        path: directory,
        js: [plugins.uglify(config.js.minifyOptions), plugins.rev],
        css: [plugins.minifyCss(config.css.minifyOptions), 'concat']
    }
}
