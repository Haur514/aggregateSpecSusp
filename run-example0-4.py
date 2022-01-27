import os
import sys
import shutil

base = './'
countRankBase = './../countRankBSBFL'
jarFile = base + './build/libs/aggregateSpecSusp.jar'

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

formuraName = {0: "Ochiai",
               1: "Zoltar",
               2: "Jaccard",
               3: "Ample",
               4: "Tarantula"}

args = sys.argv
for j in range(0,5):
    formuraType=formuraName[j]
    os.makedirs("./"+str(formuraType), exist_ok=True)
    
for j in range(0,5):
    os.system('python3 run-example.py 4 '+ str(j))