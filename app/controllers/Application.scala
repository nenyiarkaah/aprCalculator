package controllers

import javax.inject._

import models.calcData
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import views.html.helper.form
import play.api.data.format.Formats._
/**
  * Created by Nenyi on 09/08/2016.
  */
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {


  def index = Action { implicit request =>
    Ok(views.html.index(None, "", InterestForm))
  }
  val InterestForm = Form(
    mapping(
      "inputAmount" -> of(doubleFormat),
      "inputInterest" -> of(doubleFormat)
    )
    (calcData.apply)(calcData.unapply)
  )

  def process = Action (parse.form(InterestForm)) { implicit request =>

    val result = request.body
    val message = "With an amount of " + result.amount + " at APR of " + result.interest + "% interest on payment will be."
    Ok(views.html.index(CalculateInterest(result.amount, result.interest), message, InterestForm))
  }

  def CalculateInterest(amount: Double, intrest: Double): Option[Double] = {
    if(amount <  0) throw new Error("Amount can not be less than 0.")
    else if(intrest == 0 ) throw new Error("Interest can not be 0.")
    else Option(amount * intrest / 100)
  }

  //
  //  def CalculatePaymentPlan(amount: Double, intrest: Double, payment: Double): Unit = {
  //
  //    var payments = List[(Double, Double)]()
  //    if(amount == 0) Nil
  //    else if(payment <= 0 ) throw new Error("Payment can not be 0.")
  //    //pass amounts into function
  //    else
  //    {
  //      payments ::= CalculatePayment(amount, intrest, payment)
  //      println(payments.head)
  //      CalculatePaymentPlan(payments.head._1, intrest, payment)
  //    }
  //
  //    def CalculatePayment(amount: Double, intrest: Double, payment: Double): (Double, Double) = {
  //      val calulatedInterest = CalculateInterest(amount, intrest)
  //      def matchCalculatedIntrest: (Double, Double) = calulatedInterest match {
  //        case calculatedIntrestplusAmount if calculatedIntrestplusAmount  + amount < payment =>  (0.00, 0.00)
  //        case _ => (amount + calulatedInterest- payment, calulatedInterest)
  //      }
  //      matchCalculatedIntrest
  //    }
  //
  //  }


}