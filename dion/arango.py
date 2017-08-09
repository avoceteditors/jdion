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

from arango import ArangoClient

from logging import getLogger
logger = getLogger()

class ArangoDatabase():

    # Initialize Database Class
    def __init__(self, name, host, port, user, passwd, root, root_passwd):
        """ Initializes the Database Class for ArangoDB"""

        # Configure Database Connection 
        self.name = name
        self.host = host
        self.port = port

        self.user = user
        self.passwd = passwd

        if root is not None:
            self.root = root 
            self.root_passwd = root_passwd
        else:
            self.root = user
            self.root_passwd = passwd

        # Connect and Configure Database
        try:

            # Initialize Database Connection
            self.client = ArangoClient(
                    protocol = 'http',
                    host = self.host,
                    port = self.port,
                    username = self.root,
                    password = self.root_passwd,
                    enable_logging = True)

            # Open Database and Configure the Schema
            self.open_database()
            self.configure_schema()
        except as err:
            logger.warning("Unable to Connect to ArangoDB")
            print(err)
        

    # Open Database
    def open_database(self):
        """ Initializes the Database Object by either creating the database
        or by opening an existing database with the given name"""

        # Create or Open Database 
        if self.name in self.client.databases():
            self.db = self.client.database(
                    name = self.name,
                    username = self.user, 
                    password = self.passwd)
        else:
            self.db = self.client.create_database(
                    username = self.root_user,
                    password = self.root_password,
                    users = {
                        "username": self.user,
                        "password": self.passwd,
                        "active": True,
                        "extra": {}})

    # Configure Schema
    def configure_schema(self):
        """ Initializes the database schema and sets the base
        collections on the database by either creating them
        or initializing existing collections on the class."""

        # Create or Load Collections
        collections = ['projects', 'resources', 'files', 'sections']
        collection_list = self.db.collections()
        for i in collections:
            if i in collection_list: 
                setattr(self, '%s_collection' % i, self.db.collection(i))
            else:
                setattr(self, '%s_collection' % i, 
                        self.db.create_collection(
                            name = i))
            


        

        
            
