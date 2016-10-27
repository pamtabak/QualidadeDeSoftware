'use strict';

describe('Controller Tests', function() {

    describe('Aluno Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAluno, MockPrograma, MockInscricao, MockProfessor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAluno = jasmine.createSpy('MockAluno');
            MockPrograma = jasmine.createSpy('MockPrograma');
            MockInscricao = jasmine.createSpy('MockInscricao');
            MockProfessor = jasmine.createSpy('MockProfessor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Aluno': MockAluno,
                'Programa': MockPrograma,
                'Inscricao': MockInscricao,
                'Professor': MockProfessor
            };
            createController = function() {
                $injector.get('$controller')("AlunoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterApp:alunoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
