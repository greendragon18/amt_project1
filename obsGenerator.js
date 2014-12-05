var http = require('http');
var async = require('async');

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
var simulate = function(sensors) {
  async.forever(sendLoop(sensors), error);
}

var sendLoop = function(sensors, done) {
  send(sensors[Math.floor(sensors.length * Math.random())]);
  setTimeout(function(){}, Math.floor((Math.random() * 30000)+500)); //entre 500 milis et 30 sec.
  setImmediate(done);
}

var execute = function(sensors) {
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
  console.log(data);

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
      res.on('data', function (chunk) {
        console.log('Response: ' + chunk);
      });
  });

  req.on('error', function (err) {
    //console.log(req);
    console.log(err.message);
  });

  // post the data
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
    if(process.argv[2] == 'fill') {
      execute(JSON.parse(str));
    }
    else if(process.argv[2] == 'simulate') {
      simulate(JSON.parse(str))
    }
    
  });

}).on('error', function(e) {
    console.log("Error: " + e.message);
  });