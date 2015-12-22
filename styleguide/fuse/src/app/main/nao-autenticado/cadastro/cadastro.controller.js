(function() {

	angular.module('app.nao-autenticado.cadastro').classy.controller({
		name: 'CadastroController',

		inject: ['$scope'],

		init: function() {
			this.form = {
				tipoCadastro: 1
			};
		},

		methods: {
			cadastrar: function() {
				console.log("TODO: Fazer cadastro. Detalhes do usuário: ", this.form);
			}
		}
	});

})();