(function() {
    'use strict';

    var app = angular.module('app.processos.ultimos-acessos');

    app.classy.controller({
        name: 'ProcessosUltimosAcessosController',

        inject: ['$state', '$filter'],

        init: function() {
            // MOCK
            this.ultimosAcessosDtOptions = {
                dom       : '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                pagingType: 'simple',
                autoWidth : true,
                responsive: false,
                searching: false,
                columns: [
                    null,
                    null,
                    null,
                    null,
                    {iDataSort: 5},
                    {bVisible: false},
                    null,
                    null
                ]
            };

            this.ultimosAcessosOrig = [
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

            this.ultimosAcessos = angular.copy(this.ultimosAcessosOrig);


        },

        methods: {
            foo: function() {
                console.log("bar");
            },

            setActiveFilter: function(index) {
                var newActiveFilter = (index !== this.activeFilter);
                this.activeFilter = index;

                if (newActiveFilter) {
                    this.updateFilterObject();
                }
            },

            updateFilterObject: function() {
                switch (this.activeFilter) {
                    case 0:
                        this.filterObject = {};
                        break;
                    case 1:
                        this.filterObject = {type: {name: "Petição"}};
                        break;
                    case 2:
                        this.filterObject = {type: {name: "Processo"}};
                        break;
                    case 3:
                        this.filterObject = {type: {name: "Peça"}};
                        break;
                }

                this.ultimosAcessos = this.$filter('filter')(this.ultimosAcessosOrig, this.filterObject);
            },

            isTabActive: function(stateName, index) {
                var active = this.$state.current.name == stateName;

                if (active) {
                    this.setActiveFilter(index);
                }

                return active;
            }
        }
    });
})();
