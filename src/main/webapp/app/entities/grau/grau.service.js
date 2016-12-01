(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Grau', Grau);

    Grau.$inject = ['$resource', 'DateUtils'];

    function Grau ($resource, DateUtils) {
        var resourceUrl =  'api/inscricaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.periodo = DateUtils.convertDateTimeFromServer(data.periodo);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
