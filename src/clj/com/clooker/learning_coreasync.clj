(ns com.clooker.learning-coreasync
  (:gen-class)
  (:require [clojure.core.async :refer [chan go >! <! go-loop] :as async]))

;;go blocks share a thread pool

;;channel
(def my-chan (chan))

;;take a message off a channel once
(go (println (<! my-chan)))

;;put a message on a channel once
(go (>! my-chan "message I put on a channel once"))

;;take all messages from channel
(go (loop []
      (println (<! my-chan))
      (recur)))

;;shorthand
(go-loop []
  (println (<! my-chan))
  (recur))

;;blocking put a message
;;blocks until something reads the message
(async/>!! my-chan "Blocking as I put the message on")

;;fn that accepts a channel
(defn print-listener [some-chan]
  (go-loop []
    (println (<! some-chan))
    (recur))
  some-chan)

(def another-chan (chan))
(print-listener another-chan)
(go (>! another-chan "Hello, another-chan"))

(defn reverser [input-chan]
  (let [output-chan (chan)]
    (go-loop []
      (>! output-chan (reverse (<! input-chan)))
      (recur))
    output-chan))

(def in-ch (chan))
(def rev-ch (reverser in-ch))
(print-listener rev-ch)
(go (>! in-ch [1 2 3]))
