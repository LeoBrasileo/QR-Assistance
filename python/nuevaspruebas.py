import time
import schedule
import pyrebase
import numpy as np

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

numerosImpares = [1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51]

division = "5°MA" #esto deberia cambiar cada un tiempo pero en nuestro caso solo vamos a trabajar con una division

def buscarFecha(): 
	horaLimites = [745, 905, 1040, 1310, 1430, 1600, 1730, 2400] 
	i = 0 #i es el apuntador de timbres, es el numero que usa la db para estructurar las materias
	print("Fecha y hora actuales: ", time.ctime())
	print("Año actual: ", time.strftime("%Y"))
	print("Mes del año: ", time.strftime("%B"))
	print("Número de semana del año: ", time.strftime("%W"))
	print("Dia de la semana: ", time.strftime("%w"))
	print("Dia del año: ", time.strftime("%j"))
	print("Dia del mes: ", time.strftime("%d"))
	print("Dia de la semana: ", time.strftime("%A"))
	horaminString = time.strftime("%H") + time.strftime("%M")
	int_horamin = int(horaminString)
	switcher = {
		"Monday": "lunes",
		"Tuesday": "martes",
		"Wednesday": "miercoles",
		"Thursday": "jueves",
		"Friday": "viernes",
		"Saturday": "sabado",
		"Sunday": "domingo"
		}
	diaESP = switcher.get(time.strftime("%A"),"Argumento invalido")

	if diaESP == "sabado" and diaESP == "domingo":
		time.sleep(86350)

	int_horamin = 759

	while int_horamin > horaLimites[i]:
		i = i + 1
	if i >= 7:
		#Aca se puede colgar
		while(not(int_horamin > 730 and int_horamin < 745)):
			#ACTUALIZAR HORAMIN
			horaminString = time.strftime("%H") + time.strftime("%M")
			int_horamin = int(horaminString)
			print("Descansando")
			time.sleep(900) #esto tiene que ser mas chico que la brecha del while
			print("me despierto")

	idMateriActual = db.child("horarios").child(division).child(diaESP).child(i).child("id").get()
	idMateriActualString = str (idMateriActual.val())
	print("la materia actual es: " + idMateriActualString)

	alumnos = db.child("divisiones").child(division).get()
	alumnos = str (alumnos.val()) #OrderedDict([('1', 'Pruebas'), ('43626546', 'Luciano Garbarino'), ('43725334', 'Dan Farach'), ('43917960', 'Dan Szujatovich'), ('43992906', 'Gianfranco Loayza'), ('44159126', 'Leonel Braginski')])
	alumnos = alumnos.replace("OrderedDict", "")
	alumnos = alumnos.replace("(", "")
	alumnos = alumnos.replace("[", "")
	alumnos = alumnos.replace(")", "")
	alumnos = alumnos.replace("]", "")
	alumnos = alumnos.replace(",", "")
	alumnos = alumnos.replace("'", "") #1 Pruebas 43626546 Luciano Garbarino 43725334 Dan Farach 43917960 Dan Szujatovich 43992906 Gianfranco Loayza 44159126 Leonel Braginski
	alumnos = alumnos.split(" ")
	#En este punto la variable alumnos es un vector en la que los numeros pares son dnis y los impares nombres con una separacion entre nombre y apellido por guion bajo
	#tengo que dividirme alumnos en 2 varianles: alumnosdnis y alumnosnombres
	print(alumnos[3])
#---------------------------------------------------------
schedule.every(3).seconds.do(buscarFecha)

# Loop
while True:
	schedule.run_pending()
	time.sleep(1)