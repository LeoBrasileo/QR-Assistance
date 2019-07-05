import schedule
import time
import pyrebase
import random
import qrcode
import numpy as np
import cv2

# Configuracion e inicializacion de Firebase
config = {
"apiKey": "AIzaSyAbMl8vM1IHJj6ygDad_TSg4b8daYQVXJA",
"authDomain": "ususarios-3b9a8.firebaseapp.com",
"databaseURL": "https://ususarios-3b9a8.firebaseio.com",
"storageBucket": "ususarios-3b9a8.appspot.com"
}
firebase = pyrebase.initialize_app(config)
db = firebase.database ()

# Callback del scheduler
def job():
	print("Updating Firebase...")

	# Genero mensaje aleatorio
	msg =random.randrange (1,392022)
	msg = str (msg)

	# Subo el mensaje (string) a Firebase
	data = {"Numero": msg}
	db.child ("Numeros").set(data)

	# Genero el QR con el mensaje (string)
	name = 'QR_'+msg+'.jpg'
	qr = qrcode.make(msg)
	print("Nombre del archivo: "+name)
	print("Mensaje: "+msg)
	print("")
	qr.save(name)

	img = cv2.imread(name)
	cv2.imshow('image',img)
	k = cv2.waitKey(1)


#----------------------------------------------------------
schedule.every(7).seconds.do(job)

# Loop
while True:
	schedule.run_pending()
	time.sleep(1)

