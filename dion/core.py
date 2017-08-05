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
import datetime
from re import sub
from os.path import exists, isfile, abspath, expanduser
from . import commands as cmds
import logging

logger = None 

###################### FETCH TIME NOW()############################
timer_start = 0

def timer():
    return datetime.datetime.now()

def start():
    global timer_start
    timer_start = timer()

######################### EXIT PROCESS ############################
def exit(code = 0, msg = "Closing Dion"):
    """ Initiates the exit process """

    # Run Message
    if code == 0:
        logger.info(msg)
    else:
        logger.critical(msg)

    # Fetch Timer
    global timer_start
    timer_end = timer() 
    timer_diff = timer_end - timer_start
    sec = round(timer_diff.total_seconds(), 2)

    # Close Exit

    from sys import exit as sys_exit
    print("\nOperation Complete: %s seconds" % sec)
    sys_exit(code)


############################# VALIDATE FILE #################################
def validate_file(error, path):

    path = abspath(expanduser(path))
    if path is None:
        exit(2, "%s: No file defined" % error)
    elif not exists(path):
        exit(2, "%s: File path '%s' does not exist" % (error, path))
    elif not isfile(path):
        exit(2, "%s: Defined path '%s' is not a file" % (error, path))

    return path



######################### RUN PROCESS #############################

# Set Legitimate Commands
legit_commands = ['dry-run']

# Run Process
def run(args):
    """ Runs the main Dion process """

    # Start Timer
    start()

    # Configure Logging
    if args.verbose:
        logformat = "[%(levelname)s %(asctime)s]: %(filename)s - %(funcName)s()\n\t%(message)s"
    else:
        logformat = "%(levelname)s: %(message)s"

    if args.debug:
        loglevel = logging.DEBUG
    else:
        loglevel = logging.WARNING

    logging.basicConfig(level = loglevel, format = logformat)
    global logger
    logger = logging.getLogger()

    logger.info("Starting Dion")

    # Initialize Commands
    if args.command not in legit_commands:
        exit(1, "Invalid Command")

    # Fetch and Run Command
    argument = sub("-", "_", args.command)
    command = getattr(cmds, argument) 
    command(args)
   
    exit()



