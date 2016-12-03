(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('GerenciamentoDeleteController',GerenciamentoDeleteController);

    GerenciamentoDeleteController.$inject = ['$uibModalInstance', '$scope', 'entity', 'Inscricao'];

    function GerenciamentoDeleteController($uibModalInstance, $scope, entity, Inscricao) {
        var vm = this;

        vm.inscricao = entity;
        vm.clear = clear;
        vm.confirmAgreement = confirmAgreement;
        vm.confirmUnagreement = confirmUnagreement;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmAgreement () {
            vm.inscricao.estado = "concordado";
            Inscricao.update(vm.inscricao, onSaveSuccess, onSaveError);            
        }

        function confirmUnagreement () {
            vm.inscricao.estado = "não concordado";
            Inscricao.update(vm.inscricao, onSaveSuccess, onSaveError);            
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:GerenciamentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
