(function () {
    'use strict';

    var app = angular.module('app.tarefas.painel-de-fases');
        
    app.classy.controller({

        name: 'LabelsMenuController',

        inject: ['$document', '$mdColorPalette', '$mdDialog', 'fuseGenerator', 'msUtils', 'BoardService'],
    
        init: function() {
            this.board = this.BoardService.data;
            this.palettes = this.$mdColorPalette;
            this.rgba = this.fuseGenerator.rgba;
            this.hue = 500;
            this.newLabelColor = 'red';
            this.newLabelName = '';

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
                    title              : 'Remove Label',
                    parent             : this.$document.find('#scrumboard'),
                    textContent        : 'Are you sure want to remove label?',
                    ariaLabel          : 'remove label',
                    targetEvent        : ev,
                    clickOutsideToClose: true,
                    escapeToClose      : true,
                    ok                 : 'Remove',
                    cancel             : 'Cancel'
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