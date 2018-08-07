// create angular app
var validationApp = angular.module('validationApp', []);

// create angular controller
validationApp.controller('mainController', function($scope,$http) {

	// function to submit the form after all validation has occurred			
	$scope.submitForm = function(isValid) {

		// check to make sure the form is completely valid
		if(isValid) {
			window.location.href="customer_login.action?telephone="+$scope.user.telephone+"&password="+$scope.user.password;
		}else{
            $scope.userForm.telephone.$pristine=false;
            $scope.userForm.password.$pristine=false;

            $scope.userForm.password.$dirty=true;
		}

	};

});