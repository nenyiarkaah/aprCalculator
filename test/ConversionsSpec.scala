import org.scalatestplus.play._
import org.scalatest.Matchers._

import services._
/**
  * Created by Nenyi on 21/12/2016.
  */
class ConversionsSpec extends PlaySpec with Conversions with Calculations {
  val formatter = java.text.NumberFormat.getCurrencyInstance
  "roundToTwoDecimalPlaces" must {
    "return a 0.00 when number is 0" in {
      val result = roundToTwoDecimalPlaces(0)
      result shouldEqual 0.00
    }

    "return a 0.01 when number is 0.01" in {
      val result = roundToTwoDecimalPlaces(0.01)
      result shouldEqual 0.01
    }

    "return a 0.10 when number is 0.1" in {
      val result = roundToTwoDecimalPlaces(0.1)
      result shouldEqual 0.10
    }

    "return a 1.00 when number is 1" in {
      val result = roundToTwoDecimalPlaces(1)
      result shouldEqual 1.00
    }

    "return a 10.00 when number is 10" in {
      val result = roundToTwoDecimalPlaces(10)
      result shouldEqual 10.00
    }

    "return a 100.00 when number is 100" in {
      val result = roundToTwoDecimalPlaces(100)
      result shouldEqual 100.00
    }

    "return a 0.26 when number is 0.2567897" in {
      val result = roundToTwoDecimalPlaces(0.2567897)
      result shouldEqual 0.26
    }

    "return a 100.26 when number is 100.2567897" in {
      val result = roundToTwoDecimalPlaces(100.2567897)
      result shouldEqual 100.26
    }

    "return a 0.25 when number is 0.2457897" in {
      val result = roundToTwoDecimalPlaces(0.2457897)
      result shouldEqual 0.25
    }

    "return a 100.25 when number is 100.2457897" in {
      val result = roundToTwoDecimalPlaces(100.2457897)
      result shouldEqual 100.25
    }
  }

  "roundCurrencyToTwoDecimalPlaces" must {
    "return a 0.00 when number is 0" in {
      val result = roundCurrencyToTwoDecimalPlaces(0)
      result shouldEqual formatter.format(0.00)
    }

    "return a 0.01 when number is 0.01" in {
      val result = roundCurrencyToTwoDecimalPlaces(0.01)
      result shouldEqual formatter.format(0.01)
    }

    "return a 0.10 when number is 0.1" in {
      val result = roundCurrencyToTwoDecimalPlaces(0.1)
      result shouldEqual formatter.format(0.10)
    }

    "return a 1.00 when number is 1" in {
      val result = roundCurrencyToTwoDecimalPlaces(1)
      result shouldEqual formatter.format(1.00)
    }

    "return a 10.00 when number is 10" in {
      val result = roundCurrencyToTwoDecimalPlaces(10)
      result shouldEqual formatter.format(10.00)
    }

    "return a 100.00 when number is 100" in {
      val result = roundCurrencyToTwoDecimalPlaces(100)
      result shouldEqual formatter.format(100.00)
    }

    "return a 0.26 when number is 0.2567897" in {
      val result = roundCurrencyToTwoDecimalPlaces(0.2567897)
      result shouldEqual formatter.format(0.26)
    }

    "return a 100.26 when number is 100.2567897" in {
      val result = roundCurrencyToTwoDecimalPlaces(100.2567897)
      result shouldEqual formatter.format(100.26)
    }

    "return a 0.25 when number is 0.2457897" in {
      val result = roundCurrencyToTwoDecimalPlaces(0.2457897)
      result shouldEqual formatter.format(0.25)
    }

    "return a 100.25 when number is 100.2457897" in {
      val result = roundCurrencyToTwoDecimalPlaces(100.2457897)
      result shouldEqual formatter.format(100.25)
    }
  }
}
