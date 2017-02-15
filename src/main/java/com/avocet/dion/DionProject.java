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
import java.nio.file.FileSystems;
import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import com.avocet.dion.ReadXMLFile;
import com.avocet.dion.DionResource;

/**
 * Project Configuration Class
 *
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
public class DionProject {

	// Initialize Logger
	private static final Logger logger = Logger.getLogger(App.class.getName());

	// Global Variables
	private static String name;
	private static String title;
	private static Path path;

	// Update Conditions
	private static Boolean valid;

	private static ReadXMLFile project;

	private static ArrayList<DionResource> resources;

	/**
	 * This method initializes the class.
	 *
	 * @param name: The base name of the project.
	 * @param projPath: The path to the project root.
	 * @param title: The display title for the project.
	 */
	public DionProject(String projName, String projPath, String projTitle){


		// Initialize Base Variables
		name = projName;
		title = projTitle;
		valid = true;

		logger.info(String.format("Initializing Project: %s", title));

		// Configure Path
		path = FileSystems.getDefault().getPath(projPath);

		if (!path.isAbsolute()){
			
			// Expand Home Directory
			if(path.startsWith("~")){

				// Replace $HOME
				projPath = String.format("%s/%s", System.getProperty("user.home"), projPath.replace("~", "")); 
				
				// Reinitialize Path on Absolute Value
				path = FileSystems.getDefault().getPath(projPath);

				
			} else {
				// Convert Relative Path to Absolute Path
				path = path.toAbsolutePath();
			}
		}


		// Find Project XML
		File f = new File(projPath, "project.xml");
		
		
		if (f.exists() && !f.isDirectory()){
			
			try {
				readXml(f);
			} catch(NullPointerException e){
				valid = false;
				e.printStackTrace();
				logger.severe(String.format("Unable to Read Project File: %s", name));
			}

		} else {
			valid = false;
		}
		

	}

	/**
	 * 
	 */
	public static void readXml(File f){
		// Log Operation
		logger.info("Reading Project File");

		// Initialize Project
		project = new ReadXMLFile();
		project.parseFile(f);

		// Locate Resource Directories

		
		fetchResources(project);

		logger.info("Project Ready");
	}

	public static void fetchResources(ReadXMLFile project){
		
		// Fetch Resources from File
		NodeList baseResources = project.fetchXPath("/bacch:project/bacch:config/bacch:resources/bacch:resource");

		// Find Length
		int len = baseResources.getLength();
		logger.finest(String.format("Identified Resources: %s", len));


		// Configure Resources
		for (int i = 0; i < len; i++){
			NamedNodeMap node = baseResources.item(i).getAttributes();

			// Fetch Data
			String name		= node.getNamedItem("name").getNodeValue();
			String rpath	= node.getNamedItem("path").getNodeValue();
			String type		= node.getNamedItem("type").getNodeValue();

			DionResource resource = new DionResource(name, type, path.toString(), rpath);
		}		

	}

}
