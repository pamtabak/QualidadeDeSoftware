(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Aluno', Aluno);

    Aluno.$inject = ['$resource', 'DateUtils'];

    function Aluno ($resource, DateUtils) {
        var resourceUrl =  'api/alunos/:id';

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
