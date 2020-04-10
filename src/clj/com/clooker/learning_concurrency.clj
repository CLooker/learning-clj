(ns com.clooker.learning-concurrency
  (:gen-class))

;;clj fns implement Java Runnable interface
(instance? Runnable #())

(defn prn-from-thread []
  (.start (Thread. (println "Hello from prn-from-thread"))))

(prn-from-thread)

(deliver (promise) "Hello delivered to promise...")

(defn slow-fn []
  (let [p (promise)]
    (.start
     (Thread. (fn []
                (Thread/sleep 2000)
                (deliver p "Hello delivered in slow-fn"))))
    p))

;;not blocking
(slow-fn)

;;blocking until promise resolves
(deref (slow-fn))

;;shorthand for deref is @
@(slow-fn)

;;future abstracts works of handling promises manually
;;don't have to write fn that returns a promise, just wrap it in a future
;;wrapping in a future is a way to get work done while not blocking
(defn slow-fn-2 []
  (Thread/sleep 2000)
  (println "Hello from slow-fn-2"))

(future (slow-fn-2))

;;delay is like future, but it won't start executing until it is dereffed once
;;slow-fn-2 will never actually be called
(defn delayed-fn []
  (delay (slow-fn-2))
  :ok)

(delayed-fn)

;;clj has memoize
;;slow fib implementation becomes fast with memoize
(defn fib [n]
  (if (< n 2)
    1
    (+ (fib (- n 1)) (fib (- n 2)))))

(def fib (memoize fib))

(fib 40)