module app.novoProcesso.peticoesFisicas {
    'use strict';
    import IHttpService = angular.IHttpService;
    import IPromise = angular.IPromise;

    export class FormaRecebimento {
        public static SEDEX = 'SEDEX';
        public static BALCAO = 'BALCAO';
        public static FAX = 'FAX';
    }

    export interface PeticaoFisica {
        formaRecebimento: FormaRecebimento,
        volumes: number,
        apensos: number,
        numeroSedex: string,
        tipoProcesso: string
    }
    
    export class PeticaoFisicaService {

        private static apiRemessa: string = '/recebimento/api/remessas';

        /** @ngInject **/
        constructor(private $http: IHttpService, private properties) { }

        public registrar(peticao: PeticaoFisica): IPromise<any> {
            return this.$http.post(this.properties.url + ":" + this.properties.port + PeticaoFisicaService.apiRemessa, peticao);
        }
    }

    angular
        .module('app.novo-processo.peticoes-fisicas')
        .service('app.novo-processo.peticoes-fisicas.PeticaoFisicaService', PeticaoFisicaService);

}