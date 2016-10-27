(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('SecretariaAcademicaDialogController', SecretariaAcademicaDialogController);

    SecretariaAcademicaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SecretariaAcademica'];

    function SecretariaAcademicaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SecretariaAcademica) {
        var vm = this;

        vm.secretariaAcademica = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.secretariaAcademica.id !== null) {
                SecretariaAcademica.update(vm.secretariaAcademica, onSaveSuccess, onSaveError);
            } else {
                SecretariaAcademica.save(vm.secretariaAcademica, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:secretariaAcademicaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
