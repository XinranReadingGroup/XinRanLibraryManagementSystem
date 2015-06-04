// package com.xinran.business.impl;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import javax.servlet.http.HttpServletRequest;
//
// import org.apache.commons.collections.CollectionUtils;
// import org.springframework.beans.BeanUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// import com.xinran.business.BookBusinesssService;
// import com.xinran.constant.BookType;
// import com.xinran.constant.BorrowStatus;
// import com.xinran.constant.ExceptionCode;
// import com.xinran.controller.util.UserIdenetityUtil;
// import com.xinran.event.Event;
// import com.xinran.event.impl.BookOnStockEvent;
// import com.xinran.event.util.EventListenerSupport;
// import com.xinran.exception.BorrowOrReturnValidationException;
// import com.xinran.exception.StockException;
// import com.xinran.pojo.Book;
// import com.xinran.pojo.BookLocation;
// import com.xinran.pojo.BorrowReturnRecord;
// import com.xinran.pojo.OnOffStockRecord;
// import com.xinran.pojo.Pagination;
// import com.xinran.pojo.User;
// import com.xinran.service.BookLocationService;
// import com.xinran.service.BookService;
// import com.xinran.service.BorrowReturnRecordService;
// import com.xinran.service.OnOffStockRecordService;
// import com.xinran.service.UserService;
// import com.xinran.util.DateUtil;
// import com.xinran.util.ThreadLocalUtil;
// import com.xinran.vo.BasicUserVO;
// import com.xinran.vo.BookDetail;
// import com.xinran.vo.BookLocationVO;
//
// /**
// * @author 高海军 帝奇 Jun 4, 2015 7:14:59 PM
// */
// @Service
// public class BookBusinesssServiceImpl implements BookBusinesssService {
//
// /*
// * (non-Javadoc)
// * @see com.xinran.business.BookBusinesssService#onStock(java.lang.Long, java.lang.Long, java.lang.String,
// * javax.servlet.http.HttpServletRequest, com.xinran.constant.BookType)
// */
// @Override
// public OnOffStockRecord onStock(Long bookId, Long location, String phone, HttpServletRequest request,
// BookType bookType) throws StockException {
// // TODO Auto-generated method stub
// return null;
// }
//
// /*
// * (non-Javadoc)
// * @see com.xinran.business.BookBusinesssService#borrowBook(com.xinran.pojo.OnOffStockRecord, java.lang.Long)
// */
// @Override
// public void borrowBook(OnOffStockRecord onOffStockRecord, Long currentUserId) {
// // TODO Auto-generated method stub
//
// }
//
// /*
// * (non-Javadoc)
// * @see com.xinran.business.BookBusinesssService#returnBook(java.lang.Long, java.lang.Long)
// */
// @Override
// public void returnBook(Long onStockId, Long currentUserId) throws BorrowOrReturnValidationException {
// // TODO Auto-generated method stub
//
// }
//
// /*
// * (non-Javadoc)
// * @see com.xinran.business.BookBusinesssService#buildBookDetail(java.lang.Long)
// */
// @Override
// public BookDetail buildBookDetail(Long onOffStockId) {
// // TODO Auto-generated method stub
// return null;
// }
//
//
// }
