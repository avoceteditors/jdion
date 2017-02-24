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
import java.io.File;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.avocet.dion.DionConfig;

/**
 * Controller Class for OrientDB
 *
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
public class DionDatabase {

	// Init Logger
	private static final Logger logger = Logger.getLogger(DionDatabase.class.getName());

	// Global Variables
	private static String dbUser;
	private static String dbPass;
	private static String rootUser;
	private static String rootPass;
	private static String databaseName;
	private static String databaseURI;
	private static ODatabaseDocumentTx database;
	private static OServer server;

	/**
     * Database Class Constructor
     */
	public DionDatabase(Boolean init, DionConfig config, String userName, String userPasswd){

		// Set User and Password
		rootUser = userName;
		rootPass = userPasswd;
		dbUser = config.getDbConfig("user");
		dbPass = config.getDbConfig("passwd");
		databaseName = config.getDbConfig("name");
		databaseURI = String.format("plocal:localhost%s%s", File.separator, databaseName);

		// Initialize Database
		database = new ODatabaseDocumentTx(databaseURI);

		
		// Open or Create Database
		logger.info("Searching for database: " + databaseURI);
		if (init && database.exists()){
			// Removing Database
			logger.warning("Database Exists, Removing");
			
			// Open Database
			logger.finest("Opening Database");
			database.open(dbUser, dbPass);
			logger.finest("Database Open, removing...");
			database.drop();
			logger.finest("Database Removed");
		
			// Create Database
			logger.info("Creating New Database");
			database.create();

			// Initialize Schema
			configureSchema();

		} else if (database.exists()){
			logger.info("Opening Database");
			database.open(dbUser, dbPass);
		} else {
			// Create Database
			logger.info("Creating Database");
			database.create();
			logger.finest("Database Created");
			
			// Initialize Schema
			configureSchema();
		}

	}

	/**
     * This method configures the database schema 
     */
	private static void configureSchema(){

	}


}
