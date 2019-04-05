# -*- coding: utf-8 -*-
"""
Created on Thu Apr  4 18:52:53 2019

@author: user
"""

import cv2
import numpy as np
cap = cv2.VideoCapture('brake_1.mp4')
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
   
    print(total)
    
    if total > 7000:
        check=1
    
    if total <1000 and check==1:
        image = frame1
        cv2.imwrite("frame%d.jpg" % count, image) 
        count= count+1
        check=0
        
    cv2.imshow('hello',dilated)
    frame2 =frame1
    ret, frame1 =cap.read()
    
    if cv2.waitKey(20) & 0xFF == ord('q'):
        break

    
cap.release()
cv2.destroyAllWindows()