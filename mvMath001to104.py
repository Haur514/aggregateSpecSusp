import os
import sys
import shutil

for i in range(1,100):
    sub = 'math'+str(i).rjust(3,'0')
    shutil.copy('/opt/apr-data/example/'+sub+'/TR.txt','/home/h-yosiok/Lab/2022/aggregateSpecSusp/route/'+sub+'.txt')
