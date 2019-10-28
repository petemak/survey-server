(ns survey-server.application
  (:require  [survey-server.routes :as routes]
             [ring.adapter.jetty :as jetty]))


;; ----------------------------------------------------------------
;; Atom to hold server instance
;;
;; ----------------------------------------------------------------
(defonce server (atom nil))


;; ----------------------------------------------------------------
;; Ensure we have a valid port to use
;; ----------------------------------------------------------------
(defn valid-port
  "Ensure port is valid otherwise default to 8080"
  [port]
  (or (or port (pos-int? port)) 8080))


;; ----------------------------------------------------------------
;; Start server
;; ----------------------------------------------------------------
(defn start-server
  "Starts a server instances on a given port if provided ortherwise
   defaults to 8080"
  [& port]
  (let [p (valid-port port)]
    (println "::=> Starting server on port [" p "] ....")
    (reset! server (jetty/run-jetty routes/app {:port p :join? false}))
    (println "::=> Server started on port [" p "]" ) ))


;; ----------------------------------------------------------------
;; Stop server
;; ----------------------------------------------------------------
(defn stop-server
  "Stop any running instances and resets any cached state"
  []
  (.stop @server)
  (reset! server nil))
