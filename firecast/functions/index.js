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

// [START addMessage]
// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/mensaje
// [START addMessageTrigger]
exports.addMessageForTest1 = functions.https.onRequest(async (req, res) => {
    // [END addMessageTrigger]
      const mensaje = "no quiero mas childs";
      // [START adminSdkPush]
      // Push the new message into the Realtime Database using the Firebase Admin SDK.
      const snapshot = await admin.database().ref('javascript').push(mensaje);
      // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
      res.redirect(303, snapshot.ref.toString());
      // [END adminSdkPush]
    });
    // [END addMessage]

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