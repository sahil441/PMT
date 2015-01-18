
//Referenced from http://www.htmlxprs.com/post/32/creating-an-angularjs-autocomplete-tag-input-widget

var directives =angular.module('penut.directives', []);


app.directive('autoComplete',
		['$http', 
		 function($http) {
			return {
				restrict : 'AE',
				scope : {
					selectedTags : '=model'
				},
				templateUrl : '/partials/autocomplete-template.html',
				link : function(scope, elem, attrs) {
					scope.suggestions = [];
					scope.selectedTags = [];
					scope.selectedIndex = -1;
					scope.selectedTagsObj=[];

					scope.removeTag = function(index) {
						scope.selectedTags.splice(index, 1);
						scope.selectedTagsObj.splice(index, 1);
					}
					//
					// This can be replaced with a server call.
					// Currently we assume a static city array
					// which can be filtered by term on 'Starts
					// With' basis
					//
					scope.search = function() {
						var  allCities =[];
						if(attrs.url=="") {
							$http.get('/json/static.json').success(function(CityArray) {										
								for(i in CityArray){
									var citiesInCountry = CityArray[i].city;
									for( j in citiesInCountry){
										allCities.push(citiesInCountry[j].value);
									}
								}
								var filteredCities =  allCities.filter(
										function(city){
											var term = scope.searchText; 
											return city.lastIndexOf(term, 0) === 0;
										}
								);

								scope.suggestions = filteredCities;
								scope.selectedIndex = -1;
							});		
						} else {

							// System should send search text along with the url- Required JPA implementation for that
							$http.get(attrs.url).success(function(data) {
								scope.tagsObj=data;
								var filteredTags = data.filter(
										function(tag) {
											var term=scope.searchText;
											return tag.displayText.lastIndexOf(term, 0) === 0;
										}
								);
								scope.suggestions = filteredTags;
								scope.selectedIndex = -1;
							});
						}
					}
					scope.addToSelectedTags = function(index) {
						if (scope.selectedTags
								.indexOf(scope.suggestions[index].displayText) === -1) {
							scope.selectedTags
							.push(scope.suggestions[index].displayText);
							scope.selectedTagsObj.push(scope.suggestions[index]);
							scope.searchText = '';
							scope.suggestions = [];
						}
					}

					scope.checkKeyDown = function(event) {
						if (event.keyCode === 40) {
							event.preventDefault();
							if (scope.selectedIndex + 1 !== scope.suggestions.length) {
								scope.selectedIndex++;
							}
						} else if (event.keyCode === 38) {
							event.preventDefault();
							if (scope.selectedIndex - 1 !== -1) {
								scope.selectedIndex--;
							}
						} else if (event.keyCode === 13) {
							scope.addToSelectedTags(scope.selectedIndex);
						}
					}

					scope.$watch('selectedIndex',
							function(val) {
						if (val !== -1) {
							scope.searchText = scope.suggestions[scope.selectedIndex].displayText;
						}
					}
					);
				}
			}
		}]);

app.directive('Maxlength', ['$compile', '$log', function($compile, $log) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function (scope, elem, attrs, ctrl) {
			attrs.$set("ngTrim", "false");
			var maxlength = parseInt(attrs.myMaxlength, 10);
			ctrl.$parsers.push(function (value) {
				$log.info("In parser function value = [" + value + "].");
				if (value.length > maxlength)
				{
					$log.info("The value [" + value + "] is too long!");
					value = value.substr(0, maxlength);
					ctrl.$setViewValue(value);
					ctrl.$render();
					$log.info("The value is now truncated as [" + value + "].");
				}
				return value;
			});
		}
	};
}]);

app.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function(){
				scope.$apply(function(){
					modelSetter(scope, element[0].files);
				});
			});
		}
	};
}]);