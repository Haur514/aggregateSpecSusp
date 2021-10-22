import os
import sys
import shutil

sub = sys.argv[1]

os.chdir('kGenProg-example')
os.chdir(sub)
os.system('java -jar ./../../kGenProg-1.8.2.jar')
shutil.copy("./TR.txt", "./../../TR.txt")
os.chdir('..')
os.chdir('..')
os.system('gradle run')
shutil.copy("./BSBFL.txt", "./kGenProg-example/" + sub + "/BSBFL.txt")
shutil.copy("./SBFL.txt", "./kGenProg-example/" + sub + "/SBFL.txt")
