(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('aluno', {
            parent: 'entity',
            url: '/aluno',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.aluno.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aluno/alunos.html',
                    controller: 'AlunoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aluno');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('aluno-detail', {
            parent: 'entity',
            url: '/aluno/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.aluno.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aluno/aluno-detail.html',
                    controller: 'AlunoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aluno');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Aluno', function($stateParams, Aluno) {
                    return Aluno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'aluno',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('aluno-detail.edit', {
            parent: 'aluno-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/aluno-dialog.html',
                    controller: 'AlunoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aluno.new', {
            parent: 'aluno',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/aluno-dialog.html',
                    controller: 'AlunoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                documento: null,
                                matricula: null,
                                periodo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('aluno', null, { reload: 'aluno' });
                }, function() {
                    $state.go('aluno');
                });
            }]
        })
        .state('aluno.edit', {
            parent: 'aluno',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/aluno-dialog.html',
                    controller: 'AlunoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aluno', null, { reload: 'aluno' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aluno.delete', {
            parent: 'aluno',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/aluno-delete-dialog.html',
                    controller: 'AlunoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aluno', null, { reload: 'aluno' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
