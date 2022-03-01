import pandas as pd
import numpy as np
import sys
import matplotlib.pyplot as plt
import os
import matplotlib

plt.rcParams['font.family']="Ubuntu"

args = sys.argv
weightType = args[1]
threshold = args[2]
formulaType=args[3]
lst = pd.read_csv("./../"+formulaType+"/"+args[1]+args[2]+".csv").values.tolist()

fig=plt.figure()
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

for i in lst:
    if i[1]!=i[2] or i[1] != i[3]:
        result.append(float(i[1])/float(i[5]))
        result.append(float(i[2])/float(i[5]))
        result.append(float(i[3])/float(i[5]))
        diff_sbfl_ave += (float(i[1])/float(i[5]))
        diff_nonbsbfl_ave+=(float(i[2])/float(i[5]))
        diff_bsbfl_ave+=(float(i[3])/float(i[5]))
    sbfl_ave += (float(i[1])/float(i[5]))
    nonbsbfl_ave+=(float(i[2])/float(i[5]))
    bsbfl_ave+=(float(i[3])/float(i[5]))
    if float(i[1]) > float(i[3]):
        linesNonBSBFLwinSBFL += 1
        linesNonBSBFLwinSBFL_sum += float(i[3])/float(i[5]) - float(i[1])/float(i[5])
    elif float(i[1]) < float(i[3]):
        linesNonBSBFLloseSBFL += 1
        linesNonBSBFLloseSBFL_sum += float(i[3])/float(i[5]) - float(i[1])/float(i[5])
    filename.append(i[4])

linesNonBSBFLwinSBFL_ave = linesNonBSBFLwinSBFL_sum/linesNonBSBFLwinSBFL
linesNonBSBFLloseSBFL_ave = linesNonBSBFLloseSBFL_sum/linesNonBSBFLloseSBFL

if False:
    print(str(round(float(args[2]),2))+'&'+str(round(linesNonBSBFLwinSBFL_ave*100.0,2))+'&'+str(round(linesNonBSBFLloseSBFL_ave*100.0,2))+'\\\\')

if False:
    print(str(round(float(args[2]),2))+'&'+str(linesNonBSBFLwinSBFL)+'&'+str(linesNonBSBFLloseSBFL)+'\\\\')

if True:
    print(str(round(float(args[2]),2))+'&'+str(round(sbfl_ave/(len(lst))*100,5)).ljust(8,'0')+'&'+str(round(nonbsbfl_ave/(len(lst))*100,5)).ljust(8,'0')+'&'+str(round(bsbfl_ave/(len(lst))*100,5)).ljust(8,'0')+'\\\\')
    
if False:
    print(str(round(float(args[2]),2))+'    '+str(round(diff_sbfl_ave/(len(lst))*100,5))+'  '+str(round(diff_nonbsbfl_ave/(len(lst))*100,5))+'   '+str(round(diff_bsbfl_ave/(len(lst))*100,5))+'\\\\')

if False:
    a = np.array(result)
    b =  a.reshape([-1,3])

    b_sort = b[np.argsort(b[:,0])[::-1]]
    filename_sort = []
    for j in np.argsort(b[:,0]):
        filename_sort.append(filename[len(filename)-j-1])


    left = np.arange(len(b_sort))
    sbfl_x = []
    nonbsbfl_x = []
    bsbfl_x = []
    for i in left:
        sbfl_x.append(left[i])
        nonbsbfl_x.append(left[i]+0.3)
        bsbfl_x.append(left[i]+0.6)
    sbfl = []
    nonbsbfl = []
    bsbfl = []
    for i in b_sort:
        sbfl.append(i[0])
        nonbsbfl.append(i[1])
        bsbfl.append(i[2])

    sbfl = plt.bar(sbfl_x,sbfl,width=0.3,color='#dc143c',align="edge",label="SBFL")
    plt.xticks(rotation=90)
    nonsbfl = plt.bar(nonbsbfl_x,nonbsbfl,width=0.3,color='#708090',align="edge",label="NonBSBFL")
    plt.legend()
    bsbfl = plt.bar(bsbfl_x,bsbfl,width=0.3,color='#4169e1',align="edge",label="NonBSBFL")
    plt.legend()
    #plt.legend(handles=[sbfl,nonbsbfl],loc='best')
    plt.ylabel('欠陥箇所の順位/疑惑値のついた行数')

    os.makedirs("./../"+args[3]+"/graph", exist_ok=True)
    os.makedirs("./../"+args[3]+"/graph/diff_all/", exist_ok=True)
    fig.savefig("./../"+args[3]+"/graph/diff_all/"+args[1]+args[2]+".pdf",bbox_inches="tight",pad_inches=0.05)