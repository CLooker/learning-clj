(ns com.clooker.learning-routing
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [compojure.route :as route]
            [compojure.core :refer [context defroutes GET POST]]))

(defroutes app
  (context "/admin" [] ;; can be used to create a router
    (GET "/login" [] "Logging in")
    (GET "/login/:id" [id] (str "Loggin in id: " id)))
  (GET "/" [] "Hello from compojure")
  (POST "/:name" [name] (str "Hello, " name))
  (GET "/:name" [] (fn [req] (str "Hello, " (-> req :route-params :name)))) ; can use a fn to handle req
  (GET "/route1" [] "Hello from route1")
  (GET "/route2" [] "Hello from route2")
  (GET ["/hex-id/:id" :id #"[a-fA-F0-9]+"] [id]  (str "ID: " id)) ; can use regex to match routes  
  (GET "/favicon.ico" [] {})
  (route/resources "/static")
  (route/not-found "404 Not Found"))

;; testing routes is easy
(app {:uri "/" :request-method :get})
(app {:uri "/Chad" :request-method :post})
(app {:uri "/Bilyana" :request-method :get})
(app {:uri "/hex-id/beef" :request-method :get})
(app {:uri "/hex-id/ham" :request-method :get})
(app {:uri "/static/test.html" :request-method :get})
(app {:uri "/admin/login" :request-method :get})
(app {:uri "/admin/login/42" :request-method :get})

(defn -main []
  (jetty/run-jetty app {:port 3000}))