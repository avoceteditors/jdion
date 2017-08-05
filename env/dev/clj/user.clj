(ns user
  (:require [mount.core :as mount]
            dion-backend.core))

(defn start []
  (mount/start-without #'dion-backend.core/repl-server))

(defn stop []
  (mount/stop-except #'dion-backend.core/repl-server))

(defn restart []
  (stop)
  (start))


