package services

import play.api.libs.json.{JsValue, Json}

/**
  * Created by Nenyi on 21/12/2016.
  */
trait Conversions {
  def roundToTwoDecimalPlaces(number: Double) = {
    BigDecimal(number).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def roundCurrencyToTwoDecimalPlaces(number: Double) = {
    val formatter = java.text.NumberFormat.getCurrencyInstance
    formatter.format(BigDecimal(number).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  }

  def convertPaymentObjectsToJson(paymentPlan: List[(Double, Double)], total: (Double, Double)): JsValue = {
    val payments =
      paymentPlan.filter(_._1.isNaN != true).filter(_._2.isNaN != true).map {
        p => Map("amount" -> roundCurrencyToTwoDecimalPlaces(p._1), "interest" -> roundCurrencyToTwoDecimalPlaces(p._2))
      }
    val totals = Map("totalPayments" -> roundCurrencyToTwoDecimalPlaces(total._1), "noOfPayments" -> total._2.toInt.toString)
    val json = Json.obj("payments" -> payments, "paymentTotal" -> totals)
    json
  }
}
