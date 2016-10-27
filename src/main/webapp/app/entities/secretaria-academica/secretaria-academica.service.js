(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('SecretariaAcademica', SecretariaAcademica);

    SecretariaAcademica.$inject = ['$resource'];

    function SecretariaAcademica ($resource) {
        var resourceUrl =  'api/secretaria-academicas/:id';

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
