(ns com.clooker.learning-html-rendering
  (:gen-class)
  (:require [selmer.parser :as tmpl]))

;; render html from an html string and map
(tmpl/render "<h1>Hello, {{ name }}</h1>" {:name "Chad"})

;; render html from a file
(tmpl/render-file "templates/hello.html" {:name "Chad"})

;; set templates dir
(require '[clojure.java.io :as io])
(tmpl/set-resource-path! (io/resource "templates"))

;; can now stop mentioning templates dir
(tmpl/render-file "hello.html" {:name "Chad"})

;; hook it up with ring
(defn app [req]
  {:body (tmpl/render-file "hello.html" {:name (-> req :params (get "name"))})
   :status 200
   :header {"Content-Type" "text/html"}})

;; test it
(app {:params {"name" "Chad"}})

;; reusable
(defn handle-html-res [html]
  {:body html
   :status 200
   :header {"Content-Type" "text/html"}})

(def handle-templ-res
  (comp handle-html-res tmpl/render-file))

(defn app [{{name "name"} :params}]
  (handle-templ-res "hello.html" {:name name}))

(app {:params {"name" "Bilyana"}})