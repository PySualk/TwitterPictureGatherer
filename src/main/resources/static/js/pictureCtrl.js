'use strict';

app.controller('PictureCtrl', [ '$scope', '$http', '$uibModal', function($scope, $http, $uibModal) {

    var url = '/api/tweets?size=30&sort=timestamp,desc';

    console.log('Loading Picture Controller');

    $http.get(url).success(function(data) {
	$scope.totalElements = data.page.totalElements;
	if ($scope.totalElements > 0) {
	    $scope.totalPages = data.page.totalPages;
	    $scope.tweets = data._embedded.tweets;
	    if (angular.isDefined(data._links.prev)) {
		$scope.prev = data._links.prev.href;
	    }
	    if (angular.isDefined(data._links.nex)) {
		$scope.next = data._links.next.href;
	    }
	}

	angular.forEach($scope.tweets, function(item) {
	    var splitted = item.picturePath.split('/');
	    item.filename = '/pictures/' + splitted[splitted.length - 1];
	});

    });

    $scope.showTweet = function(size, tweet) {
	var modalInstance = $uibModal.open({
	    animation : $scope.animationsEnabled,
	    templateUrl : 'tweetModal.html',
	    controller : 'TweetModalCtrl',
	    size : size,
	    resolve : {
		tweet : function() {
		    return tweet;
		}
	    }
	});

	modalInstance.result.then(function() {
	}, function() {
	});
    };

    $scope.nextPage = function() {
	$http.get($scope.next).success(function(data) {
	    $scope.tweets = data._embedded.tweets;
	    if (angular.isDefined(data._links.prev)) {
		$scope.prev = data._links.prev.href;
	    }
	    if (angular.isDefined(data._links.next)) {
		$scope.next = data._links.next.href;
	    }
	    angular.forEach($scope.tweets, function(item) {
		var splitted = item.picturePath.split('/');
		item.filename = '/pictures/' + splitted[splitted.length - 1];
	    });
	});

    };

    $scope.previousPage = function() {
	if (angular.isDefined($scope.prev)) {
	    $http.get($scope.prev).success(function(data) {
		$scope.tweets = data._embedded.tweets;
		if (angular.isDefined(data._links.prev)) {
		    $scope.prev = data._links.prev.href;
		}
		if (angular.isDefined(data._links.next)) {
		    $scope.next = data._links.next.href;
		}
		angular.forEach($scope.tweets, function(item) {
		    var splitted = item.picturePath.split('/');
		    item.filename = '/pictures/' + splitted[splitted.length - 1];
		});
	    });
	}
    };

} ]);
