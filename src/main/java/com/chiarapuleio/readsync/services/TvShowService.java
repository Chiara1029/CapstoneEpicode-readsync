package com.chiarapuleio.readsync.services;

import com.chiarapuleio.readsync.entities.Book;
import com.chiarapuleio.readsync.entities.TvShow;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.TvShowDTO;
import com.chiarapuleio.readsync.repositories.BookDAO;
import com.chiarapuleio.readsync.repositories.TvShowDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {
    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private BookDAO bookDAO;

    public List<TvShow> getAllTvShows(){
        return tvShowDAO.findAll();
    }

    public TvShow saveTvShow(TvShowDTO newTvShow){
        Book book = bookDAO.findByIsbnCode(newTvShow.bookIsbn()).orElseThrow(()-> new NotFoundException(newTvShow.bookIsbn()));
        TvShow tvShow = new TvShow(newTvShow.title(), newTvShow.year(), newTvShow.plot(), newTvShow.poster(), newTvShow.genre(), book, newTvShow.seasons(), newTvShow.network());
        return tvShowDAO.save(tvShow);
    }

    public void delete(Long tvShowId){
        TvShow tvShow = tvShowDAO.findById(tvShowId).orElseThrow(()-> new NotFoundException(String.valueOf(tvShowId)));
        tvShowDAO.delete(tvShow);
    }

    public TvShow findByIdAndUpdate(Long tvShowId, TvShowDTO updatedTvShow){
        TvShow foundTvShow = tvShowDAO.findById(tvShowId).orElseThrow(() -> new NotFoundException(String.valueOf(tvShowId)));
        Book book = bookDAO.findByIsbnCode(updatedTvShow.bookIsbn()).orElseThrow(()-> new NotFoundException(updatedTvShow.bookIsbn()));
        foundTvShow.setTitle(updatedTvShow.title());
        foundTvShow.setYear(updatedTvShow.year());
        foundTvShow.setPlot(updatedTvShow.plot());
        foundTvShow.setPoster(updatedTvShow.poster());
        foundTvShow.setBook(book);
        foundTvShow.setSeasons(updatedTvShow.seasons());
        foundTvShow.setNetwork(updatedTvShow.network());
        return tvShowDAO.save(foundTvShow);
    }

    public List<TvShow> getTvShowsByBookIsbn(String isbnCode){
        return tvShowDAO.findByBookIsbnCode(isbnCode);
    }
}
