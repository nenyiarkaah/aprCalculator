/**
 * Created by Nenyi on 01/12/2016.
 */
var assert = require('assert');
var data = require('../public/assets/js/inputvalidation');


var decimalOnly = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;

describe('testInputData', function() {
    it('should return false when zero 00', function() {
        assert.equal(data.testInputData("00", decimalOnly), false);
    });
    it('should return false when zero 0', function() {
        assert.equal(data.testInputData("0", decimalOnly), false);
    });
    it('should return false when zero 0.', function() {
        assert.equal(data.testInputData("0.", decimalOnly), false);
    });
    it('should return false when zero 0.00', function() {
        assert.equal(data.testInputData("0.00", decimalOnly), false);
    });
    it('should return false when -1', function() {
        assert.equal(data.testInputData("-1", decimalOnly), false);
    });
    it('should return false when -1.', function() {
        assert.equal(data.testInputData("-1.", decimalOnly), false);
    });
    it('should return false when -1.00', function() {
        assert.equal(data.testInputData("-1.00", decimalOnly), false);
    });
    it('should return true when zero 0.25', function() {
        assert.equal(data.testInputData("0.25", decimalOnly), true);
    });
});