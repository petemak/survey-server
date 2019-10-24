(ns survey-server.db-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [survey-server.db :refer :all]))

(deftest test-app
  (testing "question retrieval"
    (let [qs (questions :ps-2019)]
      (is (not (nil? qs)))
      (is (some? (:questions qs)))
      (is (> (count (:questions qs)) 0)))))
