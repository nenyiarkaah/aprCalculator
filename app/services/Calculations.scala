package services

import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue}
import play.api.mvc.BodyParsers.parse

/**
  * Created by Nenyi on 30/11/2016.
  */
trait Calculations {

  def CalculateInterest(amount: Double, intrest: Double): Double = {
    if(amount <  0) throw new Error("Amount can not be less than 0.")
    else if(intrest == 0 ) throw new Error("Interest can not be 0.")
    else amount * intrest / 100
  }


  def CalculatePaymentPlan(amount: Double, intrest: Double, payment: Double): Unit = {

    var payments = List[(Double, Double)]()
    if(amount == 0) Nil
    else if(payment <= 0 ) throw new Error("Payment can not be 0.")
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
