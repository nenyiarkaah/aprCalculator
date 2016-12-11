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

  def CalculatePaymentPlan(amount: Double, intrest: Double, payment: Double): Unit = {

    var payments = List[(Double, Double)]()
    if(amount == 0) Nil
    else if(payment <= 0 ) throw new Exception("Payment can not be 0.")
    //pass amounts into function
    else
    {
      payments ::= CalculatePayment(amount, intrest, payment)
      println(payments.head)
      CalculatePaymentPlan(payments.head._1, intrest, payment)
    }

    def CalculatePayment(amount: Double, intrest: Double, payment: Double): (Double, Double) = {
      val calculatedInterest = CalculateInterest(amount, intrest)
      def matchCalculatedIntrest: (Double, Double) = calculatedInterest match {
        case calculatedIntrestplusAmount if  calculatedIntrestplusAmount + amount < payment =>  (0.00, 0.00)
        case _ => (amount + calculatedInterest - payment, calculatedInterest)
      }
      matchCalculatedIntrest
    }

  }
}
