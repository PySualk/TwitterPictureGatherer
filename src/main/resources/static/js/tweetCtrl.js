'use strict';

app.controller('TweetModalCtrl', [ '$scope', '$http', '$location', '$modalInstance', 'tweet',
	function($scope, $http, $location, $modalInstance, tweet) {

	    console.log('Loading TweetCtrl');

	    var splitted = tweet.picturePath.split('/');
	    tweet.filename = '/pictures/' + splitted[splitted.length - 1];
	    $scope.tweet = tweet;

	    $scope.close = function() {
		$modalInstance.dismiss('cancel');
	    };

	} ]);