(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Disciplina', Disciplina);

    Disciplina.$inject = ['$resource', 'DateUtils'];

    function Disciplina ($resource, DateUtils) {
        var resourceUrl =  'api/disciplinas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.horario = DateUtils.convertDateTimeFromServer(data.horario);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
