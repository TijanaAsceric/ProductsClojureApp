# ProductsClojureApp

ProductsClojureApp is Clojure application, that helps you managing products. User must be logged in first. User can add new products, delete it and see how exchange rate affects on product's price.(It uses Incanter library).
The application calls REST API https://currencylayer.com/documentation to change USD to EUR and gets exchange rate. 
The application automaticaly inserts dummy data in database about products and one user.

## Usage

Login:
Username: admin<br/>
Password: admin
<img width="708" alt="login" src="https://cloud.githubusercontent.com/assets/8823815/10270561/9ac8fab4-6af5-11e5-9b90-e068d99a564b.png">

Add new product
<img width="895" alt="addnewproduct" src="https://cloud.githubusercontent.com/assets/8823815/10270593/77512e7a-6af6-11e5-8a65-b42aba6f6f69.png">

List of products

<img width="697" alt="products" src="https://cloud.githubusercontent.com/assets/8823815/10270601/e53c1058-6af6-11e5-9447-a7416e989bfe.png">

Incanter- How exchange rate affects on product's price

<img width="251" alt="incanter" src="https://cloud.githubusercontent.com/assets/8823815/10270764/795ab9b0-6afc-11e5-9f66-64610b84504d.png">

## Setup
Download and install Leiningen: http://leiningen.org/
Download and install MongoDB: https://www.mongodb.org/.It's necessary to start MongoDB before running the application.
Navigate to project folder and type lein ring server

## References
Practical Clojure
Web Development with Clojure
Clojure Data Analysis Cookbook


## License

Copyright © 2015 Tijana Asceric tijana.asceric@gmail.com

Distributed under the Eclipse Public License.
