from firebase import firebase

#This is a script for when a car goes into the parking lot
firebaseGet = firebase.FirebaseApplication('https://parksmart-f23cb.firebaseio.com')
GilmanCapacity = firebaseGet.get('/Schools/UCSD/Gilman','/Capacity')

GilmanCapacity += 1

result = firebaseGet.patch('/Schools/UCSD/Gilman/',{'Capacity' : GilmanCapacity})



