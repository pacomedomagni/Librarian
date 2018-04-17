/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.BookLoans;

/**
 * @BookLoans ppradhan
 *
 */
@Component
public class BookLoansDAO extends BaseDAO<BookLoans> implements ResultSetExtractor<List<BookLoans>>{
	
	public void createBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate,dateIn) VALUES (?,?,?,now(),date_add(now(), INTERVAL 1 WEEK),null)",
				new Object[] { bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo() });
	}

	public void createCompleteBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) VALUES (?,?,?,?,?,?)",
				new Object[] { bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo(),
						bookLoans.getDateOut(), bookLoans.getDueDate(), bookLoans.getDateIn() });
	}
	
	public void updateDateInBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("UPDATE tbl_book_loans SET dateIn = now() WHERE bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo() });
	}
	public void updateDueDateBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("UPDATE tbl_book_loans SET dueDate = ? WHERE bookId = ? and branchId = ? and cardNo = ?", new Object[] {
				bookLoans.getDueDate(), bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo() });
	}

	public void deleteBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("DELETE FROM tbl_book_loans WHERE bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo() });
	}
	public void overrideDueDate(BookLoans bookLoans) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_loans SET dueDate = date_add(dueDate, INTERVAL 1 week) WHERE bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo() });
	}
	
	public List<BookLoans> readBookLoans() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_book_loans WHERE dateIn IS null", this);
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> bookLoans = new ArrayList<>();
		while (rs.next()) {
			BookLoans bookLoan = new BookLoans();
			bookLoan.setBookId(rs.getInt("bookId"));
			bookLoan.setBranchId(rs.getInt("branchId"));
			bookLoan.setCardNo(rs.getInt("cardNo"));
			bookLoan.setDateOut(rs.getString("dateOut"));
			bookLoan.setDueDate(rs.getString("dueDate"));
			bookLoan.setDateIn(rs.getString("dateIn"));
			bookLoans.add(bookLoan);
		}
		return bookLoans;
	}
	

}
