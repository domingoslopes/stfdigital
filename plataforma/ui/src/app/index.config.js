(function ()
{
    'use strict';

    angular
        .module('app')
        .config(config);

    /** @ngInject */
    function config($translatePartialLoaderProvider) {
        // Adiciona o conjunto de strings de tradução
        $translatePartialLoaderProvider.addPart('app/main');
    }

})();