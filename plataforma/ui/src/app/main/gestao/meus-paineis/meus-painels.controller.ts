module app.gestao.meusPaineis {
	'use strict';
	import IStateService = angular.ui.IStateService;
	
	export class PaineisController {

		/** @ngInject **/
		constructor(private $state: IStateService) { }

        public foo(): void {
            console.log("bar");
        }

		public isTabActive(stateName: string): boolean {
			return this.$state.current.name === stateName;
		}

	}

	angular
		.module('app.gestao.meus-paineis')
		.controller('app.gestao.meus-paineis.PaineisController', PaineisController);

}