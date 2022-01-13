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
formuraType = args[2]
weightType = weightFunctionName[int(args[1])]
data = {}

for j in range(12,20):
    os.system('java -jar /Users/h-yosiok/Lab/countRankBSBFL/build/libs/countRankBSBFL.jar '+args[1]+' ' + str(round(j*0.05,2)) + ' ' + formuraType)
    shutil.copy("./sample.txt", "./"+formuraType+"/"+weightType+str(round(j*0.05,2))+".csv")
