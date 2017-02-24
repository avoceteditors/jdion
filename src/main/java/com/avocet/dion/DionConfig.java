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

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.util.ArrayList;
import java.util.HashMap;

// Logger
import java.util.logging.Logger;

// XML
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


// Map
import java.util.HashMap;

// Avocet
import com.avocet.dion.DionProject;
import com.avocet.dion.DionXMLHandler;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * This class handles configuration for Dion
 *
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
class DionConfig {

	// Initialize Logger
	private static final Logger logger = Logger.getLogger(DionConfig.class.getName());

	// Initialize Global Variables
	private File configFile;
	private HashMap<String, DionProject> projects = new HashMap<String, DionProject>();
	private HashMap<String, String> dbConfig = new HashMap<String, String>();
	private DionXMLHandler manifest;
	private ArrayList<HashMap<String, String>> projectList;

	/**
     * Constructor for Configuration Class
     *
     * @param options: DionCommands Instance
     */
	public DionConfig(String path) {

		// Log Operation
		logger.info("Parsing Configuration File");


		try {

			// Initialize Manifest
			manifest = new DionXMLHandler(path);	



			// Fetch Project Data
			ArrayList<String> keys = new ArrayList<String>();
			keys.add("name");
			keys.add("path");

			String xpathExpression = "/dion:manifest/dion:projects/dion:project";
		
			projectList = manifest.fetchData(xpathExpression, keys);

			// Correct Paths for user.home 
			for (HashMap<String, String> project : projectList){
				String projectPath = project.get("path");
				
				// Test Opening Character
				char firstChar = projectPath.charAt(0);	

				if (firstChar == '~'){
					String basePath = projectPath.split("~")[1];
					project.put("path", System.getProperty("user.home") + basePath); 
					
				}

			}

			// Fetch Configuration
			keys = new ArrayList<String>();
			keys.add("name");
			keys.add("user");
			keys.add("passwd");

			xpathExpression = "/dion:manifest/dion:config/dion:database";
			dbConfig = manifest.fetchData(xpathExpression, keys).get(0);
						

		} catch(IOException e){
			
			// Report Error
			e.printStackTrace();
			logger.severe("Unable to find manifest.xml");

		} catch(ParserConfigurationException e){

			// Report Error
			e.printStackTrace();
			logger.severe("Error Configuring Parser for manifest.xml");

		} catch(SAXException e){
		
			// Report Error
			e.printStackTrace();
			logger.severe("Error Parsing manifest.xml");

		} catch(XPathExpressionException e){

			// Report Error
			e.printStackTrace();
			logger.severe("Error Retrieving Data from manifest.xml");
		}

	}

	/**
     * This method returns database config values
     */
	public String getDbConfig(String key){
		return dbConfig.get(key);
	}
	
	/**
     * This method initializes DionProjects
     */
	public void loadProjects(){


	}


}
