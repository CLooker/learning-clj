(ns com.clooker.learning-types-records
  (:gen-class))

;;deftypes creates Java class
(deftype Person [name])
(def Chad (Person. "Chad"))
(.name Chad)
;;not printable
Chad

;;factory syntax
(def Bilyana (->Person "Bilyana"))
(.name Bilyana)

;;defrecords create hashmap
(defrecord Pet [name])
(def Boone (Pet. "Boone"))
(.name Boone)
(:name Boone)
;;printable
Boone

;;defrecord can accept a map
(def Charlie (map->Pet {:name "Charlie"}))
(:name Charlie)

;;implements Java interfaces
(deftype MyRunnable [name] Runnable
         (run [this] (println (.name this))))
(.run (MyRunnable. "Katie"))