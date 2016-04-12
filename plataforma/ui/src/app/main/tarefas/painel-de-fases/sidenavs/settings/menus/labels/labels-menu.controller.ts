(function () {
    'use strict';

    var app = angular.module('app.tarefas.painel-de-fases');
        
    app.classy.controller({

        name: 'LabelsMenuController',

        inject: ['$document', '$mdColorPalette', '$mdDialog', '$filter', 'fuseGenerator', 'msUtils', 'BoardService'],
    
        init: function() {
            this.board = this.BoardService.data;
            this.palettes = this.$mdColorPalette;
            this.rgba = this.fuseGenerator.rgba;
            this.hue = 500;
            this.newLabelColor = 'red';
            this.newLabelName = '';
            this.translate = this.$filter('translate');
        },
        
        methods: {
            /**
             * Add New Label
             */
            addNewLabel: function() {
                this.board.labels.push({
                    id: this.msUtils.guidGenerator(),
                    name: this.newLabelName,
                    color: this.newLabelColor
                });
                this.newLabelName = '';
            },

            /**
             * Remove label
             *
             * @param ev
             * @param labelId
             */
            removeLabel: function(ev, labelId) {
                var confirm = this.$mdDialog.confirm({
                    title              : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-ROTULO'),
                    parent             : this.$document.find('#scrumboard'),
                    textContent        : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-ROTULO-CONFIRMACAO'),
                    ariaLabel          : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-ROTULO'),
                    targetEvent        : ev,
                    clickOutsideToClose: true,
                    escapeToClose      : true,
                    ok                 : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER'),
                    cancel             : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.CANCELAR')
                });

                this.$mdDialog.show(confirm).then(function () {
                    var arr = this.board.labels;
                    arr.splice(arr.indexOf(arr.getById(labelId)), 1);

                    angular.forEach(this.board.cards, function (card) {
                        if (card.idLabels && card.idLabels.indexOf(labelId) > -1 ) {
                            card.idLabels.splice(card.idLabels.indexOf(labelId), 1);
                        }
                    });
                }.bind(this));
            }
        }
    });
})();