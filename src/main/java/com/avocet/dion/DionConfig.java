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
import java.nio.file.Paths;
import java.io.File;
import java.util.HashMap;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import java.lang.NullPointerException;

import com.avocet.dion.ReadXMLFile;
import com.avocet.dion.DionProject;

/**
 * This Class Controls Dion Configuration Reads
 *
 * @author Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version 1.0
 * @since 1.0
 */
public class DionConfig {

	// Initialize Logger
	private static final Logger logger = Logger.getLogger(App.class.getName());


	// Global Variables
	private static NodeList projects;
	private static HashMap<String, DionProject> projectConfig;

	/**
	 * This method controls the configuration process for Dion
	 *
	 * @param options the command-line arguments
	 *
	 */
	public static void setup(DionCommands options){

		// Log Operation
		logger.finest("Searching for configuration file");

		// Read Configuration File
		try {
			ReadXMLFile manifest = new ReadXMLFile();
			File f = new File(
				System.getProperty("user.home"),
				".manifest.xml");

			manifest.parseFile(f);
			logger.finest("Configuration file loaded");

			// Fetch Projects
			logger.finest("Loading Projects");
			projects = manifest.fetchXPath("/dion:manifest/dion:projects/dion:project");
			
			int len = projects.getLength();
			logger.finest(String.format("Projects Found: %s", len));

			for (int i = 0; i < len; i++){
				NamedNodeMap node = projects.item(i).getAttributes();

				// Fetch Data
				String name		= node.getNamedItem("name").getNodeValue();
				String path		= node.getNamedItem("path").getNodeValue();
				String title	= node.getNamedItem("title").getNodeValue();
			
				// Initialize Project
				logger.finest(String.format("Configuring Project: %s", name));
				DionProject project = new DionProject(name, path, title);	

			}



		} catch(NullPointerException e){
			logger.severe("Unable to parse configuration file");
			e.printStackTrace();
		}

	}


}

