# TwitterPictureGatherer



## What does it do? (Short)
* Collect data via the Twitter Streaming API
* Store Tweet (text, date, username, picture url, language, coordinates if present) at a MongoDB database
* Store Picture in an external directory
* Collected data can be displayed via web interface or via Rest API

## What does ist do? (Long)
...
## Getting Started
* Clone the repository by running <code>git clone https://github.com/PySualk/TwitterPictureGatherer.git</code>
* As a first step please open the file <code>application.properties</code> and modify your configuration here.
* Configure your MongoDB connection by entering hostname and port and optionally username and password.
* Configure the local directory where the pictures are being stored (the application only gatheres Tweets with pictures).
* Enter your consumer key, consumer secret, access token and access secret obtained from Twitter. If you did not have them yet register with your Twitter account at <a href="https://dev.twitter.com/" target="_blank">https://dev.twitter.com/</a>.
* Download and install Maven
* Now you are able to build a standalone jar file with your configuration by running <code>mvn clean install</code> or simply start the application with <code>mvn spring-boot:run</code>
* Define some jobs, so the application is able to gather some data from Twitter for you.
* By pressing <code>Start Gathering Tweets</code> the application starts collecting Tweets from the Twitter Streaming API. All Tweets that match your defined jobs are being stored at the MongoDB database. The corresponding pictures are being stored at the local directory you defined earlier.

## Used technology stack
* Backend: Spring Boot, MongoDB, Maven, Twitter Hosebird Client
* Frontend: AngularJS, Bower, Bootstrap, Bootswatch

## Screenshots

![Screen Shot 1](/screen1.jpg?raw=true "Screen Shot 1")
![Screen Shot 2](/screen2.jpg?raw=true "Screen Shot 2")
![Screen Shot 3](/screen3.jpg?raw=true "Screen Shot 3")
