(function () {
    'use strict';

    angular
        .module('app.core')
        .directive('stfMatches', stfMatches);

    /** @ngInject */
    function stfMatches() {
        return {
            require: 'ngModel',
            scope: {
                'stfMatches': '='
            },
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$validators.matches = function(modelValue) {
                    if (ctrl.$isEmpty(modelValue) && ctrl.$isEmpty(scope.stfMatches)) {
                        return true;
                    }
                    
                    return (modelValue === scope.stfMatches);
                };

                scope.$watch("stfMatches", function() {
                    ctrl.$validate();
                });
            }
        };
    }

})();
