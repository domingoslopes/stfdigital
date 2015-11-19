/*
*  Altair Admin
*  @version v2.0.0
*  @author tzd
*  @license http://themeforest.net/licenses
*  forms_wysiwyg.js - forms_wysiwyg.html
*/

$(function() {
    // wysiwyg editor
    altair_wysiwyg.init();
});

// wysiwyg editor
altair_wysiwyg = {
    init: function() {
        var $wysiwygEditor = $('#wysiwyg_editor');

        if($wysiwygEditor.length) {
            $wysiwygEditor
                .ckeditor(function() {
                /* Callback function code. */
                }, {
                    customConfig: '../../assets/js/custom/ckeditor_config.js'
                });
        }

    }
};