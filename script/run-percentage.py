import pandas as pd
import numpy as np
import sys
import os
import matplotlib.pyplot as plt
import matplotlib

plt.rcParams['font.family']="Hiragino Sans"

rangestart = 4
rangeend = 10

# 0.05 or 0.1
kizamihaba=0.1 


args = sys.argv

if False:
    print('sbfl_bsbfl')
    for i in range(rangestart,rangeend):
        os.system('python3 diff_sbfl_bsbfl.py Haka '+str(round(i*kizamihaba,2))+' '+args[1])
        
if False:
    print('sbfl_nonbsbfl')
    for i in range(rangestart,rangeend):
        os.system('python3 diff_sbfl_nonbsbfl.py Haka '+str(round(i*kizamihaba,2))+' '+args[1])
if False:
    print('bsbfl_nonbsbfl')
    for i in range(rangestart,rangeend):
        os.system('python3 diff_bsbfl_nonbsbfl.py Haka '+str(round(i*kizamihaba,2)))
        
if True:
    print("Ochiai")
    for i in range(rangestart,rangeend):
        os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Ochiai')
    print("Jaccard")
    for i in range(rangestart,rangeend):
        os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Jaccard')
    print("Zoltar")
    for i in range(rangestart,rangeend):
        os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Zoltar')
    print("Ample")
    for i in range(rangestart,rangeend):
        os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Ample')
    print("Tarantula")
    for i in range(rangestart,rangeend):
        os.system('python3 diff_all.py Haka '+str(round(i*kizamihaba,2))+' Tarantula')