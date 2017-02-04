package services

/**
  * Created by Nenyi on 30/11/2016.
  */
trait Calculations {

  class AmountCannotBeZeroException() extends Exception("Amount can not be less than or equal to 0.")

  class InterestCannotBeZeroException() extends Exception("Interest can not be less than or equal to 0.")

  class PaymentCannotBeZeroException extends Exception("Payment can not be 0.")

  def calculateInterest(amount: Double, interest: Double): Double = {
    isNotLessThanOrEqualToZero(amount) match {
      case false => throw new AmountCannotBeZeroException
      case true =>
    }
    isNotLessThanOrEqualToZero(interest) match {
      case false => throw new InterestCannotBeZeroException
      case true =>
    }
    amount * interest / 100
  }

  private def isNotLessThanOrEqualToZero(input: Double) = {
    input match {
      case x if x <= 0 => false
      case _ => true
    }
  }

  def calculatePaymentPlan(payments: List[(Double, Double)], amount: Double, interest: Double, payment: Double): List[(Double, Double)] = {
    isNotLessThanOrEqualToZero(payment) match {
      case false => throw new PaymentCannotBeZeroException
      case true =>
    }
    amount match {
      case 0 => payments
      case x if (x.isNaN) => payments
      case _ =>
        val internalPayments = payments match {
          case Nil =>
            val head: (Double, Double) = (amount, 0.00)
            List(head) :+ calculatePayment(amount, interest, payment)
          case _ => payments :+ calculatePayment(amount, interest, payment)
        }
        val newPayment = internalPayments.lastOption.getOrElse(throw new RuntimeException("No new payments are available"))
        calculatePaymentPlan(internalPayments, newPayment._1, interest, payment)
    }
  }

  def calculatePayment(amount: Double, interest: Double, payment: Double): (Double, Double) = {
    val calculatedInterest = calculateInterest(amount, interest)

    def matchCalculatedInterest: (Double, Double) = calculatedInterest match {
      case Double.NaN => (Double.NaN, Double.NaN)
      case calculatedInterestPlusAmount if calculatedInterestPlusAmount + amount < payment => (Double.NaN, Double.NaN)
      case _ => (amount + calculatedInterest - payment, calculatedInterest)
    }

    //    println(matchCalculatedInterest)
    matchCalculatedInterest
  }

  def calculatePaymentPlanTotal(payments: List[(Double, Double)], payment: Double, total: Double, noOfPayments: Int): Map[String, Double] = {
    payments match {
      case Nil => Map("total" -> total, "noOfPayments" -> noOfPayments)
      case head :: tail =>
        head match {
          case head if head._1.isNaN || head._2.isNaN => Map("total" -> total, "noOfPayments" -> noOfPayments)
          case _ =>
            val newTotal = if (payment < head._1) total + payment else total + head._1
            val newNoOfPayments = noOfPayments + 1
            calculatePaymentPlanTotal(tail, payment, newTotal, newNoOfPayments)
        }
    }
  }
}
