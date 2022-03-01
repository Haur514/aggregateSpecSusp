import pandas as pd
import numpy as np
import sys
import matplotlib.pyplot as plt
import os
import matplotlib


from matplotlib.font_manager import FontProperties
font_path = "/home/h-yosiok/.fonts/SourceHanCodeJP-Medium.otf"
font_prop = FontProperties(fname=font_path)
matplotlib.rcParams["font.family"] = font_prop.get_name()

args = sys.argv
weightType = args[1]
threshold = args[2]
formulaType=args[3]
lst = pd.read_csv("./../"+formulaType+"/"+args[1]+args[2]+".csv").values.tolist()

fig=plt.figure(figsize=(8,3))
result = []
filename = []

linesNonBSBFLwinSBFL = 0
linesNonBSBFLloseSBFL = 0

linesNonBSBFLwinSBFL_sum = 0.0
linesNonBSBFLloseSBFL_sum = 0.0

sbfl_ave = 0.0
nonbsbfl_ave = 0.0
bsbfl_ave = 0.0

diff_sbfl_ave = 0.0
diff_nonbsbfl_ave = 0.0
diff_bsbfl_ave = 0.0

linesSBFLwinBSBFL = 0
linesSBFLloseBSBFL = 0

linesSBFLwinBSBFL_sum = 0.0
linesSBFLloseBSBFL_sum = 0.0

for i in lst:
    if i[1] != i[3]:
        result.append(float(i[1])/float(i[5])*100)
        result.append(float(i[3])/float(i[5])*100)
        filename.append(i[4])
        diff_sbfl_ave += (float(i[1])/float(i[5]))
        diff_bsbfl_ave+=(float(i[3])/float(i[5]))
    sbfl_ave += (float(i[1])/float(i[5]))
    bsbfl_ave+=(float(i[3])/float(i[5]))
    if float(i[1]) > float(i[3]):
        linesSBFLloseBSBFL += 1
        linesSBFLloseBSBFL_sum += float(i[3])/float(i[5]) - float(i[1])/float(i[5])
    elif float(i[1] < float(i[3])):
        linesSBFLwinBSBFL += 1
        linesSBFLwinBSBFL_sum += float(i[3])/float(i[5]) - float(i[1])/float(i[5])

if True:
    print(str(round(float(args[2]),2))+'&'+str(round((linesSBFLloseBSBFL_sum / linesSBFLloseBSBFL)*100.0,2))+'&'+str(round((linesSBFLwinBSBFL_sum / linesSBFLwinBSBFL)*100.0,2))+'\\\\')

if True:
    print(str(round(float(args[2]),2)).ljust(4,'0')+'&'+str(linesSBFLwinBSBFL)+'&'+str(linesSBFLloseBSBFL)+'\\\\')

if False:
    print(str(round(float(args[2]),2))+'&'+str(round(sbfl_ave/(len(lst))*100,5))+'&'+str(round(nonbsbfl_ave/(len(lst))*100,5))+'&'+str(round(bsbfl_ave/(len(lst))*100,5))+'\\\\')
    
if False:
    print(str(round(float(args[2]),2))+'&'+str(round(diff_sbfl_ave/(len(result)/2)*100,5))+'&'+str(round(diff_nonbsbfl_ave/(len(result)/2)*100,5))+'&'+str(round(diff_bsbfl_ave/(len(result)/2)*100,5))+'\\\\')

if True:
    a = np.array(result)
    b =  a.reshape([-1,2])

    b_sort = b[np.argsort(b[:,0])[::-1]]
    filename_sort = []
    for j in np.argsort(b[:,0])[::-1]:
        filename_sort.append(filename[j])

    left = np.arange(len(b_sort))
    sbfl_x = []
    nonbsbfl_x = []
    bsbfl_x = []
    for i in left:
        sbfl_x.append(left[i])
        bsbfl_x.append(left[i]+0.4)
    sbfl = []
    nonbsbfl = []
    bsbfl = []
    for i in b_sort:
        sbfl.append(i[0])
        bsbfl.append(i[1])
    
    sbfl = plt.bar(sbfl_x,sbfl,width=0.4,color='#c0c0c0',align="edge",label="既存手法")
    plt.xticks(rotation=90)
    bsbfl = plt.bar(bsbfl_x,bsbfl,width=0.4,color='#1e90ff',align="edge",label="BSBFL")
    plt.legend()
    plt.ylabel('TopN%')
    plt.xticks(fontsize=6)
    plt.xticks(bsbfl_x,filename_sort)
    plt.xticks(rotation=45)

    os.makedirs("./../"+args[3]+"/graph", exist_ok=True)
    os.makedirs("./../"+args[3]+"/graph/diff_sbfl_bsbfl/", exist_ok=True)
    fig.savefig("./../"+args[3]+"/graph/diff_sbfl_bsbfl/"+args[1]+args[2]+".pdf",bbox_inches="tight",pad_inches=0.05)
