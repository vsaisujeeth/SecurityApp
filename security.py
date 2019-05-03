# -*- coding: utf-8 -*-
"""
Created on Thu Apr  4 18:52:53 2019

@author: user
"""

import cv2
import numpy as np
import os
import datetime
import json


from firebase import firebase
from google.cloud import vision
from twilio.rest import Client
from pusher_push_notifications import PushNotifications


beams_client = PushNotifications(
    instance_id='34215f76-aae8-4584-8643-a1a72e605faa',
    secret_key='E404EC114F01BD1DC1E6175FCD2A61FDD949FAD8FE9229FF9C43628BA2E5AF40',
)




os.environ["GOOGLE_APPLICATION_CREDENTIALS"]="google_ocr.json"
client = vision.ImageAnnotatorClient()

firebase = firebase.FirebaseApplication('https://securityapp-c3233.firebaseio.com', None)
result = firebase.get('/college/iit patna/vehicles', None)
#print(result)
#with open('json.json') as json_file:  
#    result = json.load(json_file)

now = datetime.datetime.now()

def Notify(vecNo):
    response = beams_client.publish(
    interests=['hello'],
    publish_body={
            'fcm': {
                    'notification': {
                            'title': 'Alert',
                            'body': 'The vechicle with No: '+vecNo+' is autherised to enter. Added to Log',
                            },
                    },
                },
    )
    
    

def search(num):
    num = num.upper()
    if num in result:
        print("Authenticated")
        date= now.strftime("%Y-%m-%d,%H:%M %p")
        Notify(num)
        phNo = result[num]['mobile']
        print(phNo)
        send_sms(phNo,num)
        firebase.patch('https://securityapp-c3233.firebaseio.com/college/iit patna/log/'+date, {
          'date' : now.strftime("%Y-%m-%d,%H:%M %p"),
          'time' : now.strftime("%H:%M %p"),
          'vehicleno' : num,
          'mobile':phNo,
        })

    else:
        print("Not Authenticated")
        
def send_sms(phNo, vecNo):
    if len(phNo)<10:
        print("Wrong Number")
        return
    elif len(phNo)==10:
        phNo = "+91"+phNo
    
    account_sid = "AC0ef3a43c9664fafc94f8184862d29bf7"
    auth_token = "8ae9c6a71ffee197bbc73afd49f8d96f"

    client = Client(account_sid, auth_token)
    print("Sending SMS")
    
    message = client.messages.create(
            to=phNo, 
            from_="+447480537014",
            body="Hello, your vechicle with the registration number: " + vecNo +" has reached the gate 1.")

    print(message.sid)
    
def enhance(text):
    text= str(text)
    text=text.replace(" ","")
    text=text.replace("-","")
    text=text.replace("/","")
    text=text.replace(".","")
    text=text.replace("\n","")
    text=text.upper().strip()
    
    if len(text)==10:
        part1 = text[:2]
        if '0' in part1:
          part1=part1.replace("0","O")  
        part2 = text[2:4]
        if 'O' in part2:
          part2=part2.replace("O","0")  
        part3 = text[4:6]
        if '0' in part3:
         part3=part3.replace("0","O")  
        part4 = text[6:]
        if 'O' in part4:
          part4=part4.replace("O","0")  
          
        text= part1+part2+part3+part4
    return text
    

      
def ocr(image):
    success, img = cv2.imencode('.jpg', image)
    content = img.tobytes()
    image = vision.types.Image(content=content)
    resp = client.text_detection(image=image)
    for text in resp.text_annotations:
            number= enhance(text.description)
            print(number)
            search(number)
            break
            
        
        
    
#search('AP00WQ1234')


cap = cv2.VideoCapture("rtsp://admin:vsaisujeeth1@192.168.137.230/live/ch00_0")
#cap = cv2.VideoCapture(0)
#cap = cv2.VideoCapture('11.mp4')


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
   
    print(total)
    
    if total > 7000:
        check=check+1
   
    
    if total <5000 and check>3:
        print("----------------------------")
        image = frame1
        image = image[24:480, 0:640]
        cv2.imwrite("frame%d.jpg" % count, image) 
        ocr(image)
        count= count+1
        check=0
        
    if total < 5000:
        check=0
        
    cv2.imshow('hello',dilated)
    frame2 =frame1
    ret, frame1 =cap.read()
    
    if cv2.waitKey(20) & 0xFF == ord('q'):
        break

    
cap.release()
cv2.destroyAllWindows()
