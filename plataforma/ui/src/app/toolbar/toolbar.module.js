(function ()
{
    'use strict';

    angular
        .module('app.toolbar', ['classy'])
        .config(config);

    /** @ngInject */
    function config($translatePartialLoaderProvider) {
        $translatePartialLoaderProvider.addPart('app/toolbar');
    }
})();
