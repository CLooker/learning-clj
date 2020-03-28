(ns com.clooker.learning-clj
  (:gen-class))

;lein stuff I got for free
(defn -main
  ;a doc string
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

(defn control-flow
  "Demonstrate some clj control stuff"
  []
  (if (empty? "")
    "is empty"
    "is not empty")

   ;do -  multiple side-effects
  (if (empty? "")
    (do
      (println "first step")
      (println "second step")))

   ;when - has implicit do
  (when (empty? "")
    (println "first step")
    (println "second step"))

   ;case - is like switch
  (let [x "Hello"]
    (case x
      "Goodbye" :goodbye
      "Hello" :hello
      :else :default-value))

   ;cond - for custom comparison
  (let [x 400]
    (cond
      (> x 100) :more-than-100
      (< x 100) :less-than-100
      (= x 100) :is-100
      :else :weird)))

(defn fns
  "Demonstrate some clj functions"
  []
  ; fn that takes no args and returns a string
  (fn [] "Hello World")

   ; fn that has a name
  (def hello-world (fn [] "Hello World"))

   ; call the fn by name
  (hello-world)

   ; shorthand to bind name to fn
  (defn hello-world [] "Hello World")

  (hello-world)

   ; shorthand for anon fn
  (#(str "Hello World"))

   ;fn that takes an arg
  (defn hello [name] (str "Hello, " name))

  (hello "Chad")

  ;& is like js rest operator
  (defn sum-nums [& nums]
    (reduce + nums))

  (sum-nums 1 2 3)

  ;multiple fn body
  (defn greet
    ([] "Hello")
    ([name] (str "Hello " name)))

  (greet)

  (greet "Chad")

  ;default arg using recursion
  (defn greet-with-default
    ([] (greet-with-default "World"))
    ([name] (str "Hello " name)))

  (greet-with-default)

  ;destructure map in args
  ;:as is like js spread
  (defn introduce-self [{name :name :as config}]
    (str "Hello, my name is " name))

  (introduce-self {:name "Chad"})

  ;destructure vector/list in args
  (defn introduce-self-and-hobby [[name hobby]]
    (str "Hello, my name is " name ". My hobby is " hobby "."))

  (introduce-self-and-hobby ["Chad" "Clojure"]))
