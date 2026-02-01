package com.restful.product;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit 5 Suite to run all service layer tests
 */
@Suite
@SelectPackages("com.restful.product.repository")
public class RepositoryTestSuite {

}
