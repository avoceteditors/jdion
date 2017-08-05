(ns dion-backend.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[dion-backend started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[dion-backend has shut down successfully]=-"))
   :middleware identity})
