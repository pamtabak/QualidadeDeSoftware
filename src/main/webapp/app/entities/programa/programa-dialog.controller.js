(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('ProgramaDialogController', ProgramaDialogController);

    ProgramaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Programa', 'Aluno'];

    function ProgramaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Programa, Aluno) {
        var vm = this;

        vm.programa = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alunos = Aluno.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.programa.id !== null) {
                Programa.update(vm.programa, onSaveSuccess, onSaveError);
            } else {
                Programa.save(vm.programa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:programaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
