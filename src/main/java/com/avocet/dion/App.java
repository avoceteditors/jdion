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
import org.kohsuke.args4j.CmdLineException;
import java.util.logging.Logger;
import com.avocet.dion.LogHandler;
import java.io.IOException;
/**
 *
 * @author Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version 1.0
 * @since 1.0
 */

// MAIN CLI CLASS
public class App {

	// GLOBAL VARIABLES
	public static DionCommands options;

	// INIT LOGGER
	private static final Logger logger = Logger.getLogger(App.class.getName());


    /**
	 * This method controls the main process for Dion.
	 *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Initialize CLI Options
        DionCommands options = new DionCommands();

		// Initialize CLI Parser
		CmdLineParser parser = new CmdLineParser(options);

		// Parse Arguments
		try {
			parser.parseArgument(args);

		} catch(CmdLineException e){
			System.err.println(e.getMessage());
			parser.printUsage(System.err);
		}

		// Print Masthead
	    masthead(options);

		// Initialize Logger
		try {
			LogHandler.setup();
		
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problem creating the log file.");
		
		}

		logger.info("Starting Dion");

		// Configure Dion
		logger.info("Configuring Dion");
		DionConfig config = new DionConfig();
		config.parseConfig(options.config);

	} 

	// MASTHEAD CONTROLLER
	/**
	 * This method prints the application masthead on startup.
	 */
	private static void masthead(DionCommands options){

		// BASE VARIABLES
		Package pack	= Package.getPackage("com.avocet.dion");
		String slogan	= "The Document Processor";
		String name		= pack.getSpecificationTitle(); 
		String version	= pack.getImplementationVersion();

		ArrayList<String> masthead = new ArrayList<String>();

		// BUILD MASTHEAD
		if (options.verbose){
			
			// Add Name Line
			masthead.add(String.format("%s - %s", name, slogan));

			// Add Name and Contact
			String dash = " -";
			masthead.add(String.format("%s Kenneth P. J. Dyer <kenneth@avoceteditors.com>", dash));
			masthead.add(String.format("%s Avocet Editorial Consulting", dash));
			// Add Version Line 
			masthead.add(String.format("%s Version %s", dash, version));
		
		} else {
			// Add Nonverbose Masthead
			masthead.add(String.format("%s - version %s", name, version));
		}

		// PRINT MASTHEAD
		masthead.add("\n");
		for (String line : masthead){
		
			// Print Line
			System.out.println(line);
		}

	}
    
}

