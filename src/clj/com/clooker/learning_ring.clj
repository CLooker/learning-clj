;; started the server with lein run -m com.clooker.learning-ring

(ns com.clooker.learning-ring
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]))

(defn app [req]
  (str "Hello, " (get (:params req) "name")))

(defn handle-str-resp [handler]
  (fn [req]
    (let [resp (handler req)]
      (if (instance? String resp)
        {:body resp
         :status 200
         :headers {"Content-Type" "text/html"}}
        resp))))

;; helpful to think of requests as if they come in from the bottom to the top of handler
;; and responses go from the top to the bottom of handler
(def handler
  (-> app
      handle-str-resp
      wrap-params))

(defn -main []
  (jetty/run-jetty handler {:port 3000}))