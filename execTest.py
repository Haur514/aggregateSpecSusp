import os
import sys
import shutil

base = 'C:/Users/h-yosiok/Lab/aggregateSpecSusp'
jarFile = base + '/build/libs/aggregateSpecSusp.jar'

args = sys.argv
num = args[1]

os.chdir(base)
os.chdir('spectrum')
sub = "math"+str(num).zfill(3)
os.chdir(sub)
shutil.copy("./TR.txt", "./../../TR.txt")
os.chdir('..')
os.chdir('..')
os.system('gradle run')
shutil.copy("./BSBFL.txt", "./spectrum/" + sub + "/BSBFL.txt")
shutil.copy("./SBFL.txt", "./spectrum/" + sub + "/SBFL.txt")
