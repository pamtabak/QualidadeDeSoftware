(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('InscricaoDeleteController',InscricaoDeleteController);

    InscricaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Inscricao'];

    function InscricaoDeleteController($uibModalInstance, entity, Inscricao) {
        var vm = this;

        vm.inscricao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Inscricao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
