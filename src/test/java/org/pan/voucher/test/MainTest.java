package org.pan.voucher.test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainTest {
	  public static void main(String[] args) {
	    List<String> countries = new ArrayList<String>();

	    Locale[] locales = Locale.getAvailableLocales();
	    for (Locale locale : locales) {
	      String name = locale.getDisplayCountry();

	      if (!"".equals(name)) {
	        countries.add(name);
	      }
	    }

	    Collections.sort(countries);
	    for (String country : countries) {
	      System.out.println(country);
	    }
	  }
	}

	class CountryComparator implements Comparator<String> {
	  private Comparator comparator;

	  CountryComparator() {
	    comparator = Collator.getInstance();
	  }

	  @Override
	public int compare(String o1, String o2) {
	    return comparator.compare(o1, o2);
	  }
	}

	class Country {
	  private String iso;

	  private String code;

	  public String name;

	  Country(String iso, String code, String name) {
	    this.iso = iso;
	    this.code = code;
	    this.name = name;
	  }

	  @Override
	public String toString() {
	    return iso + " - " + code + " - " + name.toUpperCase();
	  }
	}
