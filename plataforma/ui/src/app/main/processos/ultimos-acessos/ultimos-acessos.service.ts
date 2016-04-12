module app.processos.ultimosAcessos {
    'use strict';
    import IQService = angular.IQService;
    import IPromise = angular.IPromise;
    import IDeferred = angular.IDeferred;

    export interface Acesso {
        name: string,
        type: {
            name: string,
            icon: {
                name: string,
                color: string
            }
        },
        last_update_by: string,
        size: number,
        updated_at: number
    }

    export class UltimosAcessosService {

        /** @ngInject **/
        constructor(private $q: IQService) { }

        public acessos(): IPromise<Acesso[]> {
            var deferred: IDeferred<Acesso[]> = this.$q.defer();
            deferred.resolve(UltimosAcessosService.mockAcessos());
            return deferred.promise;
        }

        private static mockAcessos(): Acesso[] {
            return [
                {
                    "name": "AP 470",
                    "type": {
                        "name": "Processo",
                        "icon": {
                            "name": "icon-folder",
                            "color": "#FFB300"
                        }
                    },
                    "last_update_by": "leandro.rezende",
                    "size": null,
                    "updated_at": 1448892351
                },
                {
                    "name": "ADI 100",
                    "type": {
                        "name": "Processo",
                        "icon": {
                            "name": "icon-folder",
                            "color": "#FFB300"
                        }
                    },
                    "last_update_by": "leandro.rezende",
                    "size": null,
                    "updated_at": 1422743429
                },
                {
                    "name": "AP 470 Mérito",
                    "type": {
                        "name": "Processo",
                        "icon": {
                            "name": "icon-folder",
                            "color": "#FFB300"
                        }
                    },
                    "last_update_by": "leandro.rezende",
                    "size": null,
                    "updated_at": 1442087478
                },
                {
                    "name": "AP 470 - Petição inicial",
                    "type": {
                        "name": "Peça",
                        "icon": {
                            "name": "icon-file-document",
                            "color": "#1565C0"
                        }
                    },
                    "last_update_by": "leandro.rezende",
                    "size": 729434384,
                    "updated_at": 1438505648
                },
                {
                    "name": "AP 470 - Prova 3",
                    "type": {
                        "name": "Peça",
                        "icon": {
                            "name": "icon-file-document",
                            "color": "#1565C0"
                        }
                    },
                    "last_update_by": "joao.silva",
                    "size": 751451839,
                    "updated_at": 1420360288
                },
                {
                    "name": "2345/2015",
                    "type": {
                        "name": "Petição",
                        "icon": {
                            "name": "icon-table-large",
                            "color": "#4CAF50"
                        }
                    },
                    "last_update_by": "leandro.rezende",
                    "size": 135204823,
                    "updated_at": 1448124630
                },
                {
                    "name": "AP 470 - Prova 4",
                    "type": {
                        "name": "Peça",
                        "icon": {
                            "name": "icon-file-document",
                            "color": "#1565C0"
                        }
                    },
                    "last_update_by": "leandro.rezende",
                    "size": 382447739,
                    "updated_at": 1435705066
                },
                {
                    "name": "AP 470 - Prova 5",
                    "type": {
                        "name": "Peça",
                        "icon": {
                            "name": "icon-file-document",
                            "color": "#1565C0"
                        }
                    },
                    "last_update_by": "leandro.rezende",
                    "size": 48694542,
                    "updated_at": 1434653523
                },
                {
                    "name": "234345/2015",
                    "type": {
                        "name": "Petição",
                        "icon": {
                            "name": "icon-table-large",
                            "color": "#4CAF50"
                        }
                    },
                    "last_update_by": "leandro.rezende",
                    "size": 62740446,
                    "updated_at": 1421683665
                },
            ];
        }
    }

    angular
        .module('app.processos.ultimos-acessos')
        .service('app.processos.ultimos-acessos.UltimosAcessosService', UltimosAcessosService);
}