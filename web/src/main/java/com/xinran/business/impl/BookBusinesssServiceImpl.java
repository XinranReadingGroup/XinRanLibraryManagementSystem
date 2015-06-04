package com.xinran.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.constant.BookType;
import com.xinran.constant.BorrowStatus;
import com.xinran.constant.ExceptionCode;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.event.Event;
import com.xinran.event.impl.BookOnStockEvent;
import com.xinran.event.util.EventListenerSupport;
import com.xinran.exception.BorrowOrReturnValidationException;
import com.xinran.exception.StockException;
import com.xinran.pojo.Book;
import com.xinran.pojo.BookLocation;
import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Pagination;
import com.xinran.pojo.User;
import com.xinran.service.BookLocationService;
import com.xinran.service.BookService;
import com.xinran.service.BorrowReturnRecordService;
import com.xinran.service.OnOffStockRecordService;
import com.xinran.service.UserService;
import com.xinran.util.DateUtil;
import com.xinran.util.ThreadLocalUtil;
import com.xinran.vo.BasicUserVO;
import com.xinran.vo.BookDetail;
import com.xinran.vo.BookLocationVO;

/**
 * @author 高海军 帝奇 Jun 4, 2015 7:14:59 PM
 */
@Service
public class BookBusinesssServiceImpl {

    @Autowired
    protected BookService               bookService;

    @Autowired
    protected UserService               userService;

    @Autowired
    protected BookLocationService       bookLocationService;

    @Autowired
    protected OnOffStockRecordService   onOffStockRecordService;

    @Autowired
    protected BorrowReturnRecordService borrowReturnRecordService;

    public OnOffStockRecord onStock(Long bookId, Long location, String phone, HttpServletRequest request,
                                     BookType bookType) throws StockException {
        OnOffStockRecord record = new OnOffStockRecord();
        record.setOwnerUserId(UserIdenetityUtil.getCurrentUserId(request));
        record.setOwnerPhone(phone);
        record.setBookType(bookType.getType());
        record.setLocation(location);
        record.setBookId(bookId);
        record.setBorrowStatus(BorrowStatus.UNBORROWED.getStatus());
        record = onOffStockRecordService.onStock(record);

        try {
            ThreadLocalUtil.set(record);
            Event event = new BookOnStockEvent();
            EventListenerSupport.fireEvent(event);
        } finally {
            ThreadLocalUtil.remove(record);
        }

        return record;

    }

    public void borrowBook(OnOffStockRecord onOffStockRecord, Long currentUserId) {
        BorrowReturnRecord borrowReturnRecord = new BorrowReturnRecord();
        borrowReturnRecord.setBookId(onOffStockRecord.getBookId());
        borrowReturnRecord.setBookType(onOffStockRecord.getBookType());
        borrowReturnRecord.setBorrowStatus(BorrowStatus.BORROWED.getStatus());
        borrowReturnRecord.setBorrowUserId(currentUserId);
        borrowReturnRecord.setBorrowDate(DateUtil.getCurrentDate());
        borrowReturnRecord.setOnOffStockId(onOffStockRecord.getId());
        borrowReturnRecord.setOwnerUserId(onOffStockRecord.getOwnerUserId());
        borrowReturnRecordService.insert(borrowReturnRecord);

        onOffStockRecord.setBorrowId(borrowReturnRecord.getId());
        onOffStockRecord.setBorrowUserId(currentUserId);
        onOffStockRecord.setBorrowStatus(BorrowStatus.BORROWED.getStatus());
        onOffStockRecordService.updateOnOffStockRecord(onOffStockRecord);
    }

    public void returnBook(Long onStockId, Long currentUserId) throws BorrowOrReturnValidationException {
        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(onStockId);

        if (null == onOffStockRecord) {
            throw new BorrowOrReturnValidationException(ExceptionCode.InvalidOnOffStockId.getCode());
        } else {

            Integer borrowStatus = onOffStockRecord.getBorrowStatus();
            if (BorrowStatus.BORROWED.getStatus() != borrowStatus) {
                throw new BorrowOrReturnValidationException(ExceptionCode.TheBookShouldBeInBorrowedStatus.getCode());

            }

            if (!onOffStockRecord.getBorrowUserId().equals(currentUserId)) {
                throw new BorrowOrReturnValidationException(
                                                            ExceptionCode.TheBookYouReturnedShouldBeBorrowedByYou.getCode());

            }

            BorrowReturnRecord borrowReturnRecord = borrowReturnRecordService.findBorrowReturnRecordById(onOffStockRecord.getBorrowId());
            borrowReturnRecord.setBorrowStatus(BorrowStatus.UNBORROWED.getStatus());
            borrowReturnRecord.setReturnDate(DateUtil.getCurrentDate());

            borrowReturnRecordService.updateBorrowReturnRecord(borrowReturnRecord);

            onOffStockRecord.setBorrowStatus(BorrowStatus.UNBORROWED.getStatus());
            onOffStockRecord.setBorrowId(null);
            onOffStockRecord.setBorrowUserId(null);
            onOffStockRecordService.updateOnOffStockRecord(onOffStockRecord);
        }
    }

    public BookDetail buildBookDetail(Long onOffStockId) {

        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(onOffStockId);
        Book book = bookService.findBookById(onOffStockRecord.getBookId());

        Long bookLocationId = onOffStockRecord.getLocation();
        BookLocation bookLocation = bookLocationService.findBookLocationById(bookLocationId);
        BookLocationVO bookLocationVO = new BookLocationVO();
        if (null != bookLocationVO) {
            BeanUtils.copyProperties(bookLocation, bookLocationVO);
        }

        Long ownerUserId = onOffStockRecord.getOwnerUserId();
        BasicUserVO basicUserVO = buildBasicUserVO(ownerUserId);

        Pagination pagination = new Pagination();
        List<BorrowReturnRecord> borrowReturnRecordList = borrowReturnRecordService.findHistroicBorrowedBooks(onOffStockRecord.getId(),
                                                                                                              pagination);
        List<BasicUserVO> basicUserVOList = null;
        if (CollectionUtils.isNotEmpty(borrowReturnRecordList)) {
            basicUserVOList = new ArrayList<BasicUserVO>();
            for (BorrowReturnRecord borrowReturnRecord : borrowReturnRecordList) {
                Long borrowUserId = borrowReturnRecord.getBorrowUserId();
                BasicUserVO basicUserVO1 = buildBasicUserVO(borrowUserId);
                basicUserVOList.add(basicUserVO1);
            }
        }

        BookDetail bookDetail = new BookDetail();
        bookDetail.setBook(book);
        bookDetail.setOnOffStockRecord(onOffStockRecord);
        bookDetail.setOwnerUserVO(basicUserVO);
        bookDetail.setBookLocationVO(bookLocationVO);
        bookDetail.setHistroicBorrowedRecordList(basicUserVOList);
        return bookDetail;
    }

    private BasicUserVO buildBasicUserVO(Long ownerUserId) {
        User user = userService.findUserByUserId(ownerUserId);
        BasicUserVO basicUserVO = new BasicUserVO();
        BeanUtils.copyProperties(user, basicUserVO);
        return basicUserVO;
    }

}
