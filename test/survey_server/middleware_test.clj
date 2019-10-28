(ns survey-server.middleware-test
  (:require  [cheshire.core :as json]
             [clojure.test :refer :all]
             [ring.mock.request :as mock]
             [survey-server.middleware :refer :all]
             [ring.util.response :as ring-util]))

(deftest test-decode-json-body
  (testing "validity of decoded data structure"
    (let [example-json (json/encode {:name "Igglepiggle"
                                     :address "Night Garden"})
          json-req (mock/request :post "/answer" example-json)
          response (decode-json-body (update json-req :body slurp))]
      (is (not (nil? (:body response))))
      (is (= (get (:body response) "name") "Igglepiggle")))))
 
