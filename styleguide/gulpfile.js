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
            config.bower + "/jquery/dist/jquery.js",
            config.bower + "/modernizr/modernizr.js",
            // moment
            config.bower + "/moment/moment.js",
            // retina images
            config.bower + "/dense/src/dense.js",
            // fastclick (touch devices)
            config.bower + "/fastclick/lib/fastclick.js",
            // custom scrollbar
            config.bower + "/jquery.scrollbar/jquery.scrollbar.js",
            // create easing functions from cubic-bezier co-ordinates
            config.bower + "/jquery-bez/jquery.bez.min.js",
            // Get the actual width/height of invisible DOM elements with jQuery
            config.bower + "/jquery.actual/jquery.actual.js",
            // waypoints
            config.bower + "/waypoints/lib/jquery.waypoints.js",
            // velocityjs (animation)
            config.bower + "/velocity/velocity.js",
            config.bower + "/velocity/velocity.ui.js",
            // advanced cross-browser ellipsis
            config.bower + "/jQuery.dotdotdot/src/js/jquery.dotdotdot.js",
            // iCheck
            config.bower + "/jquery-icheck/icheck.js",
            // selectize
            config.bower + "/selectize/dist/js/standalone/selectize.js",
            // switchery
            config.bower + "/switchery/dist/switchery.js",
            // prism syntax highlighter
            config.bower + "/prism/prism.js",
            config.bower + "/prism/components/prism-php.js",
            config.bower + "/prism/plugins/line-numbers/prism-line-numbers.js",
            // textarea-autosize
            config.bower + "/autosize/dist/autosize.js",
            // hammerjs
            config.bower + "/hammerjs/hammer.js",
            // jquery.debouncedresize
            config.bower + "/jquery.debouncedresize/js/jquery.debouncedresize.js"
        ])
        .pipe(plugins.concat(config.js.concat))
        .on('error', function(err) {
            console.log(chalk_error(err.message));
            this.emit('end');
        })
        .pipe(gulp.dest(config.js.dest))
        .pipe(plugins.uglify({
            mangle: true
        }))
        .pipe(plugins.rename('common.min.js'))
        .pipe(plugins.size({
            showFiles: true
        }))
        .pipe(gulp.dest(config.js.dest));
});

// 1.2 - js:uikit
// cutom uikit
gulp.task('js:uikit', function () {
    return gulp.src([
            // uikit core
            config.bower + "/uikit/js/uikit.js",
            // uikit components
            config.bower + "/uikit/js/components/accordion.js",
            config.bower + "/uikit/js/components/autocomplete.js",
            config.js.source + "/custom/uikit_datepicker.js",
            config.bower + "/uikit/js/components/form-password.js",
            config.bower + "/uikit/js/components/form-select.js",
            config.bower + "/uikit/js/components/grid.js",
            config.bower + "/uikit/js/components/nestable.js",
            config.bower + "/uikit/js/components/notify.js",
            config.bower + "/uikit/js/components/sortable.js",
            config.bower + "/uikit/js/components/sticky.js",
            config.bower + "/uikit/js/components/tooltip.js",
            config.js.source + "/custom/uikit_timepicker.js",
            config.bower + "/uikit/js/components/upload.js",
            config.js.source + "/custom/uikit_beforeready.js"
        ])
        .pipe(plugins.concat('uikit_custom.js'))
        .pipe(gulp.dest(config.js.dest))
        .pipe(plugins.uglify({
            mangle: true
        }))
        .pipe(plugins.rename('uikit_custom.min.js'))
        .pipe(plugins.size({
            showFiles: true
        }))
        .pipe(gulp.dest(config.js.dest));
});

// 1.3 - js:uikit_htmleditor
// uikit htmleditor
gulp.task('js:uikit_htmleditor', function () {
    return gulp.src([
            // htmleditor
            config.bower + "/codemirror/lib/codemirror.js",
            config.bower + "/codemirror/mode/markdown/markdown.js",
            config.bower + "/codemirror/addon/mode/overlay.js",
            config.bower + "/codemirror/mode/javascript/javascript.js",
            config.bower + "/codemirror/mode/php/php.js",
            config.bower + "/codemirror/mode/gfm/gfm.js",
            config.bower + "/codemirror/mode/xml/xml.js",
            config.bower + "/marked/lib/marked.js",
            config.bower + "/uikit/js/components/htmleditor.js"
        ])
        .pipe(plugins.concat('uikit_htmleditor_custom.js'))
        .pipe(gulp.dest(config.js.dest))
        .pipe(plugins.uglify({
            mangle: true
        }).on('error', function(e) {
            console.log('\x07',e.message); return this.end();
        }))
        .pipe(plugins.rename('uikit_htmleditor_custom.min.js'))
        .pipe(plugins.size({
            showFiles: true
        }))
        .pipe(gulp.dest(config.js.dest));
});

// 1.4 - js:kendoui
// custom kendoui
gulp.task('js:kendoui', function () {
    // js
    return  gulp.src([
            config.bower + "/kendo-ui-core/src/js/kendo.core.js",
            config.bower + "/kendo-ui-core/src/js/kendo.color.js",
            config.bower + "/kendo-ui-core/src/js/kendo.data.js",
            config.bower + "/kendo-ui-core/src/js/kendo.calendar.js",
            config.bower + "/kendo-ui-core/src/js/kendo.popup.js",
            config.bower + "/kendo-ui-core/src/js/kendo.datepicker.js",
            config.bower + "/kendo-ui-core/src/js/kendo.timepicker.js",
            config.bower + "/kendo-ui-core/src/js/kendo.datetimepicker.js",
            config.bower + "/kendo-ui-core/src/js/kendo.list.js",
            config.bower + "/kendo-ui-core/src/js/kendo.fx.js",
            config.bower + "/kendo-ui-core/src/js/kendo.userevents.js",
            config.bower + "/kendo-ui-core/src/js/kendo.menu.js",
            config.bower + "/kendo-ui-core/src/js/kendo.draganddrop.js",
            config.bower + "/kendo-ui-core/src/js/kendo.slider.js",
            config.bower + "/kendo-ui-core/src/js/kendo.mobile.scroller.js",
            config.bower + "/kendo-ui-core/src/js/kendo.autocomplete.js",
            config.bower + "/kendo-ui-core/src/js/kendo.combobox.js",
            config.bower + "/kendo-ui-core/src/js/kendo.dropdownlist.js",
            config.bower + "/kendo-ui-core/src/js/kendo.colorpicker.js",
            config.bower + "/kendo-ui-core/src/js/kendo.combobox.js",
            config.bower + "/kendo-ui-core/src/js/kendo.maskedtextbox.js",
            config.bower + "/kendo-ui-core/src/js/kendo.multiselect.js",
            config.bower + "/kendo-ui-core/src/js/kendo.numerictextbox.js",
            config.bower + "/kendo-ui-core/src/js/kendo.toolbar.js",
            config.bower + "/kendo-ui-core/src/js/kendo.panelbar.js",
            config.bower + "/kendo-ui-core/src/js/kendo.window.js"
        ])
        .pipe(plugins.concat('kendoui_custom.js'))
        .pipe(gulp.dest(config.js.dest))
        .pipe(plugins.uglify({
            mangle: true
        }))
        .pipe(plugins.rename('kendoui_custom.min.js'))
        .pipe(plugins.size({
            showFiles: true
        }))
        .pipe(gulp.dest(config.js.dest));

});

// 1.5 - js:page_specific
// common/page specific functions
gulp.task('js:page_specific', function () {
    return gulp.src([
            config.js.source + '/altair_admin_common.js',
            config.js.source + '/pages/*.js',
            config.js.source + '/custom/*.js',
            '!' + config.js.source + '/**/*.min.js'
        ])
        .pipe(plugins.uglify({
            mangle: true
        }))
        .pipe(plugins.rename({
            extname: ".min.js"
        }))
        .pipe(gulp.dest(function(file) {
            var fullPath     = path.resolve(__dirname, config.js.source);
            var baseDestPath = path.resolve(__dirname, config.js.dest);
            var finalPath    = path.resolve(baseDestPath, file.path.replace(fullPath + path.sep, ''))
            
            return path.dirname(finalPath);
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
        .pipe(gulp.dest(config.sass.dest))
        .pipe(plugins.size({
            title: 'sass'
        }));
});

// 2.2 - css
// Concatena arquivos CSS do SASS com CSS adicionais
gulp.task('css', function() {
    var stream = gulp.src(config.css.source)
        .pipe(plugins.concat(config.css.concat))

    if (production) {
        /** @TODO: Gerar Sourcemaps **/
        stream.pipe(plugins.minifyCss(config.css.minifyOptions))
    }

    return stream.pipe(gulp.dest(config.css.dest))
});

/***************************
 * 3. IMAGENS
 **************************/
// 3.1. images
// Otimiza as imagens, em ambiente de produção
gulp.task('images', function() {
    stream = gulp.src(config.image.source);

    if (production) {
        stream = stream.pipe(plugins.imagemin({
            progressive: true,
            interlaced: true
        }));
    }
    
    return stream.pipe(plugins.size({
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
    return gulp.src(config.jade.source)
        .pipe(plugins.jade())
        .pipe(gulp.dest(config.jade.dest))
});

// 4.2. html
// Minifica os HTMLs (ambiente de produção, somente)
gulp.task('html', function() {
    stream = gulp.src(config.html.source);

    if (production) {
        stream = stream.pipe(plugins.htmlMinifier(config.html.minifyOptions));
    }

    return stream.pipe(gulp.dest(config.html.dest));
});

/***************************
 * 5. OUTROS
 **************************/

// 5.1. assets
// Copia assets necessários e que não passam por algum tipo de processamento
gulp.task('assets', function(callback) {
    return gulp.src(config.assets.source)
        .pipe(gulp.dest(config.assets.dest));
});

/***************************
 * 6. ESTRUTURAIS
 **************************/

// 6.1. default
// Constrói arquivos e inicia servidor web em ambiente de desenvolvimento
gulp.task('default', function(callback) {
    return plugins.runSequence('build', 'browser-sync', callback);
});

// 6.2. production
// Entra no ambiente de produção
gulp.task('production', function() {
    production = true;
});

// 6.3. build
// Constrói todos os arquivos no ambiente selecionado
gulp.task('build', function(callback) {
    return plugins.runSequence('sass', ['js', 'css', 'images', 'jade', 'assets'], 'html', callback);
});

// 6.4. release
// Faz a release de produção de todos os arquivos
gulp.task('release', function(callback) {
    return plugins.runSequence('production', 'build', callback);
});

// 6.5. serve
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

    // Escuta por mudanças, roda tarefas necessárias e recarrega o navegador
    gulp.watch(config.sass.source, generateRunCallback('sass', browserSync.reload));
    gulp.watch(config.jade.source, generateRunCallback('jade', browserSync.reload));
    gulp.watch(config.css.source, generateRunCallback('css', browserSync.reload));
    gulp.watch(config.html.source, generateRunCallback('html', browserSync.reload));
    gulp.watch(config.image.source, generateRunCallback('images', browserSync.reload));
    gulp.watch(config.js.source, generateRunCallback('js', browserSync.reload));
    gulp.watch(config.assets.source, generateRunCallback('assets', browserSync.reload));
});

/***************************
 * 7. SUPORTE
 **************************/

// 7.1. generateRunCallback
// Roda uma tarefa e, em sequência (síncrono), chama um callback
function generateRunCallback(task, callback) {
    return function() {
        plugins.runSequence(task, callback);
    }
}