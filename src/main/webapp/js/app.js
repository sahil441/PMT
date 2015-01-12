angular.module('penut', [ 'penut.services', 'penut.controllers' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/welcome', {
				templateUrl : 'partials/welcome.html',
				controller : 'WelcomeCtrl'
			});
			$routeProvider.when('/city-list', {
				templateUrl : 'partials/city-list.html',
				controller : 'CityListCtrl'
			});
			$routeProvider.when('/city-update/:id', {
				templateUrl : 'partials/city-detail.html',
				controller : 'CityFormCtrl'
			});
			$routeProvider.when('/city-add', {
				templateUrl : 'partials/city-detail.html',
				controller : 'CityFormCtrl'
			});
			$routeProvider.when('/attraction-add', {
				templateUrl : 'partials/attraction-detail.html',
				controller : 'AttractionFormCtrl'
			});
			$routeProvider.when('/attraction-list', {
				templateUrl : 'partials/attraction-list.html',
				controller : 'AttractionListCtrl'
			});
			$routeProvider.otherwise({
				redirectTo : '/welcome'
			});
		} ]);
