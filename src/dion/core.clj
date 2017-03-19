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

  [
   ;; Verbose Output
   ["-v" "--verbose" "Defines the verbosity level." 
    :id :verbosity
    :default false 
    :assoc-fn (fn [m k _] (update-in m [k] inc))] 

   ;; Help
   ["-h" "--help"] ])

;; Print Usage Information
(defn usage [options-summary]
	(->> ["Dion description."
		"Usage: dion [options] action"
		"Options:"
		options-summary
		"\nActions:"
        "- start"
        "- stop"
        "- status"
	] (string/join \newline)))

;; Error Message
(defn error-msg [errors]
	(str "The following errors occured while parsing your command:\n\n"
		(string/join \newline errors)))

;; Exit Process
(defn exit [status msg]
	(println msg)
	(System/exit status))

;; Main Process
(defn -main
	"Main Process"
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

