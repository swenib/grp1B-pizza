import controllers.BillController
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers.{GET, OK, POST, SEE_OTHER, BAD_REQUEST, contentAsString, defaultAwaitTimeout, redirectLocation, running, status}
import play.api.test.{FakeApplication, FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class BillControllerSpec extends Specification {

  def memDB[T](code: => T) =
    running(FakeApplication(additionalConfiguration = Map(
      "db.default.driver" -> "org.postgresql.Driver",
      "db.default.url" -> "jdbc:h2:mem:test;MODE=PostgreSQL"
    )))(code)

  "BillController" should {

    "add one product to the cart" in memDB {
      val request = FakeRequest(POST, "/addToBill").withFormUrlEncodedBody(
        "names[0]" -> "Regina",
        "sizes[0]" -> "medium",
        "numbers[0]" -> "2"
      )
      val result = BillController.addToBill()(request)
      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result) must beSome("/setOrder?orderedProducts=2x+Regina+%28medium%29&sumOfOrder=14.580000000000002")
    }

    "cause a bad request when an non-existing product is added to the cart" in memDB {
      val request = FakeRequest(POST, "/addToBill").withFormUrlEncodedBody(
        "names[0]" -> "Regina",
        "sizes[0]" -> "medium",
        "numbers[0]" -> "hallo"
      )
      val result = BillController.addToBill()(request)
      status(result) must equalTo(BAD_REQUEST)
    }

    "add two products to the cart" in memDB {
      val request = FakeRequest(POST, "/addToBill").withFormUrlEncodedBody(
        "names[0]" -> "Regina",
        "sizes[0]" -> "medium",
        "numbers[0]" -> "2",
        "names[1]" -> "Margarita",
        "sizes[1]" -> "large",
        "numbers[1]" -> "3"
      )
      val result = BillController.addToBill()(request)
      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result) must beSome("/setOrder?orderedProducts=2x+Regina+%28medium%29%2C+3x+Margarita+%28large%29&sumOfOrder=36.660000000000004")
    }

    "cause a bad request when a second non-existing product is added to the cart" in memDB {
      val request = FakeRequest(POST, "/addToBill").withFormUrlEncodedBody(
        "names[0]" -> "Regina",
        "sizes[0]" -> "medium",
        "numbers[0]" -> "2",
        "names[1]" -> "Margarita",
        "sizes[1]" -> "large",
        "numbers[1]" -> "hallo"
      )
      val result = BillController.addToBill()(request)
      status(result) must equalTo(BAD_REQUEST)
    }

    "show an 'at least one product necessary' hint when the cart is empty" in memDB {
      val request = FakeRequest(POST, "/addToBill").withFormUrlEncodedBody(
        "names[0]" -> "Regina",
        "sizes[0]" -> "medium",
        "numbers[0]" -> "0"
      )
      val result = BillController.addToBill()(request)
      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result) must beSome("/attemptFailed?errorcode=atLeastOneProduct")
    }

    "save an order to the session and show the bill" in memDB {
      val request = FakeRequest(GET, "/setOrder").withSession(
        "user" -> "-20",
        "forename" -> "John",
        "name" -> "Claude",
        "address" -> "Leopoldstraße 2",
        "zipcode" -> "82340",
        "city" -> "Starnberg"
      )
      val result = BillController.setOrder("1x Margarita (medium)", 6.21)(request)
      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result) must beSome("/showBill")
    }

    "save an order to the session and redirect to login when user is not logged in" in memDB {
      val request = FakeRequest(GET, "/setOrder").withSession(
        "forename" -> "John",
        "name" -> "Claude",
        "address" -> "Leopoldstraße 2",
        "zipcode" -> "82340",
        "city" -> "Starnberg"
      )
      val result = BillController.setOrder("1x Margarita (medium)", 6.21)(request)
      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result) must beSome("/attemptFailed?errorcode=loginrequired")
    }

    "show an 'at least one product necessary' hint when the cart is empty because two products have been added with amount < 1" in memDB {
      val request = FakeRequest(POST, "/addToBill").withFormUrlEncodedBody(
        "names[0]" -> "Regina",
        "sizes[0]" -> "medium",
        "numbers[0]" -> "0",
        "names[1]" -> "Margarita",
        "sizes[1]" -> "large",
        "numbers[1]" -> "0"
      )
      val result = BillController.addToBill()(request)
      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result) must beSome("/attemptFailed?errorcode=atLeastOneProduct")
    }

    "add one product to the cart when two products have been added (amount 0 & 1)" in memDB {
      val request = FakeRequest(POST, "/addToBill").withFormUrlEncodedBody(
        "names[0]" -> "Regina",
        "sizes[0]" -> "medium",
        "numbers[0]" -> "1",
        "names[1]" -> "Margarita",
        "sizes[1]" -> "large",
        "numbers[1]" -> "0"
      )
      val result = BillController.addToBill()(request)
      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result) must beSome("/setOrder?orderedProducts=1x+Regina+%28medium%29&sumOfOrder=7.290000000000001")
    }

    "show the showBill view" in new WithApplication {
      val request = FakeRequest(GET, "/showBill")
      val result = BillController.showBill()(request)
      status(result) must equalTo(OK)
      contentAsString(result) must contain("Warenkorb")
    }

    "cancel an order" in new WithApplication {
      val request = FakeRequest(GET, "/cancelOrder").withSession(
        "orderedProducts" -> "1x Magarita (medium)",
        "sumOfOrder" -> "6.21",
        "customerData" -> "John Claude Bahnhofstraße 10",
        "currentDate" -> "10.10.10"
      )
      val result = BillController.cancelOrder()(request)
      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result) must beSome("/showMenu")
    }

  }
}