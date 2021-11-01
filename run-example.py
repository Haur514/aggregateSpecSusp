import os
import sys
import shutil

base = 'C:/Users/h-yosiok/Lab/aggregateSpecSusp'
countRankBase = 'C:/Users/h-yosiok/Lab/countRankBSBFL'
jarFile = base + '/build/libs/aggregateSpecSusp.jar'

rangeStart = 1
rangeEnd = 50
os.system('gradle jar')

for i in range(rangeStart, rangeEnd):
    os.chdir(base)
    os.chdir('spectrum')
    sub = "math"+str(i).zfill(3)
    os.chdir(sub)
    shutil.copy("./TR.txt", "./../../TR.txt")
    os.chdir('..')
    os.chdir('..')
    os.system('java -jar ./build/libs/aggregateSpecSusp.jar')
    shutil.copy("./BSBFL.txt", "./spectrum/" + sub + "/BSBFL.txt")
    shutil.copy("./SBFL.txt", "./spectrum/" + sub + "/SBFL.txt")

os.system('java -jar ' + countRankBase + '/build/libs/countRankBSBFL.jar')
