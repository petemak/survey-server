(ns survey-server.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [survey-server.handler :as handler]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


;; ----------------------------------------------------------------
;; Application routes
;; ----------------------------------------------------------------
(defroutes app-routes
  (GET "/" [] (handler/home))
  (GET "/questions/:survey-id" [survey-id] (handler/questions (keyword survey-id)))
  (POST "/answer" request (handler/register-answer (:body request)))
  (route/not-found "Not Found"))


;; ----------------------------------------------------------------
;; Applicat routes with defaults
;; called from application.clj
;; (jetty/run-jetty routes/app {:port p :join? false})
;; ----------------------------------------------------------------
(def app
  (-> app-routes
      (wrap-defaults site-defaults)))
