(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('GrauController', GrauController);

  GrauController.$inject = ['$scope', '$state','Principal',  'Grau', 'Disciplina'];

  function GrauController ($scope, $state, Principal,  Inscricao, Disciplina) {
    var vm = this;

    vm.inscricaos = [];
    vm.discplinas = [];
    Principal.identity().then(r => {
      var authorities = r.authorities;
      if(authorities.indexOf("ROLE_ADMIN") != -1 || authorities.indexOf('ROLE_SECRETARIA') != -1) {
        loadAll();
      } else {
        Inscricao.query(function(result) {
          vm.inscricaos = result.filter(function(element) {
            console.log(element);
            if (element.estado === "trancado") {return false;}
            if (element.disciplina.professor.login !== r.login) {return false;}
            if (element.disciplina.estado === "Inativa") {return false;}
            return true;
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
