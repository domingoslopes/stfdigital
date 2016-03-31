(function ()
{
    'use strict';

    angular
        .module('app')
        .controller('MainController', mainController);

    /** @ngInject */
    function mainController($scope, $rootScope) {
        // Remove a tela de carregamento
        $scope.$on('$viewContentAnimationEnded', function (event) {
            if (event.targetScope.$id === $scope.$id) {
                $rootScope.$broadcast('msSplashScreen::remove');
            }
        });
    }
})();