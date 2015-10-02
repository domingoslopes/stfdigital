$(function() {
   
    // advanced selects
    altair_form_adv.adv_selects();

});

altair_form_adv = {
    // advanced selects (selectizejs)
    adv_selects: function() {
       
        var REGEX_EMAIL = '([a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*@' +
            '(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)';

        $('#selec_adv_3').selectize({
            persist: false,
            maxItems: null,
            valueField: 'email',
            labelField: 'name',
            searchField: ['name', 'email'],
            options: [
                {email: 'Ricardo Lewandowski', name: 'Ministro'},
                {email: 'Cármen Lúcia', name: 'Ministro'},
                {email: 'Celso de Mello', name: 'Ministro'},
                {email: 'Marco Aurélio', name: 'Ministro'},
                {email: 'Gilmar Mendes', name: 'Ministro'},
                {email: 'Dias Toffoli', name: 'Ministro'},
                {email: 'Luiz Fux', name: 'Ministro'},
                {email: 'Rosa Weber', name: 'Ministro'},
                {email: 'Teori Zavascki', name: 'Ministro'},
                {email: 'Roberto Barroso', name: 'Ministro'},
                {email: 'Edson Fachin', name: 'Ministro'}

            ],
            render: {
                item: function(item, escape) {
                    return '<div>' +
                        (item.name ? '<span class="name">' + escape(item.name) + '</span>' : '') +
                        (item.email ? '<span class="email">' + escape(item.email) + '</span>' : '') +
                        '</div>';
                },
                option: function(item, escape) {
                    var label = item.name || item.email;
                    var caption = item.name ? item.email : null;
                    return '<div>' +
                        '<span class="label">' + escape(label) + ' - </span>' +
                        (caption ? '<span class="caption">' + escape(caption) + '</span>' : '') +
                        '</div>';
                }
            },
            createFilter: function(input) {
                var match, regex;

                // email@address.com
                regex = new RegExp('^' + REGEX_EMAIL + '$', 'i');
                match = input.match(regex);
                if (match) return !this.options.hasOwnProperty(match[0]);

                // name <email@address.com>
                regex = new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i');
                match = input.match(regex);
                if (match) return !this.options.hasOwnProperty(match[2]);

                return false;
            },
            create: function(input) {
                if ((new RegExp('^' + REGEX_EMAIL + '$', 'i')).test(input)) {
                    return {email: input};
                }
                var match = input.match(new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i'));
                if (match) {
                    return {
                        email : match[2],
                        name  : $.trim(match[1])
                    };
                }
                alert('Invalid email address.');
                return false;
            },
            onDropdownOpen: function($dropdown) {
                $dropdown
                    .hide()
                    .velocity('slideDown', {
                        begin: function() {
                            $dropdown.css({'margin-top':'0'})
                        },
                        duration: 200,
                        easing: easing_swiftOut
                    })
            },
            onDropdownClose: function($dropdown) {
                $dropdown
                    .show()
                    .velocity('slideUp', {
                        complete: function() {
                            $dropdown.css({'margin-top':''})
                        },
                        duration: 200,
                        easing: easing_swiftOut
                    })
            }
        });

        $('#selec_adv_2').selectize({
            persist: false,
            maxItems: null,
            valueField: 'email',
            labelField: 'name',
            searchField: ['name', 'email'],
            options: [
                {email: 'Ação Direta de Inconstitucionalidade', name: 'ADI'},
                {email: 'AÇÃO CAUTELAR', name: 'AC'},
                {email: 'AÇÃO CÍVEL ORIGINÁRIA', name: 'ACO'},
                {email: 'AÇÃO DECLARATÓRIA DE CONSTITUCIONALIDADE', name: 'ADC'},
                {email: 'AÇÃO DIRETA DE INCONSTITUCIONALIDADE', name: 'ADI'},
                {email: 'AÇÃO DIRETA DE INCONSTITUCIONALIDADE POR OMISSÃO', name: 'ADO'},
                {email: 'ARGÜIÇÃO DE DESCUMPRIMENTO DE PRECEITO FUNDAMENTAL', name: 'ADPF'},
                {email: 'AGRAVO DE INSTRUMENTO', name: 'AI'},
                {email: 'ARGÜIÇÃO DE IMPEDIMENTO', name: 'AImp'},
                {email: 'AÇÃO ORIGINÁRIA', name: 'AO'},
                {email: 'AÇÃO ORIGINÁRIA ESPECIAL', name: 'AOE'},
                {email: 'AÇÃO PENAL', name: 'AP'},
                {email: 'AÇÃO RESCISÓRIA', name: 'AR'},
                {email: 'RECURSO EXTRAORDINÁRIO COM AGRAVO', name: 'ARE'},
                {email: 'ARGÜIÇÃO DE SUSPEIÇÃO', name: 'AS'},
                {email: 'CONFLITO DE COMPETÊNCIA', name: 'CC'},
                {email: 'COMUNICAÇÃO', name: 'Cm'},
                {email: 'EXCEÇÃO DE INCOMPETÊNCIA', name: 'EI'},
                {email: 'EXCEÇÃO DE LITISPENDÊNCIA', name: 'EL'},
                {email: 'EXECUÇÃO PENAL', name: 'EP'},
                {email: 'EXTRADIÇÃO', name: 'Ext'},
                {email: 'HABEAS CORPUS', name: 'HC'},
                {email: 'HABEAS DATA', name: 'HD'},
                {email: 'INTERVENÇÃO FEDERAL', name: 'IF'},
                {email: 'INQUÉRITO', name: 'Inq'},
                {email: 'MANDADO DE INJUNÇÃO', name: 'MI'},
                {email: 'MANDADO DE SEGURANÇA', name: 'MS'},
                {email: 'OPOSIÇÃO EM AÇÃO CIVIL ORIGINÁRIA', name: 'OACO'},
                {email: 'PETIÇÃO', name: 'Pet'},
                {email: 'PRISÃO PREVENTIVA PARA EXTRADIÇÃO', name: 'PPE'},
                {email: 'PROPOSTA DE SÚMULA VINCULANTE', name: 'PSV'},
                {email: 'RECURSO CRIME', name: 'RC'},
                {email: 'RECLAMAÇÃO', name: 'Rcl'},
                {email: 'RECURSO EXTRAORDINÁRIO', name: 'RE'},
                {email: 'RECURSO ORDINÁRIO EM HABEAS CORPUS', name: 'RHC'},
                {email: 'RECURSO ORDINÁRIO EM HABEAS DATA', name: 'RHD'},
                {email: 'RECURSO ORDINÁRIO EM MANDADO DE INJUNÇÃO', name: 'RMI'},
                {email: 'RECURSO ORD. EM MANDADO DE SEGURANÇA', name: 'RMS'},
                {email: 'REVISÃO CRIMINAL', name: 'RvC'},
                {email: 'SENTENÇA ESTRANGEIRA CONTESTADA', name: 'SEC'},
                {email: 'SUSPENSÃO DE LIMINAR', name: 'SL'},
                {email: 'SUSPENSÃO DE SEGURANÇA', name: 'SS'},
                {email: 'SUSPENSÃO DE TUTELA ANTECIPADA', name: 'STA'},
            ],
            render: {
                item: function(item, escape) {
                    return '<div>' +
                        (item.name ? '<span class="name">' + escape(item.name) + '</span>' : '') +
                        (item.email ? '<span class="email">' + escape(item.email) + '</span>' : '') +
                        '</div>';
                },
                option: function(item, escape) {
                    var label = item.name || item.email;
                    var caption = item.name ? item.email : null;
                    return '<div>' +
                        '<span class="label">' + escape(label) + ' - </span>' +
                        (caption ? '<span class="caption">' + escape(caption) + '</span>' : '') +
                        '</div>';
                }
            },
            createFilter: function(input) {
                var match, regex;

                // email@address.com
                regex = new RegExp('^' + REGEX_EMAIL + '$', 'i');
                match = input.match(regex);
                if (match) return !this.options.hasOwnProperty(match[0]);

                // name <email@address.com>
                regex = new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i');
                match = input.match(regex);
                if (match) return !this.options.hasOwnProperty(match[2]);

                return false;
            },
            create: function(input) {
                if ((new RegExp('^' + REGEX_EMAIL + '$', 'i')).test(input)) {
                    return {email: input};
                }
                var match = input.match(new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i'));
                if (match) {
                    return {
                        email : match[2],
                        name  : $.trim(match[1])
                    };
                }
                alert('Invalid email address.');
                return false;
            },
            onDropdownOpen: function($dropdown) {
                $dropdown
                    .hide()
                    .velocity('slideDown', {
                        begin: function() {
                            $dropdown.css({'margin-top':'0'})
                        },
                        duration: 200,
                        easing: easing_swiftOut
                    })
            },
            onDropdownClose: function($dropdown) {
                $dropdown
                    .show()
                    .velocity('slideUp', {
                        complete: function() {
                            $dropdown.css({'margin-top':''})
                        },
                        duration: 200,
                        easing: easing_swiftOut
                    })
            }
        });

        $('#selec_adv_1').selectize({
            persist: false,
            maxItems: null,
            valueField: 'email',
            labelField: 'name',
            searchField: ['name', 'email'],
            options: [
                {email: 'Ação Direta de Inconstitucionalidade', name: 'ADI'},
                {email: 'AÇÃO CAUTELAR', name: 'AC'},
                {email: 'AÇÃO CÍVEL ORIGINÁRIA', name: 'ACO'},
                {email: 'AÇÃO DECLARATÓRIA DE CONSTITUCIONALIDADE', name: 'ADC'},
                {email: 'AÇÃO DIRETA DE INCONSTITUCIONALIDADE', name: 'ADI'},
                {email: 'AÇÃO DIRETA DE INCONSTITUCIONALIDADE POR OMISSÃO', name: 'ADO'},
                {email: 'ARGÜIÇÃO DE DESCUMPRIMENTO DE PRECEITO FUNDAMENTAL', name: 'ADPF'},
                {email: 'AGRAVO DE INSTRUMENTO', name: 'AI'},
                {email: 'ARGÜIÇÃO DE IMPEDIMENTO', name: 'AImp'},
                {email: 'AÇÃO ORIGINÁRIA', name: 'AO'},
                {email: 'AÇÃO ORIGINÁRIA ESPECIAL', name: 'AOE'},
                {email: 'AÇÃO PENAL', name: 'AP'},
                {email: 'AÇÃO RESCISÓRIA', name: 'AR'},
                {email: 'RECURSO EXTRAORDINÁRIO COM AGRAVO', name: 'ARE'},
                {email: 'ARGÜIÇÃO DE SUSPEIÇÃO', name: 'AS'},
                {email: 'CONFLITO DE COMPETÊNCIA', name: 'CC'},
                {email: 'COMUNICAÇÃO', name: 'Cm'},
                {email: 'EXCEÇÃO DE INCOMPETÊNCIA', name: 'EI'},
                {email: 'EXCEÇÃO DE LITISPENDÊNCIA', name: 'EL'},
                {email: 'EXECUÇÃO PENAL', name: 'EP'},
                {email: 'EXTRADIÇÃO', name: 'Ext'},
                {email: 'HABEAS CORPUS', name: 'HC'},
                {email: 'HABEAS DATA', name: 'HD'},
                {email: 'INTERVENÇÃO FEDERAL', name: 'IF'},
                {email: 'INQUÉRITO', name: 'Inq'},
                {email: 'MANDADO DE INJUNÇÃO', name: 'MI'},
                {email: 'MANDADO DE SEGURANÇA', name: 'MS'},
                {email: 'OPOSIÇÃO EM AÇÃO CIVIL ORIGINÁRIA', name: 'OACO'},
                {email: 'PETIÇÃO', name: 'Pet'},
                {email: 'PRISÃO PREVENTIVA PARA EXTRADIÇÃO', name: 'PPE'},
                {email: 'PROPOSTA DE SÚMULA VINCULANTE', name: 'PSV'},
                {email: 'RECURSO CRIME', name: 'RC'},
                {email: 'RECLAMAÇÃO', name: 'Rcl'},
                {email: 'RECURSO EXTRAORDINÁRIO', name: 'RE'},
                {email: 'RECURSO ORDINÁRIO EM HABEAS CORPUS', name: 'RHC'},
                {email: 'RECURSO ORDINÁRIO EM HABEAS DATA', name: 'RHD'},
                {email: 'RECURSO ORDINÁRIO EM MANDADO DE INJUNÇÃO', name: 'RMI'},
                {email: 'RECURSO ORD. EM MANDADO DE SEGURANÇA', name: 'RMS'},
                {email: 'REVISÃO CRIMINAL', name: 'RvC'},
                {email: 'SENTENÇA ESTRANGEIRA CONTESTADA', name: 'SEC'},
                {email: 'SUSPENSÃO DE LIMINAR', name: 'SL'},
                {email: 'SUSPENSÃO DE SEGURANÇA', name: 'SS'},
                {email: 'SUSPENSÃO DE TUTELA ANTECIPADA', name: 'STA'},
            ],
            render: {
                item: function(item, escape) {
                    return '<div>' +
                        (item.name ? '<span class="name">' + escape(item.name) + '</span>' : '') +
                        (item.email ? '<span class="email">' + escape(item.email) + '</span>' : '') +
                        '</div>';
                },
                option: function(item, escape) {
                    var label = item.name || item.email;
                    var caption = item.name ? item.email : null;
                    return '<div>' +
                        '<span class="label">' + escape(label) + ' - </span>' +
                        (caption ? '<span class="caption">' + escape(caption) + '</span>' : '') +
                        '</div>';
                }
            },
            createFilter: function(input) {
                var match, regex;

                // email@address.com
                regex = new RegExp('^' + REGEX_EMAIL + '$', 'i');
                match = input.match(regex);
                if (match) return !this.options.hasOwnProperty(match[0]);

                // name <email@address.com>
                regex = new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i');
                match = input.match(regex);
                if (match) return !this.options.hasOwnProperty(match[2]);

                return false;
            },
            create: function(input) {
                if ((new RegExp('^' + REGEX_EMAIL + '$', 'i')).test(input)) {
                    return {email: input};
                }
                var match = input.match(new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i'));
                if (match) {
                    return {
                        email : match[2],
                        name  : $.trim(match[1])
                    };
                }
                alert('Invalid email address.');
                return false;
            },
            onDropdownOpen: function($dropdown) {
                $dropdown
                    .hide()
                    .velocity('slideDown', {
                        begin: function() {
                            $dropdown.css({'margin-top':'0'})
                        },
                        duration: 200,
                        easing: easing_swiftOut
                    })
            },
            onDropdownClose: function($dropdown) {
                $dropdown
                    .show()
                    .velocity('slideUp', {
                        complete: function() {
                            $dropdown.css({'margin-top':''})
                        },
                        duration: 200,
                        easing: easing_swiftOut
                    })
            }
        });

    },

};