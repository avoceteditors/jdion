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
	import org.kohsuke.args4j.CmdLineParser;
	import org.kohsuke.args4j. CmdLineException;
	import java.util.logging.Logger;
	import java.io.IOException;
	import com.avocet.dion.DionLogger;
	import com.avocet.dion.DionCommands;
	/**
	 * @author Kenneth P. J. Dyer <kenneth@avoceteditors.com>
	 * @version 1.0
	 * @since 1.0
	 */
	class App {

		// Initialize Logger
		private static final Logger logger = Logger.getLogger(App.class.getName());


		// Command-line Arguments
		public static DionCommands options;

		// Configuration
		public static DionConfig config;

		/**
		 * This metod controls the main process for Dion.
		 *
		 * @param args the command-line arguments
		 */
		public static void main(String[] args){

			// Parse Command-line Arguments
			DionCommands options = new DionCommands();
			CmdLineParser parser = new CmdLineParser(options);

			try {
				parser.parseArgument(args);
			} catch(CmdLineException e){
				System.err.println(e.getMessage());
				parser.printUsage(System.err);
			}	

			// Run Masthead
			masthead(options);

			// Configure Log Handling
			try {
				DionLogger.setup();
			} catch(IOException e) {
				e.printStackTrace();
			}

		logger.info("Starting Dion");

		// Configure Dion
		logger.info("Beginning Configuration");
		DionConfig config = new DionConfig();
		config.setup(options);
		logger.info("Configuration Complete");
	}

	/**
   * This method prints the application masthead on startup.
   */
	private static void masthead(DionCommands options){

		// Fetch Build Options
		Package pack 		=	Package.getPackage("com.avocet.dion");
		String name 		=	pack.getSpecificationTitle();
		String version	=	pack.getImplementationVersion();


		// Init Mastead
		ArrayList<String> masthead = new ArrayList<String>();

		// Configure Verbose Masthead
		if (options.verbose){

			// Add Title Line
			masthead.add(String.format("%s - %s", name, options.slogan));

			// Add Name and Contact
			masthead.add(String.format(" - %s <%s>", options.author, options.email));

			// Company
			masthead.add(String.format(" - %s", options.company));

			// Version
			masthead.add(String.format(" - Version %s", version));

		} 
		// Configure Quiet Masthead
		else {
			masthead.add(String.format("%s - version %s", name, version));
		}
			
		// Print Masthead
		masthead.add("\n");
		for (String line : masthead){

			// Print Line
			System.out.println(line);
		}

	}

}
