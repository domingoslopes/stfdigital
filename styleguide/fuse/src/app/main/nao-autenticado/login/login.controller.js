(function() {

	angular.module('app.nao-autenticado.login').classy.controller({
		name: 'LoginController',

		inject: ['$state'],

		init: function() {
			console.log("LoginController inicializado.");
		},

		methods: {
			entrar: function() {
				console.log("TODO: Fazer login. Detalhes do usuário: ", this.form);
				this.$state.go('app.autenticado');
			}
		}
	});

})();