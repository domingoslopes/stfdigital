(function ()
{
    'use strict';

    angular
        .module('app.scrumboard')
        .controller('ScrumboardCalendarEventDialogController', ScrumboardCalendarEventDialogController);

    /** @ngInject */
    function ScrumboardCalendarEventDialogController($mdDialog, dueDate, BoardService, msUtils)
    {
        var vm = this;

        // Data
        vm.board = BoardService.data;
        vm.dueDate = dueDate;
        vm.eventDate = moment(dueDate).toDate();
        vm.newCardName = '';
        vm.newCardListId = '';
        vm.selectedCards = [];

        // Methods
        vm.exists = msUtils.exists;
        vm.toggleInArray = msUtils.toggleInArray;
        vm.closeDialog = closeDialog;
        vm.addNewCard = addNewCard;
        vm.assignDueDate = assignDueDate;

        //////////

        /**
         * Close Dialog
         */
        function closeDialog()
        {
            $mdDialog.hide();
        }

        /**
         * Add New Card
         */
        function addNewCard()
        {
            if ( vm.newCardName === '' || vm.newCardListId === '' )
            {
                return;
            }

            var newCardId = msUtils.guidGenerator();

            vm.board.cards.push({
                "id"               : newCardId,
                "name"             : vm.newCardName,
                "description"      : '',
                "idAttachmentCover": '',
                "idMembers"        : [],
                "idLabels"         : [],
                "attachments"      : [],
                "subscribed"       : false,
                "checklists"       : [],
                "checkItems"       : 0,
                "checkItemsChecked": 0,
                "comments"         : [],
                "activities"       : [],
                "due"              : vm.eventDate
            });

            vm.board.lists.getById(vm.newCardListId).idCards.push(newCardId);

            vm.newCardName = vm.newCardListId = '';

            closeDialog();
        }

        /**
         * Assign Due Date
         */
        function assignDueDate()
        {
            angular.forEach(vm.selectedCards, function (cardId)
            {
                vm.board.cards.getById(cardId).due = vm.eventDate;
            });

            vm.selectedCards = [];

            closeDialog();
        }
    }
})();