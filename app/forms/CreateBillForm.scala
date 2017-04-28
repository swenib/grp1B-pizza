package forms

/**
  * Created by Hasibullah Faroq on 25.11.2016.
  *
  * Form beinhaltet Daten um eine Rechnung zu erstellen.
  *
  * @param pizzaName      Name der bestellten Pizza
  * @param pizzaNumber    Anzahl der bestellen Pizzen
  * @param pizzaSize      größe der Pizza
  * @param beverageName   Name der bestellten Getränkes
  * @param beverageNumber Anzahl der bestellen Getränke
  * @param beverageSize   größe des bestellten Getränkes
  * @param dessertName    Name der bestellten Desserts
  * @param dessertNumber  Anzahl der bestellen Desserts
  */
case class CreateBillForm(var pizzaName: String,
                          var pizzaSize: String,
                          var pizzaNumber: Int,
                          var beverageName: String,
                          var beverageSize: String,
                          var beverageNumber: Int,
                          var dessertName: String,
                          var dessertSize: String,
                          var dessertNumber: Int)
