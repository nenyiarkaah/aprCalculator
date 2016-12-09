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
          thrown.getMessage mustBe "Amount can not be less than 0."
      }

      "return the correct value when all inputs are valid" in {
        val result = CalculateInterest(100, 10)
        result mustBe 10
      }
    }
}