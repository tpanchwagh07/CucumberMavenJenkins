@CartTest
Feature: Add to Cart Functionality

  Background:
    Given the user is logged into Saucedemo

  Scenario: User adds a product to the cart
    When the user adds the product to the cart
    Then the cart should contain the product
    
@CheckoutTest
	Scenario: User completes the checkout process
    Given the user has a product in the cart
    When the user proceeds to checkout with the following details:
      | FirstName | LastName | ZipCode |
      | John      | Doe      | 12345   |
    Then the order confirmation message should be displayed
    
@FilterTest
	Scenario: User applies product filters
    Given the user is on the inventory page
    When the user selects the filter "Price (low to high)"
    Then the products should be sorted in ascending order