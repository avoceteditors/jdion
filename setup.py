#!/usr/bin/env python3
from setuptools import setup
from os.path import join

binlist = []
for i in ['dion']:
	binlist.append(join('scripts', i))

setup(  name = "dion",
        version = "0.2",
        author = "Kenneth P. J. Dyer",
        author_email = "kenneth@avoceteditors.com",
        url = "https://github.com/avoceteditors/dion",
        packages = ['dion'],
        scripts = binlist, 
        license = "Revised BSD"
)
