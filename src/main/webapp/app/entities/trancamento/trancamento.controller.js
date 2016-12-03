(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('TrancamentoController', TrancamentoController);

  TrancamentoController.$inject = ['$scope', '$state','Principal',  'Trancamento', 'Disciplina'];

  function TrancamentoController ($scope, $state, Principal,  Inscricao, Disciplina) {
    var vm = this;

    vm.inscricaos = [];

    Principal.identity().then(r => {
      var authorities = r.authorities;
      if(authorities.indexOf("ROLE_ADMIN") != -1 || authorities.indexOf('ROLE_SECRETARIA') != -1) {
        loadAll();
      } else if(authorities.indexOf('ROLE_ALUNO')) {
        Inscricao.query(function(result) {
          vm.inscricaos = result.filter(function(element) {
            return element.aluno.login === r.login && element.estado === 'inscrito';
          });
        });
      }
    });

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
