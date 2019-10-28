(ns survey-server.middleware
  (:require [cheshire.core :as json]
            [compojure.core :refer :all]
            [ring.util.response :as ring-util]))




;; ----------------------------------------------------------------
;; Helper functions, Replace java stream in body with text
;; ----------------------------------------------------------------
(defn decode-json-body
  "Given a request obect, decode the JSON body ans replace it with Clojure EDN"
  [request]
  (try
    (update request :body json/decode)
    (catch Exception e
      nil)))

;; ----------------------------------------------------------------
;; Replace java stream in body with text
;; ----------------------------------------------------------------
(defn wrap-slurp-body
  "Returns a handler that reads/slurps the body if it 
  contains instance of the mutable input stream and updates the :body
  keyword with slurped string"
  [handler]
  (fn [request]
    (if (instance? java.io.InputStream (:body request))
      (let [slurped-request (update request :body slurp)]
        (handler slurped-request))
      (handler request))))


;; ----------------------------------------------------------------
;; Decode JSON in a request body
;; ----------------------------------------------------------------
(defn wrap-json-request
  "Returns a handler that decodes the JSON content in the body of a request
   before handing over to the next handler"
  [handler]
  (fn [request]
    (if-let [decoded-body (decode-json-body request)]
      (handler decoded-body)
      (-> (ring-util/response "Expected JSON but received an invalid body")
          (ring-util/status 400)))))

;; ----------------------------------------------------------------
;; Replace java stream in body with text
;; ----------------------------------------------------------------
(defn wrap-json-response
  [handler]
  "Returns a handler that decodes the JSON content in the body of a request
   before handing over to the next handler"
  (fn [request]
    (-> (handler request)
        (update :body json/encode)
        (ring-util/content-type "application/json"))))
            
