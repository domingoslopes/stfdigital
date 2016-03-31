(function() {
    'use strict';

    var app = angular.module('app.gestao.meus-paineis');

    app.classy.controller({
        name: 'GestaoMeusPaineisAutuacoesController',

        inject: ['$http'],

        init: function() {
            this.$http.get('app/data/sample/gestao/meus-paineis/dashboard-widgets.json').then(function(response) {
                var dashboardData = response.data;

                // Widget 8
                this.widget8 = {
                    title    : dashboardData.widget8.title,
                    mainChart: {
                        options: {
                            chart: {
                                type     : 'pieChart',
                                color    : ['#f44336', '#9c27b0', '#03a9f4', '#e91e63', '#ffc107'],
                                height   : 400,
                                margin   : {
                                    top   : 0,
                                    right : 0,
                                    bottom: 0,
                                    left  : 0
                                },
                                labelType: 'percent',
                                x        : function (d)
                                {
                                    return d.label;
                                },
                                y        : function (d)
                                {
                                    return d.value;
                                },
                                tooltip  : {
                                    gravity: 's',
                                    classes: 'gravity-s'
                                }
                            }
                        },
                        data   : dashboardData.widget8.mainChart
                    }
                };

                // Widget 9
                this.widget9 = {
                    title       : dashboardData.widget9.title,
                    weeklySpent : {
                        title    : dashboardData.widget9.weeklySpent.title,
                        count    : dashboardData.widget9.weeklySpent.count,
                        chartData: []
                    },
                    totalSpent  : {
                        title    : dashboardData.widget9.totalSpent.title,
                        count    : dashboardData.widget9.totalSpent.count,
                        chartData: []
                    },
                    remaining   : {
                        title    : dashboardData.widget9.remaining.title,
                        count    : dashboardData.widget9.remaining.count,
                        chartData: []
                    },
                    totalBudget : dashboardData.widget9.totalBudget,
                    chart       : {
                        config : {
                            refreshDataOnly: true,
                            deepWatchData  : true
                        },
                        options: {
                            chart: {
                                type                   : 'lineChart',
                                color                  : ['#00BCD4'],
                                height                 : 50,
                                margin                 : {
                                    top   : 8,
                                    right : 0,
                                    bottom: 0,
                                    left  : 0
                                },
                                isArea                 : true,
                                interpolate            : 'cardinal',
                                clipEdge               : true,
                                duration               : 500,
                                showXAxis              : false,
                                showYAxis              : false,
                                showLegend             : false,
                                useInteractiveGuideline: true,
                                x                      : function (d)
                                {
                                    return d.x;
                                },
                                y                      : function (d)
                                {
                                    return d.y;
                                },
                                yDomain                : [0, 9],
                                xAxis                  : {
                                    tickFormat: function (d)
                                    {
                                        return this.widget9.days[d];
                                    }.bind(this)
                                },
                                interactiveLayer       : {
                                    tooltip: {
                                        gravity: 'e',
                                        classes: 'gravity-e'
                                    }
                                }
                            }
                        }
                    },
                    days        : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                    ranges      : dashboardData.widget9.ranges,
                    currentRange: '',
                    changeRange : function (range)
                    {
                        this.widget9.currentRange = range;

                        /**
                         * Update mini charts. They only have 1 dataset
                         * so we can do [0] without needing to iterate
                         * through in their data arrays
                         */
                        this.widget9.weeklySpent.chartData[0] = {
                            key   : dashboardData.widget9.weeklySpent.chart.label,
                            values: dashboardData.widget9.weeklySpent.chart.values[range]
                        };

                        this.widget9.totalSpent.chartData[0] = {
                            key   : dashboardData.widget9.totalSpent.chart.label,
                            values: dashboardData.widget9.totalSpent.chart.values[range]
                        };

                        this.widget9.remaining.chartData[0] = {
                            key   : dashboardData.widget9.remaining.chart.label,
                            values: dashboardData.widget9.remaining.chart.values[range]
                        };
                    }.bind(this),
                    init        : function ()
                    {
                        // Run this function once to initialize widget

                        /**
                         * Update the range for the first time
                         */
                        this.widget9.changeRange('TW');
                    }.bind(this)
                };

                this.widget9.init();

                // Widget 10
                this.widget10 = dashboardData.widget10;

            }.bind(this));
        },

        methods: {
        }
    });
})();
