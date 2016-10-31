(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('InscricaoDialogController', InscricaoDialogController);

    InscricaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Inscricao', 'Aluno', 'Disciplina'];

    function InscricaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Inscricao, Aluno, Disciplina) {
        var vm = this;

        vm.inscricao = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.alunos = Aluno.query();
        vm.disciplinas = Disciplina.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.inscricao.id !== null) {
                Inscricao.update(vm.inscricao, onSaveSuccess, onSaveError);
            } else {
                Inscricao.save(vm.inscricao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:inscricaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.periodo = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();