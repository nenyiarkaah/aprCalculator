var decimalOnly = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
var numbersOnly = /^\d+$/;
var uppercaseOnly = /^[A-Z]+$/;
var lowercaseOnly = /^[a-z]+$/;
var stringOnly = /^[A-Za-z0-9]+$/;

  function CalculatorFormValidation() {
      var interestfield = document.getElementById('inputInterest');
      var amountfield = document.getElementById('inputAmount');
      var amountresult = extractAndTestInputData('inputAmount', decimalOnly);
      var interestresult = extractAndTestInputData('inputInterest', decimalOnly);
      if (amountresult && interestresult) {
          return true;
      } else {
          if (!amountresult) {
              amountfield.setCustomValidity('The amount should be a 2 decimal number.');
          }
          if (!interestresult) {
              interestfield.setCustomValidity('The interest should be a 2 decimal number.');
          }
          return false;
      }
  }

function PaymentPlanFormValidation() {
    var interestField = document.getElementById('paymentInterest');
    var amountField = document.getElementById('paymentAmount');
    var paymentField = document.getElementById('paymentPayment');
    var amountResult = extractAndTestInputData('paymentAmount', decimalOnly);
    var interestResult = extractAndTestInputData('paymentInterest', decimalOnly);
    var paymentResult = extractAndTestInputData('paymentPayment', decimalOnly);
    if (amountResult && interestResult && paymentResult) {
        return true;
    } else {
        if (!amountResult) {
            amountField.setCustomValidity('The amount should be a 2 decimal number.');
        }
        if (!interestResult) {
            interestField.setCustomValidity('The interest should be a 2 decimal number.');
        }
        if (!paymentResult) {
            paymentField.setCustomValidity('The payment should be a 2 decimal number.');
        }
        return false;
    }
}

  function extractAndTestInputData(myfield, restrictionType) {
      var field = document.getElementById(myfield);
      var data = field.value;
      // console.log(data);
      var fieldClass = myfield.replace('input', '').toLowerCase() + 'Class';
      var fieldSpan = myfield.replace('input', '').toLowerCase() + 'Span';

      if (data !== '') {
          // console.log("isFinite " + isFinite(data));
          if (testInputData(data, restrictionType)) {
              $('#' + fieldClass).removeClass('has-error').addClass('has-success');
              $('#' + fieldSpan).removeClass('glyphicon-remove').addClass('glyphicon-ok');
              return true;
          } else {
              $('#' + fieldClass).removeClass('has-success').addClass('has-error');
              $('#' + fieldSpan).removeClass('glyphicon-ok').addClass('glyphicon-remove');
              field.setCustomValidity('The amount should be a 2 decimal number.');
              return false;
          }
      } else {
          $('#' + fieldClass).removeClass('has-error');
          $('#' + fieldClass).removeClass('has-success');
          $('#' + fieldSpan).removeClass('glyphicon-ok');
          $('#' + fieldSpan).removeClass('glyphicon-remove');
      }
      
  }

testInputData = function (data, restrictionType) {
    return restrictionType.test(data) && isFinite(data) && parseFloat(data) > 0;
};
exports.testInputData = function (data, restrictionType) {
  return testInputData(data, restrictionType);
};

exports.decimalOnly = function(){
  return decimalOnly;
};
