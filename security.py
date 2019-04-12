# -*- coding: utf-8 -*-
"""
Created on Thu Apr  4 18:52:53 2019

@author: user
"""

import cv2
import numpy as np
import os
import datetime


from firebase import firebase
from google.cloud import vision

os.environ["GOOGLE_APPLICATION_CREDENTIALS"]="google_ocr.json"
client = vision.ImageAnnotatorClient()

firebase = firebase.FirebaseApplication('https://securityapp-c3233.firebaseio.com', None)
result = firebase.get('/college/iit patna/vehicles', None)
#print(result)


now = datetime.datetime.now()

def search(num):
    num = num.upper()
    if num in result:
        print("Authenticated")
        firebase.patch('https://securityapp-c3233.firebaseio.com/college/iit patna/log/'+num, {
          'date' : now.strftime("%Y/%m/%d,%H:%M %p"),
          'time' : now.strftime("%H:%M %p"),
          'vehicleno' : num,
        })

    else:
        print("Not Authenticated")
        

def ocr(image):
    success, encoded_image = cv2.imencode('.jpg', image)
    content = encoded_image.tobytes()
    image = vision.types.Image(content=content)
    resp = client.text_detection(image=image)
    for text in resp.text_annotations:
        print(text.description)
       2 search(text.description)
        break
    
#search('AP00WQ1234')


cap = cv2.VideoCapture('vid1.mp4')
#cap = cv2.VideoCapture("rtsp://admin:vsaisujeeth1@192.168.137.173/live/ch00_0")
#cap = cv2.VideoCapture(0)

if cap.isOpened():
    ret, frame = cap.read()
else:
    ret = False

ret, frame1 = cap.read()   
ret, frame2 = cap.read()  

count = 0      
check = 0
     
while ret :
    d= cv2.absdiff(frame1,frame2)  
    
    grey = cv2.cvtColor(d,cv2.COLOR_BGR2GRAY)
    blur = cv2.blur(grey, (5,5))  
    ret, th= cv2.threshold(grey, 20, 255, cv2.THRESH_BINARY)
    
    dilated = cv2.dilate(th,np.ones((3,3),np.uint8),iterations=1 )
    
    img = np.array(dilated)
    total = np.sum(img > 0)
   
    #print(total)
    
    if total > 7000:
        check=1
    
    if total <5000 and check==1:
        image = frame1
        cv2.imwrite("frame%d.jpg" % count, image) 
        ocr(image)
        count= count+1
        check=0
        
    cv2.imshow('hello',dilated)
    frame2 =frame1
    ret, frame1 =cap.read()
    
    if cv2.waitKey(20) & 0xFF == ord('q'):
        break

    
cap.release()
cv2.destroyAllWindows()

