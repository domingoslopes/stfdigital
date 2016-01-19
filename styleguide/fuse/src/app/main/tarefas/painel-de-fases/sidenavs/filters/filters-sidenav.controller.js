(function ()
{
    'use strict';

    var app = angular.module('app.tarefas.painel-de-fases');

    app.classy.controller({
        name: 'FiltersSidenavController',

        inject: ['msUtils', 'BoardService', 'CardFilters'],

        init: function() {
            // Data
            this.board = this.BoardService.data;
            this.cardFilters = this.CardFilters;
            this.labels = this.board.labels;
            this.members = this.board.members;
            this.selectedMenu = 'Settings';

            // Methods
            this.exists = this.msUtils.exists;
            this.toggleInArray = this.msUtils.toggleInArray;
            this.clearFilters = this.CardFilters.clear;
            this.filteringIsOn = this.CardFilters.isOn;
        }
    });
})();