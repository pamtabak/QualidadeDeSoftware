(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('InscricaoController', InscricaoController);

  InscricaoController.$inject = ['$scope', '$state', 'Principal', 'Inscricao'];

  function InscricaoController ($scope, $state, Principal, Inscricao) {
    var vm = this;

    vm.inscricaos = [];

    Principal.identity().then(r => {
      var authorities = r.authorities;
      if(authorities.indexOf("ROLE_ADMIN") != -1 || authorities.indexOf('ROLE_SECRETARIA') != -1) {
        loadAll();
      } else if(authorities.indexOf('ROLE_ALUNO')) {
        Inscricao.query(function(result) {
          vm.inscricaos = result.filter(function(element) {
            if (element.aluno.login !== r.login) {return false;}
            return true;
          });
        });
      }
    });


    function loadAll() {
      Inscricao.query(function(result) {
        vm.inscricaos = result;
      });
    }
  }
})();
