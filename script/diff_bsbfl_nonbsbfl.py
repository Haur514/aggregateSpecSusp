import pandas as pd
import numpy as np
import sys
import matplotlib.pyplot as plt
import matplotlib

from matplotlib.font_manager import FontProperties
font_path = "/home/h-yosiok/.fonts/SourceHanCodeJP-Medium.otf"
font_prop = FontProperties(fname=font_path)
matplotlib.rcParams["font.family"] = font_prop.get_name()

args = sys.argv

lst = pd.read_csv("./../"+args[3]+"/"+args[1]+args[2]+".csv").values.tolist()

fig=plt.figure()
result = []
filename = []

linesBSBFLwinNonBSBFL = 0
linesBSBFLloseNonBSBFL = 0

linesNonBSBFLwinSBFL_sum = 0.0
linesNonBSBFLloseSBFL_sum = 0.0

nonbsbfl_ave = 0.0
bsbfl_ave = 0.0

for i in lst:
    if(i[2]==i[3]):
        continue
    result.append(float(i[1])/float(i[5]))
    result.append(float(i[2])/float(i[5]))
    bsbfl_ave+=(float(i[3])/float(i[5]))
    nonbsbfl_ave+=(float(i[2])/float(i[5]))
    if float(i[2]) > float(i[3]):
        linesBSBFLwinNonBSBFL += 1
        linesNonBSBFLwinSBFL_sum += float(i[2])/float(i[5]) - float(i[1])/float(i[5])
    elif float(i[2]) < float(i[3]):
        linesBSBFLloseNonBSBFL += 1
        linesNonBSBFLloseSBFL_sum += float(i[2])/float(i[5]) - float(i[1])/float(i[5])
    filename.append(i[4])


if False:
    print(str(round(float(args[2]),2))+'&'+str(round(linesNonBSBFLwinSBFL_ave*100.0,2))+'&'+str(round(linesNonBSBFLloseSBFL_ave*100.0,2))+'\\\\')

if True:
    print(str(round(float(args[2]),2))+'&'+str(linesBSBFLwinNonBSBFL)+'&'+str(linesBSBFLloseNonBSBFL)+'\\\\')

if False:
    print(str(round(float(args[2]),2))+'&'+str(round(bsbfl_ave/(len(result)/2)*100,2))+'&'+str(round(nonbsbfl_ave/(len(result)/2)*100,2))+'\\\\')

#print("all:"+str(len(result)/2))
#print("nonbsbfl wins:" + str(linesBSBFLwinSBFL))
#print("sbfl:"+str(sbfl_ave/(len(result)/2)))
#print("nonsbfl:"+str(nonbsbfl_ave/(len(result)/2)))


if False:
    a = np.array(result)
    b =  a.reshape([-1,2])

    b_sort = b[np.argsort(b[:,0])[::-1]]
    filename_sort = []
    for j in np.argsort(b[:,0]):
        filename_sort.append(filename[len(filename)-j-1])


    left = np.arange(len(b_sort))
    sbfl = []
    nonbsbfl = []
    for i in b_sort:
        sbfl.append(i[0])
        nonbsbfl.append(i[1])

    sbfl = plt.bar(left,sbfl,width=0.3,color='#0080ff',align="edge",label="SBFL")
    plt.xticks(rotation=90)
    nonsbfl = plt.bar(left,nonbsbfl,width=-0.3,color='#ff8000',align="edge",label="NonBSBFL")
    plt.legend()
    #plt.legend(handles=[sbfl,nonbsbfl],loc='best')
    plt.ylabel('欠陥箇所の順位/疑惑値のついた行数')

    fig.savefig("./../figure/bar/diff_sbfl_nonbsbfl_"+args[1]+args[2]+".pdf",bbox_inches="tight",pad_inches=0.05)