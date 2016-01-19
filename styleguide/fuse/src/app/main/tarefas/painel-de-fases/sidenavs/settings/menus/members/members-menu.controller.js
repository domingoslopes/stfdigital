(function () {
    'use strict';

    var app = angular.module('app.tarefas.painel-de-fases');
        
    app.classy.controller({
        name: 'MembersMenuController',

        inject: ['$document', '$mdDialog', 'BoardService'],

        init: function() {
            this.board = this.BoardService.data;
            this.newMemberSearchInput = '';
        },

        methods: {

            /**
             * Add New Member
             */
            addNewMember: function() {
                // Add new member
            },

            /**
             * Remove member
             *
             * @param ev
             * @param memberId
             */
            removeMember: function(ev, memberId) {
                var confirm = $mdDialog.confirm({
                    title              : 'Remove Member',
                    parent             : $document.find('#scrumboard'),
                    textContent        : 'Are you sure want to remove member?',
                    ariaLabel          : 'remove member',
                    targetEvent        : ev,
                    clickOutsideToClose: true,
                    escapeToClose      : true,
                    ok                 : 'Remove',
                    cancel             : 'Cancel'
                });

                $mdDialog.show(confirm).then(function () {
                    var arr = this.board.members;
                    arr.splice(arr.indexOf(arr.getById(memberId)), 1);

                    angular.forEach(this.board.cards, function (card) {
                        if ( card.idMembers && card.idMembers.indexOf(memberId) > -1 ) {
                            card.idMembers.splice(card.idMembers.indexOf(memberId), 1);
                        }
                    });
                }.bind(this));
            }
        }
    });
})();