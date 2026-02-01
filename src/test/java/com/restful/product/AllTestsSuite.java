package com.restful.product;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Runs all tests: controllers, services, repositories
 */
@Suite
@SelectPackages({
    "com.restful.product.repository",
    "com.restful.product.service",
    "com.restful.product.controller"
})
public class AllTestsSuite {
    // No code required
}
