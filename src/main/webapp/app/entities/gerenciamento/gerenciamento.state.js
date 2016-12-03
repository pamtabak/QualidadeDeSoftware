(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gerenciamento', {
            parent: 'entity',
            url: '/gerenciamento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.gerenciamento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gerenciamento/gerenciamento.html',
                    controller: 'GerenciamentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gerenciamento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })     
        .state('gerenciamento.agree', {
            parent: 'gerenciamento',
            url: '/{id}/agree',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gerenciamento/gerenciamento-agree-dialog.html',
                    controller: 'GerenciamentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gerenciamento', null, { reload: 'gerenciamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })      
        .state('gerenciamento.delete', {
            parent: 'gerenciamento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gerenciamento/gerenciamento-delete-dialog.html',
                    controller: 'GerenciamentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gerenciamento', null, { reload: 'gerenciamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        }); 
    }

})();
