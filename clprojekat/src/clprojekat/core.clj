(ns clprojekat.core  
  (:use compojure.core
        clprojekat.views
        clprojekat.controller
        [hiccup.middleware :only (wrap-base-url)]
        [noir.util.middleware])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]            
            [compojure.response :as response]
            [noir.session :as session]
             [clojure.string :as str]
            [noir.response :refer [redirect]]))

(defroutes main-routes  
  (GET "/" [] (login-page))
  (GET "/index" [] (index-page))
  (POST "/login" [username password] (login username password))
  (GET "/logout" [] (logout))
  (POST "/plotPriceExrate" [] (plotPriceExrate))
  (POST "/insertproduct" [product_name description year price] (insertproduct product_name description year price))
  (GET "/products" [] (let [products (get-products)] (products-page products)))
  (POST "/delete-product" [product_id] (delete-product product_id))
  (route/resources "/"))

(def app
  (-> (handler/site main-routes)
    (session/wrap-noir-session)
    (wrap-base-url)))

;(defn app [req]
;  {:status  200
;   :headers {"content-Type" "text/html"}
;   :body    "Hello World! Tijana"})