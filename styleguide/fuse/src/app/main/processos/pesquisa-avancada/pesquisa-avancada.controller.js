(function () {

    'use strict';

    var app = angular.module('app.processos.pesquisa-avancada');

    app.classy.controller({

        name: 'ProcessosPesquisaAvancadaController',

        inject: ['$mdDialog', '$scope', '$document', '$filter', '$mdToast', 'traits'],

        init: function() {
            this.translate = this.$filter('translate');
            this.traits = this.traits.data;

            this.search = {
                id: null,
                name: '',
                criterias: []
            };
            
            this.selectedTab = false;
            this.searchComplete = false;   
        },

        methods: {
            canSearch: function() {
                return ((this.search.criterias.length > 0) && (_.all(this.search.criterias, 'valid')));
            },

            doSearch: function() {
                // TODO: Do search
                this.selectedTab = 1;
                this.currentSearch = this.search;
                this.searchComplete = true;
            },

            saveSearch: function() {
                // TODO: Save search
                this.$mdToast.show(
                    this.$mdToast.simple()
                        .textContent(this.translate('PROCESSOS.PESQUISA-AVANCADA.PESQUISA-SALVA'))
                        .position('top right')
                        .hideDelay(3000)
                );
            }

        }

    });

})();