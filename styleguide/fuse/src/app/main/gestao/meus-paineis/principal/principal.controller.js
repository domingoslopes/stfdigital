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
			}.bind(this));
		},

		methods: {
			foo: function() {
				console.log("bar");
			}
		}
	})
})();