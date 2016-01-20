(function () {
    /**
     * Diretiva <stf-header>
     *
     * Mostra o cabeçalho padrão para as views. Faz breadcrumbs automaticamente
     * e mostra o título usando o serviço de navegação do FUSE. 
     *
     * O conteúdo da tag ficará alinhado do lado direito do header.
     *
     * É possível utilizar o atributo layout-only="true|false" para exibir 
     * somente o conteúdo da tag, sem breadcrumb e título.
     */

    'use strict';

    angular
        .module('app.core')
        .directive('stfHeader', stfHeader);

    /** @ngInject */
    function stfHeader(msNavigationService) {
        return {
            scope: {
                'layoutOnly': '=',
                'fabAction': '&',
                'fabAriaLabel': '@',
                'fabIcon': '@'
            },
            templateUrl: 'app/core/directives/stf-header/stf-header.html',
            transclude: true,
            controller: 'StfHeaderController',
            link: function(scope,elem,attrs) {
                scope.hasFabAction = angular.isDefined(attrs.fabAction);
            }
        };
    }

})();
