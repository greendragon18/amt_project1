/*
 * To use this script, use the command:
 * > node obsGenerator ['fill', 'simulate'] ([fill_amount, simulation_cadency])
 * 
 * NOTE: to make this script run, you have to install the following packages:
 * async (https://www.npmjs.org/package/async)
 * sleep (https://www.npmjs.org/package/sleep)
 *
 * Your can install both with NPM
 * npm install package_name 
 *
 * (You'll maybe need the admin rights)
 */
var http = require('http');
var async = require('async');
var sleep = require('sleep');

var sensors;

//Create Observation
var createObs = function(){
  var obs = {
    timestamp:Date.now(),
    value:Math.floor((Math.random() * 100) + 1)
  }

  return obs;
}

//Utils
var error = function(err){
    console.log(err) ;
} ;

//Working functions
var simulate = function() {
  async.forever(sendLoop, error);
}

var sendLoop = function(done) {
  var limit = (process.argv[3] === undefined)?30:process.argv[3];
  send(sensors[Math.floor(sensors.length * Math.random())].idSensor);
  var timer = Math.floor((Math.random() * limit)+1);
  console.log("Wait " + timer + " sec...");
  sleep.sleep(timer); //entre 1 et 30 sec.
  setImmediate(done);
}

var execute = function() {
  sensors.forEach(function (val, index, array) {
    var num = (process.argv[3] === undefined)?5000:process.argv[3];
    for(i=0; i <= num; i++) {
      send(val.idSensor);
    }
  });
}

//POST
var send = function(id) {
  var data = JSON.stringify(createObs());

  // Set up the request
  var options = {
        //host: 'http://localhost',
        port: '8080',
        path: '/amt_project1/api/sensors/' + id + '/observations/',
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Content-Length': data.length
        }
    };

  var req = http.request(options, function(res) {
      res.setEncoding('utf8');
      res.on('data', function(chunk) {
        console.log("Add " + chunk + " to sensor #" + id);
      });
  });

  req.on('error', function (err) {
    console.log(err.message);
  });

  req.write(data);
  req.end();
}

//Main
if(process.argv.length < 3) {
  console.log("Usage: obsGenerator ['fill', 'simulate'] (nombre_fill)");
  process.exit(0);
}

http.get("http://localhost:8080/amt_project1/api/sensors/", function(res) {
  var str = '';

  res.on('data', function(chunk) {
    str += chunk;
  });

  res.on('end', function() {
    sensors = JSON.parse(str);
    if(process.argv[2] == 'fill') {
      execute();
    }
    else if(process.argv[2] == 'simulate') {
      simulate();
    }
    
  });

}).on('error', function(e) {
    console.log("Error: " + e.message);
  });