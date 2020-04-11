(ns com.clooker.learning-protocols
  (:gen-class))

;;protocols are like Java interfaces
;;but they can be attached to classes/records/types at runtime

(defprotocol Shape
  (area [this])
  (perimeter [this]))

(defrecord Circle [radius]
  Shape
  (area [this] (* (. Math PI) (:radius this) (:radius this)))
  (perimeter [this] (* 2 (:radius this) (. Math PI))))

(area (->Circle 5))
(perimeter (->Circle 5))