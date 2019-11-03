(ns survey-server.db
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [clojure.java.io :as io]))


;;====================================================================
;; Survey questions
;;====================================================================
(def survey-questions
  {:ps-2019
   {:name "Programmer Survey 2019"
    :questions [{:id "Quesion 1"
                 :text "How would you describe your role?"
                 :answers {:a1 "Developer/Programmer"
                               :a2 "Architect"
                               :a3 "Business Analyst"
                               :a4 "Project Manager"}}
                
                {:id "Question 2"
                 :text "Which of these is your primary programming language?"
                     :answers {:a1 "JavaScript"
                               :a2 "Java"
                               :a3 "Scala"
                               :a4 "Python"
                               :a5 "Rust"
                               :a6 "Go"
                               :a7 "Clojure/ClojureScript"}}]}})

;; ----------------------------------------------------------------
;; submitted answers
;; ----------------------------------------------------------------

(def answers
  {:ps-2019 {"Question-1" {:a1 0
                           :a2 0
                           :a3 3
                           :a4 0
                           :a5 0}}})


;; ----------------------------------------------------------------
;; Dabase schema
;; Load schemam and data from files
;; ----------------------------------------------------------------
(def db-schema (read-string (slurp (io/resource "db/schema.edn"))))
(def db-data (read-string  (slurp (io/resource "db/data.edn"))))

;; ----------------------------------------------------------------
;; Datomic 
;; ----------------------------------------------------------------
(def db-url "datomic:mem://survey")


;; ----------------------------------------------------------------
;; Stop server
;; ----------------------------------------------------------------
(defn open-connection
  "Creates an in memory databse and connects the
   the peer library to the database. Returns a
   connection if successful"
  []
  (if (d/create-database db-url)
    (d/connect db-url)))


;; ----------------------------------------------------------------
;; Close DB connection
;; ----------------------------------------------------------------
(defn close-connection
  [cn]
  (.release cn))


;; ----------------------------------------------------------------
;; Define db component lifecycle
;; ----------------------------------------------------------------
(defstate conn :start (open-connection)
               :stop  (close-connection conn))



;; ----------------------------------------------------------------
;; Dabase schema
;; ----------------------------------------------------------------
(defn initiated-db
  "Write schema"
  []
  (d/transact conn db-schema))



(defn transact-data
  "writes the specified data to the DB.
  The argument data must be a list of datoms"
  [data]
  (d/transact conn data))



;; ----------------------------------------------------------------
;; List of questions in a survey
;; ----------------------------------------------------------------
(defn questions
  "Returns questions for the survey specified by the given sid"
  [sid qid]
  (get survey-questions [sid qid]))


;; ----------------------------------------------------------------
;; 
;; ----------------------------------------------------------------
(defn answer-count
  [sid qid aid]
  (if-let [count (get-in answers [sid qid aid])]
    count
    0))


(defn add-answer
  [sid uid qid aid]
  true)


