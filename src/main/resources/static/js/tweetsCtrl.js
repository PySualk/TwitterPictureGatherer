'use strict';

app.controller('TweetsCtrl', [ '$scope', '$http', '$uibModal', function($scope, $http, $uibModal) {

    console.log('Loading TweetsCtrl');

    var url = '/api/tweets?size=10&sort=timestamp,desc';

    $http.get(url).success(function(data) {
	$scope.totalElements = data.page.totalElements;
	if ($scope.totalElements > 0) {
	    $scope.totalPages = data.page.totalPages;
	    $scope.tweets = data._embedded.tweets;
	    if (angular.isDefined(data._links.prev)) {
		$scope.prev = data._links.prev.href;
	    }
	    $scope.next = data._links.next.href;
	}

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
	    });
	}
    };
    
    $scope.deleteTweet = function(item, index) {
	$http.delete(item._links.self.href).success(function() {
	});
	$scope.tweets.splice(index, 1);
    };
    

} ]);