(function() {
	'use strict';

	var app = angular.module('app.gestao.meus-paineis');

	app.classy.controller({
		name: 'GestaoMeusPaineisPrincipalController',

		inject: ['$http'],

		init: function() {
			this.$http.get('app/data/sample/gestao/meus-paineis/dashboard-widgets.json').then(function(response) {
				var dashboardData = response.data;

				this.widget1 = dashboardData.widget1;
				this.widget2 = dashboardData.widget2;
				this.widget3 = dashboardData.widget3;
				this.widget4 = dashboardData.widget4;

				// Widget 5
		        this.widget5 = {
		            title       : dashboardData.widget5.title,
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
		                        x           : function (d)
		                        {
		                            return d.x;
		                        },
		                        y           : function (d)
		                        {
		                            return d.y;
		                        },
		                        yAxis       : {
		                            tickFormat: function (d)
		                            {
		                                return d;
		                            }
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
		                        data : dashboardData.widget5.supporting.created,
		                        chart: {
		                            data: []
		                        }
		                    },
		                    closed   : {
		                        data : dashboardData.widget5.supporting.closed,
		                        chart: {
		                            data: []
		                        }
		                    },
		                    reOpened : {
		                        data : dashboardData.widget5.supporting.reOpened,
		                        chart: {
		                            data: []
		                        }
		                    },
		                    wontFix  : {
		                        data : dashboardData.widget5.supporting.wontFix,
		                        chart: {
		                            data: []
		                        }
		                    },
		                    needsTest: {
		                        data : dashboardData.widget5.supporting.needsTest,
		                        chart: {
		                            data: []
		                        }
		                    },
		                    fixed    : {
		                        data : dashboardData.widget5.supporting.fixed,
		                        chart: {
		                            data: []
		                        }
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
		                                    return this.widget5.days[d];
		                                }.bind(this)
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
		            ranges      : dashboardData.widget5.ranges,
		            currentRange: '',
		            changeRange : function (range)
		            {
		                this.widget5.currentRange = range;

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
		                angular.forEach(dashboardData.widget5.mainChart, function (chartData, index)
		                {
		                    this.widget5.mainChart.data[index] = {
		                        key   : chartData.key,
		                        values: chartData.values[range]
		                    };
		                }.bind(this));

		                /**
		                 * Do the same thing for the supporting widgets but they
		                 * only have 1 dataset so we can do [0] without needing to
		                 * iterate through in their data arrays
		                 */
		                angular.forEach(dashboardData.widget5.supporting, function (widget, name)
		                {
		                    this.widget5.supporting.widgets[name].chart.data[0] = {
		                        key   : widget.chart.key,
		                        values: widget.chart.values[range]
		                    };
		                }.bind(this));
		            }.bind(this),
		            init        : function ()
		            {
		                // Run this function once to initialize widget

		                /**
		                 * Update the range for the first time
		                 */
		                this.widget5.changeRange('TW');
		            }.bind(this)
		        };

		        // Widget 6
		        this.widget6 = {
		            title       : dashboardData.widget6.title,
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
		                        x           : function (d)
		                        {
		                            return d.label;
		                        },
		                        y           : function (d)
		                        {
		                            return d.value;
		                        },
		                        tooltip     : {
		                            gravity: 's',
		                            classes: 'gravity-s'
		                        }
		                    }
		                },
		                data   : []
		            },
		            footerLeft  : dashboardData.widget6.footerLeft,
		            footerRight : dashboardData.widget6.footerRight,
		            ranges      : dashboardData.widget6.ranges,
		            currentRange: '',
		            changeRange : function (range)
		            {
		                this.widget6.currentRange = range;

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
		                angular.forEach(dashboardData.widget6.mainChart, function (data, index)
		                {
		                    this.widget6.mainChart.data[index] = {
		                        label: data.label,
		                        value: data.values[range]
		                    };
		                }.bind(this));
		            }.bind(this),
		            init        : function ()
		            {
		                // Run this function once to initialize widget

		                /**
		                 * Update the range for the first time
		                 */
		                this.widget6.changeRange('TW');
		            }.bind(this)
		        };

		        // Widget 7
		        this.widget7 = {
		            title       : dashboardData.widget7.title,
		            ranges      : dashboardData.widget7.ranges,
		            schedule    : dashboardData.widget7.schedule,
		            currentRange: 'T'
		        };

		        this.widget5.init();
		        this.widget6.init();
			}.bind(this));
		},

		methods: {
		}
	});
})();
