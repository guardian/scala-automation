#!/usr/bin/python

import os
import tarfile
import urllib
import subprocess

if not os.path.exists("local.tools/gitchangelog-2.1.3"):
    os.mkdir("local.tools")
    urllib.URLopener().retrieve("https://pypi.python.org/packages/source/g/gitchangelog/gitchangelog-2.1.3.tar.gz","local.tools/gitchangelog-2.1.3.tar.gz")
    tar = tarfile.open("local.tools/gitchangelog-2.1.3.tar.gz")
    tar.extractall("local.tools")
    tar.close()

newEnv = os.environ.copy()
newEnv["GITCHANGELOG_CONFIG_FILENAME"] = "gitchangelog.rc"
with open("local.changelog.txt", "wb") as f:
    subprocess.check_call(["python", "local.tools/gitchangelog-2.1.3/gitchangelog.py"], stdout=f, env=newEnv)
