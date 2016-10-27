(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('programa', {
            parent: 'entity',
            url: '/programa',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.programa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/programa/programas.html',
                    controller: 'ProgramaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('programa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('programa-detail', {
            parent: 'entity',
            url: '/programa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.programa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/programa/programa-detail.html',
                    controller: 'ProgramaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('programa');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Programa', function($stateParams, Programa) {
                    return Programa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'programa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('programa-detail.edit', {
            parent: 'programa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programa/programa-dialog.html',
                    controller: 'ProgramaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Programa', function(Programa) {
                            return Programa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('programa.new', {
            parent: 'programa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programa/programa-dialog.html',
                    controller: 'ProgramaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                codigo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('programa', null, { reload: 'programa' });
                }, function() {
                    $state.go('programa');
                });
            }]
        })
        .state('programa.edit', {
            parent: 'programa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programa/programa-dialog.html',
                    controller: 'ProgramaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Programa', function(Programa) {
                            return Programa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('programa', null, { reload: 'programa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('programa.delete', {
            parent: 'programa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programa/programa-delete-dialog.html',
                    controller: 'ProgramaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Programa', function(Programa) {
                            return Programa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('programa', null, { reload: 'programa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
