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

            'app.novo-processo',

            'app.gestao.meus-paineis',

            'app.processos.ultimos-acessos',
            'app.processos.pesquisa-avancada',

            'app.tarefas.minhas-tarefas',
            'app.tarefas.painel-de-fases',
            'app.tarefas.painel-de-fases.processo-de-autuacao',
            'app.tarefas.painel-de-fases.recebimento-de-remessas',
            'app.tarefas.painel-de-fases.distribuicao-de-processos',
            'app.tarefas.notificacoes',

            'app.configuracoes.meu-perfil',
            'app.configuracoes.administracao'
        ]);
})();
