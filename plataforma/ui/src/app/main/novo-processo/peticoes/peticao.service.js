var app;
(function (app) {
    var novoProcesso;
    (function (novoProcesso) {
        var peticoes;
        (function (peticoes) {
            'use strict';
            var PeticaoService = (function () {
                /** @ngInject **/
                PeticaoService.$inject = ["$http"];
                function PeticaoService($http) {
                    this.$http = $http;
                }
                PeticaoService.prototype.peticionar = function (peticao) {
                    return this.$http.post(PeticaoService.apiPeticionamento, peticao);
                };
                PeticaoService.apiPeticionamento = 'http://localhost:8090/api/peticoes';
                return PeticaoService;
            }());
            peticoes.PeticaoService = PeticaoService;
            angular
                .module('app.novo-processo.peticoes')
                .service('app.novo-processo.peticoes.PeticaoService', PeticaoService);
        })(peticoes = novoProcesso.peticoes || (novoProcesso.peticoes = {}));
    })(novoProcesso = app.novoProcesso || (app.novoProcesso = {}));
})(app || (app = {}));

//# sourceMappingURL=peticao.service.js.map
