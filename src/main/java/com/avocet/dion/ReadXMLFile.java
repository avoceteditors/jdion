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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import java.io.IOException;
import org.xml.sax.SAXException;

import java.util.logging.Logger;

/**
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since 1.0
 */
public class ReadXMLFile {

	// FILENAME
	public static String filename;

	// LOG HANDLER
	private static final Logger logger = Logger.getLogger(ReadXMLFile.class.getName());

	private static DocumentBuilder builder;
	private static DocumentBuilderFactory factory; 
	public static Document document;

	// FILEPATH READER
	public static void parseFilePath(String filename){
		
		// Open File
		logger.finest("Opening File: " + filename);
		
		File f = new File(filename);
		parseFile(f);
	}

	public static void parseFile(File xmlFile){
		// Log Operation
		logger.finest("Parsing XML: " + xmlFile.getPath());

		// Initialize XML
		try {

			// Init Builders
			logger.finest("Initializing XML Factory, Builder and Document");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder(); 

			// Init Document
			Document document = builder.parse(xmlFile);

		} catch(Exception e) {

			// Note: Add logic to pass over XML documents that fail
			e.printStackTrace();

		} 
		logger.finest("XML Document Initialized");

	}
}

