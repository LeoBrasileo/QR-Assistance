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

division = "5MA" #esto deberia cambiar cada un tiempo pero en nuestro caso solo vamos a trabajar con una division

def AgregarAusentes():
	horaminString = time.strftime("%H") + time.strftime("%M")
	int_horamin = int(horaminString)
	horaLimites = [744, 905, 1040, 1309, 1430, 1600, 1730, 2400] 
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
	numeroDia = int (time.strftime("%d"))
	StringNumeroDia = str(numeroDia)
	fechaESP = StringNumeroDia + switcher.get(time.strftime("%B"),"Entrada invalida")

	#-------------------------------------------------
	#zona para harcordear

	
	#-------------------------------------------------

	while diaESP == "sabado" or diaESP == "domingo":
		print("Hoy no hay clases")
		time.sleep(85800) #23 horas 50 minutos
		return

	while int_horamin > horaLimites[i]:
		i = i + 1
	if i >= 7:
		#Aca se puede colgar
		while(not(int_horamin > 730 and int_horamin < 745)):
			#ACTUALIZAR HORAMIN
			horaminString = time.strftime("%H") + time.strftime("%M")
			int_horamin = int(horaminString)
			print("En este momento no hay clases")
			#time.sleep(2000) #esto tiene que ser mas chico que la brecha del while
			return

	idMateriActual = db.child("horarios").child(division).child(diaESP).child(i).child("id").get()
	if not idMateriActual.val():
		print("En este momento no hay ninguna materia")
		return
	idMateriActualString = str (idMateriActual.val())

	alumnos = db.child("divisiones").child(division).get()
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


#---------------------------------------------------------
AgregarAusentes()
#---------------------------------------------------------

# Loop
while True:
	schedule.run_pending()
	time.sleep(1)


'''
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
	#tengo que dividirme alumnos en 2 variables: alumnosdnis y alumnosnombres
	#Usar for para que busque solo entre los pares y cree otro vector solo de dnis o nombres
	for x in range(0,20):
		if x % 2 == 0:
			#el numero es par
			alumnosdnis = max(alumnos[x])
	print(alumnos[3])
	print(alumnosdnis[2])
'''
#todo esto era para la funcion de copiar y pegar rama alumnos pero ya no lo necesito pero me da pena borrarlo porque estuve trabajando mucho en esto