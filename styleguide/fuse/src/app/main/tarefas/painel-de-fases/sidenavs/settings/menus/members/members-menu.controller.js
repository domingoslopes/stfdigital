(function () {
    'use strict';

    var app = angular.module('app.tarefas.painel-de-fases');
        
    app.classy.controller({
        name: 'MembersMenuController',

        inject: ['$document', '$mdDialog', '$filter', 'BoardService'],

        init: function() {
            this.board = this.BoardService.data;
            this.newMemberSearchInput = '';
            this.translate = this.$filter('translate');
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
                var confirm = this.$mdDialog.confirm({
                    title              : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-MEMBRO'),
                    parent             : this.$document.find('#scrumboard'),
                    textContent        : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-MEMBRO-CONFIRMACAO'),
                    ariaLabel          : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-MEMBRO'),
                    targetEvent        : ev,
                    clickOutsideToClose: true,
                    escapeToClose      : true,
                    ok                 : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER'),
                    cancel             : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.CANCELAR')
                });

                this.$mdDialog.show(confirm).then(function () {
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