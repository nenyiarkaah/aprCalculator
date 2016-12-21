package services

import play.api.libs.json.{JsValue, Json}

/**
  * Created by Nenyi on 21/12/2016.
  */
trait Conversions {
  def RoundToTwoDecimalPlaces(number: Double) = {
    BigDecimal(number).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def RoundCurrencyToTwoDecimalPlaces(number: Double) = {
    val formatter = java.text.NumberFormat.getCurrencyInstance
    formatter.format(BigDecimal(number).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  }

  def ConvertPaymentObjectsToJson(paymentPlan: List[(Double, Double)], total: (Double, Double)): JsValue = {
    val payments =
      paymentPlan.filter(_._1.isNaN != true).filter(_._2.isNaN != true).map {
        p => Map("amount" -> RoundCurrencyToTwoDecimalPlaces(p._1), "interest" -> RoundCurrencyToTwoDecimalPlaces(p._2))
      }
    val totals = Map("totalPayments" -> RoundCurrencyToTwoDecimalPlaces(total._1), "noOfPayments" -> total._2.toInt.toString)
    val json = Json.obj("payments" -> payments, "paymentTotal" -> totals)
    json
  }
}
