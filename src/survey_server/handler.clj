(ns survey-server.handler
  (:require [compojure.core :refer :all]
            [ring.util.request :as requtil]
            [ring.util.response :as resputil]
            [survey-server.db :as db]))


;; ----------------------------------------------------------------
;; Application routes
;; ----------------------------------------------------------------
(defn home
  "Handles home request"
  []
  (resputil/response "Survey Services"))

;; ----------------------------------------------------------------
;; Application routes
;; ----------------------------------------------------------------
(defn questions
  "Returns questions from the survey with the specified sid"
  [sid]
  (if-let [qs (db/questions sid)]
    (resputil/response qs)
    (resputil/not-found (str "Survey " sid " not found!"))))


;; ----------------------------------------------------------------
;; Application routes
;; ----------------------------------------------------------------
(defn register-answer
  [sid d]
  (let [uid (:user-id d)
        qid (:question-id d)
        aid (:answer-id d)]
    
    (if-let [rec (db/add-answer sid uid qid aid)]
      (resputil/response rec)
      (-> (resputil/response "n/a")
          (resputil/status 400)))))
