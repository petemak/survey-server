(ns survey-server.db-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [survey-server.db :refer :all]))


(deftest test-db-read
  (testing "question retrieval"
    (let [qs (questions :ps-2019)]
      (is (not (nil? qs)))
      (is (some? (:questions qs)))
      (is (> (count (:questions qs)) 0)))))


(deftest test-db-write
  (testing "answer registry"
    (let [sid :ps-2019
          uid "user-1"
          qid "Question-2"
          aid :a7
          pcnt (answer-count sid qid aid)]
      (let [added? (add-answer sid uid qid aid)
            ncnt (answer-count sid qid aid)]
        (is (= true added?))
        (is (= (inc pcnt) ncnt))))))

