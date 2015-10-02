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
 *
 * 1. js:common
 * 2. sass
 * 3. copy:assets
 * 4. images
 * 5. browser-sync
 * 6. all_js
 * 7. release
 * 8. default
 */

/***************************
 * 0. Varáveis genéricas
 **************************/

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
 * Baixa os pacotes necessários para o template
 */
var pjson = require('./package.json');
var version = pjson.version;

/**
 * Ambiente de produção ou desenvolvimento
 */
var production = false;

/***************************
 * 1. JAVASCRIPT
 **************************/

// 1.1 - js:common
// Minifica e concatena os javascripts
gulp.task('js:common', function () {
    return gulp.src([
            "bower_components/jquery/dist/jquery.js",
            "bower_components/modernizr/modernizr.js",
            // moment
            "bower_components/moment/moment.js",
            // retina images
            "bower_components/dense/src/dense.js",
            // fastclick (touch devices)
            "bower_components/fastclick/lib/fastclick.js",
            // custom scrollbar
            "bower_components/jquery.scrollbar/jquery.scrollbar.js",
            // create easing functions from cubic-bezier co-ordinates
            "bower_components/jquery-bez/jquery.bez.min.js",
            // Get the actual width/height of invisible DOM elements with jQuery
            "bower_components/jquery.actual/jquery.actual.js",
            // waypoints
            "bower_components/waypoints/lib/jquery.waypoints.js",
            // velocityjs (animation)
            "bower_components/velocity/velocity.js",
            "bower_components/velocity/velocity.ui.js",
            // advanced cross-browser ellipsis
            "bower_components/jQuery.dotdotdot/src/js/jquery.dotdotdot.js",
            // iCheck
            "bower_components/jquery-icheck/icheck.js",
            // selectize
            "bower_components/selectize/dist/js/standalone/selectize.js",
            // switchery
            "bower_components/switchery/dist/switchery.js",
            // prism syntax highlighter
            "bower_components/prism/prism.js",
            "bower_components/prism/components/prism-php.js",
            "bower_components/prism/plugins/line-numbers/prism-line-numbers.js",
            // textarea-autosize
            "bower_components/autosize/dist/autosize.js",
            // hammerjs
            "bower_components/hammerjs/hammer.js",
            // jquery.debouncedresize
            "bower_components/jquery.debouncedresize/js/jquery.debouncedresize.js"
        ])
        .pipe(plugins.concat('common.js'))
        .on('error', function(err) {
            console.log(chalk_error(err.message));
            this.emit('end');
        })
        .pipe(gulp.dest('assets/js/'))
        .pipe(plugins.uglify({
            mangle: true
        }))
        .pipe(plugins.rename('common.min.js'))
        .pipe(plugins.size({
            showFiles: true
        }))
        .pipe(gulp.dest('assets/js/'));
});

// 1.2 - js:uikit
// cutom uikit
gulp.task('js:uikit', function () {
    return gulp.src([
            // uikit core
            "bower_components/uikit/js/uikit.js",
            // uikit components
            "bower_components/uikit/js/components/accordion.js",
            "bower_components/uikit/js/components/autocomplete.js",
            "assets/js/custom/uikit_datepicker.js",
            "bower_components/uikit/js/components/form-password.js",
            "bower_components/uikit/js/components/form-select.js",
            "bower_components/uikit/js/components/grid.js",
            "bower_components/uikit/js/components/nestable.js",
            "bower_components/uikit/js/components/notify.js",
            "bower_components/uikit/js/components/sortable.js",
            "bower_components/uikit/js/components/sticky.js",
            "bower_components/uikit/js/components/tooltip.js",
            "assets/js/custom/uikit_timepicker.js",
            "bower_components/uikit/js/components/upload.js",
            "assets/js/custom/uikit_beforeready.js"
        ])
        .pipe(plugins.concat('uikit_custom.js'))
        .pipe(gulp.dest('assets/js/'))
        .pipe(plugins.uglify({
            mangle: true
        }))
        .pipe(plugins.rename('uikit_custom.min.js'))
        .pipe(plugins.size({
            showFiles: true
        }))
        .pipe(gulp.dest('assets/js/'));
});

// 1.3 - js:uikit_htmleditor
// uikit htmleditor
gulp.task('js:uikit_htmleditor', function () {
    return gulp.src([
            // htmleditor
            "bower_components/codemirror/lib/codemirror.js",
            "bower_components/codemirror/mode/markdown/markdown.js",
            "bower_components/codemirror/addon/mode/overlay.js",
            "bower_components/codemirror/mode/javascript/javascript.js",
            "bower_components/codemirror/mode/php/php.js",
            "bower_components/codemirror/mode/gfm/gfm.js",
            "bower_components/codemirror/mode/xml/xml.js",
            "bower_components/marked/lib/marked.js",
            "bower_components/uikit/js/components/htmleditor.js"
        ])
        .pipe(plugins.concat('uikit_htmleditor_custom.js'))
        .pipe(gulp.dest('assets/js/'))
        .pipe(plugins.uglify({
            mangle: true
        }).on('error', function(e) {
            console.log('\x07',e.message); return this.end();
        }))
        .pipe(plugins.rename('uikit_htmleditor_custom.min.js'))
        .pipe(plugins.size({
            showFiles: true
        }))
        .pipe(gulp.dest('assets/js/'));
});

// 1.4 - js:kendoui
// custom kendoui
gulp.task('js:kendoui', function () {
    // js
    return  gulp.src([
            "bower_components/kendo-ui-core/src/js/kendo.core.js",
            "bower_components/kendo-ui-core/src/js/kendo.color.js",
            "bower_components/kendo-ui-core/src/js/kendo.data.js",
            "bower_components/kendo-ui-core/src/js/kendo.calendar.js",
            "bower_components/kendo-ui-core/src/js/kendo.popup.js",
            "bower_components/kendo-ui-core/src/js/kendo.datepicker.js",
            "bower_components/kendo-ui-core/src/js/kendo.timepicker.js",
            "bower_components/kendo-ui-core/src/js/kendo.datetimepicker.js",
            "bower_components/kendo-ui-core/src/js/kendo.list.js",
            "bower_components/kendo-ui-core/src/js/kendo.fx.js",
            "bower_components/kendo-ui-core/src/js/kendo.userevents.js",
            "bower_components/kendo-ui-core/src/js/kendo.menu.js",
            "bower_components/kendo-ui-core/src/js/kendo.draganddrop.js",
            "bower_components/kendo-ui-core/src/js/kendo.slider.js",
            "bower_components/kendo-ui-core/src/js/kendo.mobile.scroller.js",
            "bower_components/kendo-ui-core/src/js/kendo.autocomplete.js",
            "bower_components/kendo-ui-core/src/js/kendo.combobox.js",
            "bower_components/kendo-ui-core/src/js/kendo.dropdownlist.js",
            "bower_components/kendo-ui-core/src/js/kendo.colorpicker.js",
            "bower_components/kendo-ui-core/src/js/kendo.combobox.js",
            "bower_components/kendo-ui-core/src/js/kendo.maskedtextbox.js",
            "bower_components/kendo-ui-core/src/js/kendo.multiselect.js",
            "bower_components/kendo-ui-core/src/js/kendo.numerictextbox.js",
            "bower_components/kendo-ui-core/src/js/kendo.toolbar.js",
            "bower_components/kendo-ui-core/src/js/kendo.panelbar.js",
            "bower_components/kendo-ui-core/src/js/kendo.window.js"
        ])
        .pipe(plugins.concat('kendoui_custom.js'))
        .pipe(gulp.dest('assets/js/'))
        .pipe(plugins.uglify({
            mangle: true
        }))
        .pipe(plugins.rename('kendoui_custom.min.js'))
        .pipe(plugins.size({
            showFiles: true
        }))
        .pipe(gulp.dest('assets/js/'));

});

// 1.5 - js:page_specific
// common/page specific functions
gulp.task('js:page_specific', function () {
    return gulp.src([
        'assets/js/altair_admin_common.js',
        'assets/js/pages/*.js',
        'assets/js/custom/*.js',
        '!assets/js/**/*.min.js'
    ])
        .pipe(plugins.uglify({
            mangle: true
        }))
        .pipe(plugins.rename({
            extname: ".min.js"
        }))
        .pipe(gulp.dest(function(file) {
            return file.base;
        }));
});

// 1.6 - js
// Roda todas as tarefas de Javascript
gulp.task('js', ['js:common','js:uikit','js:uikit_htmleditor','js:kendoui', 'js:page_specific']);

/***************************
 * 2. CSS
 **************************/

// 2.1 - sass
// Transforma SASS em CSS
gulp.task('sass', function() {
    return gulp.src(config.sass.source)
        .pipe(plugins.sass())
        .on('error', plugins.sass.logError)
        .pipe(gulp.dest(production ? config.sass.prodDest : config.sass.devDest))
        .pipe(plugins.size({
            title: 'sass'
        }));
});

// 2.2 - css
// Concatena arquivos CSS do SASS com CSS adicionais
gulp.task('css', function() {
    return gulp.src(config.css.source)
        /** @TODO: Concatenar e minificar **/
        .pipe(gulp.dest(production ? config.css.prodDest : config.css.devDest))
});

/***************************
 * 3. copy:assets
 * Copia os assets para a pasta adequada
 **************************/
/*
 gulp.task('copy:assets', function() {
    return gulp.src(config.assets, {
        dot: true
        })
        .pipe(gulp.dest(config.dist + '/assets-stf'))
        .pipe(plugins.size({
            title: 'copy:assets'
        }));
});
*/

/***************************
 * 3. IMAGENS
 **************************/
gulp.task('images', function() {
    return gulp.src(config.images)
        .pipe(plugins.imagemin({
            progressive: true,
            interlaced: true
        }))
        .pipe(gulp.dest(config.imagesstf))
        .pipe(plugins.size({
            title: 'images'
        }));
});

/***************************
 * 4. HTML
 **************************/

// 4.1. jade
// Processa os códigos JADE
gulp.task('jade', function() {
    return gulp.src(config.jade.source)
        .pipe(jade())
        .pipe(gulp.dest(production ? config.jade.prodDest : config.jade.devDest))
});

// 4.2. html
// Minifica os HTMLs
gulp.task('html', function() {
    return gulp.src(config.html.source)
        .pipe(plugins.htmlminifier({collapseWhitespace: true}))
        .pipe(gulp.dest(production ? config.html.prodDest : config.html.devDest))
});

/***************************
 * 5. ESTRUTURAIS
 **************************/

// 5.1. build
// Constrói todos os arquivos no ambiente selecionado
gulp.task('build', function(callback) {
    return plugins.runSequence('js', 'css', 'images', 'html', callback);
});

// 5.2. release
// Faz a release de produção de todos os arquivos
gulp.task('release', function(callback) {
    return plugins.runSequence('production', 'build', callback);
});

// 5.3. production
// Entra no ambiente de produção
gulp.task('production', function() {
    production = true;
});

// 5.4. browser-sync
// Inicia um servidor Web e escuta por mudanças
gulp.task('browser-sync', function() {
    // Inicia o servidor BrowserSync
    browserSync.init({
        // http://www.browsersync.io/docs/options/#option-server
        server: {
            baseDir: (production ? config.webRoot.prod : config.webRoot.dev)
        },
        // http://www.browsersync.io/docs/options/#option-notify
        notify: false
    });

    // Escuta por mudanças e roda tarefas necessárias
    gulp.watch(config.sass.source, ['sass'])

    // Observa mudanças em todos os sources e recarrega a página em mudanças
    gulp.watch([
        config.html,
        config.js,
        config.images,
        config.css,
        config.jsvendor,
        config.imagesstf,
        '!assets/js/**/*.min.js'        
    ]).on('change', browserSync.reload);
});

// 5.5. default
// Constrói arquivos e inicia servidor web em ambiente de desenvolvimento
gulp.task('default', function(callback) {
    return plugins.runSequence('build', 'browser-sync', callback);
});