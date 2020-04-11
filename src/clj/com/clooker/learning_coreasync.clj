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

;;consume from many channels at once
(defn combine-chans [& chans]
  (let [out-chan (chan)]
    (go-loop []
      (let [[value c] (async/alts! chans)]
        (>! out-chan value))
      (recur))
    out-chan))

(def chan1 (chan))
(def chan2 (chan))
(def both-chans (combine-chans chan1 chan2))
(print-listener both-chans)
(go (>! chan2 "Hi chans"))

;;channels have a map fn
(def in-chan-1 (chan))
(def rev-chan-1 (async/map reverse [in-chan-1]))
(print-listener rev-chan-1)
(go (>! in-chan-1 [3 2 1]))

;;channels have a reduce fn
;;but channel must be closed before reduce will occur
(def in-chan-2 (chan))
(def sum-chan (async/reduce + 0 in-chan-2))
(go (println (<! sum-chan)))
(go (>! in-chan-2 1))
(go (>! in-chan-2 2))
(go (>! in-chan-2 3))
(async/close! in-chan-2)