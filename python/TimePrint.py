import time
import schedule

def job(): 
	print("Fecha y hora actuales: ", time.ctime())
	print("Año actual: ", time.strftime("%Y"))
	print("Mes del año: ", time.strftime("%B"))
	print("Número de semana del año: ", time.strftime("%W"))
	print("Dia de la semana: ", time.strftime("%w"))
	print("Dia del año: ", time.strftime("%j"))
	print("Dia del mes: ", time.strftime("%d"))
	print("Dia de la semana: ", time.strftime("%A"))


#---------------------------------------------------------
schedule.every(10).seconds.do(job)

# Loop
while True:
	schedule.run_pending()
	time.sleep(1)
