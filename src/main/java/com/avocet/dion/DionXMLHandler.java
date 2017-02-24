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

// File I/O
import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

// XML DOM
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// XPath
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.XMLConstants;
/**
 * This Class Handles XML Parsing and data retrieval
 *
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
class DionXMLHandler {


	// Initialize Logger
	private static final Logger logger = Logger.getLogger(DionXMLHandler.class.getName());	

	// Global Variables
	private Document document;
	private File file;

	/**
     * Class Constructor to initialize parser.
     */
	public DionXMLHandler(String path) throws IOException, ParserConfigurationException, SAXException {

		// Log Operation
		logger.info("XML Handler: " + path);

		// Open File
		logger.finest("Opening File: " + path);
		file = new File(path);
		if (!file.exists() || file.isDirectory()) {
			logger.warning("This file does not exist or it is a directory");
			throw new IOException();
		}


		
		// Initialize Factory
		logger.finest("Initializing Factory");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);

		// Initialize Builder
		logger.finest("Initializing Builder");
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Parse File
		logger.finest("Parsing XML");
		document = builder.parse(file);

	}

	/**
     * Method to Fetch Nodes with XPath
     */
	public NodeList fetchXPath(String expression) throws XPathExpressionException {

		// Log Operation
		logger.finest("Running XPath Expression: " + expression);

		// Initialize XPath Factory
		XPath xpath = XPathFactory.newInstance().newXPath();

		// Define Namespace Context
		xpath.setNamespaceContext(new NamespaceContext() {

			// Get Namespace Context
			public String getNamespaceURI(String prefix){

				// Null Prefix
				if (prefix == null) throw new NullPointerException("Null Prefix");
				
				// DocBook
				else if ("book".equals(prefix)) return "http://docbook.org/ns/docbook";
				
				// Bacch
				else if ("bacch".equals(prefix)) return "http://avoceteditors.com/2016/bacch";

				// Dion
				else if ("dion".equals(prefix)) return "http://avoceteditors.com/2016/dion";

				// XInclude
				else if ("xi".equals(prefix)) return "http://www.w3.org/2001/XInclude";

				// XML
				else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
				
				// Return
				return XMLConstants.NULL_NS_URI;

			}

			// Get Prefix (Unnecessary)
			public String getPrefix(String uri){
				throw new UnsupportedOperationException();
			}

			// Get Prefixes (Unnecessary)
			public Iterator getPrefixes(String uri) {
				throw new UnsupportedOperationException();
			}
		});


		// Compile Expression
		XPathExpression compiledExpression = xpath.compile(expression);

		return (NodeList) compiledExpression.evaluate(document, XPathConstants.NODESET);

	}

	/**
     * Method Fetches Attributes from a Node
     */
	public ArrayList<HashMap<String, String>> fetchData(String expression, ArrayList<String> keys) throws XPathExpressionException {

		// Log Operation
		logger.finest("Retrieving Attributes on: " + expression);
		ArrayList<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();

		// Fetch NodeList
		NodeList nodes = fetchXPath(expression);

		// Loop Over Nodes
		for (int i = 0; i < nodes.getLength(); i++) {

			// Init Variables
			HashMap<String, String> map = new HashMap<String, String>();	
			NamedNodeMap attrs = nodes.item(i).getAttributes();

			// Loop Over Keys
			for (int j = 0; j < keys.size(); j++){

				// Fetch Key
				String key = keys.get(j);
				String value = attrs.getNamedItem(key).getTextContent();
				
				// Set Data
				map.put(key, value);
			}

			// Add Map
			maps.add(map);

		}
		return maps;

	}


}
