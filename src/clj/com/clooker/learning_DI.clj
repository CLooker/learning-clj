(ns com.clooker.learning-DI (:gen-class))

;; can use dependency injection to easily swap out, for example, storage
;; during dev you can use a ref/atom
;; in prod you can use a real db
(defprotocol UserStore
  (get-user-by-email [store email])
  (put-user! [store user]))

;; dev store
(defrecord AtomStore [data])

;; prod store
(defrecord DBStore [db-spec])

(extend-protocol UserStore
  AtomStore
  (get-user-by-email [store email]
    (let [users (:users @(:data store))]
      (first (filter #(= (:email %) email) users))))
  (put-user! [store user]
    (swap! (:data store) update-in [:users] conj user))

;; implement this interfact however you like
  DBStore
  (get-user-by-email [store email]
    ; ...
    )
  (put-user! [store user]
    ; ...
    ))

(def store (->AtomStore (atom {})))

(put-user! store {:email "test@example.com"})
(put-user! store {:email "test2@example.com"})
(get-user-by-email store "test@example.com")

;; you can use ring middleware to inject a store so all of your handlers have access to it
(defn wrap-store-middleware [handler store]
  (fn [req]
    (handler (assoc req :store store))))

(defn app [req]
  {:body "hello"
   :status 200
   :headers {"Content-Type" "text/html"}})

;; instead of ->AtomStore you could use -DBStore
(def handler (-> app
                 (wrap-store-middleware (->AtomStore (atom {})))))