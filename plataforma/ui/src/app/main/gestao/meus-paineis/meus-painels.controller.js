var app;
(function (app) {
    var gestao;
    (function (gestao) {
        var meusPaineis;
        (function (meusPaineis) {
            'use strict';
            var PaineisController = (function () {
                /** @ngInject **/
                PaineisController.$inject = ["$state"];
                function PaineisController($state) {
                    this.$state = $state;
                }
                PaineisController.prototype.foo = function () {
                    console.log("bar");
                };
                PaineisController.prototype.isTabActive = function (stateName) {
                    return this.$state.current.name === stateName;
                };
                return PaineisController;
            }());
            meusPaineis.PaineisController = PaineisController;
            angular
                .module('app.gestao.meus-paineis')
                .controller('app.gestao.meus-paineis.PaineisController', PaineisController);
        })(meusPaineis = gestao.meusPaineis || (gestao.meusPaineis = {}));
    })(gestao = app.gestao || (app.gestao = {}));
})(app || (app = {}));

//# sourceMappingURL=meus-painels.controller.js.map
