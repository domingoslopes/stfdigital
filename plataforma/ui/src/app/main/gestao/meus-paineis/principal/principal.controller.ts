module app.gestao.meusPaineis {
	'use strict';

	export class PrincipalController {

		public widget1: any;
		public widget2: any;
		public widget3: any;
		public widget4: any;
		public widget5: any;
		public widget6: any;
		public widget7: any;

		static $inject = ['app.gestao.meus-paineis.MeusPaineisService'];
		
		constructor(private meusPaineisService: MeusPaineisService) {
			this.widget1 = meusPaineisService.loadDashboardWidget('Widget1');
			this.widget2 = meusPaineisService.loadDashboardWidget('Widget2');
			this.widget3 = meusPaineisService.loadDashboardWidget('Widget3');
			this.widget4 = meusPaineisService.loadDashboardWidget('Widget4');
			this.widget5 = meusPaineisService.loadDashboardWidget('Widget5');
			this.widget6 = meusPaineisService.loadDashboardWidget('Widget6');
			this.widget7 = meusPaineisService.loadDashboardWidget('Widget7');
		}
	}

	angular
		.module('app.gestao.meus-paineis')
		.controller('app.gestao.meus-paineis.PrincipalController', PrincipalController);
}