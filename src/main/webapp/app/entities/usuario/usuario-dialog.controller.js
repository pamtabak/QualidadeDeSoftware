(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('UsuarioDialogController', UsuarioDialogController);

    UsuarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Usuario'];

    function UsuarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Usuario) {
        var vm = this;

        vm.usuario = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        $scope.submitForm = function(isValid){
            if(isValid){
                save();         
            }
        }

        function save () {
            vm.isSaving = true;
            if (vm.usuario.id !== null) {
                Usuario.update(vm.usuario, onSaveSuccess, onSaveError);
            } else {
                Usuario.save(vm.usuario, onSaveSuccess, onSaveError);
            }
        }



        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:usuarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
