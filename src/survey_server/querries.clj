(ns survey-server.querries)

(def survey-querry '[:find ?e ?n
                     :where [?e :survey/name ?n]])

(def question-querry '[:find ?e ?id ?txt
                       :where [?e :question/id ?id]
                       [?e :question/text ?txt]])
