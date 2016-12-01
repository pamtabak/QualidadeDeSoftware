(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('GrauDetailController', GrauDetailController);

    GrauDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Inscricao', 'Aluno', 'Disciplina'];

    function GrauDetailController($scope, $rootScope, $stateParams, previousState, entity, Inscricao, Aluno, Disciplina) {
        var vm = this;

        vm.inscricao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:inscricaoUpdate', function(event, result) {
            vm.inscricao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
