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

for j in range(20,21):
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
            'java -jar ./build/libs/aggregateSpecSusp.jar -weightType '+args[1] + ' -threshold ' + str(round(j*0.05,2)))
        os.makedirs("./spectrum/" + sub + "/"+weightType, exist_ok=True)
        shutil.copy("./BSBFL.txt", "./spectrum/" +
                    sub + "/BSBFL.txt")
        shutil.copy("./NonBSBFL.txt", "./spectrum/" +
                    sub + "/NonBSBFL.txt")
        shutil.copy("./SBFL.txt", "./spectrum/" +
                    sub + "/SBFL.txt")
        shutil.copy("./Weight.txt", "./spectrum/" +
                    sub + "/Weight.txt")
        shutil.copy("./BSBFL.txt", "./spectrum/" +
                    sub + "/" + weightType + "/BSBFL"+str(round(j*0.05,2))+".txt")
        shutil.copy("./NonBSBFL.txt", "./spectrum/" +
                    sub + "/" + weightType + "/NonBSBFL"+str(round(j*0.05,2))+".txt")
        shutil.copy("./SBFL.txt", "./spectrum/" +
                    sub + "/" + weightType + "/SBFL.txt")
        shutil.copy("./Weight.txt", "./spectrum/" +
                    sub + "/" + weightType + "/Weight"+str(round(j*0.05,2))+".txt")
    os.system('java -jar C:/Users/h-yosiok/Lab/countRankBSBFL/build/libs/countRankBSBFL.jar')
    shutil.copy("./sample.txt", "./"+weightType+str(round(j*0.05,2))+".csv")
