/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var LoginPage = require('./pages/login.page');
	
	var PrincipalPage = require('./pages/principal.page');
	
	var PeticionamentoPage = require('./pages/peticionamento.page');
	
	var RegistroPage = require('./pages/registro.page');
	
	var AutuacaoPage = require('./pages/autuacao.page');
	
	var DistribuicaoPage = require('./pages/distribuicao.page');
	
	var PreautuacaoPage = require('./pages/preautuacao.page');
	
	var OrganizaPecasPage = require('./pages/organizaPecas.page');
	
	var principalPage;
	
	var loginPage;
	
	var pos;
	
	var peticaoId;
	
	var processoId;
	
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage) loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
	
	var itemHeight = 20, itemWidth = 100, dxOffset = 5, numberOfLists = 2;

	describe('Organização das Peças do Processo:', function() { 
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
		it('Deveria logar como organizador de peças', function() {
			login('organizador-pecas');
		});
		
		it('Deveria inserir uma nova peca', function() {
			
		});
		
		it('Deveria juntar uma nova peça', function() {
		
		});
		
		it('Deveria unir duas peças', function() {
			
		});
		
		it('Deveria editar as informações da peça', function() {
			
		});
		
		it('Deveria exluir uma peça', function() {
			
		});
		
		/*it('should move last item to start of current list', function() {
			
			var principalPage = new PrincipalPage();
			
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	processoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Organizar Peças #' + processoId);
		    });
		    
		    var organizaPecasPage = new OrganizaPecasPage();
		    
		    var values = {
		    		1: {
		    			before: [1,2,3,4],
		    			after:  [4,1,2,3]
		    		}
		    };
		    dragAndCompare(1, 4, values, {dy: -itemHeight*3});
		    // browser.driver.actions().dragAndDrop(element(by.css("#tabelaPecas tbody tr:nth-child(1)")), element(by.css("#tabelaPecas tbody tr:nth-child(2)"))).mouseUp().perform();
		});
		
		
		  // dxOffset is used to offset each drag along the x-axis. This was necessary
		  // to ensure the item dropped on a valid target.
		  var itemHeight = 20, itemWidth = 100, dxOffset = 5, numberOfLists = 2;

		  *//**
		   * Get repeater string for list (lists start at 1)
		   *
		   * @param {Integer} listNumber
		   *//*
		  function getRepeater(listNumber) {
		    return by.repeater('peca in organiza.processo.pecas['+(listNumber-1)+']');
		  }

		  *//**
		   * Compare list data to values.
		   *
		   * @param {Integer} listNumber list number to compare against. This is index
		   * into the controller items array
		   * @param {Array} values array of values to compare against a
		   *//*
		  function expectDataToBe(listNumber, values) {
		    browser.executeAsyncScript(function(callback) {
		      callback(angular.element('body').scope().items);
		    }).then(function(items) {
		      var ids = items[listNumber-1].map(function (item) { return item.id; });
		      expect(ids.join(',')).toBe(values.join(','));
		    });
		  }

		  *//**
		   * Compare list ng-repeat html against list of values. We do this in addition
		   * to looking at the data directly to make sure model and HTML are what we
		   * expect when we apply filters etc. We do this as the DOM is modified by the
		   * drag and drop so just making sure nothing has been messed up.
		   *
		   * @param {Integer} listNumber list number to compare against. This is index
		   * into the controller items array used as in ng-repeat
		   * @param {Array} values array of values to compare against a
		   *//*
		  function expectHtmlToBe(listNumber, values) {
		    element.all(getRepeater(listNumber).column('{{item.id}}')).then(function (items) {
		      for (var i=0; i < items.length; i++) {
		        expect(items[i].getText()).toBe(''+values[i]);
		      }
		    });
		  }

		  function checkValues(listValues, key) {
		    switch (key) {
		      case 'before':
		      case 'after':
		        break;
		      default:
		        throw new Exception('Unknown key '+key);
		    }

		    var htmlKey = key+'Html', i;
		    for (i = 0;i < numberOfLists;i++) {
		      if (undefined !== listValues[i])  {
		        expectDataToBe(i, listValues[i][key]);
		        if (undefined === listValues[i][htmlKey]) {
		          listValues[i][htmlKey] = listValues[i][key];
		        }
		        expectHtmlToBe(i, listValues[i][htmlKey]);
		      }
		    }
		  }

		  *//**
		   * Drag an item and compare to expected values
		   *
		   * @param {Integer} listNumber list to move from (starting at 1)
		   * @param {Integer} itemNumber item in list to move (starting at 1)
		   * @param {Object} listValues expected values before / after move
		   *
		   *  listValues is indexed by listNumber (starting at 0), eg
		   *
		   *  1: {
		   *    before: {Array} model values before drag
		   *    after: {Array} model values after drag
		   *    beforeHtml: {Array} what HTML is displayed before drag
		   *    afterHtml: {Array} what HTML is displayed after drag
		   *  }
		   *
		   *  the *Html keys are relevant when you are filtering and so not all items
		   *  in the model are shown in the HTML
		   *
		   * @param {Object} dydx offset to move
		   * @param {Boolean} doSelect whether to do a selection rather than drag and
		   * drop TODO: Not implemented
		   *//*
		  function dragAndCompare(listNumber, itemNumber, listValues, dydx, doSelect) {
			  browser.findElement(getRepeater(listNumber).row(itemNumber-1)).then(function (el) {

		      checkValues(listValues, 'before');

		      if (!doSelect) {
		        dydx.dx = (dydx.dx || 0) + dxOffset;
		        driver.actions().dragAndDrop(
		          el.findElement(by.css('.handle')),
		          {x:dydx.dx || 0, y:dydx.dy || 0})
		          .perform();
		      } else {
		        driver.actions().dragAndDrop(
		          el.findElement(by.css('.id')),
		          {x:dydx.dx || 0, y:dydx.dy || 0})
		          .perform();
		      }

		      checkValues(listValues, 'after');
		    });

		  }

		  *//**
		   * Check item html against expected values
		   *
		   * @param {Integer} listNumber
		   * @param {Object} values @see dragAndCompare listValues for details
		   *//*
		  function testItemHtml(listNumber, values) {
		    var repeater = getRepeater(listNumber);
		    if (values.length === 0) {
		      expect(repeater.count()).toBe(0);
		    } else {
		      element.all(repeater.column('{{item.id}}')).then( function(ids) {
		        for (var i=0;i<ids.length;i++) {
		          expect(ids[i].getText()).toBe(''+values[i]);
		        }
		      });
		    }
		  }*/
		
	});
})();