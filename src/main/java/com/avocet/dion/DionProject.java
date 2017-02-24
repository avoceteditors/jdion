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

// Logger
import java.util.logging.Logger;

// General
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.xml.sax.InputSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class hadnles project data for Dion.
 *
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
class DionProject {

	// Initialize Logger
	private static final Logger logger = Logger.getLogger(DionProject.class.getName());

	// Global Variables
	private String name;
	private String path;
	private Boolean valid = true;

	/**
     * Class Contrusctor
     */
	public DionProject(String projectName, String projectPath) {
	
		// Log Operation
		logger.info("Initializing Project: " + projectName);

		// Set Global Variables
		name = projectName;
		path = projectPath;
		

		try{
			File projectXMLFile = new File(path, "project.xml");
			parseXML(projectXMLFile);
		} catch(Exception e){
			valid = false;
		}

	}

	/**
     * This method parses the Project XML File for relevant source data
     */
	private void parseXML(File projectXML){

		// Log Operation
		logger.finest("Parsing project.xml");
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			
			// Configure Handler
			DefaultHandler handler = new DefaultHandler(){


				private DionPrompt prompt;
				private Boolean inPromptPS1 = false;
				private Boolean inPromptPS2 = false;


				/**
                 * This internal method handles start elements for the SAX Parser
                 */
				public void startElement(String uri, String localName, String qName,
						Attributes attr) throws SAXException {

					// Resource Processing
					if (qName == "bacch:resource"){
						
						// Fetch Attributes
						String name = attr.getValue("name");
						String path = attr.getValue("path");
						String type = attr.getValue("type");

					}

					// Prompts
					else if (qName == "bacch:prompt") {

						// Fetch Attributes
						String name = attr.getValue("name");
						String syntax = attr.getValue("Syntax");

						prompt = new DionPrompt(name, syntax);

					}

					else if (qName == "bacch:ps"){

						// Fetch Attributes
						String role = attr.getValue("role");
						if (role == "PS1"){
							inPromptPS1 = true;
						System.out.println(role);
						} else if (role == "PS2") {
							inPromptPS2 = true;
						}
						

					}

					
				}

				/**
                 * This internal method handles ending elements for SAX parser
                 */
				public void endElement(String uri, String localName, String qName){
			
					if (qName == "bacch:prompt"){
						inPromptPS1 = false;
						inPromptPS2 = false;
					}
				}

				/**
                 * This internal method handles characters within elements
                 */
				public void characters(char ch[], int start, int length){

					if (inPromptPS1){
						System.out.println("YES!");
					}
				}

			};

			// Configure UTF-8 Stream
			InputStream inputStream = new FileInputStream(projectXML);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");

			// Load Source
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			parser.parse(is, handler);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
