(function () {
    'use strict';

    angular.module('app.toolbar').classy.controller({
        name: 'ToolbarController',

        inject: ['$scope', '$rootScope', '$mdSidenav', '$mdToast', '$mdMedia', 'msNavigationService', '$state'],

        init: function() {
            this.bodyEl = angular.element('body');
            
            this.$scope.$watch(function() {
                return this.$mdMedia('gt-sm');
            }.bind(this), this.updateNavigation);

            // TODO: Isso deve virar um enum em alguma factory para indicar o tipo de pesquisa
            // 1 = Processo, 2 = Petição
            this.tipoBusca = 1;
        },

        methods: {
            toggleNavigation: function () { 
                if (this.$mdMedia('gt-sm')) {
                    this.msNavigationService.toggleFolded();
                } else {
                    this.toggleSidenav('navigation');
                }

                this.updateNavigation();
            },

            updateNavigation: function() {
                if (this.$mdMedia('gt-sm')) {
                    this.folded = this.msNavigationService.getFolded();
                } else {
                    this.folded = this.$mdSidenav('navigation').isOpen();
                }
            },

            alterarBusca: function(tipo) {
                this.tipoBusca = tipo;
            },

            teclaPressionadaBusca: function(evento) {
                // Só nos interessa a tecla enter
                if (evento.keyCode !== 13) {
                    return;
                }

                console.log('TODO: Buscar por um' + (this.tipoBusca == 1 ? ' processo' : 'a petição') + ' com o termo: ' + this.termoBusca);
            },

            /**
             * Toggle sidenav
             *
             * @param sidenavId
             */
            toggleSidenav: function (sidenavId) {
                this.$mdSidenav(sidenavId).toggle();
            },

            /**
             * Sets User Status
             * @param status
             */
            setUserStatus: function (status) {
                this.userStatus = status;
            },

            /**
             * Logout Function
             */
            logout: function () {
                this.$state.go('app.nao-autenticado.login');
                console.log("TODO: Fazer logout do usuário");
            },

            /**
             * Toggle horizontal mobile menu
             */
            toggleHorizontalMobileMenu: function () {
                this.bodyEl.toggleClass('ms-navigation-horizontal-mobile-menu-active');
            }
        }
    });    
})();
