import akka.event.Logging.Error
import org.scalatestplus.play._

import scala.collection.mutable
import services._

class CalculationsSpec extends PlaySpec with Calculations {

    "CalculateInterest" must {
      "throw error when amount is 0" in {
        val thrown = intercept[Exception] {
          CalculateInterest(0, 10)
        }
          thrown.getMessage mustBe "Amount can not be less than or equal to 0."
      }

      "throw error when interest is 0" in {
        val thrown = intercept[Exception] {
          CalculateInterest(10, 0)
        }
        thrown.getMessage mustBe "Interest can not be less than or equal to 0."
      }

      "throw error when amount is less than 0" in {
        val thrown = intercept[Exception] {
          CalculateInterest(-0.25, 10)
        }
        thrown.getMessage mustBe "Amount can not be less than or equal to 0."
      }

      "throw error when interest is less than 0" in {
        val thrown = intercept[Exception] {
          CalculateInterest(10, -0.25)
        }
        thrown.getMessage mustBe "Interest can not be less than or equal to 0."
      }

      "return the correct value when all inputs are valid" in {
        val result = CalculateInterest(100, 10)
        result mustBe 10
      }
    }
}