/**
 * 
 */
package com.gcit.lms.service;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Branch;

/**
 * @author domes
 *
 */
@RestController
public class LibrarianService extends BaseController{
	@Autowired
	BookCopiesDAO bcdao;
	@Autowired
	BookLoansDAO bldao;
	@Autowired
	BranchDAO bradao;
	
	
	@RequestMapping(value="initBranch", method=RequestMethod.GET, produces="application/json" )
	public Branch initBranch() throws SQLException {
		return new Branch();
	}
	@RequestMapping(value="initBookLoans", method=RequestMethod.GET, produces="application/json" )
	public BookLoans initBookLoans() throws SQLException {
		return new BookLoans();
	}
	@RequestMapping(value="initBookCopies", method=RequestMethod.GET, produces="application/json" )
	public BookCopies initBookCopies() throws SQLException {
		return new BookCopies();
	}
	
	 //function to checkOut a book input branchId, bookId and cardNo
		@Transactional
		@RequestMapping(value="BookLoan", method=RequestMethod.POST, consumes="application/json" )
		public ResponseEntity<Object>  checkOutBook(@RequestBody BookLoans bookLoan) {
			
			try {
				bldao.createBookLoans(bookLoan);
				//bcdao.checkOutBookCopies(bookCopie);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(URI.create("BookLoan"));
				return new ResponseEntity<Object>(headers,HttpStatus.OK);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		//function to return a book input branchId, bookId and cardNo,
		@Transactional
		@RequestMapping(value="BookLoan", method=RequestMethod.PUT, consumes="application/json" )
		public ResponseEntity<Object>  returnBook(@RequestBody BookLoans bookLoan) {
			
			try {
				bldao.updateDateInBookLoans(bookLoan);
				//bcdao.returnBookCopies(bookCopie);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(URI.create("BookLoan"));
				return new ResponseEntity<Object>(headers,HttpStatus.OK);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	// everything about branches follow below
	//create a new branch input branch name and address
	@Transactional
	@RequestMapping(value = "Branch", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object>  createNewBranch(@RequestBody Branch branch) {

		try {
			bradao.createBranch(branch);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Branch"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// output all the branchs available no input needed
	@Transactional
	@RequestMapping(value = "Branchs", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object>  getAllBranchs() {
		List<Branch> branchs = new ArrayList<>();
		try {
			branchs = bradao.readBranchs();
			return new ResponseEntity<Object>(branchs,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// update branch info name and address while the branchId is set
	@RequestMapping(value = "Branch", method = RequestMethod.PUT, consumes = "application/json")
	@Transactional
	public ResponseEntity<Object>  updateBranchInfo(@RequestBody Branch branch) {
		try {
			bradao.updateBranch(branch);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Branch"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// search branch by name
	@Transactional
	@RequestMapping(value = "Branchs/{search}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object>  searchBranchByName(@PathVariable("search") String search) {
		List<Branch> branchs = new ArrayList<>();
		try {
			branchs = bradao.SearchBranchByName(search);
			return new ResponseEntity<Object>(branchs,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// delete a branch by input branch id
	@Transactional
	@RequestMapping(value = "Branch/{branchId}", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<Object>  deleteBranch(@PathVariable Integer branchId) {

		try {
			bradao.deleteBranch(branchId);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Branch/"+branchId));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@Transactional
	@RequestMapping(value = "Branch/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object>  getBranchByPk(@PathVariable Integer branchId) {
		Branch branch = null;
		try {
			 branch= bradao.getBranchByPk(branchId);
			 return new ResponseEntity<Object>(branch,HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// numeric number of a certain book at a certain branch
	@Transactional
	@RequestMapping(value = "BookCopie/{branchId}/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public Integer getAvailableBookCopie(@PathVariable int branchId, @PathVariable int bookId) {
		Integer totals = 0;
		try {
			totals = bcdao.countBookCopies(branchId, bookId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return totals;
	}

	// needed to be improved
	@Transactional
	@RequestMapping(value = "addBookCopies", method = RequestMethod.POST, consumes = "application/json")
	public void addBookCopies(@RequestBody BookCopies bookCopie) {
		try {
			bcdao.updateBookCopies(bookCopie);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*// output the numeric number of the total branchs
		@Transactional
		@RequestMapping(value = "totalBranchs", method = RequestMethod.GET, produces = "application/json")
		public Integer totalNumberOfBranchs() {

			Integer total = 0;
			try {
				total = bradao.getBranchesCount();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return total;
		}*/
	
	/*// list of all the books available in a certain branch
		@Transactional
		@RequestMapping(value = "listOfBooksByBranchId/{branchId}", method = RequestMethod.GET, produces = "application/json")
		public List<Book> listOfBooksByBranchId(@PathVariable("branchId") int branchId) {
			List<Book> books = new ArrayList<>();
			try {
				books = bdao.getBooksFromBranchId(branchId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return books;
		}*/

}
