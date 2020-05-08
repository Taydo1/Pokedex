import os
arr = os.listdir()
#print(arr)

for i in range(809):
    iStr=str(i)+".png"
    if(i<10): iStr="00"+iStr
    elif(i<100): iStr="0"+iStr
    if(not iStr in arr):
        print(i, iStr)
