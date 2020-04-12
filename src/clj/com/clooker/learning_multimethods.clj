(ns com.clooker.learning-multimethods
  (:gen-class))

;expression problem involves
;how to create a programming language that allows the addition of new data types as well as new operations over those data types
;
;OOP traditionally makes it easy to add new data types by adding another class;
;OOP traditionally makes it hard to add new operations over data types because you have to add the operation's implementation
;in every data type that implements that base data type
;
;FP traditionally makes it easy to add new operations over a data type by creating another function
;FP traditionally makes it hard to add new data types because you have to update your existing functions to accept the new data type
;
;multimethods address the expression problem in clj as a way to dispatch function calls

(defmulti hello :language)
(defmethod hello ::french [_] "Bonjour")
(hello {:language ::french})

;;throws error as we haven't defined ::english
;; (hello {:language ::english})

;;use :default to create default value
(defmethod hello :default [_] "Hello")

;;returns default value
(hello {:language ::pig-latin})

;can define default in any order
;::spanish will be found first even though we define it after default
(defmethod hello ::spanish [_] "Hola")
(hello {:language ::spanish})

;can combine multimethod with derive fn to establish relationships between keys
;false
(isa? ::cockney ::english)

(derive ::cockney ::english)

;;true
(isa? ::cockney ::english)

(defmethod hello ::english [_] "Hi")
(hello {:language ::cockney})