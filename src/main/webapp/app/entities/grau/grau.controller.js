(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('GrauController', GrauController);

    GrauController.$inject = ['$scope', '$state', 'Grau'];

    function GrauController ($scope, $state, Inscricao, Disciplina) {
        var vm = this;
        
        vm.inscricaos = [];
        vm.discplinas = [];

        console.log($scope,'-----',$state)

        loadAll();
    
        function loadAll() {
            Inscricao.query(function(result) {
                vm.inscricaos = result;
            });
            Disciplina.query(function(result) {
                vm.disciplinas = result;
            });
        }
    }
})();
