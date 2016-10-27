(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('inscricao', {
            parent: 'entity',
            url: '/inscricao',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.inscricao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inscricao/inscricaos.html',
                    controller: 'InscricaoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('inscricao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('inscricao-detail', {
            parent: 'entity',
            url: '/inscricao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.inscricao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inscricao/inscricao-detail.html',
                    controller: 'InscricaoDetailController',
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
        .state('inscricao-detail.edit', {
            parent: 'inscricao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inscricao/inscricao-dialog.html',
                    controller: 'InscricaoDialogController',
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
        .state('inscricao.new', {
            parent: 'inscricao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inscricao/inscricao-dialog.html',
                    controller: 'InscricaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                periodo: null,
                                grau: null,
                                frequencia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('inscricao', null, { reload: 'inscricao' });
                }, function() {
                    $state.go('inscricao');
                });
            }]
        })
        .state('inscricao.edit', {
            parent: 'inscricao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inscricao/inscricao-dialog.html',
                    controller: 'InscricaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inscricao', null, { reload: 'inscricao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inscricao.delete', {
            parent: 'inscricao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inscricao/inscricao-delete-dialog.html',
                    controller: 'InscricaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inscricao', null, { reload: 'inscricao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
