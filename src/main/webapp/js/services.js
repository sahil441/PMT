
//$resource - service in module ngResource
//A factory which creates a resource object that lets you interact with RESTful server-side data sources.

var services = angular.module('penut.services', ['ngResource']);

services.factory('Welcome', function ($resource) {
	return $resource('/welcome/msg', {}, {
		query: { method: 'GET' }
	});
});

services.factory('CityList', function ($resource) {
	return $resource('/city/list', {}, {
		query: { method: 'GET', isArray: true }
	});
});

services.factory('StaticRepo', function($resource){
	return $resource('/json/static.json', {}, {
		query: { method: 'GET', isArray: true }
	});
});

services.factory('TagRepo', function($resource){
	return $resource('json/tags.json', {}, {
		query: { method: 'GET', isArray: true }
	});
});

services.factory('AttractionTagRepo', function($resource){
	return $resource('json/attraction_tags.json', {}, {
		query: { method: 'GET', isArray: true }
	});
});

services.factory('AttractionList', function ($resource) {
	return $resource('/attraction/list', {}, {
		query: { method: 'GET', isArray: true },
	})
});

services.factory('AttractionForm', function($resource) {
	return $resource('/attraction/form/:id', {}, {
		show: { method: 'GET', params: {id: '@id'} },
		create: { method: 'POST' },
		update: { method: 'POST', params: {id: '@id'} },
		delete: { method: 'DELETE', params: {id: '@id'} }
	})
});

services.factory('CityForm', function ($resource) {
	return $resource('/city/form/', {}, {
		show: { method: 'GET' ,params: {id: '@id'}},
		create: { method: 'POST' },
		update: { method: 'POST',  },
		delete: { method: 'DELETE', params: {id: '@id'} }
	})
});

services.factory('fileUpload', function($http) {
	return {
		uploadFileToUrl: function (file, uploadUrl) {
			var fd = new FormData();
			for(i=0;i<file.length;i++) {
				fd.append('file', file[i]);
			}
			$("#attractionsImageThrobber").toggle();
			return $http.post(uploadUrl, fd, {
				transformRequest: angular.identity,
				headers: {'Content-Type': undefined}
			});
		}
	}
});