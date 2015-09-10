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
 * 1. common_js
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

/***************************
 * 1. common_js
 * Minifica e concatena os javascripts
 **************************/

gulp.task('common_js', function () {
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

// cutom uikit
gulp.task('uikit_js', function () {
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

// uikit htmleditor
gulp.task('uikit_htmleditor_js', function () {
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

// custom kendoui
gulp.task('kendoui_js', function () {
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

// common/page specific functions
gulp.task('page_specific_js', function () {
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

/***************************
 * 2. sass
 * Transforma SASS em CSS
 **************************/

gulp.task('sass', function() {
    return gulp.src(config.sass)
        .pipe(plugins.sass())
        .on('error', plugins.sass.logError)
        .pipe(gulp.dest(config.sasscompiled))
        .pipe(plugins.size({
            title: 'sass'
    }));
});

/***************************
 * 3. copy:assets
 * Copia os assets para a pasta adequada
 **************************/

 gulp.task('copy:assets', function() {
    return gulp.src(config.assets, {
        dot: true
        })
        .pipe(gulp.dest(config.dist + '/assets-stf'))
        .pipe(plugins.size({
            title: 'copy:assets'
        }));
});

/***************************
 * 4. images
 * Otimiza as imagens e coloca na pasta adequada
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
 * 5. browser-sync
 * Antes de abrir o HTML no browser, executa o SASS e copia os assets
 **************************/


gulp.task('browser-sync', ['sass', 'copy:assets'], function() {
    browserSync.init({
        // http://www.browsersync.io/docs/options/#option-host
        //host: "192.168.1.2",
        // http://www.browsersync.io/docs/options/#option-proxy
        proxy: config.proxy,
        // http://www.browsersync.io/docs/options/#option-notify
        notify: false
    });

    gulp.watch(config.sass, ['sass'])

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

/***************************
 * 6. all_js
 * Roda todas as tarefas de Javascript
 **************************/

gulp.task('all_js', ['common_js','uikit_js','uikit_htmleditor_js','kendoui_js','page_specific_js']);

/***************************
 * 7. release
 * Roda a task browser sync
 **************************/

gulp.task('release', function(callback) {
    return plugins.runSequence(
        ['browser-sync', 'images'],
        callback
    );
});

/***************************
 * 8. default
 * Roda a task browser sync
 **************************/

gulp.task('default', function(callback) {
    return plugins.runSequence(
        ['browser-sync'],
        callback
    );
});