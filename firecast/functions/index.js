const functions = require('firebase-functions');
const os = require("os");
const path = require("path");
const cors = require("cors")({ origin: true });
const fs = require("fs");

var currentTime = new Date();
var currentHours = currentTime.getHours();
var currentMinutes = currentTime.getMinutes();
var currentSeconds = currentTime.getSeconds();

currentMinutes = ( currentMinutes < 10 ? "0" : "" ) + currentMinutes;
currentSeconds = ( currentSeconds < 10 ? "0" : "" ) + currentSeconds;
currentHours = currentHours - 3;
currentHours = ( currentHours > 12 ) ? currentHours - 12 : currentHours;
currentHours = ( currentHours === 0 ) ? 12 : currentHours;
var timeOfDay = ( currentHours < 12 ) ? "AM" : "PM";
var currentTimeString = currentHours + ":" + currentMinutes + ":" + currentSeconds + " " + timeOfDay;

const admin = require('firebase-admin');
admin.initializeApp();

 exports.helloWorld = functions.https.onRequest((request, response) => {
  response.send(currentTimeString);
 });

exports.addMessageForDatabase1 = functions.https.onRequest(async (req, res) => {
      const mensaje = "ProBande3lOsbdYHBjbfe";
      const snapshot = await admin.database().ref('javascript').push(mensaje);
      res.redirect(303, snapshot.ref.toString());
    });

exports.ChangeMessage = functions.database.ref("javascript/{childId}")
.onCreate((snapshot, context) => {
  const original = snapshot.val();
  const nuevo = original.toUpperCase();
  return snapshot.ref.parent.child("upercase").set(nuevo);
});

    //Function with Database
 // https://firebase.google.com/docs/functions/database-events

 // Create and Deploy Your First Cloud Functions
// https://firebase.google.com/docs/functions/write-firebase-functions