package com.bits.af.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bits.af.entities.Book;
import com.bits.af.pojo.BookingRequest;
import com.bits.af.repository.BookRepository;

@RestController
@RequestMapping("/bookings")
public class BookController {
    @Autowired
    private BookRepository bookRepo;
       
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Book>> listAll() {
        List<Book> bookingList= bookRepo.findAll(); 
        return ResponseEntity.ok(bookingList);
    }
    
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Book> listById(@PathVariable Integer id) {
        Optional<Book> booking= bookRepo.findById(id); 
      
        if(booking.isPresent())
        	return ResponseEntity.ok(booking.get());
        else
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity addBooking(@RequestBody BookingRequest bookingRequest) throws Exception {
        
    	Book book = new Book();
    	BeanUtils.copyProperties(bookingRequest, book);
    	try {
    		book = bookRepo.save(book);
            return new ResponseEntity<>("Booking successful", HttpStatus.CREATED);
    	}
        catch(Exception e) {
        	throw new Exception("Could not process booking " + e.getMessage());
        }
    }
       
}