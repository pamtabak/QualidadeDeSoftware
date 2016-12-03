(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('GerenciamentoController', GerenciamentoController);

  GerenciamentoController.$inject = ['$scope', '$state','Principal',  'Gerenciamento', 'Disciplina'];

  function GerenciamentoController ($scope, $state, Principal,  Inscricao, Disciplina) {
    var vm = this;

    vm.inscricaos = [];
    vm.agreement = false;

    Principal.identity().then(r => {
      var authorities = r.authorities;
      if(authorities.indexOf("ROLE_ADMIN") != -1 || authorities.indexOf('ROLE_SECRETARIA') != -1) {
        loadAll();
      } else if(authorities.indexOf('ROLE_PROFESSOR') != -1 ) {
        Inscricao.query(function(result) {
          vm.inscricaos = result.filter(function(element) {
            if(element.aluno.professor) {
              return element.aluno.professor.login === r.login && 
              element.estado != "trancado" && element.estado != 'inscrito' ;
            } else {
              return ;
            }
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
