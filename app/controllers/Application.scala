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
import play.api.libs.json.JsValue
import play.api.routing._
import play.api.libs.json._
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

  def process =  Action (parse.form(InterestForm)) { implicit request =>

    val body = request.body
    var message = "With an amount of " + body.amount + " at APR of " + body.interest + "% interest on payment will be."
    val interest = CalculateInterest(body.amount, body.interest);
    val json: JsValue = JsObject(Seq(
      "message" -> JsString(message),
      "interest" -> JsNumber(interest.getOrElse(0).asInstanceOf[Double])
    ))
    Ok(json)
  }

  def CalculateInterest(amount: Double, intrest: Double): Option[Double] = {
    if(amount <  0) throw new Error("Amount can not be less than 0.")
    else if(intrest == 0 ) throw new Error("Interest can not be 0.")
    else Option(amount * intrest / 100)
  }

  def javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.Application.process
      )
    ).as("text/javascript")
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