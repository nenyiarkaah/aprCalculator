package controllers

import javax.inject._

import models._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.data.format.Formats._
//import play.api.libs.json.JsValue
import play.api.routing._
import play.api.libs.json._
import services._
/**
  * Created by Nenyi on 09/08/2016.
  */
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport with Calculations with Conversions{


  def index = Action { implicit request =>
    Ok(views.html.index(None, "", interestForm))
  }
  val interestForm = Form(
    mapping(
      "inputAmount" -> of(doubleFormat),
      "inputInterest" -> of(doubleFormat)
    )
    (calcData.apply)(calcData.unapply)
  )

  val paymentForm = Form(
    mapping(
      "paymentAmount" -> of(doubleFormat),
      "paymentInterest" -> of(doubleFormat),
      "paymentPayment" -> of(doubleFormat)
    )
    (paymentData.apply)(paymentData.unapply)
  )

  def processCalculator =  Action (parse.form(interestForm)) { implicit request =>

    val body = request.body
    var message = "With an amount of " + body.amount + " at APR of " + body.interest + "% interest on payment will be."
    val interest = CalculateInterest(body.amount, body.interest);
    val json: JsValue = JsObject(Seq(
      "message" -> JsString(message),
      "interest" -> JsNumber(interest)
    ))
    Ok(json)
  }

  def processPaymentPlan =  Action (parse.form(paymentForm)) { implicit request =>

    val body = request.body
    var head = List[(Double, Double)]()
    head ::= (body.amount, 0.00)
    val paymentPlan = CalculatePaymentPlan(head, body.amount, body.interest, body.payment);
    val total = CalculatePaymentPlanTotal(paymentPlan, body.payment, 0, 0)
    val json: JsValue = ConvertPaymentObjectsToJson(paymentPlan, total)
    Ok(json)
  }



  def javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.Application.processCalculator
      )
    ).as("text/javascript")
  }

}