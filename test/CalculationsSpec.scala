import akka.event.Logging.Error
import org.scalatestplus.play._
import org.scalatest.Matchers._

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

  "CalculatePaymentPlan" must {
    "throw error when interest is 0" in {
      val thrown = intercept[Exception] {
        CalculatePaymentPlan(List[(Double, Double)](), 1000, 0, 200)
      }
      thrown.getMessage mustBe "Interest can not be less than or equal to 0."
    }

    "throw error when interest is a negative greater than -1" in {
      val thrown = intercept[Exception] {
        CalculatePaymentPlan(List[(Double, Double)](), 1000, -0.25, 200)
      }
      thrown.getMessage mustBe "Interest can not be less than or equal to 0."
    }

    "throw error when interest is a negative less than -1" in {
      val thrown = intercept[Exception] {
        CalculatePaymentPlan(List[(Double, Double)](), 1000, -10.25, 200)
      }
      thrown.getMessage mustBe "Interest can not be less than or equal to 0."
    }

    "throw error when amount is a negative greater than -1" in {
      val thrown = intercept[Exception] {
        CalculatePaymentPlan(List[(Double, Double)](), -0.25, 10, 200)
      }
      thrown.getMessage mustBe "Amount can not be less than or equal to 0."
    }

    "throw error when amount is a negative less than -1" in {
      val thrown = intercept[Exception] {
        CalculatePaymentPlan(List[(Double, Double)](), -10.25, 10, 200)
      }
      thrown.getMessage mustBe "Amount can not be less than or equal to 0."
    }

    "when amount is 0 return empty unit" in {
      assert(CalculatePaymentPlan(List[(Double, Double)](), 0, 10, 200) === List())
    }

    "throw error when payment is 0" in {
      val thrown = intercept[Exception] {
        CalculatePaymentPlan(List[(Double, Double)](), 1000, 10, 0)
      }
      thrown.getMessage mustBe "Payment can not be 0."
    }

    "throw error when payment is a negative greater than -1" in {
      val thrown = intercept[Exception] {
        CalculatePaymentPlan(List[(Double, Double)](), 1000, 10, -0.25)
      }
      thrown.getMessage mustBe "Payment can not be 0."
    }

    "throw error when payment is a negative less than -1" in {
      val thrown = intercept[Exception] {
        CalculatePaymentPlan(List[(Double, Double)](), 1000, 10, -10.25)
      }
      thrown.getMessage mustBe "Payment can not be 0."
    }

    "throw error when payment plan is infinite and causes a stack overflow" in {
      val thrown = intercept[StackOverflowError] {
        CalculatePaymentPlan(List[(Double, Double)](), 1000, 10, 100)
      }
    }

    "return the correct value when all inputs are valid test 1" in {
      val expected = List((82.0,2.0), (63.64,1.64), (44.912800000000004,1.2728), (25.811056000000008,0.898256), (6.327277120000009,0.5162211200000002), (0.0,0.0))
      val result = CalculatePaymentPlan(List[(Double, Double)](), 100, 2, 20)
      result shouldEqual expected
    }

    "return the correct value when all inputs are valid test 2" in {
      val expected = List((910.0,10.0), (819.1,9.1), (727.291,8.191), (634.5639100000001,7.27291), (540.9095491,6.3456391000000005), (446.31864459100007,5.409095491), (350.7818310369101,4.463186445910001), (254.28964934727918,3.507818310369101), (156.83254584075195,2.542896493472792), (58.40087129915946,1.5683254584075195), (0.0,0.0))
      val result = CalculatePaymentPlan(List[(Double, Double)](), 1000, 1, 100)
      result shouldEqual expected
    }
  }
}