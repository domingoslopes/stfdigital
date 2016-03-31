(function() {

	angular.module('app.login').classy.controller({
		name: 'LoginController',

		inject: ['$state'],

		init: function() {
			console.log("LoginController inicializado.");
		},

		methods: {
			entrar: function() {
				console.log("TODO: Fazer login. Detalhes do usuário: ", this.form);
                this.$state.go('app.tarefas.minhas-tarefas');
			}
		}
	});

})();
