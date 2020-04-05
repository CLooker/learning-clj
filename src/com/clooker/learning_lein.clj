(ns com.clooker.learning-lein
  (:gen-class))

(defn foo [x]
  (println x "Hello, World!"))

(defn -main []
  (foo "Main"))