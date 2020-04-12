(ns com.clooker.learning-file-io
  (:gen-class))

(def test-file-path "/tmp/test.txt")

;; write to a file
(spit test-file-path "spitting this text into the file\n")

;; read from a file
(slurp test-file-path)

;; hook into java io
(require '[clojure.java.io :as io])
(def reader (io/reader test-file-path))
(def lines (line-seq reader))

;; cannot read lines after reader is closed
(.close reader)
;; (print lines)

;; with-open makes working with closable things safer
;; will automatically close the stream
(with-open [reader (io/reader test-file-path)
            writer (io/writer (str test-file-path ".test2.txt"))]
  (doall (map #(.write writer (apply str (reverse %))) (line-seq reader))))

(slurp (str test-file-path ".test2.txt"))

;; can pass in streams, but also can pass in File objects
;; can do that to make it clear you are reading from a file and not a URL
(io/file test-file-path)

;; can read from a URL
(slurp "http://google.com/")