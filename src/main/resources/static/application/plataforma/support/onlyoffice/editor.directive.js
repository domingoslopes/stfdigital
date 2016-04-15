/**
 * @author Tomas.Godoi
 */
(function() {
	'use strict';
	
	angular.plataforma.directive('editor', function() {
		return {
			restrict: 'EA',
			scope: {
				api: "=?",
				documento: "=editor",
				edicaoConcluida: "&",
				edicaoTimeout: "&",
				aguardarConclusao: "@"
			},
			templateUrl: 'application/plataforma/support/onlyoffice/editor.tpl.html',
			link: function(scope, element, attrs) {
				
			},
			controller: function($scope, $q, $interval, OnlyofficeService, SecurityService) {
				$scope.showEditor = true;
				$scope.showProgress = false;
				$scope.edicaoIniciada = false;
				
				var verificarEdicaoIniciada = function() {
					var tentativas = 0;
					var deferred = $q.defer();
					var verificarEdicao = function() {
						var tratarNaoAtivo = function() {
							tentativas++;
							if (tentativas < 20) {
								$interval(verificarEdicao, 1000, 1);
							} else {
								deferred.reject();
							}
						};
						OnlyofficeService.recuperarNumeroEdicao($scope.documento.id).then(function(edicao) {
							console.log(edicao);
							if (edicao.ativo) {
								deferred.resolve();
							} else {
								tratarNaoAtivo();
							}
						}, function(edicao) {
							console.log(edicao);
							tratarNaoAtivo();
						});
					};
					verificarEdicao();
					return deferred.promise;
				};
				
				var verificarEdicaoCompleta = function() {
					var tentativas = 0;
					var deferred = $q.defer();
					var verificarEdicao = function() {
						OnlyofficeService.recuperarNumeroEdicao($scope.documento.id).then(function(edicao) {
							console.log(edicao);
							tentativas++;
							if (tentativas < 20) {
								$interval(verificarEdicao, 1000, 1);
							} else {
								deferred.reject();
							}
						}, function(edicao) {
							console.log(edicao);
							deferred.resolve();
						});
					};
					verificarEdicao();
					return deferred.promise;
				};
				
				$scope.api = {
					salvar: function() {
						if ($scope.aguardarConclusao == "true") {
							$scope.showEditor = false;
							$scope.showProgress = true;
							verificarEdicaoCompleta().then(function() {
								$scope.edicaoConcluida();
							}, function() {
								$scope.edicaoTimeout();
								$scope.showEditor = true;
								$scope.showProgress = false;
							});
						} else if ($scope.aguardarConclusao == "false") {
							$scope.edicaoConcluida();
						}
					}
				};
				
				var iniciarEditor = function(numeroEdicao) {
					$q.all([OnlyofficeService.criarUrlConteudoDocumento($scope.documento.id),
						OnlyofficeService.recuperarUrlCallback($scope.documento.id)])
						.then(function(urls) {
							$scope.config = {
								editorConfig : {
									lang: 'pt-BR',
									customization: {
										about: true,
										chat: true
									},
									user: {
										id: SecurityService.user().login,
										name: SecurityService.user().nome
									},
								},
								document: {
									src: urls[0],
									key: numeroEdicao,
									name: $scope.documento.nome,
									callbackUrl: urls[1]
								}
							};
							verificarEdicaoIniciada().then(function() {
								$scope.edicaoIniciada = true;
							});
						});
				};
				OnlyofficeService.gerarNumeroEdicao($scope.documento.id).then(function(edicao) {
					iniciarEditor(edicao.numero);
				});
				$scope.save = function(url, close) {
					console.log('Salvando...');
					console.log(url);
					console.log(close);
				};
			}
		};
	});
})();