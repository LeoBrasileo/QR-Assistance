import time
import schedule
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
"projectId": "ususarios-3b9a8",
"storageBucket": "ususarios-3b9a8.appspot.com",
"appId": "1:1017064696866:web:aefbbb731d7d8b40b4ae7b"
}
firebase = pyrebase.initialize_app(config)
db = firebase.database ()

division = "5°MA" #esto deberia cambiar cada un tiempo pero en nuestro caso solo vamos a trabajar con una division

def AgregarAusentes():

	alumnos = db.child("divisiones").child(division).get()

	horaminString = time.strftime("%H") + time.strftime("%M")
	int_horamin = int(horaminString)
	horaLimites = [745, 905, 1040, 1310, 1430, 1600, 1730, 2400] 
	i = 0 #i es el apuntador de timbres, es el numero que usa la db para estructurar las materias

	switcher = {
		"Monday": "lunes",
		"Tuesday": "martes",
		"Wednesday": "miercoles",
		"Thursday": "jueves",
		"Friday": "viernes",
		"Saturday": "sabado",
		"Sunday": "domingo",
		"January": "ene",
		"February": "feb",
		"March": "mar",
		"April": "apr",
		"May": "may",
		"June": "jun",
		"July": "jul",
		"September": "sep",
		"October": "oct",
		"November": "nov",
		"December": "dec",
		"August": "ago"
		}
	diaESP = switcher.get(time.strftime("%A"),"Argumento invalido")
	fechaESP = time.strftime("%d") + switcher.get(time.strftime("%B"),"Entrada invalida")

	#-------------------------------------------------
	#zona para harcordear


	#-------------------------------------------------

	if diaESP == "sabado" or diaESP == "domingo":
		print("Hoy no hay clases")
		time.sleep(86350)

	while int_horamin > horaLimites[i]:
		i = i + 1
	if i >= 7:
		#Aca se puede colgar
		while(not(int_horamin > 730 and int_horamin < 745)):
			#ACTUALIZAR HORAMIN
			horaminString = time.strftime("%H") + time.strftime("%M")
			int_horamin = int(horaminString)
			print("En este momento no hay clases")
			time.sleep(2000) #esto tiene que ser mas chico que la brecha del while
			print("Actualizando...")

	idMateriActual = db.child("horarios").child(division).child(diaESP).child(i).child("id").get()
	if not idMateriActual.val():
		print("En este momento no hay ninguna materia")
		time.sleep(14400)#4 horas
	idMateriActualString = str (idMateriActual.val())
	#Si el programa no pasa por aca crashea, asi que hay que procurar utilizarlo solo en tiempo de clase

	dictAlumnos = db.child("divisiones").child(division).get().val()
	for x in dictAlumnos:
		print(x)
        #todos los dnis son esta variable x que esta solo en este for
		faltasMateria = db.child("faltas").child(division).child(x).child(idMateriActualString).get()
		if not faltasMateria.val():
			db.child("faltas").child(division).child(x).child(idMateriActualString).set(0)
		faltasMateria = int (db.child("faltas").child(division).child(x).child(idMateriActualString).get().val())
		faltasMateria = faltasMateria + 2
		db.child("faltas").child(division).child(x).child(idMateriActualString).set(faltasMateria)
		db.child("escaneado").child(x).set(0)

	db.child("inasistencias").child(division).child(idMateriActualString).child(fechaESP).child("ausentes").set(alumnos.val())
	print("Ausentes actualizados correctamente")

def QRS():
	# Genero mensaje aleatorio
	msg =random.randrange (1,392022009)
	msg = str (msg)

	# Subo el mensaje (string) a Firebase
	data = {"numeros": msg}
	db.child ("qrs").set(data)

	# Genero el QR con el mensaje (string)
	name = 'QR_'+msg+'.jpg'
	qr = qrcode.make(msg)
	print("string del QR actual: "+msg)
	print("")
	qr.save("QRactual.jpg")

	img = cv2.imread("QRactual.jpg")
	cv2.imshow('image',img)
	k = cv2.waitKey(1)


#---------------------------------------------------------
AgregarAusentes()
schedule.every(3).seconds.do(QRS) #Cada 2 segundos crea un qr y su respectivo string en la db
schedule.every(4850).seconds.do(AgregarAusentes) #agrega la rama ausentes a cada materia cada 90 minutos
#---------------------------------------------------------

# Loop
while True:
	schedule.run_pending()
	time.sleep(1)