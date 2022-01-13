import pandas as pd
import numpy as np
import sys
import matplotlib.pyplot as plt
import matplotlib

plt.rcParams['font.family']="Hiragino Sans"

args = sys.argv

lst = pd.read_csv("data/rankALL/BestBSBFLvsNonBSBFL.csv").values.tolist()

fig=plt.figure()
result = []
filename = []

linesBSBFLwinSBFL = 0
linesBSBFLloseSBFL = 0

linesBSBFLwinSBFL_sum = 0.0
linesBSBFLloseSBFL_sum = 0.0

bsbfl_ave = 0.0
nonbsbfl_ave = 0.0

for i in lst:
    if(i[2]==i[3]):
        continue
    result.append(float(i[2])/float(i[5])*100.0)
    result.append(float(i[3])/float(i[5])*100.0)
    nonbsbfl_ave+=(float(i[2])/float(i[5]))
    bsbfl_ave+=(float(i[3])/float(i[5]))
    if float(i[1]) > float(i[3]):
        linesBSBFLwinSBFL += 1
        linesBSBFLwinSBFL_sum += (float(i[3])/float(i[5]) - float(i[1])/float(i[5]))
    else:
        linesBSBFLloseSBFL += 1
        linesBSBFLloseSBFL_sum += (float(i[3])/float(i[5]) - float(i[1])/float(i[5]))
    filename.append(i[4])
linesBSBFLwinSBFL_ave = linesBSBFLwinSBFL_sum/linesBSBFLwinSBFL
linesBSBFLloseSBFL_ave = linesBSBFLloseSBFL_sum/linesBSBFLloseSBFL

if False:
    print(str(round(float(args[2]),2))+'&'+str(round(linesBSBFLwinSBFL_ave*100.0,2))+'&'+str(round(linesBSBFLloseSBFL_ave*100.0,2))+'\\\\')

if False:
    print(str(round(float(args[2]),2))+'&'+str(linesBSBFLwinSBFL)+'&'+str(linesBSBFLloseSBFL)+'\\\\')

if True:
    print(str(round(float(args[2]),2))+'&'+str(round(bsbfl_ave/(len(result)/2)*100,2))+'&'+str(round(nonbsbfl_ave/(len(result)/2)*100,2))+'\\\\')

#print("all:"+str(len(result)/2))
#print("bsbfl wins:" + str(linesBSBFLwinSBFL))
#print("sbfl:"+str(sbfl_ave/(len(result)/2)))
#print("bsbfl:"+str(bsbfl_ave/(len(result)/2)))

if True:
    a = np.array(result)
    b =  a.reshape([-1,2])

    b_sort = b[np.argsort(b[:,0])[::-1]]
    filename_sort = []
    for j in np.argsort(b[:,0]):
        filename_sort.append(filename[len(filename)-j-1])


    left = np.arange(len(b_sort))
    nonbsbfl = []
    bsbfl = []
    for i in b_sort:
        nonbsbfl.append(i[0])
        bsbfl.append(i[1])

    sbfl = plt.bar(left,nonbsbfl,width=0.3,color='#0080ff',align="edge",label="NonBSBFL")
    plt.xticks(rotation=90)
    nonsbfl = plt.bar(left,bsbfl,width=-0.3,color='#ff8000',align="edge",label="BSBFL")
    plt.legend()
    #plt.legend(handles=[sbfl,nonbsbfl],loc='best')
    plt.ylabel('欠陥箇所の順位/疑惑値のついた行数')


    fig.savefig("./../figure/bar/best_bsbfl_vs_nonbsbfl.pdf",bbox_inches="tight",pad_inches=0.05)