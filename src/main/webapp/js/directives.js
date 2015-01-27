//Referenced from http://www.htmlxprs.com/post/32/creating-an-angularjs-autocomplete-tag-input-widget

var directives =angular.module('penut.directives', []);

directives.directive('autoComplete',
		['$http', 
		 function($http) {
			return {
				restrict : 'AE',
				scope : {
					selectedTags : '=model'
				},
				templateUrl : 'partials/autocomplete-template.html',
				link : function(scope, elem, attrs) {
					scope.suggestions = [];
					scope.selectedTags = [];
					scope.selectedIndex = -1;
					scope.autocompleteModel=attrs.model;

					scope.removeTag = function(index) {
						scope.selectedTags.splice(index, 1);
					}
					//
					// This can be replaced with a server call.
					// Currently we assume a static city array
					// which can be filtered by term on 'Starts
					// With' basis
					//
					scope.search = function() {
						if(scope.autocompleteModel=="autocomplete.cities") {
						var  allCities =[];
						$http.get('json/static.json').success(function(countryArray) {										
							for(i in countryArray){
								var citiesInCountry = countryArray[i].city;
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

							if(filteredCities.indexOf(scope.searchText)===-1){
								filteredCities.unshift(scope.searchText);
							}

							scope.suggestions = filteredCities;
							scope.selectedIndex = -1;
						});			
					  } else {
						  var items=[];
						  $http.get(attrs.url).success(function(tagArray) {
								for(i in tagArray) {
								items.push(tagArray[i].key);
							 }
						  
						  var filteredTags= items.filter(
								  function(tag) {
									  var term = scope.searchText;
									  return tag.lastIndexOf(term, 0) === 0;
								  }
						    );
						  
						  if(filteredTags.indexOf(scope.searchText)===-1){
							  filteredTags.unshift(scope.searchText);
							}
						  
						  scope.suggestions = filteredTags;
						  scope.selectedIndex = -1;
						  
						  });
					}
					}

					scope.addToSelectedTags = function(index) {
						if (scope.selectedTags
								.indexOf(scope.suggestions[index]) === -1) {
							scope.selectedTags
							.push(scope.suggestions[index]);
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
							scope.searchText = scope.suggestions[scope.selectedIndex];
						}
					}
					);
				}
			}
		} 
		]);

directives.directive('Maxlength', [
                                   '$compile',
                                   '$log',
                                   function($compile, $log) {
                                	   return {
                                		   restrict : 'A',
                                		   require : 'ngModel',
                                		   link : function(scope, elem, attrs, ctrl) {
                                			   attrs.$set("ngTrim", "false");
                                			   var maxlength = parseInt(attrs.myMaxlength, 10);
                                			   ctrl.$parsers
                                			   .push(function(value) {
                                				   $log.info("In parser function value = ["
                                						   + value + "].");
                                				   if (value.length > maxlength) {
                                					   $log.info("The value [" + value
                                							   + "] is too long!");
                                					   value = value.substr(0, maxlength);
                                					   ctrl.$setViewValue(value);
                                					   ctrl.$render();
                                					   $log.info("The value is now truncated as ["
                                							   + value + "].");
                                				   }
                                				   return value;
                                			   });
                                		   }
                                	   };
                                   } ]);

directives.directive('fileModel', [ '$parse', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {						
							var fileArray=[];
							angular.forEach(scope.files, function(file1, key) {
									fileArray.push(file1);
							});
							var rejectedFiles="";
							angular.forEach(element[0].files, function(file, index) {
								if(file.size > 5242880) {
									if(rejectedFiles=="") {
										rejectedFiles+= file.name;
									} else {
										rejectedFiles+=", " + file.name;
									}
								} else {
									fileArray.push(file);
								}
							});
							if(rejectedFiles!="") {
								alert(rejectedFiles + "- cannot be uploaded ( as for each size > 5 M.B)");
								modelSetter(scope, fileArray);
								return;
							} else {
								modelSetter(scope, fileArray);
							}
				});
			});
		}
	};
} ]);