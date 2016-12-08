/**
 * Created by Nenyi on 01/12/2016.
 */
var assert = require('assert');
var data = require('../public/assets/js/inputvalidation');
var decimalOnly = data.decimalOnly();

describe('testInputData', function() {
    it('should return false when a NULL', function() {
        assert.equal(data.testInputData(null, decimalOnly), false);
    });
    it('should return false when a empty', function() {
        assert.equal(data.testInputData("", decimalOnly), false);
    });
    it('should return false when a space', function() {
        assert.equal(data.testInputData(" ", decimalOnly), false);
    });
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
    it('should return true when below 1 decimal', function() {
        assert.equal(data.testInputData("0.25", decimalOnly), true);
    });
    it('should return true when above 1 decimal', function() {
        assert.equal(data.testInputData("10.25", decimalOnly), true);
    });
    it('should return false when below 1 decimal to 3 decimal places', function() {
        assert.equal(data.testInputData("0.011", decimalOnly), false);
    });
    it('should return false when above 1 decimal to 3 decimal places', function() {
        assert.equal(data.testInputData("1000.011", decimalOnly), false);
    });
    it('should return false when alpha numeric string', function() {
        assert.equal(data.testInputData("100absjkgsd", decimalOnly), false);
    });
    it('should return false when alpha string', function() {
        assert.equal(data.testInputData("abc", decimalOnly), false);
    });
});

//This is just a test to see that mocha is working.
describe('Array', function() {
    describe('#indexOf()', function() {
        it('should return -1 when the value is not present', function() {
            assert.equal(-1, [1,2,3].indexOf(4));
        });
    });
});