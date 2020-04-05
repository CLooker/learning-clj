(ns com.clooker.learning-require
  (:gen-class))

;;all usage of require/import below
;;can be done inside ns declaration
;;:require, :import used instead
;;no quoting needed
;;(ns foo.bar
;;  (:require [clojure.string :as str]))

(require 'clojure.string)
(clojure.string/split "a,b,c" #",")

;;alias namespace
(require '[clojure.string :as s])
(s/split "a,b,c" #",")

;;import specific fn's
(require '[clojure.string :refer [split]])
(split "a,b,c" #",")

;import all fns
;not suggested as it makes it hard to debu
;(use 'clojure.string) does same thing but isn't used anymore
(require '[clojure.string :refer :all])
(split "a,b,c" #",")

;;used for interop
(import 'java.util.Date)

;;multiple interop imports
(import '[java.util Date Calendar])
