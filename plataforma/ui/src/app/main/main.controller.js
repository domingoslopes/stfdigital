var app;
(function (app) {
    'use strict';
    var MainController = (function () {
        /** @ngInject **/
        MainController.$inject = ["$scope", "$rootScope"];
        function MainController($scope, $rootScope) {
            $scope.$on('$viewContentAnimationEnded', function (event) {
                if (event.targetScope.$id === $scope.$id) {
                    $rootScope.$broadcast('msSplashScreen::remove');
                }
            });
        }
        return MainController;
    }());
    app.MainController = MainController;
    angular
        .module('app')
        .controller('app.MainController', MainController);
})(app || (app = {}));

//# sourceMappingURL=main.controller.js.map
