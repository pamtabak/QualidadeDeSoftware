(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('SecretariaAcademicaController', SecretariaAcademicaController);

    SecretariaAcademicaController.$inject = ['$scope', '$state', 'SecretariaAcademica'];

    function SecretariaAcademicaController ($scope, $state, SecretariaAcademica) {
        var vm = this;
        
        vm.secretariaAcademicas = [];

        loadAll();

        function loadAll() {
            SecretariaAcademica.query(function(result) {
                vm.secretariaAcademicas = result;
            });
        }
    }
})();
