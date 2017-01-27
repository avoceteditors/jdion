/*
 * Copyright (c) 2017, Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holder nor the name of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.avocet.dion;
import org.kohsuke.args4j.Option;

/**
 * Command-line Argument Handler
 * 
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
public class DionCommands {
    
  // COMMAND-LINE ARGUMENTS
  @Option(name="--verbose", usage="Enables output verbosity")
  public Boolean verbose = false;

  @Option(name="--version", usage="Print version information")
  public Boolean version = false;
    
  @Option(name="--config", usage="Sets path to configuration file")
  public String config = ".manifest.xml";
    
  // REPORT METHOD
  public void report(){
      System.out.println("Testing Dion CLI Arguments");
      System.out.println("--verbose: " + verbose);
      System.out.println("--version: " + version);
			
  }

}
