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

os.system('java -jar C:/Users/h-yosiok/Lab/countRankBSBFL/build/libs/countRankBSBFL.jar '+ args[1]+' '+"0.95")
shutil.copy("./sample.txt", "./Haka0.95ALL.csv")
