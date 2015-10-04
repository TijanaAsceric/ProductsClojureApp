(ns clprojekat.controller
 (:use [clprojekat.dbbroker :as db]
       [incanter.core]
       [incanter.stats]
       [incanter.charts :as ch]
       [incanter.io])
       
  (:require 
             [noir.response :refer [redirect]]
             [noir.session :as session]
             [clj-http.client :as client]
             [ring.util.response :refer [response]]
             [clojure.string :as str]))

(defn get-api-key []
  (str "8163e1c2d806f1299896dcb5f114c171")
  )

(defn get-currencies []
(client/get "http://apilayer.net/api/historical?access_key=8163e1c2d806f1299896dcb5f114c171&date=2010-01-01&currencies=EUR&format=1"
  {:form-params "body"
 :content-type :json
  :throw-exceptions false
  :as :json}))


(defn get-currency-rate [year]
((((client/get (str "http://apilayer.net/api/historical?access_key=" (get-api-key) "&date=" year "-01-01&currencies=EUR&format=1")
  {:form-params "body"
 :content-type :json
  :throw-exceptions false
  :as :json} :headers) :body) :quotes) :USDEUR))

(defn get-eur-price [year usdprice]
 (let [res (get-currency-rate year)]
  (* usdprice res)
  ))


(defn login [username password]
  (let [user-doesnt-exist (db/auth username password)]
    (if user-doesnt-exist
      (do
        (session/put! :session-message "Incorrect username or password!")        
        (redirect "/"))
      (do
        (session/put! :name username)       
        (redirect "index")))))
(defn logout []
  (do
    (session/remove! (session/get! :name))
    (redirect "/")))

(defn insertproduct [product_name description year price]
  (do (db/insert product_name description (read-string year) (read-string price) (get-currency-rate year) (get-eur-price year (read-string price)))
    (redirect "/products#productslist")
    ) 
  )
(defn insert-initial-info[] 
  (do (db/insert-admin) (insertproduct "Dell laptop" "laptop" "2009" "2100")
    (insertproduct "Dell laptop" "laptop" "2010" "2000")
    (insertproduct "Dell laptop" "laptop" "2012" "1870"))
  )
(defn create-root-elements [results]
  {:products (into [] results) })

(defn create-root-element [results]
  {:product (into [] results) })


(defn get-products []
  (create-root-elements(db/read-all-products)))

(defn return-products-by-id [prname]
  (create-root-element(db/search-product prname))
   )
;(defn my-plot [x y]
;  (function-plot sin x y))
;
;
;(defn sav [nameOfpic] 
;  (save (my-plot -10 10) nameOfpic))
;
;(defn viewPlot []
;  (view (my-plot -10 10)))

(def productDataSet
  (let [columns [:_id :name :description :year :price :exrate :priceeur]
    headers  (map name columns)
    result (db/read-all-products)
    rows  (mapv #(mapv % columns) result)]
     print rows))

(def productPrice (sel productDataSet :cols 6))
(def productExRate (sel productDataSet :cols 5))

(defn product-scatter-plot [X Y]
  (ch/scatter-plot X Y 
 :title "Relationship of Product's price and Exchange rate"
 :x-label "Product's price"
 :y-label "Exchange rate"
 :legend false))			

(defn plotPriceExrate [] 
  (view (ch/add-lines (product-scatter-plot productPrice productExRate)  
     productPrice (:fitted (linear-model productExRate productPrice))))
  (redirect "/products#productslist"))

(defn delete-product [product_id]
  (do
    (db/delete-pr product_id))
  (redirect "/products#productslist"))
