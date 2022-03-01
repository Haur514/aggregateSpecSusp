import pandas as pd
import numpy as np
import sys
import matplotlib.pyplot as plt
import matplotlib

plt.rcParams['font.family']="Hiragino Sans"

args = sys.argv

lst = pd.read_csv("data/rankALL/"+args[1]+".csv").values.tolist()

fig=plt.figure()
result = []
filename = []

linesBSBFLwinSBFL = 0

sbfl_ave = 0.0
bsbfl_ave = 0.0
nonbsbfl_ave = 0.0

for i in lst:
    result.append(float(i[1])/float(i[5]))
    result.append(float(i[2])/float(i[5]))
    result.append(float(i[3])/float(i[5]))
    sbfl_ave+=(float(i[1])/float(i[5])*100.0)
    nonbsbfl_ave+=(float(i[2])/float(i[5])*100.0)
    bsbfl_ave+=(float(i[3])/float(i[5])*100.0)
    if float(i[1]) > float(i[2]):
        linesBSBFLwinSBFL += 1
    filename.append(i[4])

print(linesBSBFLwinSBFL)
print(len(result)/3)
print(sbfl_ave/(len(result)/3))
print(nonbsbfl_ave/(len(result)/3))
print(bsbfl_ave/(len(result)/3))
a = np.array(result)
b =  a.reshape([-1,3])

b_sort = b[np.argsort(b[:,0])[::-1]]
filename_sort = []
for j in np.argsort(b[:,0]):
    filename_sort.append(filename[len(filename)-j-1])


left = np.arange(len(b_sort))
sbfl = []
bsbfl = []
nonbsbfl = []
for i in b_sort:
    sbfl.append(i[0])
    nonbsbfl.append(i[1])
    bsbfl.append(i[2])

x_sbfl = []
x_bsbfl = []
x_nonbsbfl = []
for i in range(1,int(len(result)/3)):
    x_sbfl.append(i)
    x_nonbsbfl.append(i+0.3)
    x_bsbfl.append(i+0.6)

sbfl = plt.bar(x_sbfl,sbfl,width=0.3,color='#0080ff',align="edge",label="SBFL")
plt.xticks(rotation=90)
bsbfl = plt.bar(x_bsbfl,bsbfl,width=0.3,color='#ff8000',align="edge",label="BSBFL")
plt.xticks()
nonbsbfl = plt.bar(x_nonbsbfl,nonbsbfl,width=0.3,color='#797979',align="edge",label="NonBSBFL")
plt.legend(handles=[sbfl,bsbfl,nonbsbfl],loc='best')
plt.ylabel('欠陥箇所の順位/疑惑値のついた行数')

fig.savefig("./../figure/bar/"+args[1]+"rankAll.pdf",bbox_inches="tight",pad_inches=0.05)