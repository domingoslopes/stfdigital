(function () {
    'use strict';
    DialogService.$inject = ["$mdDialog", "$document"];
    angular
        .module('app.tarefas.painel-de-fases')
        .factory('DialogService', DialogService);
    /** @ngInject */
    function DialogService($mdDialog, $document) {
        var service = {
            openCardDialog: openCardDialog
        };
        //////////
        /**
         * Open card dialog
         *
         * @param ev
         * @param cardId
         */
        function openCardDialog(ev, cardId) {
            $mdDialog.show({
                templateUrl: 'app/main/tarefas/painel-de-fases/dialogs/card/card-dialog.html',
                controller: 'ScrumboardCardDialogController',
                controllerAs: 'vm',
                parent: $document.find('#scrumboard'),
                targetEvent: ev,
                clickOutsideToClose: true,
                escapeToClose: true,
                locals: {
                    cardId: cardId
                }
            });
        }
        return service;
    }
})();

//# sourceMappingURL=dialog.service.js.map
