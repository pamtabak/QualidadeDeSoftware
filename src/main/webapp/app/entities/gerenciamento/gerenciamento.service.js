(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Gerenciamento', Gerenciamento);

    Gerenciamento.$inject = ['$resource', 'DateUtils'];

    function Gerenciamento ($resource, DateUtils) {
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
