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
sbflvsbsbfl = []
sbflvsnonbsbfl = []
for i in lst:
    #SBFL=notBSBFL
    if(i[1]!=i[2]):
        sbflvsnonbsbfl.append(float(i[2])/float(i[5]))
    if(i[1]!=i[3]):
        sbflvsbsbfl.append(float(i[3])/float(i[5]))
    sbfl_ave+=(float(i[1])/float(i[5])*100.0)
    nonbsbfl_ave+=(float(i[2])/float(i[5])*100.0)
    bsbfl_ave+=(float(i[3])/float(i[5])*100.0)

ave_sbflvsbsbfl = 0.0
ave_sbflvsnonbsbfl = 0.0
for i in range(0,len(sbflvsbsbfl)):
    ave_sbflvsbsbfl+=sbflvsbsbfl[i]
ave_sbflvsbsbfl = ave_sbflvsbsbfl/len(sbflvsbsbfl)
print("---")
print(ave_sbflvsbsbfl)
for i in range(0,len(sbflvsnonbsbfl)):
    ave_sbflvsnonbsbfl+=sbflvsnonbsbfl[i]
ave_sbflvsnonbsbfl = ave_sbflvsnonbsbfl/len(sbflvsnonbsbfl)
print("---")
print(ave_sbflvsnonbsbfl)

print(linesBSBFLwinSBFL)
print(len(result)/3)
print(sbfl_ave/(len(result)/3))
print(nonbsbfl_ave/(len(result)/3))
print(bsbfl_ave/(len(result)/3))
a = np.array(result)
b =  a.reshape([-1,3])
