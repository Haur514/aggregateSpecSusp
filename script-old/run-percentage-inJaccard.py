import pandas as pd
import numpy as np
import sys
import os
import matplotlib.pyplot as plt
import matplotlib

plt.rcParams['font.family']="Hiragino Sans"

args = sys.argv

if False:
    print('sbfl_bsbfl')
    for i in range(12,20):
        os.system('python3 diff_sbfl_bsbfl.py Haka '+str(round(i*0.05,2))+' '+args[1])
        
if False:
    print('sbfl_nonbsbfl')
    for i in range(12,20):
        os.system('python3 diff_sbfl_nonbsbfl.py Haka '+str(round(i*0.05,2))+' '+args[1])
if False:
    print('bsbfl_nonbsbfl')
    for i in range(12,20):
        os.system('python3 diff_bsbfl_nonbsbfl.py Haka '+str(round(i*0.05,2)))
        
if True:
    print(args[1])
    for i in range(12,20):
        os.system('python3 diff_all_inJaccard.py Haka '+str(round(i*0.05,2))+' '+args[1])