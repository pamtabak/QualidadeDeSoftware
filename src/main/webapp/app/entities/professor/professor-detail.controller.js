(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('ProfessorDetailController', ProfessorDetailController);

    ProfessorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Professor', 'Aluno', 'Disciplina'];

    function ProfessorDetailController($scope, $rootScope, $stateParams, previousState, entity, Professor, Aluno, Disciplina) {
        var vm = this;

        vm.professor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:professorUpdate', function(event, result) {
            vm.professor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
