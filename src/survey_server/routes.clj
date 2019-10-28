(ns survey-server.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [survey-server.handler :as handler]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))


;; ----------------------------------------------------------------
;; Application routes
;; ----------------------------------------------------------------
(defroutes app-routes
  (GET "/" [] (handler/home))
  (GET "/questions/:survey-id" [survey-id] (handler/questions (keyword survey-id)))
  (POST "/answer/:survey-id" [survey-id :as {req-body :body}] (handler/register-answer survey-id
                                                                                       req-body))
  (route/not-found "Not Found"))


;; ----------------------------------------------------------------
;; Applicat routes with defaults
;; called from application.clj
;; (jetty/run-jetty routes/app {:port p :join? false})
;; ----------------------------------------------------------------
(def app
  (-> app-routes
      (wrap-defaults api-defaults)))
