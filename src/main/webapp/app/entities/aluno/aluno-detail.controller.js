(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('AlunoDetailController', AlunoDetailController);

    AlunoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Aluno', 'Programa', 'Inscricao', 'Professor'];

    function AlunoDetailController($scope, $rootScope, $stateParams, previousState, entity, Aluno, Programa, Inscricao, Professor) {
        var vm = this;

        vm.aluno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:alunoUpdate', function(event, result) {
            vm.aluno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
