package com.chiarapuleio.readsync.services;

import com.chiarapuleio.readsync.entities.Book;
import com.chiarapuleio.readsync.entities.Movie;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.MovieDTO;
import com.chiarapuleio.readsync.repositories.BookDAO;
import com.chiarapuleio.readsync.repositories.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieDAO movieDAO;
    @Autowired
    private BookDAO bookDAO;

    public List<Movie> getAllMovies(){
        return movieDAO.findAll();
    }

    public Movie saveMovie(MovieDTO newMovie){
        Book book = bookDAO.findByIsbnCode(newMovie.bookIsbn()).orElseThrow(()-> new NotFoundException(newMovie.bookIsbn()));
        Movie movie = new Movie(newMovie.title(), newMovie.year(), newMovie.plot(), newMovie.poster(), newMovie.genre(), book, newMovie.director(), newMovie.distributor());
        return movieDAO.save(movie);
    }

    public void delete(Long movieId){
        Movie movie = movieDAO.findById(movieId).orElseThrow(()-> new NotFoundException(String.valueOf(movieId)));
        movieDAO.delete(movie);
    }

    public Movie findByIdAndUpdate(Long movieId, MovieDTO updatedMovie){
        Movie foundMovie = movieDAO.findById(movieId).orElseThrow(()-> new NotFoundException(String.valueOf(movieId)));
        Book book = bookDAO.findByIsbnCode(updatedMovie.bookIsbn()).orElseThrow(()-> new NotFoundException(updatedMovie.bookIsbn()));
        foundMovie.setTitle(updatedMovie.title());
        foundMovie.setYear(updatedMovie.year());
        foundMovie.setPlot(updatedMovie.plot());
        foundMovie.setPoster(updatedMovie.poster());
        foundMovie.setGenre(updatedMovie.genre());
        foundMovie.setBook(book);
        foundMovie.setDirector(updatedMovie.director());
        foundMovie.setDirector(updatedMovie.director());
        return movieDAO.save(foundMovie);
    }

    public List<Movie> getMoviesByBookIsbn(String isbnCode){
        return movieDAO.findByBookIsbnCode(isbnCode);
    }
}
