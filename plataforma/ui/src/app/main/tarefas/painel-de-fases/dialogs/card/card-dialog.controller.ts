(function ()
{
    'use strict';

    var app = angular.module('app.tarefas.painel-de-fases');
        
    app.classy.controller({

        name: 'ScrumboardCardDialogController',

        inject: ['$document', '$mdDialog', 'fuseTheming', 'fuseGenerator', 'msUtils', 'BoardService', 'cardId', '$filter'],


        init: function() {
            // Data
            this.board = this.BoardService.data;
            this.card = this.board.cards.getById(this.cardId);
            this.newLabelColor = 'red';
            this.members = this.board.members;
            this.labels = this.board.labels;
            this.translate = this.$filter('translate');

            // Methods
            this.palettes = this.fuseTheming.getRegisteredPalettes();
            this.rgba = this.fuseGenerator.rgba;
            this.toggleInArray = this.msUtils.toggleInArray;
            this.exists = this.msUtils.exists;
       
            // Convert due date to the date object
            if ( this.card.due ) {
                this.card.due = new Date(this.card.due);
            }
        },

        methods: {

            /**
             * Close Dialog
             */
            closeDialog: function() {
                this.$mdDialog.hide();
            },

            /**
             * Get Card List
             */
            getCardList: function() {
                var response;
                for ( var i = 0, len = this.board.lists.length; i < len; i++ ) {
                    if ( this.board.lists[i].idCards.indexOf(this.card.id) > -1 ) {
                        response = this.board.lists[i];
                        break;
                    }
                }
                return response;
            },

            /**
             * Remove card
             *
             * @param ev
             */
            removeCard: function(ev) {
                var confirm = this.$mdDialog.confirm({
                    title              : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-CARTAO'),
                    parent             : this.$document.find('#scrumboard'),
                    textContent        : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-CARTAO-CONFIRMACAO'),
                    ariaLabel          : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER-CARTAO'),
                    targetEvent        : ev,
                    clickOutsideToClose: true,
                    escapeToClose      : true,
                    ok                 : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.REMOVER'),
                    cancel             : this.translate('TAREFAS.PAINEL-DE-FASES.DIALOGO.CANCELAR')
                });

                this.$mdDialog.show(confirm).then(function () {
                    var cardList = this.getCardList();

                    cardList.idCards.splice(cardList.idCards.indexOf(this.card.id), 1);

                    this.board.cards.splice(this.board.cards.indexOf(this.card), 1);

                }.bind(this));
            },

            /**
             * Toggle cover image
             *
             * @param attachmentId
             */
            toggleCoverImage: function(attachmentId) {
                if ( attachmentId === this.card.idAttachmentCover ) {
                    this.card.idAttachmentCover = null;
                } else {
                    this.card.idAttachmentCover = attachmentId;
                }
            },

            /**
             * Remove attachment
             *
             * @param item
             */
            removeAttachment: function(item) {
                if ( this.card.idAttachmentCover === item.id ) {
                    this.card.idAttachmentCover = '';
                }
                this.card.attachments.splice(this.card.attachments.indexOf(item), 1);
            },

            /**
             * Add label chips
             *
             * @param query
             * @returns {filterFn}
             */
            labelQuerySearch: function(query) {
                return query ? this.labels.filter(this.createFilterFor(query)) : [];
            },

            /**
             * Label filter
             *
             * @param label
             * @returns {boolean}
             */
            filterLabel: function(label) {
                if ( !this.labelSearchText || this.labelSearchText === '' ) {
                    return true;
                }

                return angular.lowercase(label.name).indexOf(angular.lowercase(this.labelSearchText)) >= 0;
            },

            /**
             * Add new label
             */
            addNewLabel: function() {
                this.board.labels.push({
                    "id"   : this.msUtils.guidGenerator(),
                    "name" : this.newLabelName,
                    "color": this.newLabelColor
                });

                this.newLabelName = '';
            },

            /**
             * Remove label
             */
            removeLabel: function() {
                var arr = this.board.labels;
                arr.splice(arr.indexOf(arr.getById(this.editLabelId)), 1);

                angular.forEach(this.board.cards, function (card) {
                    if ( card.idLabels && card.idLabels.indexOf(this.editLabelId) > -1 ) {
                        card.idLabels.splice(card.idLabels.indexOf(this.editLabelId), 1);
                    }
                }.bind(this));

                this.newLabelName = '';
            },

            /**
             * Add member chips
             *
             * @param query
             * @returns {Array}
             */
            memberQuerySearch: function(query) {
                return query ? this.members.filter(this.createFilterFor(query)) : [];
            },

            /**
             * Member filter
             *
             * @param member
             * @returns {boolean}
             */
            filterMember: function(member) {
                if ( !this.memberSearchText || this.memberSearchText === '' ) {
                    return true;
                }

                return angular.lowercase(member.name).indexOf(angular.lowercase(this.memberSearchText)) >= 0;
            },

            /**
             * Update check list stats
             * @param list
             */
            updateCheckedCount: function(list) {
                var checkItems = list.checkItems;
                var checkedItems = 0;
                var allCheckedItems = 0;
                var allCheckItems = 0;

                angular.forEach(checkItems, function (checkItem) {
                    if ( checkItem.checked ) {
                        checkedItems++;
                    }
                });

                list.checkItemsChecked = checkedItems;

                angular.forEach(this.card.checklists, function (item) {
                    allCheckItems += item.checkItems.length;
                    allCheckedItems += item.checkItemsChecked;
                });

                this.card.checkItems = allCheckItems;
                this.card.checkItemsChecked = allCheckedItems;
            },

            /**
             * Add checklist item
             *
             * @param text
             * @param checkList
             */
            addCheckItem: function(text, checkList) {
                if ( !text || text === '' ) {
                    return;
                }

                var newCheckItem = {
                    'name'   : text,
                    'checked': false
                };

                checkList.checkItems.push(newCheckItem);

                this.updateCheckedCount(checkList);
            },

            /**
             * Remove checklist
             *
             * @param item
             */
            removeChecklist: function(item) {
                this.card.checklists.splice(this.card.checklists.indexOf(item), 1);

                angular.forEach(this.card.checklists, function (list) {
                    this.updateCheckedCount(list);
                }.bind(this));
            },

            /**
             * Create checklist
             */
            createCheckList: function() {
                this.card.checklists.push({
                    "id"               : this.msUtils.guidGenerator(),
                    "name"             : this.newCheckListTitle,
                    "checkItemsChecked": 0,
                    "checkItems"       : []
                });

                this.newCheckListTitle = '';
            },

            /**
             * Add new comment
             *
             * @param newCommentText
             */
            addNewComment: function(newCommentText) {
                var newComment = {
                    "idMember": "36027j1930450d8bf7b10158",
                    "message" : newCommentText,
                    "time"    : "now"
                };

                this.card.comments.unshift(newComment);
            },

            /**
             * Filter for chips
             *
             * @param query
             * @returns {filterFn}
             */
            createFilterFor: function(query) {
                var lowercaseQuery = angular.lowercase(query);
                return function filterFn(item)
                {
                    return angular.lowercase(item.name).indexOf(lowercaseQuery) >= 0;
                };
            }
        }
    });
})();