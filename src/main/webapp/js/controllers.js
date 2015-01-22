var app = angular.module('penut.controllers', []);


app.controller('WelcomeCtrl', ['$scope', '$location', 'Welcome', function ($scope, $location , Welcome) {
	Welcome.query(function(response){
		$scope.welcome = response.text;
	});
}]);

app.controller('CityListCtrl', ['$scope', 'CityList', 'CityForm', '$location',
                                function ($scope, CityList, CityForm, $location) {

	// callback for ng-click 'editCity':
	$scope.editCity = function (cityId) {
		$location.path('/city-update/' + cityId);
	};

	// callback for ng-click 'deleteCity':
	$scope.deleteCity = function (cityId) {
		CityForm.delete({ id: cityId });
		$scope.cities = CityList.query();
	};

	// callback for ng-click 'createCity':
	$scope.addCity = function () {
		$location.path('/city-add');
	};

	$scope.addAttraction = function (cityId, countryName) {
		$location.path('/attraction-add/cityId/'+cityId+'/countryName/'+countryName);
	};

	$scope.cities = CityList.query();
}]);

app.controller('CityFormCtrl', ['$scope', '$filter','$routeParams', 'CityForm', '$location','StaticRepo','TagRepo',
                                function ($scope, $filter, $routeParams, CityForm, $location,StaticRepo,TagRepo) {

	// Update city tag collection on selection.
	$scope.selectTags = function () {
		$scope.city.tags = jQuery.map($filter('filter')($scope.allCityTags, {checked: true}),
				function(tag){
			return tag.displayText;
		});
	};		

	// Static City object array
	$scope.citiesInCountry = [] ;

	var staticData = StaticRepo.query(function(){
		// Static Country names
		$scope.allCountries = jQuery.map(staticData,
				function(country){
			return country.countryName;
		}
		);

		// Initial City data.
		if($routeParams.id){
			$scope.city = CityForm.show({id: $routeParams.id},function(){
				// City dropdown data.
				if($scope.city.name){
					var currentCountry = staticData.filter(
							function(country){
								var cityInCountry = country.city;
								for (var i in  cityInCountry) {
									if(cityInCountry[i].value  == $scope.city.name){
										return country;
									}	
								}	    													
							}
					);
					$scope.citiesInCountry = currentCountry[0].city;
				}
				// Update tags if selected
				var selectedTags = $scope.city.tags;
				// Initial data to display all tags.
				$scope.allCityTags = TagRepo.query(function(){
					for( var i in $scope.allCityTags){
						for(var j in selectedTags){
							if( selectedTags[j].value == $scope.allCityTags[i].value ){
								$scope.allCityTags[i].checked = true;
							} 
						}
					}
				});
				// Near by cities
				$scope.autocomplete.cities = $scope.city.nearByCityList;
			}); 
			
		}else{
			$scope.city = {};  
			$scope.allCityTags = TagRepo.query();
		}		

	});		


	// Refresh city on selection.
	$scope.refreshCities = function(){
		var currentCountryName = $scope.city.country;
		var currentCountry = staticData.filter(
				function(country){
					if(country.countryName  == currentCountryName){
						return country;
					}							
				}
		);
		$scope.citiesInCountry = currentCountry[0].city;
	};


	// callback for ng-click 'updateCity':
	$scope.save = function () {
		var current = $scope.city;
		$scope.city.nearByCityList = $scope.autocomplete.cities;
		CityForm.update(current,function(){
			$location.path('/city-list');  
		});

	};

	// callback for ng-click 'cancel':
	$scope.cancel = function () {
		$location.path('/city-list');
	};        


}]);


app.controller('AttractionFormCtrl',['$scope', '$routeParams','CityList', 'AttractionForm','fileUpload', '$location','AttractionTagRepo',
                                     function ($scope, $routeParams, CityList, AttractionForm,fileUpload, $location, AttractionTagRepo) {

	if($routeParams.id) {
		//if attraction is already created
		$scope.attraction=AttractionForm.show({id: $routeParams.id},function() {
			$scope.attraction.city=$scope.attraction.city.id;
			angular.forEach($scope.attraction.tags, function(tag,index) {
				$scope.data.tags.push(tag.displayText);

			});
			$scope.autocomplete.attraction_tags= $scope.attraction.tagList;
		});
	} else if($routeParams.cityId) {
		// create attraction for a particular city
		$scope.attraction={};
		$scope.attraction.city=parseInt($routeParams.cityId);
		$scope.attraction.country=$routeParams.countryName;
	} else {
		$routeParams.attraction={};
	}
	
	$scope.attractionTags=AttractionTagRepo.query(function() {
		for( var tag in $scope.attractionTags){
			
		}
	});

	$scope.save = function () {
		var tags=$scope.autocomplete.attraction_tags;
		$scope.attraction.tagList=tags;
		$scope.replaceCityIdWithJSON();
		// upload file on saving attraction | Visible during attraction edit only
		var file = $scope.files;
		if($routeParams.id && file!=undefined) {
			console.log('file is ' + JSON.stringify(file));
			var uploadUrl = "/attachment/upload";
			// this method should return list of attachments
			var httpPost=fileUpload.uploadFileToUrl(file, uploadUrl);
			httpPost.success(function(response){
				$("#attractionsImageThrobber").toggle();
				if(response!=undefined) {
					angular.forEach(response, function(attachment, index) {
						$scope.attraction.attachments.push(attachment);
					});
					AttractionForm.update($scope.attraction);
				}
			})
			.error(function(){
				$("#attractionsImageThrobber").toggle();
				app.alert("Attachments upload error");
			});
		} else {
			AttractionForm.update($scope.attraction);
		}
		$location.path('/attraction-list');

	}

	$scope.deleteFile= function(file) {
		// TODO: this method should be changed to imporve the complexity
		var filesArray = [];
		angular.forEach($scope.files, function(file1, key) {
			if(file1!=file) {
				filesArray.push(file1);
			}
		});
		$scope.files=filesArray;
	}

	//Modify the method to use a better approach
	$scope.replaceCityIdWithJSON= function() {
		angular.forEach($scope.cities,function(city, index){
			if(city.id==$scope.attraction.city) {
				$scope.attraction.city=city;
				return;
			}
		});
	}

	$scope.cancel = function () {
		$location.path('/attraction-list');
	}; 

	$scope.cities = CityList.query();
}]);

app.controller('AttractionListCtrl',['$scope', '$routeParams', 'AttractionList','AttractionForm', '$location',
                                     function ($scope, $routeParams, AttractionList,AttractionForm, $location) {
	$scope.attractions = AttractionList.query();

	$scope.editAttraction = function (AttractionId) {
		$location.path('/attraction-update/' + AttractionId);
	};

	$scope.deleteAttraction = function (AttractionId) {
		AttractionForm.delete({ id: AttractionId });
		$scope.attractions = AttractionList.query();
	};

	$scope.addAttraction = function () {
		$location.path('/attraction-add');
	};

}]);