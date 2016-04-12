module app {
    'use strict';
    import IScope = angular.IScope;
    import IRootScopeService = angular.IRootScopeService;

    export class MainController {

        /** @ngInject **/
        constructor($scope: IScope, $rootScope: IRootScopeService) {
            $scope.$on('$viewContentAnimationEnded', function(event) {
                if(event.targetScope.$id === $scope.$id) {
                    $rootScope.$broadcast('msSplashScreen::remove');
                }
            });
        }
    }

    angular
        .module('app')
        .controller('app.MainController', MainController);
}