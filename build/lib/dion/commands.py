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
from . import core
from . import databases
import configparser

def set_var(conf, key, default):
    try:
        return conf[key]
    except:
        return default

############################ DB CONFIGURE ############################  
def db_configure(conf):
    db_type = conf['db_type'].lower()

    db_name = set_var(conf, 'db_name', 'dion')
    db_user = set_var(conf, 'db_user', 'dion')
    db_pass = set_var(conf, 'db_pass', 'dion')
    db_host = set_var(conf, 'db_host', 'localhost')
    db_port = set_var(conf, 'db_port', None)

    if db_type not in ['arangodb']:
        exit(1, "Invalid Database Type: %s is not supported" % db_type)

    # Init Database Class
    init = getattr(databases, 'init_%s' % db_type)
    return init(db_name, db_user, db_pass, db_host, db_port)
            


############################ CONFIGURE ############################  
def configure(args):

    # Retrieve Paths from Arguments
    if args.file:
        source = core.validate_file("Invalid File Option", args.file)
    configpath = core.validate_file("Invalid Configuration File Option", args.config)

    # Fetch Global Configuration File
    config = configparser.ConfigParser()
    try:
        config.read(configpath)
    except:
        core.exit(1, "Invalid Configuration File")

    return {
        'source_path': source,
        'conf_path': configpath,
        'conf': config
        }


############################ DRY RUN COMMAND ################################
def dry_run(args):
    """ Command performs basic dry-run operations, reading the file
    as defined by the -f --file option and returning information
    gleaned from the file. """

    # Configure Dion 
    config = configure(args)

    # Configure Database Interfaces
    conf = config['conf']
    try:
        db_local = conf['system']['local_database']
        database = db_configure(conf[db_local])
    except:
        core.exit(1, "Invalid Database Configuration: %s" % conf['system']['local_database'])
    


