import sys
import os
import glob
import re
import shutil

def cp_files(project_name,id):
    if os.path.exists(home_dir+project_name+"_"+str(id)+"_buggy"):
        shutil.copy(home_dir+project_name+"_"+str(id)+"_buggy",destination_dir+project_name+str(id).zfill(3)+".txt")

home_dir = "/home/h-yosiok/Lab/2022/executionRoutes/"
destination_dir = "/home/h-yosiok/Lab/2022/aggregateSpecSusp/route/"

for id in range(1,44):
    cp_files("lang",id)