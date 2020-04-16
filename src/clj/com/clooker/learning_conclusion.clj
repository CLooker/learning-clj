(ns com.clooker.learning-conclusion
  (:gen-class)
  (:require [net.cgrand.enlive-html :as enlive]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [resources]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]
            [markdown.core :refer [md-to-html-string]]
            [clojure.string :refer [escape]]))

(defrecord Tweed [title content])

(defprotocol TweedStore
  (get-tweeds [store])
  (put-tweed! [store tweed]))

(defrecord AtomStore [data])

(extend-protocol TweedStore
  AtomStore
  (get-tweeds [store]
    (get @(:data store) :tweeds))
  (put-tweed! [store tweed]
    (swap! (:data store)
           update-in [:tweeds] conj tweed)))

;; test it works
(def store (->AtomStore (atom {:tweeds '()})))
(get-tweeds store)
(put-tweed! store (->Tweed "Test Title" "test content"))
(get-tweeds store)
(put-tweed! store (->Tweed "Test Title 2" "test content 2"))
(get-tweeds store)

(enlive/defsnippet tweed-templ "tweedler/index.html" [[:article.tweed enlive/first-of-type]]
  [tweed]
  [:.title] (enlive/html-content (:title tweed))
  [:.content] (enlive/html-content (md-to-html-string (:content tweed))))

(enlive/deftemplate index-templ "tweedler/index.html"
  [tweeds]
  [:section.tweeds] (enlive/content (map tweed-templ tweeds))
  [:form] (enlive/set-attr :method "post" :action "/"))

(defn escape-html [s]
  (escape s {\> "&gt;" \< "&lt;"}))

(defn handle-create [{{title "title" content "content"} :params}]
  (put-tweed! store (->Tweed (escape-html title) (escape-html content)))
  {:body "" :status 302 :headers {"Location" "/"}})

(defroutes app-routes
  (GET "/" [] (index-templ (get-tweeds store)))
  (POST "/" req (handle-create req))
  (resources "/css" {:root "tweedler/css"})
  (resources "/img" {:root "tweedler/img"}))

(def app (-> app-routes
             (wrap-params)))

;; :join? false starts server from the repl without blocking
;; var means as we update the app fn, we won't need to re-evaluate server for server to use the latest app def
;; the shorthand for (var app) is #'app
(def server (jetty/run-jetty (var app) {:port 3001 :join? false}))
