var app;
(function (app) {
    var novoProcesso;
    (function (novoProcesso) {
        var peticoesFisicas;
        (function (peticoesFisicas) {
            'use strict';
            var FormaRecebimento = (function () {
                function FormaRecebimento() {
                }
                FormaRecebimento.SEDEX = 'SEDEX';
                FormaRecebimento.BALCAO = 'BALCAO';
                FormaRecebimento.FAX = 'FAX';
                return FormaRecebimento;
            }());
            peticoesFisicas.FormaRecebimento = FormaRecebimento;
            var PeticaoFisicaService = (function () {
                /** @ngInject **/
                PeticaoFisicaService.$inject = ["$http"];
                function PeticaoFisicaService($http) {
                    this.$http = $http;
                }
                PeticaoFisicaService.prototype.registrar = function (peticao) {
                    return this.$http.post(PeticaoFisicaService.apiRemessa, peticao);
                };
                PeticaoFisicaService.apiRemessa = 'http://localhost:8091/api/remessas';
                return PeticaoFisicaService;
            }());
            peticoesFisicas.PeticaoFisicaService = PeticaoFisicaService;
            angular
                .module('app.novo-processo.peticoes-fisicas')
                .service('app.novo-processo.peticoes-fisicas.PeticaoFisicaService', PeticaoFisicaService);
        })(peticoesFisicas = novoProcesso.peticoesFisicas || (novoProcesso.peticoesFisicas = {}));
    })(novoProcesso = app.novoProcesso || (app.novoProcesso = {}));
})(app || (app = {}));

//# sourceMappingURL=peticao-fisica.service.js.map
