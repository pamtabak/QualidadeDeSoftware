(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('SecretariaAcademicaDeleteController',SecretariaAcademicaDeleteController);

    SecretariaAcademicaDeleteController.$inject = ['$uibModalInstance', 'entity', 'SecretariaAcademica'];

    function SecretariaAcademicaDeleteController($uibModalInstance, entity, SecretariaAcademica) {
        var vm = this;

        vm.secretariaAcademica = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SecretariaAcademica.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
