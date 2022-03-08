import pandas as pd
import numpy as np
import sys
import os
import matplotlib.pyplot as plt
import matplotlib

plt.rcParams['font.family']="Hiragino Sans"

rangestart = 16
rangeend = 20

# 0.05 or 0.1
kizamihaba=0.05


args = sys.argv

if False:
    print('sbfl_bsbfl')
    for i in range(rangestart,rangeend):
        os.system('python3 diff_sbfl_bsbfl.py Haka '+str(round(i*kizamihaba,2))+' Ochiai')
        
if False:
    print('sbfl_nonbsbfl')
    for i in range(rangestart,rangeend):
        os.system('python3 diff_sbfl_nonbsbfl.py Haka '+str(round(i*kizamihaba,2))+' Ochiai')
if False:
    print('bsbfl_nonbsbfl')
    for i in range(rangestart,rangeend):
        os.system('python3 diff_bsbfl_nonbsbfl.py Haka '+str(round(i*kizamihaba,2))+' Ochiai')
        
if True:
    for i in range(rangestart,rangeend):
        os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Ochiai')
    # print("Jaccard")
    # for i in range(rangestart,rangeend):
    #     os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Jaccard')
    # print("Zoltar")
    # for i in range(rangestart,rangeend):
    #     os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Zoltar')
    # print("Ample")
    # for i in range(rangestart,rangeend):
    #     os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Ample')
    # print("Tarantula")
    # for i in range(rangestart,rangeend):
    #     os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Tarantula')