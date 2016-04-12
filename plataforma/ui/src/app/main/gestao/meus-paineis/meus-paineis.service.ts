module app.gestao.meusPaineis {
    'use strict';
    import IHttpService = angular.IHttpService;
    import IPromise = angular.IPromise;

    export class MeusPaineisService {

        private static widgetsJson: string = 'app/data/sample/gestao/meus-paineis/dashboard-widgets.json';

        /** @ngInject **/
        constructor(private $http: IHttpService) { }

        public loadDashboardWidget(widgetName: string): any {
            var widget: any = {};
            this.data()
                .then(data => {
                    angular.copy(this['get' + widgetName](data), widget);
                });
            return widget;
        }

        private data(): IPromise<any> {
            return this.$http
                .get(MeusPaineisService.widgetsJson)
                .then(response => {
                    return response.data;
                });
        }

        private getChart1(data: any): any {
            return data.chart1;
        }

        private getChart2(data: any): any {
            return data.chart2;
        }

        private getWidget1(data: any): any {
            return data.widget1;
        }

        private getWidget2(data: any): any {
            return data.widget2;
        }

        private getWidget3(data: any): any {
            return data.widget3;
        }

        private getWidget4(data: any): any {
            return data.widget4;
        }

        private getWidget5(data: any): any {
            var widget: any = {
                title       : data.widget5.title,
                mainChart   : {
                    config : {
                        refreshDataOnly: true,
                        deepWatchData  : true
                    },
                    options: {
                        chart: {
                            type        : 'multiBarChart',
                            color       : ['#03a9f4', '#b3e5fc'],
                            height      : 420,
                            margin      : {
                                top   : 8,
                                right : 16,
                                bottom: 32,
                                left  : 32
                            },
                            clipEdge    : true,
                            groupSpacing: 0.3,
                            reduceXTicks: false,
                            stacked     : true,
                            duration    : 250,
                            x           : (d) => { return d.x; },
                            y           : (d) => { return d.y; },
                            yAxis       : {
                                tickFormat: (d) => { return d; }
                            },
                            legend      : {
                                margin: {
                                    top   : 8,
                                    bottom: 32
                                }
                            },
                            controls    : {
                                margin: {
                                    top   : 8,
                                    bottom: 32
                                }
                            },
                            tooltip     : {
                                gravity: 's',
                                classes: 'gravity-s'
                            }
                        }
                    },
                    data   : []
                },
                supporting  : {
                    widgets: {
                        created  : {
                            data : data.widget5.supporting.created,
                            chart: { data: [] }
                        },
                        closed   : {
                            data : data.widget5.supporting.closed,
                            chart: { data: [] }
                        },
                        reOpened : {
                            data : data.widget5.supporting.reOpened,
                            chart: { data: [] }
                        },
                        wontFix  : {
                            data : data.widget5.supporting.wontFix,
                            chart: { data: [] }
                        },
                        needsTest: {
                            data : data.widget5.supporting.needsTest,
                            chart: { data: [] }
                        },
                        fixed    : {
                            data : data.widget5.supporting.fixed,
                            chart: { data: [] }
                        }
                    },
                    chart  : {
                        config : {
                            refreshDataOnly: true,
                            deepWatchData  : true
                        },
                        options: {
                            chart: {
                                type                   : 'lineChart',
                                color                  : ['#03A9F4'],
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
                                x                      : (d) => { return d.x; },
                                y                      : (d) => { return d.y; },
                                yDomain                : [0, 9],
                                xAxis                  : {
                                    tickFormat: (d) => { return widget.days[d]; }
                                },
                                interactiveLayer       : {
                                    tooltip: {
                                        gravity: 'e',
                                        classes: 'gravity-e'
                                    }
                                }
                            }
                        },
                        data   : []
                    }
                },
                days        : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                ranges      : data.widget5.ranges,
                currentRange: '',
                changeRange : (range) => {
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
                    angular.forEach(data.widget5.mainChart, (chartData, index) => {
                        widget.mainChart.data[index] = {
                            key   : chartData.key,
                            values: chartData.values[range]
                        };
                    });

                    /**
                     * Do the same thing for the supporting widgets but they
                     * only have 1 dataset so we can do [0] without needing to
                     * iterate through in their data arrays
                     */
                    angular.forEach(data.widget5.supporting, (wg, name) => {
                        widget.supporting.widgets[name].chart.data[0] = {
                            key   : wg.chart.key,
                            values: wg.chart.values[range]
                        };
                    });
                },
                init : () => {
                    // Run this function once to initialize widget

                    /**
                     * Update the range for the first time
                     */
                    widget.changeRange('TW');
                }
            };
            widget.init();
            return widget;
        }

        private getWidget6(data: any): any {
            var widget: any = {
                title       : data.widget6.title,
                mainChart   : {
                    config : {
                        refreshDataOnly: true,
                        deepWatchData  : true
                    },
                    options: {
                        chart: {
                            type        : 'pieChart',
                            color       : ['#f44336', '#9c27b0', '#03a9f4', '#e91e63'],
                            height      : 400,
                            margin      : {
                                top   : 0,
                                right : 0,
                                bottom: 0,
                                left  : 0
                            },
                            donut       : true,
                            clipEdge    : true,
                            cornerRadius: 0,
                            labelType   : 'percent',
                            padAngle    : 0.02,
                            x           : (d) => { return d.label; },
                            y           : (d) => { return d.value; },
                            tooltip     : {
                                gravity: 's',
                                classes: 'gravity-s'
                            }
                        }
                    },
                    data   : []
                },
                footerLeft  : data.widget6.footerLeft,
                footerRight : data.widget6.footerRight,
                ranges      : data.widget6.ranges,
                currentRange: '',
                changeRange : (range) => {
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
                    angular.forEach(data.widget6.mainChart, (data, index) => {
                        widget.mainChart.data[index] = {
                            label: data.label,
                            value: data.values[range]
                        };
                    });
                },
                init: () => {
                    // Run this function once to initialize widget

                    /**
                     * Update the range for the first time
                     */
                    widget.changeRange('TW');
                }
            };
        }

        private getWidget7(data: any): any {
            return {
                title       : data.widget7.title,
                ranges      : data.widget7.ranges,
                schedule    : data.widget7.schedule,
                currentRange: 'T'
            };
        }

        private getWidget8(data: any): any {
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
                            x: (d) => { return d.label; },
                            y: (d) => { return d.value; },
                            tooltip: {
                                gravity: 's',
                                classes: 'gravity-s'
                            }
                        }
                    },
                    data: data.widget8.mainChart
                }
            }
        }

        private getWidget9(data: any): any {
            var widget: any = {
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
                            x: (d) => { return d.x; },
                            y: (d) => { return d.y; },
                            yDomain: [0, 9],
                            xAxis: {
                                tickFormat: (d) => { return widget.days[d]; }
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
                changeRange: (range) => {
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
                init: () => {
                    // Run this function once to initialize widget

                    /**
                     * Update the range for the first time
                     */
                    widget.changeRange('TW');
                }
            }
            widget.init();
            return widget;
        }

        private getWidget10(data: any): any {
            return data.widget10;
        }

        private getWidget11(data: any): any {
            return {
                title    : data.widget11.title,
                table    : data.widget11.table,
                dtOptions: {
                    dom       : '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                    pagingType: 'simple',
                    order     : [0, 'asc'],
                }
            };
        }

    }
    
    angular
        .module('app.gestao.meus-paineis')
        .service('app.gestao.meus-paineis.MeusPaineisService', MeusPaineisService);
}
