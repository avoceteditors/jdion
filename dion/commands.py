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

from os.path import exists, isfile
import json
from re import match


from . import core
from . import databases
from .rst import parse_rst

def set_var(conf, key, default):
    try:
        return conf[key]
    except:
        return default
 


############################ CONFIGURE ############################  
def configure(args):

    # Retrieve Paths from Arguments
    if args.file:
        source = core.validate_file("Invalid File Option", args.file)
    else:
        source = None

    configpath = core.validate_file("Invalid Configuration File Option", args.config)

    # Fetch Global Configuration File
    try:
        with open(configpath, 'r') as f:
            config = json.load(f)
    except:
        core.exit(1, "Invalid Configuration File")
    
    config['source_path'] = source

    return config


############################ DRY RUN COMMAND ################################
def dry_run(args):
    """ Command performs basic dry-run operations, reading the file
    as defined by the -f --file option and returning information
    gleaned from the file. """

    # Configure Dion 
    config = configure(args)

    # Parse Source Files
    if config['source_path'] is not None:
        source = config['source_path']
        if match('^.*?\.rst', source): 

            # Generate pseudo-xml
            xml = parse_rst(source)

            # Print Pseudo-XML Data
            print('\nPseudo-XML Data:\n')
            print(xml)
            print('\n')


############################ UPDATE COMMAND ################################
def update(args):
    """ Command iterates over each project to update to the database"""
    
    # Fetch Config
    config = configure(args) 

    # Configure Database Interfaces
    try:
        db_local = config['system']['local_database']
        db_conf = config['databases'][db_local]

        # Retrieve Database Configuration
        db_type = db_conf['type']
        db_user = db_conf['user']
        db_passwd = db_conf['password']
        db_name = db_conf['name']
        root_user = args.user
        root_passwd = args.password
        host = db_conf['host']
        port = db_conf['port']

        # Initialize Database Fetch Function
        if db_type.lower() in ['arangodb', 'orientdb']:
            config['databases'][db_local]['db']  = databases.init(db_type, db_name, 
                host, port, db_user, db_passwd, root_user, root_passwd)
        else:
            core.exit(1, "Invalid Local Database Type: %s" % db_type)

    except:
        core.exit(1, "Invalid Database Configuration")

    # Iterate Over Projects
    if 'projects' in config:
        pass

    else:
        core.exit(1, "No projects found in %s" % args.config)

    
