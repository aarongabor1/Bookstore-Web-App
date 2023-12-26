package com.example.service;

import com.example.model.Book;
import com.example.model.User;
import com.example.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Set<Book> recommendBooks(User currentUser){
        List<User> allUsers = purchaseRepository.findAllUsersExcept(currentUser);
        User mostSimilarUser = findMostSimilarUser(currentUser, allUsers);
        return getBooksToRecommend(currentUser, mostSimilarUser);
    }

    private Set<Book> getBooksToRecommend(User currentUser, User similarUser) {
        if(similarUser == null){
            return new HashSet<>();
        }
        Set<Book> currentUserBooks = purchaseRepository.findBooksByUser(currentUser);
        Set<Book> similarUserBooks = purchaseRepository.findBooksByUser(similarUser);

        return similarUserBooks.stream()
                .filter(book -> !currentUserBooks.contains(book))
                .collect(Collectors.toSet());
    }

    private User findMostSimilarUser(User currentUser, List<User> allUsers) {
        double maxSimilar = 0;
        User mostSimilarUser = null;

        for (User user : allUsers) {
            double similarity = calculateJaccardSimilarity(currentUser, user);
            if(similarity > maxSimilar) {
                maxSimilar = similarity;
                mostSimilarUser = user;
            }
        }
        return mostSimilarUser;
    }

    private double calculateJaccardSimilarity(User currentUser, User user) {
        Set<Book> u1Books = purchaseRepository.findBooksByUser(currentUser);
        Set<Book> u2Books = purchaseRepository.findBooksByUser(user);

        Set<Book> intersection = new HashSet<>(u1Books);
        intersection.retainAll(u2Books);

        Set<Book> union = new HashSet<>(u1Books);
        union.addAll(u2Books);

        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }
}
