module app.novoProcesso.peticoes {
    'use strict';
    import IHttpService = angular.IHttpService;
    import IPromise = angular.IPromise;

    export interface IAnexo {
        documento: number,
        tipo: number
    }

    export interface IPeticao {
        classeId: string,
        poloAtivo: string[],
        poloPassivo: string[],
        anexos: IAnexo[]
    }
    
    export class PeticaoService {

        private static apiPeticionamento: string = '/peticionamento/api/peticoes';

        /** @ngInject **/
        constructor(private $http: IHttpService, private properties) { }

        public peticionar(peticao: IPeticao): IPromise<any> {
            return this.$http.post(this.properties.url + ":" + this.properties.port + PeticaoService.apiPeticionamento, peticao);
        }
    }

    angular
        .module('app.novo-processo.peticoes')
        .service('app.novo-processo.peticoes.PeticaoService', PeticaoService);

}