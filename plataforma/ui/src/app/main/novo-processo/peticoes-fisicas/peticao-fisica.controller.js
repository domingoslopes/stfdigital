(function ()
{
    'use strict';

    angular.module('app.novo-processo.peticao-fisica').controller('PeticaoFisicaController', peticaoFisicaController);

    /** @ngInject */
    function peticaoFisicaController($mdDialog, $http, $state) {
        var vm = this;

        // Data
        vm.basicForm = {};
        vm.formWizard = {};

        vm.states = ('ADI ADO').split(' ').map(function (state)
        {
            return {abbrev: state};
        });

        // Methods
        vm.sendForm = sendForm;

        //////////

        /**
         * Send form
         */
        function sendForm() {

            /**
            // You can do an API call here to send the form to your server

            // Show the sent data.. you can delete this safely.
            $mdDialog.show({
                controller         : function ($scope, $mdDialog, formWizardData)
                {
                    $scope.formWizardData = formWizardData;
                    $scope.closeDialog = function ()
                    {
                        $mdDialog.hide();
                    }
                },
                template           : '<md-dialog>' +
                '  <md-dialog-content><h1>You have sent the form with the following data</h1><div><pre>{{formWizardData | json}}</pre></div></md-dialog-content>' +
                '  <md-dialog-actions>' +
                '    <md-button ng-click="closeDialog()" class="md-primary">' +
                '      Close' +
                '    </md-button>' +
                '  </md-dialog-actions>' +
                '</md-dialog>',
                parent             : angular.element('body'),
                targetEvent        : ev,
                locals             : {
                    formWizardData: vm.formWizard
                },
                clickOutsideToClose: true
            });

            // Clear the form data
            vm.formWizard = {};
            */

            $http.post("http://localhost:8080/api/remessas", "{\"formaRecebimento\":\"SEDEX\", \"volumes\":1, \"apensos\":1, \"numeroSedex\":\"SR123456789BR\", \"tipoProcesso\":\"originario\"}").success(function() {
                vm.formWizard = {};
                $state.go('app.tarefas.minhas-tarefas', $state.params, { reload: true });
            });
        }
    }
})();