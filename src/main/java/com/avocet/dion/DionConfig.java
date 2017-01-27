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
import java.util.logging.Logger;
import java.util.ArrayList;
import java.io.File;
import java.lang.NullPointerException;

/**
 * @author Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version 1.0
 * @since 1.0
 */
public class DionConfig {

	// INIT LOGGER
	private static final Logger logger = Logger.getLogger(DionConfig.class.getName());

	// SEARCH DIRECTORIES
	public static ArrayList<String> locations = new ArrayList<String>();

	/**
	 * Parse Configuration File
	 */ 
	public static void parseConfig(String configpath){

		// Log Operation
		logger.finest("Searching for configuration file.");
		
		// Search Directories
		initLocations();

		// Find Config
		String path = findFile(configpath);
		
		try {
			File f = new File(path);
		} catch(NullPointerException e){
			logger.severe("Unable to locate configuration file");
		}
	}

	/**
	 * Initializes Locations Array
	 */ 
	private static void initLocations(){

		// Add Current Directory
		logger.finest("Adding current directory");
		locations.add(System.getProperty("user.dir"));

		// Add Home Directory
		logger.finest("Adding home directory");
		locations.add(System.getProperty("user.home"));
	}

	public static String findFile(String filename){

		// Log Operation
		logger.finest("Searching for: " + filename);
		String path = "";

		// Search Locations
		for(String dir : locations){
			File file = new File(dir, filename);

			// Test that File Exists
			if (file.exists()){
				path = file.getAbsolutePath();
				break;
			} 
		}
		
		// Log Finding
		logger.finest("Found: " + path);

		// Return Path
		return path;
		
	}

}

