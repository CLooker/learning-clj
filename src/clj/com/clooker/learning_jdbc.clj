(ns com.clooker.learning-jdbc
  (:gen-class)
  (:require [clojure.java.jdbc :as db]
            [jdbc.pool.c3p0 :as pool]))

(def my-db {:subprotocol "postgresql"
            :subname "//127.0.0.1:5432/testdb"
            :max-pool-size 2
            :user "clooker"
            :password "password"})

;; can pass pool around and it knows how to make good use of the connections
(def my-pool (pool/make-datasource-spec my-db))

(db/execute! my-pool
             ["DROP TABLE IF EXISTS employees"])

(db/execute! my-pool
             ["CREATE TABLE employees (id serial PRIMARY KEY, name text, email text)"])

;; insert
(db/execute! my-pool
             ["INSERT INTO employees (name, email) VALUES (?, ?)"
              "Joe" "joe@example.com"])

;; can pass map
(db/insert! my-db "employees" {:name "Jackie" :email "jackie@example.com"})

(db/insert! my-db "employees" ["name" "email"] ["Francine" "francine@aol.com"])

;; query
(def employees (db/query my-db ["SELECT * FROM employees"]))
employees

;; update
(db/update! my-db "employees" {:name "Joey"} ["name=? AND email=?" "Joe" "joe@example.com"])

;; delete
(db/delete! my-db "employees" ["name=?" "Francine"])

;; execute multiple statements in a transaction
;; second statement is invalid, so first one will be rolled back
;; (db/with-db-transaction [txn my-db]
;;   (db/delete! txn "employees" ["name='Joey'"])
;;   (db/delete! txn "employees" ["name=adlkfjadkljfjkadfjk"]))
