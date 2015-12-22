(function () {
    'use strict';

    angular.module('app.toolbar').classy.controller({
        name: 'ToolbarController',

        inject: ['$rootScope', '$mdSidenav', '$mdToast', '$mdMedia', 'msNavigationService'],

        init: function() {
            this.bodyEl = angular.element('body');
            
            this.$rootScope.global = {
                search: ''
            };

            this.userStatusOptions = [
                {
                    'title': 'Online',
                    'icon' : 'icon-checkbox-marked-circle',
                    'color': '#4CAF50'
                },
                {
                    'title': 'Away',
                    'icon' : 'icon-clock',
                    'color': '#FFC107'
                },
                {
                    'title': 'Do not Disturb',
                    'icon' : 'icon-minus-circle',
                    'color': '#F44336'
                },
                {
                    'title': 'Invisible',
                    'icon' : 'icon-checkbox-blank-circle-outline',
                    'color': '#BDBDBD'
                },
                {
                    'title': 'Offline',
                    'icon' : 'icon-checkbox-blank-circle-outline',
                    'color': '#616161'
                }
            ];

            this.userStatus = this.userStatusOptions[0];
        },

        methods: {
            /**
             * 
             */
            toggleNavigation: function () { 
                if (this.$mdMedia('gt-sm')) {
                    this.msNavigationService.toggleFolded();
                } else {
                    this.toggleSidenav('navigation');
                }
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
