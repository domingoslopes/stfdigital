(function () {
    'use strict';
    var app = angular.module('app.tarefas.painel-de-fases');
    app.classy.controller({
        name: 'ColorMenuController',
        inject: ['$mdColorPalette', 'BoardService'],
        init: function () {
            this.board = this.BoardService.data;
            this.palettes = this.$mdColorPalette;
        }
    });
})();

//# sourceMappingURL=color-menu.controller.js.map
