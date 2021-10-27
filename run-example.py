import os
import sys
import shutil

sub = sys.argv[1]
base = 'C:/Users/h-yosiok/Lab/aggregateSpecSusp'

os.chdir(base)
os.chdir('spectrum')
os.chdir(sub)
shutil.copy("./TR.txt", "./../../TR.txt")
os.chdir('..')
os.chdir('..')
os.system('gradle run')
shutil.copy("./BSBFL.txt", "./spectrum/" + sub + "/BSBFL.txt")
shutil.copy("./SBFL.txt", "./spectrum/" + sub + "/SBFL.txt")
