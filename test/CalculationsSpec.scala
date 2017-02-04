import org.scalatestplus.play._
import org.scalatest.Matchers._

import services._

class CalculationsSpec extends PlaySpec with Calculations {

    "calculateInterest" must {
      "throw error when amount is 0" in {
        val thrown = intercept[AmountCannotBeZeroException] {
          calculateInterest(0, 10)
        }
      }

      "throw error when interest is 0" in {
        val thrown = intercept[InterestCannotBeZeroException] {
          calculateInterest(10, 0)
        }
      }

      "throw error when amount is less than 0" in {
        val thrown = intercept[AmountCannotBeZeroException] {
          calculateInterest(-0.25, 10)
        }
      }

      "throw error when interest is less than 0" in {
        val thrown = intercept[InterestCannotBeZeroException] {
          calculateInterest(10, -0.25)
        }
      }

      "return the correct value when all inputs are valid" in {
        val result = calculateInterest(100, 10)
        result mustBe 10
      }
    }

  "calculatePaymentPlan" must {
    val head = List[(Double, Double)]()
    "throw error when interest is 0" in {
      val thrown = intercept[InterestCannotBeZeroException] {
        calculatePaymentPlan(head, 1000, 0, 200)
      }
    }

    "throw error when interest is a negative greater than -1" in {
      val thrown = intercept[InterestCannotBeZeroException] {
        calculatePaymentPlan(head, 1000, -0.25, 200)
      }
    }

    "throw error when interest is a negative less than -1" in {
      val thrown = intercept[InterestCannotBeZeroException] {
        calculatePaymentPlan(head, 1000, -10.25, 200)
      }
    }

    "throw error when amount is a negative greater than -1" in {
      val thrown = intercept[AmountCannotBeZeroException] {
        calculatePaymentPlan(head, -0.25, 10, 200)
      }
    }

    "throw error when amount is a negative less than -1" in {
      val thrown = intercept[AmountCannotBeZeroException] {
        calculatePaymentPlan(head, -10.25, 10, 200)
      }
    }

    "when amount is 0 return empty unit" in {
      assert(calculatePaymentPlan(head, 0, 10, 200) === head)
    }

    "throw error when payment is 0" in {
      val thrown = intercept[PaymentCannotBeZeroException] {
        calculatePaymentPlan(head, 1000, 10, 0)
      }
    }

    "throw error when payment is a negative greater than -1" in {
      val thrown = intercept[PaymentCannotBeZeroException] {
        calculatePaymentPlan(head, 1000, 10, -0.25)
      }
    }

    "throw error when payment is a negative less than -1" in {
      val thrown = intercept[PaymentCannotBeZeroException] {
        calculatePaymentPlan(head, 1000, 10, -10.25)
      }
    }

    "throw error when payment plan is infinite and causes a stack overflow" in {
      val thrown = intercept[StackOverflowError] {
        calculatePaymentPlan(head, 1000, 10, 100)
      }
    }

    "return the correct value when all inputs are valid test 1" in {
      val expected = List((100.00, 0.00), (82.0,2.0), (63.64,1.64), (44.912800000000004,1.2728), (25.811056000000008,0.898256), (6.327277120000009,0.5162211200000002), (Double.NaN,Double.NaN))
      val result = calculatePaymentPlan(head, 100, 2, 20)
      result.dropRight(1) shouldEqual expected.dropRight(1)
//      result.last._1.isNaN & result.last._2.isNaN shouldEqual expected.last._1.isNaN & expected.last._2.isNaN
    }

    "return the correct value when all inputs are valid test 2" in {
      val expected = List((1000.00, 0.00), (910.0,10.0), (819.1,9.1), (727.291,8.191), (634.5639100000001,7.27291), (540.9095491,6.3456391000000005), (446.31864459100007,5.409095491), (350.7818310369101,4.463186445910001), (254.28964934727918,3.507818310369101), (156.83254584075195,2.542896493472792), (58.40087129915946,1.5683254584075195), (Double.NaN,Double.NaN))
      val result = calculatePaymentPlan(head, 1000, 1, 100)
      result.dropRight(1) shouldEqual expected.dropRight(1)
//      result.last._1.isNaN & result.last._2.isNaN shouldEqual expected.last._1.isNaN & expected.last._2.isNaN
    }

    "return the correct value when all inputs are valid test 3" in {
      val expected = List((1000.00, 0.00), (910.0,10.0), (819.1,9.1), (727.291,8.191), (634.5639100000001,7.27291), (540.9095491,6.3456391000000005), (446.31864459100007,5.409095491), (350.7818310369101,4.463186445910001), (254.28964934727918,3.507818310369101), (156.83254584075195,2.542896493472792), (58.40087129915946,1.5683254584075195), (Double.NaN,Double.NaN))
      val result = calculatePaymentPlan(head, 1000, 1, 100)
      result.dropRight(1) shouldEqual expected.dropRight(1)
//      result.last._1.isNaN & result.last._2.isNaN shouldEqual expected.last._1.isNaN & expected.last._2.isNaN
    }
  }

  "calculatePaymentPlanTotal" must {
    val head = List[(Double, Double)]()
    "return the correct value when all inputs are valid test 1" in {
      val expected = Map("total" -> 106.32727712, "noOfPayments" -> 6.0)
      val payment = 20
      val result = calculatePaymentPlanTotal(calculatePaymentPlan(head, 100, 2, payment), payment, 0, 0)

      result shouldEqual expected
    }
  }
}