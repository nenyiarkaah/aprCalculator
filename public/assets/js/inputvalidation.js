var decimalOnly = /^\s*-?[1-9]\d*(\.\d{1,2})?\s*$/;

  function FormValidation() {
      var interestfield = document.getElementById('inputInterest');
      var amountfield = document.getElementById('inputAmount');
      var amountresult = testInputData('inputAmount', decimalOnly);
      var interestresult = testInputData('inputInterest', decimalOnly);
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

  function testInputData(myfield, restrictionType) {
      var field = document.getElementById(myfield)
      var data = field.value;
      var fieldClass = myfield.replace('input', '').toLowerCase() + 'Class'
      var fieldSpan = myfield.replace('input', '').toLowerCase() + 'Span'

      if (data !== '') {
          if (restrictionType.test(data)) {
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
