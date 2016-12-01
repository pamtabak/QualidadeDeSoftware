(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('grau', {
            parent: 'entity',
            url: '/grau',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.grau.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grau/grau.html',
                    controller: 'GrauController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('grau');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })  
        .state('grau.edit', {
            parent: 'grau',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grau/grau-dialog.html',
                    controller: 'GrauDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('grau', null, { reload: 'grau' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grau-detail', {
            parent: 'entity',
            url: '/grau/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.grau.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grau/grau-detail.html',
                    controller: 'GrauDetailController',
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
        .state('grau-detail.edit', {
            parent: 'grau-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grau/grau-dialog.html',
                    controller: 'GrauDialogController',
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
