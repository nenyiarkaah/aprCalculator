var decimalOnly = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
var numbersOnly = /^\d+$/;
var uppercaseOnly = /^[A-Z]+$/;
var lowercaseOnly = /^[a-z]+$/;
var stringOnly = /^[A-Za-z0-9]+$/;

  function FormValidation() {
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
          if (!amountresult) {
              amountfield.setCustomValidity('The amount should be a 2 decimal number.');
          }
          return false;
      }
  }

  // field.addEventListener('change', checkPasswordValidity, false);

  function extractAndTestInputData(myfield, restrictionType) {
      var field = document.getElementById(myfield)
      var data = field.value;
      console.log(data);
      var fieldClass = myfield.replace('input', '').toLowerCase() + 'Class'
      var fieldSpan = myfield.replace('input', '').toLowerCase() + 'Span'

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
      return;
  }

  exports.testInputData = function (data, restrictionType) {
      return restrictionType.test(data) && isFinite(data) && data > 0;
  }
