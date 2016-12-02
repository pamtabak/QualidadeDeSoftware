(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trancamento', {
            parent: 'entity',
            url: '/trancamento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.trancamento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trancamento/trancamento.html',
                    controller: 'TrancamentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trancamento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })  
        .state('trancamento.edit', {
            parent: 'trancamento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trancamento/trancamento-delete-dialog.html',
                    controller: 'TrancamentoDeleteController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trancamento', null, { reload: 'trancamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trancamento.delete', {
            parent: 'trancamento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trancamento/trancamento-delete-dialog.html',
                    controller: 'TrancamentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trancamento', null, { reload: 'trancamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        }); 
    }

})();
