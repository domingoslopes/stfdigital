(function ()
{
    'use strict';

    var app = angular.module('app.tarefas.painel-de-fases');

    /** @ngInject */
    app.classy.controller({
        name: 'TarefasPainelDeFasesPainelController',

        inject: ['$document', '$mdDialog', 'msUtils', 'BoardList', 'BoardService', 'CardFilters', 'DialogService', '$mdSidenav'],

        init: function() {
            // Data
            this.currentView = 'board';
            this.board = this.BoardService.data;
            this.boardList = this.BoardList.data;
            this.cardFilters = this.CardFilters;
            this.card = {};
            this.cardOptions = {};
            this.newListName = '';
            this.sortableListOptions = {
                axis       : 'x',
                delay      : 75,
                distance   : 7,
                items      : '> .list-wrapper',
                handle     : '.list-header',
                placeholder: 'list-wrapper list-sortable-placeholder',
                tolerance  : 'pointer',
                start      : function (event, ui) {
                    var width = ui.item[0].children[0].clientWidth;
                    var height = ui.item[0].children[0].clientHeight;

                    ui.placeholder.css({
                        'min-width': width + 'px',
                        'width'    : width + 'px',
                        'height'   : height + 'px'
                    });
                }
            };
            this.sortableCardOptions = {
                appendTo            : 'body',
                connectWith         : '.list-cards',
                delay               : 75,
                distance            : 7,
                forceHelperSize     : true,
                forcePlaceholderSize: true,
                handle              : this.msUtils.isMobile() ? '.list-card-sort-handle' : false,
                helper              : function (event, el)
                {
                    return el.clone().addClass('list-card-sort-helper');
                },
                placeholder         : 'list-card card-sortable-placeholder',
                tolerance           : 'pointer',
                scroll              : true,
                sort                : function (event, ui)
                {
                    var listContentEl = ui.placeholder.closest('.list-content');
                    var boardContentEl = ui.placeholder.closest('#board');

                    if ( listContentEl )
                    {
                        var listContentElHeight = listContentEl[0].clientHeight,
                            listContentElScrollHeight = listContentEl[0].scrollHeight;

                        if ( listContentElHeight !== listContentElScrollHeight )
                        {
                            var itemTop = ui.position.top,
                                itemBottom = itemTop + ui.item.height(),
                                listTop = listContentEl.offset().top,
                                listBottom = listTop + listContentElHeight;

                            if ( itemTop < listTop + 25 )
                            {
                                listContentEl.scrollTop(listContentEl.scrollTop() - 25);
                            }

                            if ( itemBottom > listBottom - 25 )
                            {
                                listContentEl.scrollTop(listContentEl.scrollTop() + 25);
                            }
                        }
                    }

                    if ( boardContentEl )
                    {
                        var boardContentElWidth = boardContentEl[0].clientWidth;
                        var boardContentElScrollWidth = boardContentEl[0].scrollWidth;

                        if ( boardContentElWidth !== boardContentElScrollWidth )
                        {
                            var itemLeft = ui.position.left,
                                itemRight = itemLeft + ui.item.width(),
                                boardLeft = boardContentEl.offset().left,
                                boardRight = boardLeft + boardContentElWidth;

                            if ( itemLeft < boardLeft + 25 )
                            {
                                boardContentEl.scrollLeft(boardContentEl.scrollLeft() - 25);
                            }

                            if ( itemRight > boardRight)
                            {
                                boardContentEl.scrollLeft(boardContentEl.scrollLeft() + 25);
                            }
                        }
                    }
                }
            };

            Array.prototype.getById = function (value) {
                return this.find(function (x) { return x.id === value; });
            }
        },

        methods: {
            /**
             * Toggle sidenav
             *
             * @param sidenavId
             */
            toggleSidenav: function(sidenavId) {
                this.$mdSidenav(sidenavId).toggle();
            },

            /**
             * Card filter
             *
             * @param cardId
             * @returns {*}
             */
            cardFilter: function(cardId) {
                var card = this.board.cards.find(function(card) { return card.id === cardId; });

                try {
                    if ( angular.lowercase(card.name).indexOf(angular.lowercase(this.cardFilters.name)) < 0 )
                    {
                        throw false;
                    }

                    angular.forEach(this.cardFilters.labels, function (label)
                    {
                        if ( !this.msUtils.exists(label, card.idLabels) )
                        {
                            throw false;
                        }
                    });

                    angular.forEach(this.cardFilters.members, function (member)
                    {
                        if ( !this.msUtils.exists(member, card.idMembers) )
                        {
                            throw false;
                        }
                    });


                } catch ( err ) {
                    return err;
                }

                return true;
            },

            /**
             * Is the card overdue?
             *
             * @param cardDate
             * @returns {boolean}
             */
            isOverdue: function(cardDate) {
                return new Date() > cardDate;
            }
        }
    });
})();