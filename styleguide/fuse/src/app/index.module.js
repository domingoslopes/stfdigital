(function ()
{
    'use strict';

    /**
     * Main module of the Fuse
     */
    angular
        .module('app', [

            // Core
            'app.core',

            // Navigation
            'app.navigation',

            // Toolbar
            'app.toolbar',

            // Quick panel
            'app.quick-panel',

            // Pages
            'app.login',
            'app.cadastro',
            
            'app.gestao.meus-paineis.principal',
            'app.gestao.meus-paineis.peticoes',
            
            'app.processos.ultimos-acessos',
            'app.processos.pesquisa-avancada',

            'app.tarefas.minhas-tarefas',
            'app.tarefas.painel-de-fases',
            'app.tarefas.notificacoes',

            'app.configuracoes.meu-perfil',
            'app.configuracoes.administracao'
        ]);
})();