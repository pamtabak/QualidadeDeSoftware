(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('TrancamentoDeleteController',TrancamentoDeleteController);

    TrancamentoDeleteController.$inject = ['$uibModalInstance', '$scope', 'entity', 'Inscricao'];

    function TrancamentoDeleteController($uibModalInstance, $scope, entity, Inscricao) {
        var vm = this;

        vm.inscricao = entity;
        vm.clear = clear;
        vm.confirmUnsubscribe = confirmUnsubscribe;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmUnsubscribe () {
            vm.inscricao.estado = "trancado";
            Inscricao.update(vm.inscricao, onSaveSuccess, onSaveError);            
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:trancamentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
