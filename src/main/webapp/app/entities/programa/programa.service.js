(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Programa', Programa);

    Programa.$inject = ['$resource'];

    function Programa ($resource) {
        var resourceUrl =  'api/programas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
