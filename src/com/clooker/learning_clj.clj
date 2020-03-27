(ns com.clooker.learning-clj
  (:gen-class))

;lein stuff I got for free
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn basics
  "Demonstrate basic stuff"
  []
  ;nil 
  (print "Hello World!")

  ;2
  (+ 1 1)

  ;1
  (- 2 1)

  ;8
  (* 2 4)

  ;2
  (/ 4 2))

(defn types
  "Demonstrate some of the clj types"
  []
   ;java.lang.Long
  (type 1)
  
  ;java.lang.Double
  (type 1.1) 
  
  ;java.lang.Boolean
  (type true) 
  
  ;java.lang.String  
  (type "Hello") 
  
  ;clojure.lang.Keyword  
  (type :a) 
  
  ;clojure.lang.PersistentList
  (type (list 1 2 3)) 
  
  ;clojure.lang.PersistentVector
  (type [1 2 3]) 
  
  ;clojure.lang.PersistentArrayMap, hash-map fn will return PersistentHashMap
  (type {:a "foo"}) 
  
  ;clojure.lang.PersistentHashSet
  (type #{1 2 3}))  
