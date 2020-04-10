(ns com.clooker.learning_interop
  (:import
   (java.util Date
              Calendar)
   MyClass))

(System/currentTimeMillis)

;;constructor
(Date. (System/currentTimeMillis))

;;method call
(. (Date.) getTime)
(.getTime (Date.))

;;call a bunch of methods
(doto (Calendar/getInstance)
  (.set Calendar/YEAR 1984)
  (.set Calendar/MONTH 3)
  (.set Calendar/DATE 28))

;;java int array
(int-array 100)

;;java String array with our values
(into-array String ["this" "is" "an" "array"])

(def my-string-array (into-array String ["my" "string" "array"]))

(aget my-string-array 1)

;;mutates!
(aset my-string-array 1 "String")

;;map that reverses each string
(amap my-string-array idx acc
      (->>
       (aget my-string-array idx)
       (reverse)
       (apply str)
       (aset acc idx)))

;;reduce that adds up lenths of strings
(areduce my-string-array idx acc (long 0)
         (->>
          (aget my-string-array idx)
          (count)
          (+ acc)))

;;type hint
(defn strlen [^String s] (.length s))
(defn slow-strlen [s] (.length s))

;;perf test
(time (reduce + (map strlen (repeat 100000 "asdf"))))
(time (reduce + (map slow-strlen (repeat 100000 "asdf"))))

;;implement an abstract class/method
(def my-thread (proxy [Thread] []
                 (run [] (println "Running in another thread"))))

(.run my-thread)

;;thread pool
(import 'java.util.concurrent.Executors)
(def my-pool (Executors/newFixedThreadPool 4))
(.submit my-pool my-thread)

(def my-runnable (proxy [Runnable] []
                   (run [] (println "Running a runnable"))))
(.submit my-pool my-runnable)

(def my-reified (reify
                  Comparable
                  (compareTo [this other] -1)
                  Runnable
                  (run [this] (println "Running via reify"))))

(.submit my-pool my-reified)

;generating java classes
(gen-class
 :name com.clooker.learning_interop.MyClass
 :prefix "my-"
 :methods [[getName [] String]]
 :constructors {[String] []}
 :state state
 :init init)

(defn my-init [name]
  [[] {:name name}])

(defn my-getName [this]
  (get (.state this) :name))

;;lein repl
;;(import com.clooker.learning_interop.MyClass)
;;(def my-class (MyClass. "Chad"))
;;(.getName my-instance)

(def myClass (MyClass. "Chad"))
(.getName myClass)