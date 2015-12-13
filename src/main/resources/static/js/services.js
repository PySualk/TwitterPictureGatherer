'use strict';

// http://stackoverflow.com/questions/22478933/call-server-every-5-seconds-returns-stack-overflow-error
app.factory('pollingFactory', function($timeout) {

    var timeIntervalInSec = 1;

    function callFnOnInterval(fn, timeInterval) {

	var promise = $timeout(fn, 1000 * timeIntervalInSec);

	return promise.then(function() {
	    callFnOnInterval(fn, timeInterval);
	});
    }

    return {
	callFnOnInterval : callFnOnInterval
    };
});