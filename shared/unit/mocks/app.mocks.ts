namespace app.core {
    "use strict";

    angular
            .module("app.core", [
                "ngCookies",
                "pascalprecht.translate",
                "ui.router"
            ]);
}

/**
 * Mocks dos módulos e alguns componentes para os testes unitários.
 * 
 */
namespace app.novoProcesso {
    "use strict";

    function config() {

    }

    angular
            .module("app.novo-processo", ["ui.router", "pascalprecht.translate"])
            .config(config);
}

namespace app.documentos {
    "use strict";

    function config() {

    }

    angular
            .module("app.documentos", [])
            .config(config);
}

namespace app.support {
    "use strict";

    function config() { }

    let url = "docker";
    let port = "8765";

    angular.module("app.support.constants", []).constant("properties", {
        "url": url,
        "port": port,
        "apiUrl": url + ":" + port,
        "development": true
    });

    angular
            .module("app.support.command", [])
            .config(config);

    class CommandServiceMock {
        public addValidator() {}
    }

    angular
            .module("app.support.command")
            .service("commandService", CommandServiceMock);

    class MessagesService {
        public error(message: string): void {

        }

        public success(message: string): void {

        }
    }

    angular
            .module("app.support.messaging", [])
            .service("messagesService", MessagesService)
            .config(config);

    angular
            .module("app.certification", []);

    angular
            .module("checklist-model", []);

    angular
            .module("app.support", ["app.support.command", "app.support.messaging", "app.support.constants"])
            .config(config);
    
    class ErrorHandlerServiceProvider {
        
        public decorate(): void {
            
        }
        
        public $get() {
            return {};
        }
        
    }
    
    angular
    .module('app.support')
    .provider('errorHandler', ErrorHandlerServiceProvider);


    /** @ngInject */
    function httpPromiseTransformationDecorator($delegate, $injector) {
        function decorate($injector, obj, func) {
            return angular.extend(function() {
                let httpPromiseTransformation: HttpPromiseTransformationService = $injector.get('httpPromiseTransformation');
                return httpPromiseTransformation.call(func, obj, arguments);
            }, func);
        }

        for (let prop in $delegate) {
            if (angular.isFunction($delegate[prop])) {
                $delegate[prop] = decorate($injector, $delegate, $delegate[prop]);
            }
        }
        return $delegate;
    }

    export class HttpPromiseTransformationService {

        /** @ngInject */
        constructor(private $q: ng.IQService) {

        }

        public call(func: Function, self, args) {
            let result = func.apply(self, args);
            
            if (this.isPromise(result)) {
                return result.then((response) => {
                    return response.data;
                }, (response) => {
                    return this.$q.reject(response.data);
                });
            }

            return result;
        }

        private isPromise(possiblePromise): boolean {
            if (possiblePromise && angular.isFunction(possiblePromise.then) && angular.isFunction(possiblePromise['catch'])) {
                return true;
            } else {
                return false;
            }
        }

    }

    export class HttpPromiseTransformationProvider implements ng.IServiceProvider {

        public decorate($provide: ng.auto.IProvideService, serviceName: string): void {
            $provide.decorator(serviceName, httpPromiseTransformationDecorator);
        }

        /** @ngInject */
        public $get($q: ng.IQService): HttpPromiseTransformationService {
            return new HttpPromiseTransformationService($q);
        }

    }

    angular
        .module('app.support')
        .provider('httpPromiseTransformation', HttpPromiseTransformationProvider);
}

angular.module("angularFileUpload", []);

angular.module("ngCookies", []);

angular.module("ui.sortable", []);