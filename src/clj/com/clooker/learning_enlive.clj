(ns com.clooker.learning-enlive
  (:gen-class)
  (:require [net.cgrand.enlive-html :as enlive]
            [ring.adapter.jetty :as jetty]))

;; enlive transformation
(enlive/deftemplate hello-tmpl "enlive-hello.html" [name] [:h1] (enlive/html-content (str "Hello, " name)))
(hello-tmpl "Chad")

(enlive/deftemplate main-tmpl "enlive-hello.html" [name] [:h1] (enlive/wrap :main))
(main-tmpl "Chad")

;; multiple transformations
(enlive/deftemplate multi-steps-tmpl "enlive-hello.html"
  [name]
  [:h1] (enlive/do->
         (enlive/wrap :main)
         (enlive/html-content "Hello")))
(multi-steps-tmpl "Chad")

(enlive/defsnippet table-row-tmpl "enlive-table.html" [:table :tbody [:tr enlive/first-of-type]]
  [row-data]
  [[:td (enlive/nth-of-type 1)]] (enlive/html-content (:id row-data))
  [[:td (enlive/nth-of-type 2)]] (enlive/html-content (:name row-data))
  [[:td (enlive/nth-of-type 3)]] (enlive/html-content (:date row-data)))

(enlive/deftemplate table-tmpl "enlive-table.html"
  [rows]
  [:h1] (enlive/html-content "Customers")
  [:table :tbody] (enlive/content (map table-row-tmpl rows)))

(table-tmpl [{:id 1, :name "Jimmy", :date "Today"}])

(defn app [req]
  {:body (table-tmpl [{:id 1 :name "Jimmy" :date "Today"}
                      {:id 2 :name "Janie" :date "Yesterday"}
                      {:id 3 :name "Dolores" :date "Never"}])
   :status 200
   :headers {"Content-Type" "text/html"}})

(defn -main []
  (jetty/run-jetty app {:port 3000}))