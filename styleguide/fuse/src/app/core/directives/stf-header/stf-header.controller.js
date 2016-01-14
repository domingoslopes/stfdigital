(function() {

	var app = angular.module('app.core');

	app.classy.controller({
		name: 'StfHeaderController',

		inject: ['$scope', 'msNavigationService'],

		init: function() {
			this.$scope.info = {};
			this.$scope.info.breadcrumbs = this.generateBreadcrumbs();
			this.$scope.info.title = this.$scope.info.breadcrumbs.current.label;
		},

		methods: {
			generateBreadcrumbs: function() {
				var path = this._getBreadcrumbPath();
				return this._getBreadcrumbFromPath(path);
            },

            _getBreadcrumbPath: function() {
            	var activeItem = this.msNavigationService.getActiveItem().node;
				var navigation = this.msNavigationService.getNavigationObject();
				
				var path = activeItem._path.split('.');
				
				var completePath = [];

				while (completePath.length < path.length) {
					var found = false;

					for (var i = 0; i < navigation.length; i++) {
						if (navigation[i]._id == path[completePath.length]) {
							completePath.push(navigation[i]);
							navigation = navigation[i].children;
							found = true;
							break;
						}
					}
				}

				return completePath;
            },

            _getBreadcrumbFromPath: function(path) {
            	var breadcrumbs = {
            		parents: [],
            		current: null
            	};

            	for (var i = 0; i < path.length; i++) {
            		var parsedPathPart = this._parsePathPart(path[i]);

            		if (i+1 == path.length) {
            			breadcrumbs.current = parsedPathPart;
            		} else {
						breadcrumbs.parents.push(parsedPathPart);
            		}
            	}

            	return breadcrumbs;
            },

            _parsePathPart: function(part) {
            	return {
            		label: part.translation,
            		link: part.uisref 
            	}
            }
		}
	});

})();