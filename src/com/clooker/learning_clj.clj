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
  ;fn that takes no args and returns a string
  (fn [] "Hello World")

  ;fn that has a name
  (def hello-world (fn [] "Hello World"))

  ;call the fn by name
  (hello-world)

  ;shorthand to bind name to fn
  (defn hello-world [] "Hello World")

  (hello-world)

  ;shorthand for anon fn
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

  ;destructure hash-map in args
  ;:as gives ref to entire hash-map
  (defn introduce-self [{name :name :as config}]
    (str "Hello, my name is " name))

  (introduce-self {:name "Chad"})

  ;destructure vector/list in args
  (defn introduce-self-and-hobby [[name hobby]]
    (str "Hello, my name is " name ". My hobby is " hobby "."))

  (introduce-self-and-hobby ["Chad" "Clojure"]))

(defn collections
  "Demonstrate some clj collections"
  []

  (def xs (list 1 2 3))

  ;returns new list, adds item at head
  (cons 0 xs)

  ;xs unchanged 
  xs

  (first xs)

  (last xs)

  ;does not return nil when index out of bounds, throws error
  ;(nth xs 6)

  (def ys [1 2 3])

  ;returns a list, even though ys is a vector
  (cons 0 ys)

  ;conj adds item to collection in the "natural way"
  ;adds item to end of vector
  (conj ys 4)

  ;adds item to front of list
  (conj xs 0)

  ;concat returns list - lazySeq
  (concat [0 1 2] ys)

  ;PersistentArrayMap - preserves order
  (array-map :a 1)

  ;PersistentHashMap - does not preserve order
  (hash-map :a 1)

  (def my-map {:a 1})

  ;put
  (def nested-map (assoc my-map :b {:c 3}))

  (assoc-in nested-map [:b :d] 4)

  ;takes a fn that computes updated value
  (update-in nested-map [:b :c] inc)

  (get my-map :a)

  ;keywords are fns
  (:a my-map)

  ;maps are fns, however if my-map is nil then you will have an error
  (my-map :a)
  ;(nil :a)

  (def my-set #{1 2 3})

  ;remove an item
  (disj my-set 3)

  ;does not change my-set
  (contains? my-set 3)

  (get my-set 4))

(defn recursion
  "Demonstrating recursion in Clojure"
  []

  ;classic recursion
  (defn sum-nums-recur [nums accum]
    (if (empty? nums)
      accum
      (sum-nums-recur (rest nums) (+ accum (first nums)))))

  (sum-nums-recur [1 2 3 4] 0)

  ;multi-variadic fn body handles initial call
  (defn sum-nums-recur
    ([nums] (sum-nums-recur nums 0))
    ([nums accum]
     (if (empty? nums)
       accum
       (sum-nums-recur
        (rest nums)
        (+ accum (first nums))))))

  (sum-nums-recur [1 2 3 4])

  ;Clojure has no TCO, so stack overflow is possible
  ;(sum-nums-recur (range 10000000))

  ;use recur to get flavor of tail call recursion in Clojure
  (defn sum-nums-recur-kw
    ([nums] (sum-nums-recur nums 0))
    ([nums accum]
     (if (empty? nums)
       accum
       (recur
        (rest nums)
        (+ accum (first nums))))))

  ;no stack overflow :0
  (sum-nums-recur-kw (range 10000000))

  ;loop recur pattern
  ;fn doesn't need arg for accum, loop creates local bindings like let
  (defn sum-nums-loop-recur [nums]
    (loop [ns nums
           accum 0]
      (if (empty? ns)
        accum
        (recur (rest ns) (+ accum (first ns))))))

  (sum-nums-loop-recur (range 10000000))

  ;reduce pattern
  ;don't usually need loop recur if you just iterate over every item to build an accum
  (defn sum-nums-reduce [nums]
    (reduce + 0 nums))

  (sum-nums-reduce (range 10000000)))




