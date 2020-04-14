(ns com.clooker.learning-hiccup
  (:gen-class)
  (:require [hiccup.core :as h]
            [hiccup.form :as hf]))

;; h1
(h/html [:h1 "Hello, World"])

;; with a class
(h/html [:h1 {:class "foo"} "Hello, World"])

;; with an id
(h/html [:h1 {:id "bar"} "Hello, World"])

;; id/class shorthand
(h/html [:h1#bar.foo "Hello, World"])

;; nestable
(h/html
 [:section
  [:p "Paragraph 1"]
  [:p "Paragraph 2"]])

;; list comprehension
(h/html
 [:ul
  (for [item ["foo" "bar" "baz"]]
    [:li item])])

;; field helpers
(h/html
 [:fieldset
  [:label "Name"]
  (hf/text-field "name" "Value")])

(h/html
 [:fieldset
  (hf/drop-down "favorite-color"
                ["red" ["Blue" "blue"] ["Verde" "green"]])])

;; composable
(defn layout [content & options]
  (let [opts (apply hash-map options)]
    (h/html
     [:head
      [:title (get opts :title "Hello, World")]]
     [:body [:main content]])))

(layout [:p "Hello"] :title "Hi")