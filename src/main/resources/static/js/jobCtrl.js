'use strict';

app.controller('JobsCtrl', [ '$scope', '$http', '$uibModal', '$log',
	function($scope, $http, $uibModal) {

	    console.log('Loading JobsCtrl');

	    $http.get('/api/jobs').success(function(data) {
		if (angular.isUndefined(data._embedded)) {
		    $scope.jobs = [];
		} else {
		    $scope.jobs = data._embedded.jobs;
		}
	    });

	    $scope.createJob = function(size, job) {
		var modalInstance = $uibModal.open({
		    animation : $scope.animationsEnabled,
		    templateUrl : 'jobModal.html',
		    controller : 'JobsModalCtrl',
		    size : size,
		    resolve : {
			job : function() {
			    return job;
			}
		    }
		});

		modalInstance.result.then(function(job) {
		    var keywords = job.keywords.split(' ');
		    job.keywords = keywords;
		    $http.post('/api/jobs', JSON.stringify(job)).success(function() {
			$scope.jobs.push(job);
		    }).error(function() {
			console.log('error adding job');
		    });

		}, function() {
		});
	    };

	    $scope.deleteJob = function(job, index) {
		$http.delete(job._links.self.href).success(function(data) {
		    $scope.jobs.splice(index, 1);
		}).error(function(data) {
		    console.log('error deleting job');
		});
	    };

	} ]);
