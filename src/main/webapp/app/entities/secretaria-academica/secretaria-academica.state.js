(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('secretaria-academica', {
            parent: 'entity',
            url: '/secretaria-academica',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.secretariaAcademica.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/secretaria-academica/secretaria-academicas.html',
                    controller: 'SecretariaAcademicaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('secretariaAcademica');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('secretaria-academica-detail', {
            parent: 'entity',
            url: '/secretaria-academica/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.secretariaAcademica.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/secretaria-academica/secretaria-academica-detail.html',
                    controller: 'SecretariaAcademicaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('secretariaAcademica');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SecretariaAcademica', function($stateParams, SecretariaAcademica) {
                    return SecretariaAcademica.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'secretaria-academica',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('secretaria-academica-detail.edit', {
            parent: 'secretaria-academica-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secretaria-academica/secretaria-academica-dialog.html',
                    controller: 'SecretariaAcademicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SecretariaAcademica', function(SecretariaAcademica) {
                            return SecretariaAcademica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('secretaria-academica.new', {
            parent: 'secretaria-academica',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secretaria-academica/secretaria-academica-dialog.html',
                    controller: 'SecretariaAcademicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('secretaria-academica', null, { reload: 'secretaria-academica' });
                }, function() {
                    $state.go('secretaria-academica');
                });
            }]
        })
        .state('secretaria-academica.edit', {
            parent: 'secretaria-academica',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secretaria-academica/secretaria-academica-dialog.html',
                    controller: 'SecretariaAcademicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SecretariaAcademica', function(SecretariaAcademica) {
                            return SecretariaAcademica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('secretaria-academica', null, { reload: 'secretaria-academica' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('secretaria-academica.delete', {
            parent: 'secretaria-academica',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secretaria-academica/secretaria-academica-delete-dialog.html',
                    controller: 'SecretariaAcademicaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SecretariaAcademica', function(SecretariaAcademica) {
                            return SecretariaAcademica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('secretaria-academica', null, { reload: 'secretaria-academica' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
