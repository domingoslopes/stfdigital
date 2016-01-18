(function() {
	'use strict';

	var app = angular.module('app.gestao.meus-paineis');

	app.classy.controller({
		name: 'GestaoMeusPaineisController',

		inject: ['$state'],

		init: function() {

		},

		methods: {
			foo: function() {
				console.log("bar");
			},

			isTabActive: function(stateName) {
				return this.$state.current.name == stateName;
			}
		}
	})
})();