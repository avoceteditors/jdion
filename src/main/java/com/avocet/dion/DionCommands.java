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
import org.kohsuke.args4j.Option;
import java.io.File;

/**
 * Command-line Arguments Handler
 *
 * @author: Kenneth P. J. Dyer <kenneth@avoceteditors.com>
 * @version: 1.0
 * @since: 1.0
 */
public class DionCommands {

	// Generic Information
	public static final String name = "Dion";
	public static final String author = "Kenneth P. J. Dyer";
	public static final String email = "kenneth@avoceteditors.com";
	public static final String company = "Avocet Editorial Consulting";
	public static final String versionName = "1.0";
	public static final String slogan = "The Document Processor for Writers and Technical Writers";

	// Command-line Arguments
	@Option(name="--verbose", usage="Enables output verbosity")
	public static Boolean verbose = false;

	@Option(name="--version", usage="Prints version information")
	public static Boolean version = false;

	@Option(name="--config", usage="Sets the path to te configuration file")
	public static String config = String.format(
				"%s%s.dion%smanifest.xml", 
				System.getProperty("user.home"),
				File.separator, File.separator);

	@Option(name="--init", usage="Configures the Database Schema")
	public static Boolean initdb = false;

	@Option(name="--user", usage="Defines database user")
	public static String user = "admin";

	@Option(name="--password", usage="Defines database user apssword")
	public static String password = "admin";


}
