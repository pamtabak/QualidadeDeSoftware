(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('AlunoDialogController', AlunoDialogController);

    AlunoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Aluno', 'Programa', 'Inscricao', 'Professor'];

    function AlunoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Aluno, Programa, Inscricao, Professor) {
        var vm = this;

        vm.aluno = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.programas = Programa.query();
        vm.inscricaos = Inscricao.query();
        vm.professors = Professor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.aluno.id !== null) {
                Aluno.update(vm.aluno, onSaveSuccess, onSaveError);
            } else {
                Aluno.save(vm.aluno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:alunoUpdate', result);
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
