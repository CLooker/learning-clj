(ns com.clooker.learning-testing (:gen-class))

(defn foo
  "Demonstrating you can write tests next to the code it tests"
  [x]
  "foo")

(comment
  (require '[clojure.test :refer [is testing]])
  (testing "Make sure foo works"
    (is (= "foo" (foo 1)))
    (is (= "foo" (foo "bar")))))