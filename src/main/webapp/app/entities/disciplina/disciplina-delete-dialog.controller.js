(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('DisciplinaDeleteController',DisciplinaDeleteController);

    DisciplinaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Disciplina'];

    function DisciplinaDeleteController($uibModalInstance, entity, Disciplina) {
        var vm = this;

        vm.disciplina = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Disciplina.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
