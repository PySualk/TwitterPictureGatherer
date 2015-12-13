'use strict';

var DATE_FORMAT = 'dd-MMMM-yyyy';
var PLACEHOLDER_NAME = 'Enter a name for the job';
var PLACEHOLDER_DESC = 'Enter a description for the job';
var PLACEHOLDER_KEYWORDS = 'Enter a list of keywords separated by a blank space';
var PLACEHOLDER_VALIDFROM = 'Enter a start date for the job';
var PLACEHOLDER_VALIDTO = 'Enter an end date for the job';

app.controller('JobsModalCtrl', [ '$scope', '$http', '$modalInstance',
	function($scope, $http, $modalInstance) {

	    $scope.newJob = {};
	    $scope.newJobFields = [ {
		key : 'name',
		type : 'input',
		templateOptions : {
		    type : 'text',
		    label : 'Name',
		    placeholder : PLACEHOLDER_NAME,
		    required : true

		}
	    }, {
		key : 'description',
		type : 'textarea',
		templateOptions : {
		    placeholder : PLACEHOLDER_DESC,
		    label : 'Description',
		    rows : 3,
		    cols : 3
		}

	    }, {
		key : 'keywords',
		type : 'input',
		templateOptions : {
		    type : 'text',
		    label : 'Keywords',
		    placeholder : PLACEHOLDER_KEYWORDS,
		    required : true
		}
	    }, {
		key : 'validFrom',
		type : 'datepicker',
		templateOptions : {
		    label : 'Valid From',
		    type : 'text',
		    datepickerPopup : DATE_FORMAT,
		    placeholder : PLACEHOLDER_VALIDFROM
		}
	    }, {
		key : 'validTo',
		type : 'datepicker',
		templateOptions : {
		    label : 'Valid To',
		    type : 'text',
		    datepickerPopup : DATE_FORMAT,
		    placeholder : PLACEHOLDER_VALIDTO
		}
	    } ];

	    $scope.createJob = function() {
		$modalInstance.close($scope.newJob);
	    };

	    $scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	    };

	} ]);