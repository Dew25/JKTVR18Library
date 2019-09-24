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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author melnikov
 */
public class App {
    List<Book> listBooks = new ArrayList<>();
    List<Reader> listReaders = new ArrayList<>();
    //Saver saver = new SaverToFile();
    Saver saver = new SaverToBase();
    List<History> listHistories = new ArrayList<>();
    
    public App() {
        listBooks.addAll(saver.loadListBooks());
        listReaders.addAll(saver.loadListReaders());
        listHistories.addAll(saver.loadListHistories());
    }
    
    public void run(){
        Scanner scanner = new Scanner(System.in);
        String operation = "0";
        boolean badOperation;
        HistoryProvider historyProvider = new HistoryProvider();
        do{
            do{
                System.out.println("Выберите операцию:");
                System.out.println("0. Выход");
                System.out.println("1. Добавить книгу");
                System.out.println("2. Добавить читателя");
                System.out.println("3. Выдать книгу");
                System.out.println("4. Вернуть книгу");
                System.out.println("5. Список книг");
                System.out.println("6. Список читателей");
                System.out.println("7. Список выданных книг");
                
                badOperation = false;
                operation = scanner.next();
                switch (operation) {
                    case "0":
                        badOperation = false;
                        break;
                    case "1":
                        BookProvider bookProvider = new BookProvider();
                        Book book = bookProvider.createBook();
                        if(book == null){
                            System.out.println("Книгу создать не удалось.");
                        }else{
                            listBooks.add(book);
                            saver.saveBooks(listBooks);
                            System.out.println("Добавлена новая книга.");
                        }
                        break;
                    case "2":
                        ReaderProvider readerProvider = new ReaderProvider();
                        Reader reader = readerProvider.createReader();
                        if(reader == null){
                            System.out.println("Читателя добавить не удалось.");
                        }else{
                            listReaders.add(reader);
                            saver.saveReaders(listReaders);
                            System.out.println("Добавлен новый читатель.");
                        }
                        break;
                    case "3":
                        History history = historyProvider.createHistory(listBooks, listReaders);
                        if(history == null){
                            System.out.println("Не удалось выдать книгу.");
                        }else{
                            listHistories.add(history);
                            saver.saveHistories(listHistories);
                            System.out.println("Книга выдана читателю.");
                        }
                        break; 
                    case "4":
                        historyProvider.returnBook(listHistories);
                        saver.saveHistories(listHistories);
                        break;
                    case "5":
                        System.out.println("---- Список книг ----");
                        for(Book b : listBooks){
                            System.out.println(b.toString());
                        }
                        break;
                    case "6":
                        System.out.println("---- Список читателей ----");
                        for(Reader r : listReaders){
                            System.out.println(r.toString());
                        }
                        break;
                    case "7":
                        System.out.println("---- Список выданных книг ----");
                        boolean flagOn = false;
                        for(History h : listHistories){
                            if(h.getReturnDate() == null){
                                System.out.printf("%d. Читатель %s %s читает \"%s%n"
                                        ,h.getId()
                                        ,h.getReader().getName()
                                        ,h.getReader().getLastname()
                                        ,h.getBook().getTitle()
                                );
                                flagOn = true;
                            }
                            
                        }
                        if(!flagOn){
                            System.out.println("Нет выданных на руки книг");
                        }
                        break;
                    default:
                        System.out.println("Неправильная операция!");
                        System.out.println("Выберите правильную операцию");
                        badOperation = true;
                }
            }while(badOperation);
            if(operation.equals("0"))break;
            
        }while(true);
    }
}
