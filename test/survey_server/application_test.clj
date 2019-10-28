(ns survey-server.application-test
  (:require [survey-server.application :as appl]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]))



(deftest test-app
  (testing "main route"
    (let [response (appl/ (mock/request :get "/"))]
      (testing "that status code is 200"
        (is (= (:status response) 200)))
      (testing "that response message is as expected"       
        (is (= (:body response) "Survey Service")))))

  (testing "qquestions route"
    (let [req (mock/request :get "/questions/ps-2019")
          rep (appl/app req)]
      (testing "that status is 200"
        (is (= (:status rep) 200)))))

)
