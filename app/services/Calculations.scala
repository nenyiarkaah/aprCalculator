package services

import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue}
import play.api.mvc.BodyParsers.parse

/**
  * Created by Nenyi on 30/11/2016.
  */
trait Calculations {

  def CalculateInterest(amount: Double, interest: Double): Double = {
    if (!IsNotLessThanOrEqualToZero(amount)) throw new Exception("Amount can not be less than or equal to 0.")
    else if (!IsNotLessThanOrEqualToZero(interest)) throw new Exception("Interest can not be less than or equal to 0.")
    else amount * interest / 100
  }

  private def IsNotLessThanOrEqualToZero(input: Double) = {
    input match {
      case x if x <= 0 => false
      case _ => true
    }
  }

  def CalculatePaymentPlan(payments: List[(Double, Double)], amount: Double, interest: Double, payment: Double): List[(Double, Double)] = {
    if(amount == 0) payments
    else if(payment <= 0 ) throw new Exception("Payment can not be 0.")
    //pass amounts into function
    else
    {
      val internalPayments = payments :+ CalculatePayment(amount, interest, payment)
      CalculatePaymentPlan(internalPayments, internalPayments.last._1, interest, payment)
    }
  }

  def CalculatePayment(amount: Double, interest: Double, payment: Double): (Double, Double) = {
    val calculatedInterest = CalculateInterest(amount, interest)
    def matchCalculatedInterest: (Double, Double) = calculatedInterest match {
      case calculatedInterestPlusAmount if calculatedInterestPlusAmount  + amount < payment => (0.00, 0.00)
      case _ => (amount + calculatedInterest- payment, calculatedInterest)
    }
    matchCalculatedInterest
  }

  def RoundToTwoDecimalPlaces(number: Double) = {
    BigDecimal(number).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def RoundCurrencyToTwoDecimalPlaces(number: Double) = {
    val formatter = java.text.NumberFormat.getCurrencyInstance
    formatter.format(BigDecimal(number).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  }

}
