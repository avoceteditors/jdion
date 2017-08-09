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
from docutils import nodes
from docutils.parsers.rst import Directive, directives
import datetime

from logging import getLogger
logger = getLogger()

# Metadata Node
class metadata(nodes.Element):
    pass

# Metadata Class
class MetadataDirective(Directive):

    required_arguments = 0
    optional_arguments = 0
    has_content = True
    option_spec = {
        "title": directives.unchanged, 
        "format": directives.unchanged,
        "date": directives.unchanged,
        "abstract": directives.unchanged}

    # Run Directive
    def run(self):

        # Initialize Node
        self.node = metadata() 

        # Add Attributes
        attrs = [
            ('title', 'none'),
            ('format', 'none'),
            ('abstract', '')]

        for (name, default) in attrs:
            self.add_option(name, default)

        # Add Date Attribute
        self.add_date_option('date', 'none')

        return [self.node]

    # Add Option to Node
    def add_option(self, name, value):

        if name in self.options:
            value = self.options[name]

        self.node.update_all_atts({name: value})

    # Add Date Option to Node
    def add_date_option(self, date, value):
        try:
            value = datetime.datetime.strptime(self.options['date'], '%Y-%m-%d %h:%M %Z')
        except:
            pass

        self.node.update_all_atts({date: value})


