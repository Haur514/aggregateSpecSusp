import os
import sys
import shutil

import simplejson

base = './'
countRankBase = './../countRankBSBFL'
jarFile = base + './build/libs/aggregateSpecSusp.jar'

rangeStart = 1
rangeEnd = 100
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
               4: "Tarantula",
               5: "Dstar3",
               6: "Dstar",
               7: "Dstar5"}

# 類似度のとり方/疑惑値の算出式/重み付けに使用した式/math???/~~~.csv
kizamihaba = 0.05

args = sys.argv
weightType = weightFunctionName[int(args[1])]
formuraType = formuraName[int(args[2])]
ruijido = "jaccard"

destinationDir = "./"+ruijido+"/"+formuraType+"/"+weightType+"/"

os.makedirs("./"+str(formuraType), exist_ok=True)
for j in range(0,20):
    for i in range(rangeStart, rangeEnd):
        os.chdir(base)
        os.chdir('route')
        sub = "math"+str(i).zfill(3)
        shutil.copy("./"+sub+".txt", "./../TR.txt")
        os.chdir('..')
        os.system(
            'java -jar ./build/libs/aggregateSpecSusp.jar -weightType '+args[1] + ' -threshold ' + str(round(j*kizamihaba,2)) + ' -formula ' + args[2])
        print("math"+str(i))
        os.makedirs(destinationDir+sub, exist_ok=True)
        shutil.copy("./BlockedExecutionRoute.txt",destinationDir+sub+"/BlockedExecutionRoute.txt")
        shutil.copy("./BSBFL.txt", destinationDir+
                    sub + "/BSBFL"+str(round(j*kizamihaba,2))+".txt")
        shutil.copy("./NonBSBFL.txt", destinationDir+
                    sub + "/NonBSBFL"+str(round(j*kizamihaba,2))+".txt")
        shutil.copy("./SBFL.txt", destinationDir+
                    sub + "/SBFL.txt")
        shutil.copy("./Weight.txt", destinationDir+
                    sub + "/Weight"+str(round(j*kizamihaba,2))+".txt")
    os.system('java -jar ~/Lab/2022//countRankBSBFL/build/libs/countRankBSBFL.jar '+args[1]+' ' + str(round(j*kizamihaba,2)) + ' ' + formuraType +' math')
    shutil.copy("./sample.txt", destinationDir+"/"+"math"+str(round(j*kizamihaba,2))+".csv")
