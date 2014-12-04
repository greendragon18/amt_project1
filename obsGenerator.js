var http = require('http');

var createObs = function(){
  var obs = {
    timestemp:Date.now(),
    value:Math.floor((Math.random() * 100) + 1);
  }
}

var send = function() {
  // Set up the request
  var options = {
        host: 'http://localhost',
        port: '8080',
        path: '/amt_project1/api/sensors/:id/oservations',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Content-Length': data.length
        }
    };

  var req = http.request(post_options, function(res) {
      res.setEncoding('utf8');
      res.on('data', function (chunk) {
          console.log('Response: ' + chunk);
      });
  });

  // post the data
  // req.write(post_data);
  // req.end();
}

//Main
http.get("http://localhost:8080/amt_project1/api/sensors/", function(res) {
  console.log("Got response: " + res.statusCode);
}).on('error', function(e) {
    console.log("Error: " + e.message);
  });