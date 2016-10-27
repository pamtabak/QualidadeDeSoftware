(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('ProgramaController', ProgramaController);

    ProgramaController.$inject = ['$scope', '$state', 'Programa'];

    function ProgramaController ($scope, $state, Programa) {
        var vm = this;
        
        vm.programas = [];

        loadAll();

        function loadAll() {
            Programa.query(function(result) {
                vm.programas = result;
            });
        }
    }
})();
