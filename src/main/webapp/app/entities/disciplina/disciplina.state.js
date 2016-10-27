(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('disciplina', {
            parent: 'entity',
            url: '/disciplina',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.disciplina.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/disciplina/disciplinas.html',
                    controller: 'DisciplinaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('disciplina');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('disciplina-detail', {
            parent: 'entity',
            url: '/disciplina/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.disciplina.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/disciplina/disciplina-detail.html',
                    controller: 'DisciplinaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('disciplina');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Disciplina', function($stateParams, Disciplina) {
                    return Disciplina.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'disciplina',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('disciplina-detail.edit', {
            parent: 'disciplina-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disciplina/disciplina-dialog.html',
                    controller: 'DisciplinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Disciplina', function(Disciplina) {
                            return Disciplina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('disciplina.new', {
            parent: 'disciplina',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disciplina/disciplina-dialog.html',
                    controller: 'DisciplinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numeroDeVagas: null,
                                horario: null,
                                sala: null,
                                codigo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('disciplina', null, { reload: 'disciplina' });
                }, function() {
                    $state.go('disciplina');
                });
            }]
        })
        .state('disciplina.edit', {
            parent: 'disciplina',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disciplina/disciplina-dialog.html',
                    controller: 'DisciplinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Disciplina', function(Disciplina) {
                            return Disciplina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('disciplina', null, { reload: 'disciplina' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('disciplina.delete', {
            parent: 'disciplina',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disciplina/disciplina-delete-dialog.html',
                    controller: 'DisciplinaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Disciplina', function(Disciplina) {
                            return Disciplina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('disciplina', null, { reload: 'disciplina' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
