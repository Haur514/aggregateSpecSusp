import os
import sys
import shutil

base = '/home/h-yosiok/Lab/2022/aggregateSpecSusp'
countRankBase = '/home/h-yosiok/Lab/2022/countRankBSBFL'
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

for j in range(4,10):
    os.system('java -jar /home/h-yosiok/Lab/2022/countRankBSBFL/build/libs/countRankBSBFL.jar '+args[1]+' ' + str(round(j*0.1,2)) + ' ' + formuraType)
    shutil.copy("./sample.txt", "./"+formuraType+"/"+weightType+str(round(j*0.1,2))+".csv")
