(ns survey-server.db)


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


(def answers
  {:ps-2019 {"Question 1" {"user-1" "a1"}}})


(defn questions
  "Returns questions for the survey specified by the given sid"
  [sid]
  (get survey-questions sid))



(defn add-answer
  [sid uid qid aid]
  true)
