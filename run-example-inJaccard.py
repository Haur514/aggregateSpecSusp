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
weightType = weightFunctionName[int(args[1])]
formuraType = formuraName[int(args[2])]
os.makedirs("./"+str(formuraType), exist_ok=True)
for j in range(12,20):
    for i in range(rangeStart, rangeEnd):
        os.chdir(base)
        os.chdir('spectrum')
        sub = "math"+str(i).zfill(3)
        os.chdir(sub)
        shutil.copy("./TR.txt", "./../../TR.txt")
        os.chdir('..')
        os.chdir('..')
        os.system(
            'java -jar ./build/libs/aggregateSpecSusp.jar -weightType '+args[1] + ' -threshold ' + str(round(j*0.05,2)) + ' -formula ' + args[2])
        os.makedirs("./spectrum/jaccard/" +formuraType+"/"+
                    sub + "/" + weightType, exist_ok=True)
        os.makedirs("./spectrum/jaccard/"+sub,exist_ok=True)
        shutil.copy("./BSBFL.txt", "./spectrum/jaccard/" +
                    sub + "/BSBFL.txt")
        shutil.copy("./NonBSBFL.txt", "./spectrum/jaccard/" +
                    sub + "/NonBSBFL.txt")
        shutil.copy("./SBFL.txt", "./spectrum/jaccard/" +
                    sub + "/SBFL.txt")
        shutil.copy("./Weight.txt", "./spectrum/jaccard/" +
                    sub + "/Weight.txt")
        shutil.copy("./BlockedExecutionRoute.txt","./spectrum/jaccard/"+sub+"/BlockedExecutionRoute.txt")
        shutil.copy("./BSBFL.txt", "./spectrum/jaccard/" +formuraType+"/"+
                    sub + "/" + weightType + "/BSBFL"+str(round(j*0.05,2))+".txt")
        shutil.copy("./NonBSBFL.txt", "./spectrum/jaccard/" +formuraType+"/"+
                    sub + "/" + weightType + "/NonBSBFL"+str(round(j*0.05,2))+".txt")
        shutil.copy("./SBFL.txt", "./spectrum/jaccard/" +formuraType+"/"+
                    sub + "/" + weightType + "/SBFL.txt")
        shutil.copy("./Weight.txt", "./spectrum/jaccard/" +formuraType+"/"+
                    sub + "/" + weightType + "/Weight"+str(round(j*0.05,2))+".txt")
    os.system('java -jar ~/Lab/2022//countRankBSBFL/build/libs/countRankBSBFL.jar '+args[1]+' ' + str(round(j*0.05,2)) + ' ' + formuraType + ' jaccard')
    os.makedirs("./jaccard/"+formuraType,exist_ok=True)
    shutil.copy("./sample.txt", "./jaccard/"+formuraType+"/"+weightType+str(round(j*0.05,2))+".csv")
