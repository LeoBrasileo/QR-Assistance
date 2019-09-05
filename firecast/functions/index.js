const functions = require('firebase-functions');
const os = require("os");
const path = require("path");
const cors = require("cors")({ origin: true });
const fs = require("fs");

const admin = require('firebase-admin');
admin.initializeApp();

 exports.helloWorld = functions.https.onRequest((request, response) => {
  response.send("🍕🍕🍕🍕🍕🍕🍕🍕");
 });

exports.addMessageForTest1 = functions.https.onRequest(async (req, res) => {
      const mensaje = "no quiero mas childs";
      const snapshot = await admin.database().ref('javascript').push(mensaje);
      res.redirect(303, snapshot.ref.toString());
    });

exports.addMessageForTest2 = functions.https.onRequest(async (req, res) => {
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