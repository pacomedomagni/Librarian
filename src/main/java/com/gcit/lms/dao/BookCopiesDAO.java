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

import com.gcit.lms.entity.BookCopies;

/**
 * @BookCopies ppradhan
 *
 */
@Component
public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>>{


	public void createBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("insert into tbl_book_copies (bookId,branchId,noOfCopies) values (?,?,?)",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId(), bookCopies.getNoOfCopies() });
	}

	public void updateBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies=? WHERE bookId=? AND branchId=?",
				new Object[] { bookCopies.getNoOfCopies(), bookCopies.getBookId(), bookCopies.getBranchId() });
	}

	public void deleteBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("delete from tbl_book_copies where bookId = ? and branchId= ?",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId() });
	}

	public List<BookCopies> readBookCopies() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_book_copies", this);
	}

	public List<BookCopies> bookCopiesPerBranch(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select noOfCopies from tbl_book_copies where bookId=? and branchId=?",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId() },this);
	}

	public Integer countBookCopies( int branchId, int bookId) throws SQLException, ClassNotFoundException {
		List<BookCopies> bc = jdbcTemplate.query("SELECT * FROM tbl_book_copies WHERE branchId = ? AND bookId = ?",
				new Object[] { branchId, bookId },this);
		if (bc != null) {
			BookCopies bookCopy = bc.get(0);
			return bookCopy.getNoOfCopies();
		}
		return null;
	}
	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bookCopies = new ArrayList<>();
		while (rs.next()) {
			BookCopies bookCopie = new BookCopies();
			bookCopie.setBookId(rs.getInt("bookId"));
			bookCopie.setBranchId(rs.getInt("branchId"));
			bookCopie.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bookCopie);
		}
		return bookCopies;
	}

	public void checkOutBookCopies(BookCopies bookCopies) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies = noOfCopies-1 WHERE bookId = ? and branchId = ?",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId() });
	}

	public void returnBookCopies(BookCopies bookCopies) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies = noOfCopies+1 WHERE bookId = ? and branchId = ?",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId() });
	}
}
