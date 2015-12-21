(function ()
{
    'use strict';

    angular
        .module('app.core')
        .directive('stfCpf', stfCpf);

    /** @ngInject */
    function stfCpf(CPFService) {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$validators.cpf = function(modelValue, viewValue) {
                    if (ctrl.$isEmpty(modelValue)) {
                        return true;
                    }

                    return CPFService.validarCPF(modelValue);
                };
            }
        };
    }

})();