package com.restful.product;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit 5 Suite to run all service layer tests
 */
@Suite
@SelectPackages("com.restful.product.service")
public class ServiceTestSuite {
    // No code needed here; the annotations handle running all tests in this package
}