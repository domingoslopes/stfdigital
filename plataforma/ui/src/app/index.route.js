(function ()
{
    'use strict';

    angular
        .module('app')
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider, $urlRouterProvider, $locationProvider) {
        $locationProvider.html5Mode(true);

        $urlRouterProvider.otherwise('/login');

        // State definitions
        $stateProvider
            .state('app', {
                abstract: true,
                views   : {
                    'main@'         : {
                        templateUrl: 'app/core/layouts/vertical-navigation.html',
                        controller : 'app.MainController',
                        controllerAs: 'vm'
                    },
                    'toolbar@app'   : {
                        templateUrl: 'app/toolbar/toolbar.html',
                        controller : 'ToolbarController',
                        controllerAs: 'vm'
                    },
                    'navigation@app': {
                        templateUrl: 'app/navigation/navigation.html',
                        controller : 'NavigationController',
                        controllerAs: 'vm'
                    },
                    'quickPanel@app': {
                        templateUrl: 'app/quick-panel/quick-panel.html',
                        controller : 'QuickPanelController',
                        controllerAs: 'vm'
                    },
                },
            });
    }

})();
