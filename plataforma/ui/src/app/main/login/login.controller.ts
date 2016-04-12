module app.login {
	'use strict';
	import IStateService = angular.ui.IStateService;
	
	export class LoginController {
	
		public form: any;
		
		/** @ngInject **/
		constructor(private $state: IStateService) { }

		public entrar(): void {
			console.log("TODO: Fazer login. Detalhes do usuário: ", this.form);
			this.$state.go('app.tarefas.minhas-tarefas');
		}
	}

	angular
		.module('app.login')
		.controller('app.login.LoginController', LoginController);
}