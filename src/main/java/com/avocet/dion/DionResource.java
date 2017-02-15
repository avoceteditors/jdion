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
import java.nio.file.Path;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.util.ArrayList;

import java.io.IOException;

/**
 * Class Manages Project Resource Data
 *
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
public class DionResource {

	// Initialize Logger
	private static final Logger logger = Logger.getLogger(App.class.getName());

	// Global Variables
	private static String name;
	private static String type;
	private static Path path;
	private static Boolean valid;

	private static ArrayList<Path> resource;

	public DionResource(String resname, String restype, String basePath, String respath) {

		// Set Variables
		name			= resname;
		type			= restype;
		File basepath	= new File(basePath, respath);
		valid			= true;	

		// Log Operation
		logger.info(String.format("Initializing Resource: %s", name));

		// Initialize Resource Contents
		if (basepath.exists() && basepath.isDirectory()){
			
			// Initialize Path
			path = basepath.toPath();
			confFiles();

		} else {
			valid = false;
			logger.severe(String.format("Resource Disabled: %s", name));
		}
		
	}

	public static void confFiles(){
		// Build Resource List
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.{xml}")){
			logger.finest("Adding Resource Files");
			
			for (Path entry: stream){
				System.out.println(entry);
				
			}

			// Look into initializing connection to OrientDB and recordign project and resource info

		} catch(IOException e){
			System.out.println(e);
			
		}
	}

}
