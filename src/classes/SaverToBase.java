/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import entity.Book;
import entity.History;
import entity.Reader;
import interfaces.Saver;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author melnikov
 */
public class SaverToBase implements Saver{
    EntityManager em;
    EntityTransaction tx;

    public SaverToBase() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JKTVR18LibraryPU");
        this.em = emf.createEntityManager();
        this.tx = em.getTransaction();
    }

    
    @Override
    public void saveBooks(List<Book> listBooks) {
        for(Book book : listBooks){
            tx.begin();
                em.persist(book);
            tx.commit();
        }
    }

    @Override
    public void saveReaders(List<Reader> listReaders) {
        for(Reader reader : listReaders){
            tx.begin();
                em.persist(reader);
            tx.commit();
        }
    }

    @Override
    public void saveHistories(List<History> listHistories) {
        tx.begin();
        for(History h : listHistories){
            if(h.getId() == null){
                
                em.persist(h);
            }
            
        }
        tx.commit();
        
        
//        listHistories.clear();
//        listHistories.addAll(this.loadListHistories());
    }

    @Override
    public List<Book> loadListBooks() {
        return em.createQuery("SELECT b FROM Book b")
                .getResultList();
    }

    @Override
    public List<Reader> loadListReaders() {
        return em.createQuery("SELECT r FROM Reader r")
                .getResultList();
    }

    @Override
    public List<History> loadListHistories() {
        return em.createQuery("SELECT h FROM History h")
                .getResultList();
    }
    
}
