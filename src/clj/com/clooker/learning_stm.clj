(ns com.clooker.learning-stm
  (:gen-class))

;;clj software transactional memory system
(def count (atom 0))

(dotimes [_ 1000]
  (future (reset! count (inc @count))))

;;won't be 1000 due to race conditions
@count

(def count (atom 0))

(dotimes [_ 1000]
  (future (swap! count inc)))

;;1000
@count

;;atom with validator
(def count-with-validator (atom 0 :validator integer?))

;;following demonstrates why atoms don't work for transactions
(def acct1 (atom 1000 :validator #(>= % 0)))
(def acct2 (atom 1000 :validator #(>= % %)))

;;not transactional as error thrown on first swap! won't cancel second swap!
(defn transfer [from-acct to-acct amt]
  (swap! to-acct + amt)
  (swap! from-acct - amt))

(dotimes [_ 1000]
  (future (transfer acct1 acct2 100)))

;;0
@acct1
;;101000
@acct2

;;ref is for syncronous coordinated updates
(def acct1 (ref 1000 :validator #(>= % 0)))
(def acct2 (ref 1000 :validator #(>= % %)))

(defn transfer [from-acct to-acct amt]
  (dosync
   (alter to-acct + amt) ;;commute instead of alter would parallelize the computations inside the transaction
   (alter from-acct - amt))) ;;but these operations are not commutative, the order matters

(dotimes [_ 1000]
  (future (transfer acct1 acct2 100)))

;;0
@acct1
;;2000
@acct2

;;agents are for async updates to a single value (no coordination)
;;like sending a value a message on how to update, without waiting for the update to occur
(def my-agent (agent 1 :validator #(> % 0)))
;;fails
(send my-agent dec)
;;can't send new values bc of previous fail
(send my-agent inc)
;;restart to be able to send values again
(restart-agent my-agent 1)
(send my-agent inc)
@my-agent

;;ingores bad state changes and continues
(def my-agent (agent 1 :validator #(> % 0) :error-mode :continue))
(send my-agent dec)
(send my-agent inc)
@my-agent