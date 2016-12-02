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
                    templateUrl: 'app/entities/trancamento/trancamento-dialog.html',
                    controller: 'TrancamentoDialogController',
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
        .state('trancamento-detail', {
            parent: 'entity',
            url: '/trancamento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.trancamento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trancamento/trancamento-detail.html',
                    controller: 'TrancamentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('inscricao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Inscricao', function($stateParams, Inscricao) {
                    return Inscricao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'inscricao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trancamento-detail.edit', {
            parent: 'trancamento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trancamento/trancamento-dialog.html',
                    controller: 'TrancamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })    
    }

})();
