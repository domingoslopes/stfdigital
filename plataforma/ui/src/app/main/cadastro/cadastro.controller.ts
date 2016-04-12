module app.cadastro {
    'use strict';

	interface ICadastro {
		tipoCadastro: number;
	}

	export class CadastroController {

		private form: ICadastro;

		constructor() {
			this.form.tipoCadastro = 1;
		}

		public cadastrar(): void {
			console.log("TODO: Fazer cadastro. Detalhes do usuário: ", this.form);
		}

	}

	angular
        .module('app.cadastro')
		.controller('CadastroController', CadastroController);
}