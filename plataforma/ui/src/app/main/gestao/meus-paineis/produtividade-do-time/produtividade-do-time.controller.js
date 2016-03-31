(function() {
    'use strict';

    var app = angular.module('app.gestao.meus-paineis');

    app.classy.controller({
        name: 'GestaoMeusPaineisProdutividadeDoTimeController',

        inject: ['$http'],

        init: function() {
            this.$http.get('app/data/sample/gestao/meus-paineis/dashboard-widgets.json').then(function(response) {
                var dashboardData = response.data;

                // Widget 11
                this.widget11 = {
                    title    : dashboardData.widget11.title,
                    table    : dashboardData.widget11.table,
                    dtOptions: {
                        dom       : '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                        pagingType: 'simple',
                        order     : [0, 'asc'],
                    }
                };

            }.bind(this));
        },

        methods: {
        }
    });
})();
