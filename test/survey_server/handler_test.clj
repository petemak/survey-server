(ns survey-server.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [survey-server.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (home)]
      (is (= (:status response) 200))
      (is (= (:body response) "Survey Services"))))
  (testing "questions retrieval"
    (let [response (questions :ps-2019)]
      (is (= (:status response) 200))
      (is (some? (get-in response [:body :questions])))
      (is (not (nil? (get-in response [:body :questions]))))
      (is (> (count (get-in response [:body :questions])) 0))
      (is (> (count (:answers (first (get-in response [:body :questions])))) 0)))))
 
