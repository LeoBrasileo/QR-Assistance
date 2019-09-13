import time
import schedule
import pyrebase

# Configuracion e inicializacion de Firebase
config = {
"apiKey": "AIzaSyAbMl8vM1IHJj6ygDad_TSg4b8daYQVXJA",
"authDomain": "ususarios-3b9a8.firebaseapp.com",
"databaseURL": "https://ususarios-3b9a8.firebaseio.com",
"storageBucket": "ususarios-3b9a8.appspot.com"
}
firebase = pyrebase.initialize_app(config)
db = firebase.database ()

def buscarFecha(): 
	horaLimites = [745, 905, 1040, 1310, 1430, 1600, 1730, 2400] 
	i = 0
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
	print("numero de hora mas min: " + str(int_horamin))
	print(int_horamin)
	print(" ")

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
		
	print(i)

	idMateriActual = db.child("inasistencias").child("5°MA").child(time.strftime("%A")).child(i).child("id").get()

#---------------------------------------------------------
schedule.every(60).seconds.do(buscarFecha)

# Loop
while True:
	schedule.run_pending()
	time.sleep(1)
