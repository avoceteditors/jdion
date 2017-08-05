# Copyright (c) 2017, Kenneth P. J. Dyer <kenneth@avoceteditors.com>
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
# * Redistributions of source code must retain the above copyright notice, this
#   list of conditions and the following disclaimer.
# * Redistributions in binary form must reproduce the above copyright notice,
#   this list of conditions and the following disclaimer in the documentation
#   and/or other materials provided with the distribution.
# * Neither the name of the copyright holder nor the name of its
#   contributors may be used to endorse or promote products derived from
#   this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
# ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
# LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
# INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
# CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
# ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGE.

from pyArango.connection import *
from logging import getLogger
logger = getLogger()

class ArangoDatabase():

    def __init__(self, name, user, passwd, host, port):

        # Init Variables
        self.name = name
        self.user = user
        self.passwd = passwd
        self.host = host
        self.port = port

        print(user)
        print(passwd)
        # Init Connection
        self.conn = Connection(username=user, password=passwd)

        # Create or Open Database 
        try:
            self.db = self.conn.createDatabase(name=self.name)
            logger.debug("Creating Database: %s" % self.name)
        except:
            logger.debug("Opening Database: %s" % self.name)
            self.db = self.conn.__getitem__(self.name)

        # Create or Load Collections
        collections = ['projects', 'resources', 'files']
        for i in collections:
            if self.db.hasCollection(name=i):
                setattr(self, '%s_collection' % i, self.db[i])
            else:
                setattr(self, '%s_collection' % i, self.db.createCollection(name=i))
            


        

        
            
