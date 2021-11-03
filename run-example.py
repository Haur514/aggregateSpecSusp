import os
import sys
import shutil

base = 'C:/Users/h-yosiok/Lab/aggregateSpecSusp'
countRankBase = 'C:/Users/h-yosiok/Lab/countRankBSBFL'
jarFile = base + '/build/libs/aggregateSpecSusp.jar'

rangeStart = 1
rangeEnd = 104
os.system('gradle jar')

weightFunctionName = {0: "Haruka",
                      1: "Yoshiruka",
                      2: "Ruka",
                      3: "Haru",
                      4: "Haka",
                      5: "Yoruka",
                      6: "Senko",
                      7: "YoshiokaHaruka"}

args = sys.argv
weightType = weightFunctionName[int(args[1])]


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
        'java -jar ./build/libs/aggregateSpecSusp.jar -weightType '+args[1])
    shutil.copy("./BSBFL.txt", "./spectrum/" + sub + "/BSBFL.txt")
    shutil.copy("./SBFL.txt", "./spectrum/" + sub + "/SBFL.txt")
    shutil.copy("./Weight.txt", "./spectrum/" + sub + "/Weight.txt")

os.system('java -jar ' + countRankBase + '/build/libs/countRankBSBFL.jar')
