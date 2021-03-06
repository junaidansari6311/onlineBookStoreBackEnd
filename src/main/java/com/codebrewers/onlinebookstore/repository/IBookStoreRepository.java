package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBookStoreRepository extends JpaRepository<BookDetails, Integer> {
    Optional<BookDetails> findByIsbn(String Isbn);

    Optional<BookDetails> findByBookNameAndAuthorName(String bookName, String authorName);

    @Query(value = "select * from book_details where author_name LIKE %:searchText% OR book_name LIKE %:searchText%", nativeQuery = true)
    List<BookDetails> findSortedBooks(@Param("searchText") String searchText);

    @Transactional
    @Modifying
    @Query(value = "update book_details set quantity= quantity - :quantity where id = :bid ", nativeQuery = true)
    int updateBookQuantity(@Param("bid") Integer cid,@Param("quantity") Integer quantity);
}
