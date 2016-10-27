(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('ProgramaDetailController', ProgramaDetailController);

    ProgramaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Programa', 'Aluno'];

    function ProgramaDetailController($scope, $rootScope, $stateParams, previousState, entity, Programa, Aluno) {
        var vm = this;

        vm.programa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:programaUpdate', function(event, result) {
            vm.programa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
