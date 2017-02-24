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
import java.util.ArrayList;

// Logging
import java.util.logging.Logger;
import java.util.logging.Level;

// CLI Arguments 
import com.avocet.dion.DionCommands;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.CmdLineException;

// Configuration
import com.avocet.dion.DionConfig;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
/**
 * Main Application Class for Dion
 *
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
class App {

	// Initialize Logger
	private static final Logger logger = Logger.getLogger(App.class.getName());


	// Global Variables
	private static DionCommands options;
	
	/**
 	 * This method controls the main process for Dion
 	 *
 	 * @param args: The command-line arguments.
 	 */
	public static void main(String[] args){

		// Handle Command-line Arguments
		options = new DionCommands();
		CmdLineParser parser = new CmdLineParser(options);

		try {
			parser.parseArgument(args);
		} catch(CmdLineException e) {
			System.err.println(e.getMessage());
			parser.printUsage(System.err);
		}

		// Configure Logging
		configureLogger();
	
		// Run Masthead 
		runMasthead();

		// Initialize Dion
		logger.info("Starting Dion");

		
		// Configure Dion
		logger.info("Configuring Dion");
		DionConfig config = new DionConfig(options.config);
		logger.info("Configuration Complete");	

		// Configure Database
		logger.info("Configuring Database");
		DionDatabase database = new DionDatabase(options.initdb, config, options.user, options.password);	
		logger.info("Database Ready");


	}


	/**
   * Method configures the logger
   */
	private static void configureLogger(){

		// Configure for Verbosity
		if(options.verbose){
			
			// Finest Level for Verboisty
			logger.setLevel(Level.FINEST);

		} else {

			// Warning Level for Quiet
			logger.setLevel(Level.WARNING);
		}

	}

	/**
   * This method prints the Masthead
   */
	private static void runMasthead(){

		// Run ASCII ART Header
		System.out.println("  ;;;;;;;;;;       ;;;;;;;;;;;;;;;      ;;;;;;;;      ;;;        ;;;\n"
									+"  ;;;;;;;;;;;      ;;;;;;;;;;;;;;;     ;;;;;;;;;;     ;;;        ;;;\n"
									+"  ;;;       ;;;          ;;;          ;;        ;;    ;;;;       ;;;\n"
									+"  ;;;        ;;;         ;;;         ;;          ;;   ;;;;;      ;;;\n"
									+"  ;;;         ;;;        ;;;        ;;;          ;;;  ;;;;;;     ;;;\n"
									+"  ;;;         ;;;        ;;;        ;;;          ;;;  ;;; ;;;    ;;;\n"
									+"  ;;;         ;;;        ;;;        ;;;          ;;;  ;;;  ;;;   ;;;\n"
									+"  ;;;         ;;;        ;;;        ;;;          ;;;  ;;;   ;;;  ;;;\n"
									+"  ;;;         ;;;        ;;;        ;;;          ;;;  ;;;    ;;; ;;;\n"
									+"  ;;;         ;;;        ;;;        ;;;          ;;;  ;;;     ;;;;;;\n"
									+"  ;;;        ;;;         ;;;         ;;          ;;   ;;;      ;;;;;\n"
									+"  ;;;       ;;;          ;;;          ;;        ;;    ;;;       ;;;;\n"
									+"  ;;;;;;;;;;;;     ;;;;;;;;;;;;;;;     ;;;;;;;;;;     ;;;        ;;;\n"
									+"  ;;;;;;;;;;;      ;;;;;;;;;;;;;;;      ;;;;;;;;      ;;;        ;;;\n\n");


		// Print Title Line
		System.out.println(String.format("%s: %s", options.name, options.slogan));
			
		// Version Report
		if (options.version){

			// Initialize Masthead ArrayList
			ArrayList<String> masthead = new ArrayList<String>();			

			if (options.verbose){

				// Verbose Masthead
				masthead.add(String.format("%s <%s>", options.author, options.email));
				masthead.add(options.company);
				masthead.add(String.format("Version: %s", options.versionName));	

			} else {

				// Brief Masthead
				masthead.add(String.format("Version: %s", options.version));
			}

			// Print Masthead Messages 
			for(String entry : masthead){
				
				System.out.println(String.format(" %s", entry));
				
			}
			
		}


	}


}
