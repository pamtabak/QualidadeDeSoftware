(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('AlunoDeleteController',AlunoDeleteController);

    AlunoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Aluno'];

    function AlunoDeleteController($uibModalInstance, entity, Aluno) {
        var vm = this;

        vm.aluno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Aluno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
