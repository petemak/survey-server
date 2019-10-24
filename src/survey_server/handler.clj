(ns survey-server.handler
  (:require [compojure.core :refer :all]
            [ring.util.request :as req]
            [ring.util.response :as resp]
            [survey-server.db :as db]))


;; ----------------------------------------------------------------
;; Application routes
;; ----------------------------------------------------------------
(defn home
  "Handles home request"
  []
  (resp/response "Survey Services"))

;; ----------------------------------------------------------------
;; Application routes
;; ----------------------------------------------------------------
(defn questions
  "Returns questions from the survey with the specified sid"
  [sid]
  (if-let [qs (db/questions sid)]
    (resp/response qs)
    (resp/not-found (str "Survey " sid " not found!"))))


;; ----------------------------------------------------------------
;; Application routes
;; ----------------------------------------------------------------
(defn register-answer
  [d]
  (let [uid (:user-id d)
        sid (:survey-id d)
        qid (:question d)
        aid (:answer d)]
    (db/add-answer sid uid qid aid )))
