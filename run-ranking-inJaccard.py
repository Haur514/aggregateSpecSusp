import os
import sys
import shutil



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

for j in range(12,13):
    os.system('java -jar ~/Lab/2022//countRankBSBFL/build/libs/countRankBSBFL.jar '+args[1]+' ' + str(round(j*0.05,2)) + ' ' + formuraType + ' jaccard')
    os.makedirs("./jaccard/"+formuraType,exist_ok=True)
    shutil.copy("./sample.txt", "./jaccard/"+formuraType+"/"+weightType+str(round(j*0.05,2))+".csv")
