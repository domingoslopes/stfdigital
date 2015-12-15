(function ()
{
    'use strict';

    angular
        .module('app.scrumboard')
        .controller('CalendarViewController', CalendarViewController);

    /** @ngInject */
    function CalendarViewController($scope, $document, $mdDialog, $mdSidenav, BoardService, DialogService)
    {
        var vm = this;

        // Data
        vm.board = BoardService.data;
        vm.eventSources = [];

        vm.calendarUiConfig = {
            calendar: {
                editable        : true,
                eventLimit      : true,
                header          : '',
                dayNames        : ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
                dayNamesShort   : ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
                viewRender      : function (view)
                {
                    vm.calendarView = view;
                    vm.calendar = vm.calendarView.calendar;
                    vm.currentMonthShort = vm.calendar.getDate().format('MMM');
                },
                columnFormat    : {
                    month: 'ddd',
                    week : 'ddd M',
                    day  : 'ddd M'
                },
                eventClick      : function eventDetail(calendarEvent, ev)
                {
                    vm.openCardDialog(ev, calendarEvent.idCard);
                },
                eventAfterRender: function (event)
                {
                    vm.board.cards.getById(event.idCard).due = moment(event.start).toDate();
                },
                selectable      : true,
                selectHelper    : true,
                dayClick        : function (date, ev)
                {
                    eventDialog(date, ev);
                }
            }
        };

        // Methods
        vm.next = next;
        vm.prev = prev;
        vm.goToDate = goToDate;
        vm.openCardDialog = DialogService.openCardDialog;
        vm.toggleSidenav = toggleSidenav;

        //////////

        init();

        /**
         * Initialize
         */
        function init()
        {
            vm.cards = getScheduledCards();
            vm.eventSources[0] = vm.cards;
        }

        /**
         * Get scheduled cards and prepare
         * them to show on the calendar
         *
         * @returns {Array}
         */
        function getScheduledCards()
        {
            var cards = [];
            angular.forEach(vm.board.cards, function (card)
            {
                if ( card.due )
                {
                    cards.push({
                        idCard         : card.id,
                        title          : card.name,
                        start          : new Date(card.due),
                        allDay         : true,
                        backgroundColor: getEventBgColor(new Date(card.due))
                    });
                }
            });
            return cards;
        }

        /**
         * Get background color
         *
         * @param cardDue
         * @returns {*}
         */
        function getEventBgColor(cardDue)
        {
            if ( new Date() > cardDue )
            {
                return '#F44336';
            }
            return '#4CAF50';
        }

        /**
         * Watch board changes
         */
        $scope.$watch('vm.board', function (current, old)
        {
            if ( angular.equals(current, old) )
            {
                return;
            }

            init();

        }, true);

        /**
         * Go to Date
         *
         * @param date
         */
        function goToDate(date)
        {
            vm.calendarView.calendar.gotoDate(date);
            $mdSidenav('scheduled-tasks-sidenav').close();
        }

        /**
         * Go to next on current view (week, month etc.)
         */
        function next()
        {
            vm.calendarView.calendar.next();
        }

        /**
         * Go to previous on current view (week, month etc.)
         */
        function prev()
        {
            vm.calendarView.calendar.prev();
        }

        /**
         * Event Dialog
         */
        function eventDialog(date, ev)
        {
            $mdDialog.show({
                templateUrl        : 'app/main/apps/scrumboard/views/calendar/dialogs/event/event-dialog.html',
                controller         : "ScrumboardCalendarEventDialogController",
                controllerAs       : "vm",
                parent             : $document.find('#scrumboard'),
                targetEvent        : ev,
                clickOutsideToClose: true,
                escapeToClose      : true,
                locals             : {
                    dueDate: date
                }
            });
        }

        /**
         * Toggle sidenav
         *
         * @param sidenavId
         */
        function toggleSidenav(sidenavId)
        {
            $mdSidenav(sidenavId).toggle();
        }

    }
})();