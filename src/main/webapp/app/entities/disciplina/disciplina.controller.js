(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('DisciplinaController', DisciplinaController);

    DisciplinaController.$inject = ['$scope', '$state', 'Disciplina'];

    function DisciplinaController ($scope, $state, Disciplina) {
        var vm = this;
        
        vm.disciplinas = [];

        loadAll();

        function loadAll() {
            Disciplina.query(function(result) {
                vm.disciplinas = result;
            });
        }
    }
})();
