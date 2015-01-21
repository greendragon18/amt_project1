var Client = require('node-rest-client').Client;
var client = new Client();
var async = require('async');
var moment = require('moment');

// This map keeps track of the observation posted by the client, but only if the server has confirmed
// their processing with a successful status code. 
// In this case, the client can assume that the transaction has been successfully processed.
// The map also calculate fact
var processedStats = {};

var logObservation = function(stats, observation) {
    var sensorFact = stats[observation.idSensor] || {
        sensorId: observation.idSensor,
        
        counterFact : {
            numberOfObservation: 0
        },

        dailyStatFact : {}
    };
    sensorFact.counterFact.numberOfObservation += 1;
    var observationDate = observation.timestamp - observation.timestamp % (24 * 60 * 60 * 1000) - 60 * 60 * 1000;
    var sensorDailyStatFact = sensorFact.dailyStatFact[observationDate] || {
        date: observationDate,
        min: observation.value,
        max: observation.value,
        numberOFObservationForThisDay: 0,
        total: 0

    };

    if (sensorDailyStatFact.min > observation.value) sensorDailyStatFact.min = observation.value;
    else if (sensorDailyStatFact.max < observation.value) sensorDailyStatFact.max = observation.value;
    sensorDailyStatFact.numberOFObservationForThisDay += 1;
    sensorDailyStatFact.total += observation.value;
    sensorFact.dailyStatFact[observationDate] = sensorDailyStatFact;

    stats[observation.idSensor] = sensorFact;
}


/*
 * This function returns a function that we can use with the async.js library. 
 */ 
var getOrganisationPOSTRequestFunction = function(organisationName) {

    return function(callback) {
        var requestData = {
            headers:{
                "Content-Type": "application/json"
            },
            data: {
                'name': ''
            }
        };
        
        requestData.data.name = organisationName;
        
        client.post("http://localhost:8080/amt_project1/api/organisations", requestData, function(data, response) {
            var error = null;
            var result = {
                requestData: requestData,
                data: data, 
                response: response
            };
            callback(error, result);
        });
    }
}

/*
 * This function returns a function that we can use with the async.js library. 
 */
var getSensorPOSTRequestFunction = function(idOrganisation, name) {
        
    return function(callback) {
        var requestData = {
            headers:{
                "Content-Type": "application/json"
            },
            data: {
                'name': '',
                'description':'',
                'type':'',
                'isPublic':''
            }
        };
        
        requestData.data.name = name;
        requestData.data.description = "Sensor for testing REST API";
        requestData.data.type = "TEST";
        requestData.data.isPublic = "true";
        
        client.post("http://localhost:8080/amt_project1/api/organisations/" + idOrganisation + "/sensors", requestData, function(data, response) {
            var error = null;
            var result = {
                requestData: requestData,
                data: data, 
                response: response
            };
            callback(error, result);
        });
    }
}

/*
 * This function returns a function that we can use with the async.js library. 
 */ 
var getObservationPOSTRequestFunction = function(idSensor) {
        
    return function(callback) {
        var requestData = {
            headers:{
                "Content-Type": "application/json"
            },
            data: {
                'idSensor' : idSensor,
                'value' : 0, // we will generate a random value below
                'timestamp': 0
            }
        };
        
        requestData.data.value = Math.floor((Math.random() * 100) + 1);
        requestData.data.timestamp = Date.now();
        
        client.post("http://localhost:8080/amt_project1/api/sensors/"+ idSensor +"/observations", requestData, function(data, response) {
            var error = null;
            var result = {
                requestData: requestData,
                data: data, 
                response: response
            };
            callback(error, result);
        });
    }
}

/*
 * Prepare an array of HTTP request functions. We will pass these to async.parallel
 */
var organisationRequests = [];
var sensorRequests = [];
var observationRequest = [];
/*
 * Prepare an array of HTTP responce functions
 */
var organisations = [];
var sensorRequests = [];
var sensors = [];

/*
 * POST organisation requests in parallel
 */
var postOrganisationRequestsInParallel = function(callback) {
	for (var organisation=1; organisation<=process.argv[2]; organisation++) {
	    organisationRequests.push(
	        getOrganisationPOSTRequestFunction("ConcurrencyTestORG"+organisation)
	    );
	};
    console.log("\n\n==========================================");
    console.log("POSTing Organisation requests in parallel");
    console.log("------------------------------------------");
    var numberOfUnsuccessfulResponses = 0;
    async.parallel(organisationRequests, function(err, results) {
        for (var i=0; i<organisationRequests.length; i++) {
            if (results[i].response.statusCode < 200 || results[i].response.statusCode >= 300) {
                console.log("Result " + i + ": " + results[i].response.statusCode);
                numberOfUnsuccessfulResponses++;
            }else{
                organisations.push(results[i].data);
            }
        }
        callback(null, results.length + " oraganisations POSTs have been sent. " + numberOfUnsuccessfulResponses + " have failed.");
    });
}

/*
 * POST sensor requests in parallel
 */
var postSensorRequestsInParallel = function(callback) {
    for (var organisation=0; organisation < organisations.length; organisation++) {
        for (var sensor=1; sensor<=process.argv[3]; sensor++) {
            sensorRequests.push(
                getSensorPOSTRequestFunction(organisations[organisation].idOrganisation, "Sensor"+sensor)
            );
        }
    };

    console.log("\n\n==========================================");
    console.log("POSTing Sensor requests in parallel");
    console.log("------------------------------------------");
    var numberOfUnsuccessfulResponses = 0;
    async.parallel(sensorRequests, function(err, results) {
        for (var i=0; i<sensorRequests.length; i++) {
            if (results[i].response.statusCode < 200 || results[i].response.statusCode >= 300) {
                console.log("Result " + i + ": " + results[i].response.statusCode);
                numberOfUnsuccessfulResponses++;
            } else {
                sensors.push(results[i].data);
            }
        }
        callback(null, results.length + " sensors POSTs have been sent. " + numberOfUnsuccessfulResponses + " have failed.");
    });
}

/*
 * POST Observation requests in parallel
 */
var postObservationRequestsInParallel = function(callback) {
    for (var sensor=0; sensor < sensors.length; sensor++) {
        for (var observation=0; observation<process.argv[4]; observation++) {
            observationRequest.push(
                getObservationPOSTRequestFunction(sensors[sensor].idSensor)
            );
        }
    };
    console.log("\n\n==========================================");
    console.log("POSTing Observation requests in parallel");
    console.log("------------------------------------------");
    var numberOfUnsuccessfulResponses = 0;
    async.parallel(observationRequest, function(err, results) {
        for (var i=0; i<results.length; i++) {
            if (results[i].response.statusCode < 200 || results[i].response.statusCode >= 300) {
                console.log("Result " + i + ": " + results[i].response.statusCode);
                numberOfUnsuccessfulResponses++;
            } else {
                logObservation(processedStats, results[i].data);
            }
        }
        callback(null, results.length + " observations POSTs have been sent. " + numberOfUnsuccessfulResponses + " have failed.");
    });
}

/*
 * Fetch server-side state (list of fact). The list of fact is passed to the callback function.
 */
var checkValues = function(callback) {
    console.log("\n\n==========================================");
    console.log("Comparing client-side and server-side stats");
    console.log("------------------------------------------");
    var requestData = {
        headers:{
            "Accept": "application/json"
        }
    };
    client.get("http://localhost:8080/amt_project1/api/facts", requestData, function(data, response) {
        var numberOfErrors = 0;

        for(var i=0; i<data.length; i++) {
            var idSensor = data[i].properties.idSensor;
            if (data[i].type == "counter" && idSensor in processedStats) {
                var serverSideNbObservation = data[i].properties.nbObervation;
                var clientSideNbObservation = processedStats[idSensor].counterFact.numberOfObservation;
                if (serverSideNbObservation !== clientSideNbObservation) {
                    numberOfErrors++;
                    console.log("Sensor " + idSensor + " --> Server/Client number of observation : " + serverSideNbObservation + "/" + clientSideNbObservation + "  X");
                }
            } else if (data[i].type == "dailyStat" && idSensor in processedStats) {
                var date = moment(data[i].properties.date, "DD/MM/YYYY").valueOf();
                var serverSideMin = data[i].properties.min;
                var serverSideMax = data[i].properties.max;
                var serverSideAverage = data[i].properties.average;
                var clientSideMin = processedStats[idSensor].dailyStatFact[date].min;
                var clientSideMax = processedStats[idSensor].dailyStatFact[date].max;
                var clientSideAverage = processedStats[idSensor].dailyStatFact[date].total / processedStats[idSensor].dailyStatFact[date].numberOFObservationForThisDay;

                if (serverSideMin !== clientSideMin || serverSideMax !== clientSideMax || serverSideAverage !== clientSideAverage) {
                    numberOfErrors++;
                    console.log("Sensor " + idSensor + " --> Server/Client (min, max, average) : (" + serverSideMin + ", " + serverSideMax + ", " + serverSideAverage + ")/(" + clientSideMin + ", " + clientSideMax + ", " + clientSideAverage  + ") X");
                }
            }
        }
        callback(null, "The client side and server side values have been compared. Number of corrupted facts: " + numberOfErrors);
    });
}


//MAIN
if(process.argv.length < 5) {
    console.log("Usage: node obsGenerator numberOfOrganisation numberOfSensor numberOfObservation");
    console.log("Example : node clientTest 2 4 60");
    conlole.log("In this example we create 2 organisations and for each organisation we create 4 sensors and for each sensor we create 60 observations")
    process.exit(1);
}

async.series([
    postOrganisationRequestsInParallel,
    postSensorRequestsInParallel,
    postObservationRequestsInParallel,
    checkValues
], function(err, results) {
    console.log("\n\n==========================================");
    console.log("Summary");
    console.log("------------------------------------------");
    //console.log(err);
    console.log(results);
});
