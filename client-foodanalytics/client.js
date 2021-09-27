const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

var fs = require('fs');
var express = require('express');
var app = express();

app.get('/', function (req, res) {

    fs.readFile('index.html', function (err, data) {
        res.writeHead(200, { 'Content-Type': 'text/html', 'Content-Length': data.length });
        res.write(data);
        res.end();
    });
});

app.get('/pkce', function (req, res) {

    fs.readFile('index-pkce.html', function (err, data) {
        res.writeHead(200, { 'Content-Type': 'text/html', 'Content-Length': data.length });
        res.write(data);
        res.end();
    });
});

app.listen(port, hostname, function () {
    console.log('Server running at http://' + hostname + ':' + port + '/');
})