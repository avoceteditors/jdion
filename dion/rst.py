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

import docutils.core
import docutils.parsers.rst
import io

from docutils.parsers.rst import directives, roles
from .metadata import MetadataDirective
from .include import IncludeDirective

# Parse RST Files for XML
def parse_rst(path):
    """ Parses reStructuredText file and returns pseudo-XML string"""


    with open(path, "r") as f:
        text = f.read()

    # Configure Parser
    rstparser = docutils.parsers.rst.Parser()
    overrides = {'input_encoding': 'utf8',
            'output_encoding': 'utf8'}

    # Loop over Directives and roles, adding them to parser
    directive_manifest = [
        ('metadata', MetadataDirective),
        ('include', IncludeDirective)
    ]
    for (name, base) in directive_manifest:
        directives.register_directive(name, base)

    roles_manifest = []
    #for (name, base) in roles_manifest:
    #    roles.regiter_local_role(name, base)


    # Parse Text
    xml = docutils.core.publish_doctree(text, 
            parser = rstparser,
            settings_overrides=overrides)

    return xml 
