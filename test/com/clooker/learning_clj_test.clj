(ns com.clooker.learning-clj-test
  (:require [clojure.test :refer :all]
            [com.clooker.learning-clj :refer :all]))

(deftest a-test
  (testing "I have been fixed"
    (is (= 1 1)))
  (testing "Ensure 2 - 1 = 1"
    (is (= 1 (- 2 1)))))

(is (= (+ 1 1) 2))

(is (nil? (first [])))

(is (instance? Integer 1))

(is (thrown? ArithmeticException (/ 1 0)))