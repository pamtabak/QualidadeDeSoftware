'use strict';

describe('Controller Tests', function() {

    describe('Inscricao Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInscricao, MockAluno, MockDisciplina;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInscricao = jasmine.createSpy('MockInscricao');
            MockAluno = jasmine.createSpy('MockAluno');
            MockDisciplina = jasmine.createSpy('MockDisciplina');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Inscricao': MockInscricao,
                'Aluno': MockAluno,
                'Disciplina': MockDisciplina
            };
            createController = function() {
                $injector.get('$controller')("InscricaoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterApp:inscricaoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
