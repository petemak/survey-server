(ns survey-server.routes-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [survey-server.routes :as routes]))

;; ----------------------------------------------------------------
;; Test default routes defined in survey.server.routes namespace
;; ----------------------------------------------------------------
(deftest default-routes-test
  (testing "home route"
    (let [req (mock/request :get "/")
          res (routes/app req)]
      (is (= (:status res) 200))
      (is (not (nil? (:body res))))))
  (testing "not-found route"
    (let [response (routes/app (mock/request :get "/invalid"))]
      (testing "that response status code is 404"
        (is (= (:status response) 404))))))


;; ----------------------------------------------------------------
;; Test business logic routes defined in survey.server.routes namespace
;; ----------------------------------------------------------------
(deftest main-routes-test
  (testing "questions route"
    (let [req (mock/request :get "/questions/ps-2019")
          res (routes/app req)]
      (is (= (:status res) 200))
      (is (> (count (get-in res [:body :questions])) 0)))))
