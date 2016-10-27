(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('ProgramaDeleteController',ProgramaDeleteController);

    ProgramaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Programa'];

    function ProgramaDeleteController($uibModalInstance, entity, Programa) {
        var vm = this;

        vm.programa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Programa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
