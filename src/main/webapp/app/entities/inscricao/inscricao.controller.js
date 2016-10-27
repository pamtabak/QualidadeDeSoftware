(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('InscricaoController', InscricaoController);

    InscricaoController.$inject = ['$scope', '$state', 'Inscricao'];

    function InscricaoController ($scope, $state, Inscricao) {
        var vm = this;
        
        vm.inscricaos = [];

        loadAll();

        function loadAll() {
            Inscricao.query(function(result) {
                vm.inscricaos = result;
            });
        }
    }
})();
