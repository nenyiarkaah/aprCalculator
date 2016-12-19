package controllers

import javax.inject._

import models._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.data.format.Formats._
import play.api.libs.json.JsValue
import play.api.routing._
import play.api.libs.json._
import services._
/**
  * Created by Nenyi on 09/08/2016.
  */
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport with Calculations {


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

  val PaymentForm = Form(
    mapping(
      "paymentAmount" -> of(doubleFormat),
      "paymentInterest" -> of(doubleFormat),
      "paymentPayment" -> of(doubleFormat)
    )
    (paymentData.apply)(paymentData.unapply)
  )

  def process =  Action (parse.form(InterestForm)) { implicit request =>

    val body = request.body
    var message = "With an amount of " + body.amount + " at APR of " + body.interest + "% interest on payment will be."
    val interest = CalculateInterest(body.amount, body.interest);
    val json: JsValue = JsObject(Seq(
      "message" -> JsString(message),
      "interest" -> JsNumber(interest)
    ))
    Ok(json)
  }

  def processPaymentPlan =  Action (parse.form(PaymentForm)) { implicit request =>

    val body = request.body
    val paymentPlan = CalculatePaymentPlan(List[(Double, Double)](), body.amount, body.interest, body.payment);
    val json: JsValue = Json.toJson(
      paymentPlan.map {p => Map("amount" -> p._1, "interest" -> p._2)}
    )
    Ok(json)
  }

  def javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.Application.process
      )
    ).as("text/javascript")
  }

}