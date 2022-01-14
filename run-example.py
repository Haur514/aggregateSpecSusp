import os
import sys
import shutil

base = '/Users/h-yosiok/Lab/aggregateSpecSusp'
countRankBase = '/Users/h-yosiok/Lab/BSBFL/countRankBSBFL'
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

formuraName = {0: "Ochiai",
               1: "Zoltar",
               2: "Jaccard",
               3: "Ample",
               4: "Tarantula"}

args = sys.argv
weightType = weightFunctionName[int(args[1])]
formuraType = formuraName[int(args[2])]
os.makedirs("./"+str(formuraType), exist_ok=True)
for j in range(15,16):
    for i in range(rangeStart, rangeEnd):
        os.chdir(base)
        os.chdir('spectrum')
        sub = "math"+str(i).zfill(3)
        print(sub)
        os.chdir(sub)
        shutil.copy("./TR.txt", "./../../TR.txt")
        os.chdir('..')
        os.chdir('..')
        os.system(
            'java -jar ./build/libs/aggregateSpecSusp.jar -weightType '+args[1] + ' -threshold ' + str(round(j*0.05,2)) + ' -formula ' + args[2])
        os.makedirs("./spectrum/" +formuraType+"/"+
                    sub + "/" + weightType, exist_ok=True)
        shutil.copy("./BSBFL.txt", "./spectrum/" +
                    sub + "/BSBFL.txt")
        shutil.copy("./NonBSBFL.txt", "./spectrum/" +
                    sub + "/NonBSBFL.txt")
        shutil.copy("./SBFL.txt", "./spectrum/" +
                    sub + "/SBFL.txt")
        shutil.copy("./Weight.txt", "./spectrum/" +
                    sub + "/Weight.txt")
        shutil.copy("./BlockedExecutionRoute.txt","./spectrum/"+sub+"/BlockedExecutionRoute.txt")
        shutil.copy("./BSBFL.txt", "./spectrum/" +formuraType+"/"+
                    sub + "/" + weightType + "/BSBFL"+str(round(j*0.05,2))+".txt")
        shutil.copy("./NonBSBFL.txt", "./spectrum/" +formuraType+"/"+
                    sub + "/" + weightType + "/NonBSBFL"+str(round(j*0.05,2))+".txt")
        shutil.copy("./SBFL.txt", "./spectrum/" +formuraType+"/"+
                    sub + "/" + weightType + "/SBFL.txt")
        shutil.copy("./Weight.txt", "./spectrum/" +formuraType+"/"+
                    sub + "/" + weightType + "/Weight"+str(round(j*0.05,2))+".txt")
    os.system('java -jar /Users/h-yosiok/Lab/countRankBSBFL/build/libs/countRankBSBFL.jar '+args[1]+' ' + str(round(j*0.05,2)) + ' ' + formuraType)
    shutil.copy("./sample.txt", "./"+formuraType+"/"+weightType+str(round(j*0.05,2))+".csv")
