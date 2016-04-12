var app;
(function (app) {
    var gestao;
    (function (gestao) {
        var meusPaineis;
        (function (meusPaineis) {
            'use strict';
            var MeusPaineisService = (function () {
                /** @ngInject **/
                MeusPaineisService.$inject = ["$http"];
                function MeusPaineisService($http) {
                    this.$http = $http;
                }
                MeusPaineisService.prototype.loadDashboardWidget = function (widgetName) {
                    var _this = this;
                    var widget = {};
                    this.data()
                        .then(function (data) {
                        angular.copy(_this['get' + widgetName](data), widget);
                    });
                    return widget;
                };
                MeusPaineisService.prototype.data = function () {
                    return this.$http
                        .get(MeusPaineisService.widgetsJson)
                        .then(function (response) {
                        return response.data;
                    });
                };
                MeusPaineisService.prototype.getChart1 = function (data) {
                    return data.chart1;
                };
                MeusPaineisService.prototype.getChart2 = function (data) {
                    return data.chart2;
                };
                MeusPaineisService.prototype.getWidget1 = function (data) {
                    return data.widget1;
                };
                MeusPaineisService.prototype.getWidget2 = function (data) {
                    return data.widget2;
                };
                MeusPaineisService.prototype.getWidget3 = function (data) {
                    return data.widget3;
                };
                MeusPaineisService.prototype.getWidget4 = function (data) {
                    return data.widget4;
                };
                MeusPaineisService.prototype.getWidget5 = function (data) {
                    var widget = {
                        title: data.widget5.title,
                        mainChart: {
                            config: {
                                refreshDataOnly: true,
                                deepWatchData: true
                            },
                            options: {
                                chart: {
                                    type: 'multiBarChart',
                                    color: ['#03a9f4', '#b3e5fc'],
                                    height: 420,
                                    margin: {
                                        top: 8,
                                        right: 16,
                                        bottom: 32,
                                        left: 32
                                    },
                                    clipEdge: true,
                                    groupSpacing: 0.3,
                                    reduceXTicks: false,
                                    stacked: true,
                                    duration: 250,
                                    x: function (d) { return d.x; },
                                    y: function (d) { return d.y; },
                                    yAxis: {
                                        tickFormat: function (d) { return d; }
                                    },
                                    legend: {
                                        margin: {
                                            top: 8,
                                            bottom: 32
                                        }
                                    },
                                    controls: {
                                        margin: {
                                            top: 8,
                                            bottom: 32
                                        }
                                    },
                                    tooltip: {
                                        gravity: 's',
                                        classes: 'gravity-s'
                                    }
                                }
                            },
                            data: []
                        },
                        supporting: {
                            widgets: {
                                created: {
                                    data: data.widget5.supporting.created,
                                    chart: { data: [] }
                                },
                                closed: {
                                    data: data.widget5.supporting.closed,
                                    chart: { data: [] }
                                },
                                reOpened: {
                                    data: data.widget5.supporting.reOpened,
                                    chart: { data: [] }
                                },
                                wontFix: {
                                    data: data.widget5.supporting.wontFix,
                                    chart: { data: [] }
                                },
                                needsTest: {
                                    data: data.widget5.supporting.needsTest,
                                    chart: { data: [] }
                                },
                                fixed: {
                                    data: data.widget5.supporting.fixed,
                                    chart: { data: [] }
                                }
                            },
                            chart: {
                                config: {
                                    refreshDataOnly: true,
                                    deepWatchData: true
                                },
                                options: {
                                    chart: {
                                        type: 'lineChart',
                                        color: ['#03A9F4'],
                                        height: 50,
                                        margin: {
                                            top: 8,
                                            right: 0,
                                            bottom: 0,
                                            left: 0
                                        },
                                        isArea: true,
                                        interpolate: 'cardinal',
                                        clipEdge: true,
                                        duration: 500,
                                        showXAxis: false,
                                        showYAxis: false,
                                        showLegend: false,
                                        useInteractiveGuideline: true,
                                        x: function (d) { return d.x; },
                                        y: function (d) { return d.y; },
                                        yDomain: [0, 9],
                                        xAxis: {
                                            tickFormat: function (d) { return widget.days[d]; }
                                        },
                                        interactiveLayer: {
                                            tooltip: {
                                                gravity: 'e',
                                                classes: 'gravity-e'
                                            }
                                        }
                                    }
                                },
                                data: []
                            }
                        },
                        days: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                        ranges: data.widget5.ranges,
                        currentRange: '',
                        changeRange: function (range) {
                            widget.currentRange = range;
                            /**
                             * Update main chart data by iterating through the
                             * chart dataset and separately adding every single
                             * dataset by hand.
                             *
                             * You MUST NOT swap the entire data object by doing
                             * something similar to this:
                             * this.widget.mainChart.data = chartData
                             *
                             * It would be easier but it won't work with the
                             * live updating / animated charts due to how d3
                             * works.
                             *
                             * If you don't need animated / live updating charts,
                             * you can simplify these greatly.
                             */
                            angular.forEach(data.widget5.mainChart, function (chartData, index) {
                                widget.mainChart.data[index] = {
                                    key: chartData.key,
                                    values: chartData.values[range]
                                };
                            });
                            /**
                             * Do the same thing for the supporting widgets but they
                             * only have 1 dataset so we can do [0] without needing to
                             * iterate through in their data arrays
                             */
                            angular.forEach(data.widget5.supporting, function (wg, name) {
                                widget.supporting.widgets[name].chart.data[0] = {
                                    key: wg.chart.key,
                                    values: wg.chart.values[range]
                                };
                            });
                        },
                        init: function () {
                            // Run this function once to initialize widget
                            /**
                             * Update the range for the first time
                             */
                            widget.changeRange('TW');
                        }
                    };
                    widget.init();
                    return widget;
                };
                MeusPaineisService.prototype.getWidget6 = function (data) {
                    var widget = {
                        title: data.widget6.title,
                        mainChart: {
                            config: {
                                refreshDataOnly: true,
                                deepWatchData: true
                            },
                            options: {
                                chart: {
                                    type: 'pieChart',
                                    color: ['#f44336', '#9c27b0', '#03a9f4', '#e91e63'],
                                    height: 400,
                                    margin: {
                                        top: 0,
                                        right: 0,
                                        bottom: 0,
                                        left: 0
                                    },
                                    donut: true,
                                    clipEdge: true,
                                    cornerRadius: 0,
                                    labelType: 'percent',
                                    padAngle: 0.02,
                                    x: function (d) { return d.label; },
                                    y: function (d) { return d.value; },
                                    tooltip: {
                                        gravity: 's',
                                        classes: 'gravity-s'
                                    }
                                }
                            },
                            data: []
                        },
                        footerLeft: data.widget6.footerLeft,
                        footerRight: data.widget6.footerRight,
                        ranges: data.widget6.ranges,
                        currentRange: '',
                        changeRange: function (range) {
                            widget.currentRange = range;
                            /**
                             * Update main chart data by iterating through the
                             * chart dataset and separately adding every single
                             * dataset by hand.
                             *
                             * You MUST NOT swap the entire data object by doing
                             * something similar to this:
                             * this.widget.mainChart.data = chartData
                             *
                             * It would be easier but it won't work with the
                             * live updating / animated charts due to how d3
                             * works.
                             *
                             * If you don't need animated / live updating charts,
                             * you can simplify these greatly.
                             */
                            angular.forEach(data.widget6.mainChart, function (data, index) {
                                widget.mainChart.data[index] = {
                                    label: data.label,
                                    value: data.values[range]
                                };
                            });
                        },
                        init: function () {
                            // Run this function once to initialize widget
                            /**
                             * Update the range for the first time
                             */
                            widget.changeRange('TW');
                        }
                    };
                };
                MeusPaineisService.prototype.getWidget7 = function (data) {
                    return {
                        title: data.widget7.title,
                        ranges: data.widget7.ranges,
                        schedule: data.widget7.schedule,
                        currentRange: 'T'
                    };
                };
                MeusPaineisService.prototype.getWidget8 = function (data) {
                    return {
                        title: data.widget8.title,
                        mainChart: {
                            options: {
                                chart: {
                                    type: 'pieChart',
                                    color: ['#f44336', '#9c27b0', '#03a9f4', '#e91e63', '#ffc107'],
                                    height: 400,
                                    margin: {
                                        top: 0,
                                        right: 0,
                                        bottom: 0,
                                        left: 0
                                    },
                                    labelType: 'percent',
                                    x: function (d) { return d.label; },
                                    y: function (d) { return d.value; },
                                    tooltip: {
                                        gravity: 's',
                                        classes: 'gravity-s'
                                    }
                                }
                            },
                            data: data.widget8.mainChart
                        }
                    };
                };
                MeusPaineisService.prototype.getWidget9 = function (data) {
                    var widget = {
                        title: data.widget9.title,
                        weeklySpent: {
                            title: data.widget9.weeklySpent.title,
                            count: data.widget9.weeklySpent.count,
                            chartData: []
                        },
                        totalSpent: {
                            title: data.widget9.totalSpent.title,
                            count: data.widget9.totalSpent.count,
                            chartData: []
                        },
                        remaining: {
                            title: data.widget9.remaining.title,
                            count: data.widget9.remaining.count,
                            chartData: []
                        },
                        totalBudget: data.widget9.totalBudget,
                        chart: {
                            config: {
                                refreshDataOnly: true,
                                deepWatchData: true
                            },
                            options: {
                                chart: {
                                    type: 'lineChart',
                                    color: ['#00BCD4'],
                                    height: 50,
                                    margin: {
                                        top: 8,
                                        right: 0,
                                        bottom: 0,
                                        left: 0
                                    },
                                    isArea: true,
                                    interpolate: 'cardinal',
                                    clipEdge: true,
                                    duration: 500,
                                    showXAxis: false,
                                    showYAxis: false,
                                    showLegend: false,
                                    useInteractiveGuideline: true,
                                    x: function (d) { return d.x; },
                                    y: function (d) { return d.y; },
                                    yDomain: [0, 9],
                                    xAxis: {
                                        tickFormat: function (d) { return widget.days[d]; }
                                    },
                                    interactiveLayer: {
                                        tooltip: {
                                            gravity: 'e',
                                            classes: 'gravity-e'
                                        }
                                    }
                                }
                            }
                        },
                        days: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                        ranges: data.widget9.ranges,
                        currentRange: '',
                        changeRange: function (range) {
                            widget.currentRange = range;
                            /**
                             * Update mini charts. They only have 1 dataset
                             * so we can do [0] without needing to iterate
                             * through in their data arrays
                             */
                            widget.weeklySpent.chartData[0] = {
                                key: data.widget9.weeklySpent.chart.label,
                                values: data.widget9.weeklySpent.chart.values[range]
                            };
                            widget.totalSpent.chartData[0] = {
                                key: data.widget9.totalSpent.chart.label,
                                values: data.widget9.totalSpent.chart.values[range]
                            };
                            widget.remaining.chartData[0] = {
                                key: data.widget9.remaining.chart.label,
                                values: data.widget9.remaining.chart.values[range]
                            };
                        },
                        init: function () {
                            // Run this function once to initialize widget
                            /**
                             * Update the range for the first time
                             */
                            widget.changeRange('TW');
                        }
                    };
                    widget.init();
                    return widget;
                };
                MeusPaineisService.prototype.getWidget10 = function (data) {
                    return data.widget10;
                };
                MeusPaineisService.prototype.getWidget11 = function (data) {
                    return {
                        title: data.widget11.title,
                        table: data.widget11.table,
                        dtOptions: {
                            dom: '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                            pagingType: 'simple',
                            order: [0, 'asc'],
                        }
                    };
                };
                MeusPaineisService.widgetsJson = 'app/data/sample/gestao/meus-paineis/dashboard-widgets.json';
                return MeusPaineisService;
            }());
            meusPaineis.MeusPaineisService = MeusPaineisService;
            angular
                .module('app.gestao.meus-paineis')
                .service('app.gestao.meus-paineis.MeusPaineisService', MeusPaineisService);
        })(meusPaineis = gestao.meusPaineis || (gestao.meusPaineis = {}));
    })(gestao = app.gestao || (app.gestao = {}));
})(app || (app = {}));

//# sourceMappingURL=meus-paineis.service.js.map
