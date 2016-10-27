(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('DisciplinaDialogController', DisciplinaDialogController);

    DisciplinaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Disciplina', 'Professor', 'Inscricao'];

    function DisciplinaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Disciplina, Professor, Inscricao) {
        var vm = this;

        vm.disciplina = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.professors = Professor.query();
        vm.inscricaos = Inscricao.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.disciplina.id !== null) {
                Disciplina.update(vm.disciplina, onSaveSuccess, onSaveError);
            } else {
                Disciplina.save(vm.disciplina, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:disciplinaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.horario = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
