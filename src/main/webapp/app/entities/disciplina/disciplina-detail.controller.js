(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('DisciplinaDetailController', DisciplinaDetailController);

    DisciplinaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Disciplina', 'Professor', 'Inscricao'];

    function DisciplinaDetailController($scope, $rootScope, $stateParams, previousState, entity, Disciplina, Professor, Inscricao) {
        var vm = this;

        vm.disciplina = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:disciplinaUpdate', function(event, result) {
            vm.disciplina = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
