(function() {
	'use strict';

	var app = angular.module('app.novo-processo');

	app.classy.controller({
		name: 'NovoProcessoController',

		inject: ['$scope'],

		init: function() {
			this.buscaProcesso = "";
			this.$scope.$watch('vm.buscaProcesso', this.filtrarProcessos);

			// MOCKS
			this.poucos_processos = [
				{
					id: 1,
					name: "Solicitar certidão negativa"
				},
				{
					id: 2,
					name: "Peticionar processo originário"
				},
				{
					id: 3,
					name: "Peticionar processo incidental"
				},
				{
					id: 4,
					name: "Suscitar informação"
				},
				{
					id: 5,
					name: "Ver muitos itens"
				},
			];

			this.muitos_processos = [
				{
					id: 1,
					name: "Solicitar certidão negativa"
				},
				{
					id: 2,
					name: "Peticionar processo originário"
				},
				{
					id: 3,
					name: "Peticionar processo incidental"
				},
				{
					id: 4,
					name: "Suscitar informação"
				},
				{
					id: 5,
					name: "Ver poucos itens"
				},
				{
					id: 6,
					name: "Nome do processo"
				},
				{
					id: 7,
					name: "Nome do processo"
				},
				{
					id: 8,
					name: "Nome do processo"
				},
				{
					id: 9,
					name: "Nome do processo"
				},
				{
					id: 10,
					name: "Nome do processo"
				},
				{
					id: 11,
					name: "Nome do processo"
				},
				{
					id: 12,
					name: "Nome do processo"
				},
				{
					id: 13,
					name: "Nome do processo"
				}
			];

			this.processos_origem = 0;
			this.processos = this.poucos_processos;
		},

		methods: {
			itemClick: function(id) {
				var item = this.processos.find(function(item) { return item.id === id; });
				console.log("Clique no item: " + item.name);

				if (item.id === 5) {
					this.processos = (this.processos_origem === 0 ? this.muitos_processos : this.poucos_processos);
					this.processos_origem = 1 - this.processos_origem;
				}

				this.filtrarProcessos();
			},

			filtrarProcessos: function() {
				var busca = this.buscaProcesso.toLowerCase();
                var origem = (this.processos_origem === 0 ? this.poucos_processos : this.muitos_processos);

                if (busca.length === 0) {
                	this.processos = origem;
                } else {
	                this.processos = origem.filter(function(processo) {
	                	return (processo.name.toLowerCase().indexOf(busca) !== -1);
					});
				}
			}
		}
	});
})();