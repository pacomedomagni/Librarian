/**
 * 
 */
package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author ppradhan
 *
 */
public class Publisher implements Serializable{
	
	
	private static final long serialVersionUID = 2129046521470833674L;
	private Integer publisherId;
	private String publisherName;
	private String publisherPhone;
	private String publisherAddress;
	private List<Book> books;
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((publisherId == null) ? 0 : publisherId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		if (publisherId == null) {
			if (other.publisherId != null)
				return false;
		} else if (!publisherId.equals(other.publisherId))
			return false;
		return true;
	}
	
	/**
	 * @return the publisherId
	 */
	public Integer getPublisherId() {
		return publisherId;
	}
	/**
	 * @param publisherId the publisherId to set
	 */
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}
	/**
	 * @param publisherName the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	/**
	 * @return the publisherPhone
	 */
	public String getPublisherPhone() {
		return publisherPhone;
	}
	/**
	 * @param publisherPhone the publisherPhone to set
	 */
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}
	/**
	 * @return the publisherAddress
	 */
	public String getPublisherAddress() {
		return publisherAddress;
	}
	/**
	 * @param publisherAddress the publisherAddress to set
	 */
	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
