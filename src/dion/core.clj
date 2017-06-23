;; Copyright (c) 2017, Kenneth P. J. Dyer <kenneth@avoceteditors.com>
;; All rights reserved.
;;
;; Redistribution and use in source and binary forms, with or without
;; modification, are permitted provided that the following conditions are met:
;;
;; * Redistributions of source code must retain the above copyright notice, this
;;   list of conditions and the following disclaimer.
;; * Redistributions in binary form must reproduce the above copyright notice,
;;   this list of conditions and the following disclaimer in the documentation
;;   and/or other materials provided with the distribution.
;; * Neither the name of the copyright holder nor the name of its
;;   contributors may be used to endorse or promote products derived from
;;   this software without specific prior written permission.
;;
;; THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
;; AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
;; IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
;; ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
;; LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
;; CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
;; SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
;; INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
;; CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
;; ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
;; POSSIBILITY OF SUCH DAMAGE.

(ns dion.core 
  (:require	[clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as string]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [dion.op :as op]
            )
  (:gen-class :main true))

;; Configure Command-line Options
(def cli-options
  "Defines and parses command-line arguments."
  {:added "0.1.0"}

  [
   ;; Verbose Output
   ["-v" "--verbose" "Defines the verbosity level." 
    :id :verbosity
    :default false 
    :assoc-fn (fn [m k _] (update-in m [k] inc))] 

   ;; Help
   ["-h" "--help"] ])

;; Print Usage Information
(defn usage 
  "Takes one argument, which provides a summary of the available command-line
  arguments with their relevant documentation then generates the usage message
  to print in the event of CLI errors."
  {:added "0.1.0"}
  [options-summary] 

  (->> ["Dion description."
		"Usage: dion [options] action"
		"Options:"
		options-summary
		"\nActions:"
        "- start"
        "- stop"
        "- status"
        ] (string/join \newline)))

;; CLI Error Message
(defn error-msg 
  "In the event that the application encounters an error while parsing arguments
  from the command-line, this message logs the errors to standard output.  It takes
  a single argumetn, an array of encountered errors."
  {:added "0.1.0"}
  [errors]

  (str "The following errors occured while parsing your command:\n\n"
       (string/join \newline errors)))

;; Exit Process
(defn exit 
    "Takes two arguments: exit-code and msg.  The exit-code is an integer between 
    0 and 2.  If the exit code is 0, it runs org.clojure.tools.logging/info to report
    the message.  Otherwise it runs org.clojure.tools.logging/error, then exits." 
    {:added "0.1.0"}
    [exit-code msg] 

    ;; Log Exit based on exit-cide 
    (if (exit-code > 0)
      (log/error msg)
      (log/info msg))

    ;; Exit
	(System/exit exit-code))

;; Main Process
(defn -main
	"Takes arguments from the command-line and initializes the main processes."
    {:added "0.1.0"}
	[& args]

    (log/info "Arguments:" args) ; remove later

	;; Parse Options
	(let [{:keys [options arguments errors summary]} 
			(parse-opts args cli-options)]
		(cond
			(:help options) (exit 0 (usage summary))
			(not= (count arguments) 1) (exit 1 (usage summary))
			errors (exit 1 (error-msg errors)))
        (case (first arguments)
          "start" (println "Starting Dion (Feature not yet implemented)")
          "stop" (println "Stoping Dion (Feature not yet implemented)")
          "status" (println "Status Dion (Feature not yet implemented)")
          (exit 1 (usage summary))
          )

		)

    ;; Exit
    (exit 0 "Closing Dion")
)

