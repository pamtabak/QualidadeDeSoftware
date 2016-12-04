(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('InscricaoDialogController', InscricaoDialogController);

    InscricaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', 'Principal', '$uibModalInstance', 'entity', 'Inscricao', 'Aluno', 'Disciplina'];

    function InscricaoDialogController ($timeout, $scope, $stateParams, Principal, $uibModalInstance, entity, Inscricao, Aluno, Disciplina) {
        var vm = this;

        vm.inscricao = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;        
        Disciplina.query(function(result) {
            vm.disciplinas = result.filter(function(element){
                return (element.estado === "Ativa");
            });
        });        

        Principal.identity().then(r => {                        
            var authorities = r.authorities;                        
            Aluno.query(function(alunos){
                if( authorities.indexOf("ROLE_ALUNO") != -1) { 
                    var element = {};
                    for(var x in alunos){   
                        if(x != "$promise" && x!= "$resolved"){
                            if(alunos[x].login == r.login){
                                element[x] = alunos[x];
                            }
                        }                        
                    }      
                    element["$promise"] = alunos["$promise"];
                    element["$resolved"] = alunos["$resolved"];
                    vm.alunos = element;                              
                }                    
                else{
                    vm.alunos=  alunos;
                }
            });               
        });              

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
