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
        (is (= 404 (:status response)))))))


;; ----------------------------------------------------------------
;; Test business logic route for retrieving questions.
;; ----------------------------------------------------------------
(deftest questions-route-test
  (testing "questions route"
    (let [req (mock/request :get "/questions/ps-2019")
          res (routes/app req)]
      (is (= 200 (:status res)))
      (is (> (count (get-in res [:body :questions])) 0)))))





;; ----------------------------------------------------------------
;; Test business logic routes for saving questions
;; ----------------------------------------------------------------
(deftest answers-route-test
  (testing "answers route"
    (let [ans {:question-id "ps-2019-q1"
               :user-id "user1"
               :answer-id "Clojure"}
          req (mock/request :post "/answer/ps-2019" ans)
          res (routes/app req)]
      (is 200 (= (:status res)))
      (is (some? (:body res)))
      (is (= (:answer-id ans) (:answer-id (:body res)))))))

