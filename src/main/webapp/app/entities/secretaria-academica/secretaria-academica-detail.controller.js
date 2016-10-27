(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('SecretariaAcademicaDetailController', SecretariaAcademicaDetailController);

    SecretariaAcademicaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SecretariaAcademica'];

    function SecretariaAcademicaDetailController($scope, $rootScope, $stateParams, previousState, entity, SecretariaAcademica) {
        var vm = this;

        vm.secretariaAcademica = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:secretariaAcademicaUpdate', function(event, result) {
            vm.secretariaAcademica = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
