/*
 * dion.c -- Provides the main server process for Dion.  Together, it initializes
 *     a socket file, monitors relevant directories for changes, and starts the
 *     various Python operations needed to keep the database up to date.
 *
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
#include<stdio.h>
#include<stdlib.h>
#include<getopt.h>

// Version Number
char version[] = "0.2";

// Usage
void print_usage(){
	printf("Usage: dion-server [options] <command>\n\n"); 
	exit(2);
}

// Masthead
void print_masthead(int verbose){
	printf("Dion: The Document Processor");
	if (verbose > 0){

		printf( "\n  Kenneth P. J. Dyer <kenneth@avoceteditors.com>\n"
				"  Avocet Editorial Consulting\n"
				"  Version %s\n\n", version);
	} else {
		printf(" - version %s\n\n", version);
	}

}
// Version
void print_version(int verbose){
    print_masthead(verbose);	
	exit(0);

}

// Main Process
int main(int argc, char *argv[]){

	/******* Command-line Arguments and Options **********/
	// Init Variables
	int option;
	int vflag = 0;
    int Vflag = 0;

	// Parse Short Options
	while ((option = getopt(argc, argv, "v")) != -1){
		switch (option) {

			// Verbose
			case 'v':
				vflag = 1;
				break;

			// Version
			case 'V':
				Vflag = 1;
				break;
			default:
				print_usage();
		}
	}

	// Print Masthead
	if (Vflag > 0){
		print_version(vflag);
	} else {
		print_masthead(vflag);
	}


	// Exit
	return 0;
}


