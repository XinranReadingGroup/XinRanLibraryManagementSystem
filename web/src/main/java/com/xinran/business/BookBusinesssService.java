// package com.xinran.business;
//
// import javax.servlet.http.HttpServletRequest;
//
// import com.xinran.constant.BookType;
// import com.xinran.exception.BorrowOrReturnValidationException;
// import com.xinran.exception.StockException;
// import com.xinran.pojo.OnOffStockRecord;
// import com.xinran.vo.BookDetail;
//
// /**
// * @author 高海军 帝奇 Jun 4, 2015 7:14:59 PM
// */
// public interface BookBusinesssService {
//
// public OnOffStockRecord onStock(Long bookId, Long location, String phone, HttpServletRequest request,
// BookType bookType) throws StockException;
//
// public void borrowBook(OnOffStockRecord onOffStockRecord, Long currentUserId);
//
// public void returnBook(Long onStockId, Long currentUserId) throws BorrowOrReturnValidationException;
//
// public BookDetail buildBookDetail(Long onOffStockId);
//
// }
