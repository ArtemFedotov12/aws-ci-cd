package com.start.springlearningdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class UserController {

  final static String MONEY = "1000000.00";

  private final LocaleResolver localeResolver;

  // this controller needs user authentication via jwt
  @GetMapping("/test")
  public String test() {
    return "test";
  }

  //
  @GetMapping("/currency")
  public String currency() {
    final Locale currentLocale = LocaleContextHolder.getLocale();
    return toCurrency(MONEY, "USD", currentLocale);
  }

  public static String toCurrency(String value, String currencyCode, Locale language) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance(language);
    formatter.setCurrency(Currency.getInstance(currencyCode));
    formatter.setMinimumFractionDigits(2);

    String result;
    try {
      double amount = Double.parseDouble(value);
      result = formatter.format(amount);
    } catch (NumberFormatException e) {
      result = currencyCode + " " + value;
    }

    return result;
  }

}
