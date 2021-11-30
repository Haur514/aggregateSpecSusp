import os
import sys
import shutil

base = 'C:/Users/h-yosiok/Lab/aggregateSpecSusp'
countRankBase = 'C:/Users/h-yosiok/Lab/countRankBSBFL'
jarFile = base + '/build/libs/aggregateSpecSusp.jar'

rangeStart = 1
rangeEnd = 105
os.system('gradle jar')

weightFunctionName = {0: "Haruka",
                      1: "Yoshiruka",
                      2: "Ruka",
                      3: "Haru",
                      4: "Haka",
                      5: "Yoruka",
                      6: "Senko",
                      7: "YoshiokaHaruka",
                      8: "functionC"}

args = sys.argv
weightType = weightFunctionName[int(args[1])]
data = {}
for j in range(12,19):
    os.system('java -jar C:/Users/h-yosiok/Lab/countRankBSBFL/build/libs/countRankBSBFL.jar')
    shutil.copy("./sample.txt", "./"+weightType+str(round(j*0.05,2))+".csv")
