(function () {
    'use strict';

    angular
        .module('app.core')
        .filter('readableFilesize', readableFilesize);

    function readableFilesize() {
        // Static
        var units = ['B', 'KB', 'MB', 'GB', 'TB', 'PB'];

        return function(bytes, precision) {
            if (isNaN(parseFloat(bytes)) || !isFinite(bytes)) {
                return '-';
            }

            if (typeof precision === 'undefined') {
                precision = 1;
            }

            var number = Math.floor(Math.log(bytes) / Math.log(1024));

            return (bytes / Math.pow(1024, Math.floor(number))).toFixed(precision) +  ' ' + units[number];
        };
    }

})();
