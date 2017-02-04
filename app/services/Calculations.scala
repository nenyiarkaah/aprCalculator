package services

/**
  * Created by Nenyi on 30/11/2016.
  */
trait Calculations {
  class AmountCannotBeZeroException() extends Exception("Amount can not be less than or equal to 0.")
  class InterestCannotBeZeroException() extends Exception("Interest can not be less than or equal to 0.")
  class PaymentCannotBeZeroException extends Exception("Payment can not be 0.")

  def CalculateInterest(amount: Double, interest: Double): Double = {
    if (!IsNotLessThanOrEqualToZero(amount)) throw new AmountCannotBeZeroException
    else if (!IsNotLessThanOrEqualToZero(interest)) throw new InterestCannotBeZeroException
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
    else if (amount.isNaN) payments
    else if(payment <= 0 ) throw new PaymentCannotBeZeroException
    //pass amounts into function
    else
    {
      val internalPayments = payments match {
        case Nil =>
          var head :(Double, Double) = (amount, 0.00)
          List(head) :+ CalculatePayment(amount, interest, payment)
        case _ => payments :+ CalculatePayment(amount, interest, payment)
      }
      CalculatePaymentPlan(internalPayments, internalPayments.last._1, interest, payment)
    }
  }

  def CalculatePayment(amount: Double, interest: Double, payment: Double): (Double, Double) = {
    val calculatedInterest = CalculateInterest(amount, interest)
    def matchCalculatedInterest: (Double, Double) = calculatedInterest match {
      case Double.NaN => (Double.NaN, Double.NaN)
      case calculatedInterestPlusAmount if calculatedInterestPlusAmount  + amount < payment => (Double.NaN, Double.NaN)
      case _ => (amount + calculatedInterest- payment, calculatedInterest)
    }
//    println(matchCalculatedInterest)
    matchCalculatedInterest
  }

  def CalculatePaymentPlanTotal(payments: List[(Double, Double)], payment: Double, total: Double, noOfPayments: Int): (Double, Double) = {
    payments match {
      case Nil => (total, noOfPayments)
      case head :: tail =>
        head match {
          case head if head._1.isNaN || head._2.isNaN => (total, noOfPayments)
          case _ =>
            val newTotal = if (payment < head._1) total + payment else total + head._1
            var newNoOfPayments = noOfPayments + 1
            CalculatePaymentPlanTotal(tail, payment, newTotal, newNoOfPayments)
        }
    }
  }


}
