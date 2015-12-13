'use strict';

app.controller('mainCtrl', [ '$scope', '$http', 'pollingFactory', function($scope, $http, pollingFactory) {

    console.log('Loading mainCtrl');

    $http.get('/api/status').success(function(data) {
	$scope.status = data;
    });

    $http.get('/api/status').success(function(data) {
	$scope.status = data;
    });

    $scope.start = function() {
	$http.get('/api/start').success(function() {
	    $http.get('/api/status').success(function(data) {
		$scope.status = data;
	    });
	});
    };

    $scope.stop = function() {
	$http.get('/api/stop').success(function() {
	    $http.get('/api/status').success(function(data) {
		$scope.status = data;
	    });
	});
    };

    $http.get('/api/tweets?size=1').success(function(data) {
	$scope.totalTweets = data.page.totalElements;
    });

    pollingFactory.callFnOnInterval(function() {
	$http.get('/api/tweets?size=1').success(function(data) {
	    $scope.totalTweets = data.page.totalElements;
	});
    });

    // $http.get('/api/init').success(function(data) {
    //	
    // });

} ]);