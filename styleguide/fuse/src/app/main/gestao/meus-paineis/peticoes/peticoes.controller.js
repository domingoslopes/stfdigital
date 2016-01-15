(function() {
    'use strict';

    var app = angular.module('app.gestao.meus-paineis');

    app.classy.controller({
        name: 'GestaoMeusPaineisPeticoesController',

        inject: ['$http'],

        init: function() {
            this.$http.get('app/data/sample/gestao/meus-paineis/dashboard-widgets.json').then(function(response) {
                var dashboardData = response.data;

                this.chart1 = dashboardData.chart1;
                this.chart2 = dashboardData.chart2;
            }.bind(this));
        },

        methods: {
        }
    })
})();
