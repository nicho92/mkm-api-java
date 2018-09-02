package org.api.mkm.modele;

import java.io.Serializable;

public class Address implements Serializable {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String name;
		private String extra;
		private String street;
		private String zip;
		private String city;
		private String country;
		
		@Override
		public String toString() {
			return getCountry();
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getExtra() {
			return extra;
		}
		public void setExtra(String extra) {
			this.extra = extra;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		
		
		
}
