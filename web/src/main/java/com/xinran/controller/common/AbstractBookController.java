package com.xinran.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.xinran.vo.AjaxResult;
import com.xinran.vo.BasicUserVO;
import com.xinran.vo.BookDetail;
import com.xinran.vo.BookLocationVO;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author zhuangyao.zy
 */
public class AbstractBookController {

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



    @RequestMapping("/book/donate/{bookId}")
    public @ResponseBody AjaxResult donate(@PathVariable(value = "bookId") Long bookId,
                                           @RequestParam("location") Long location,
                                           @RequestParam(value = "phone", required = false) String phone,
                                           HttpServletRequest request) {
        OnOffStockRecord onStock = null;
        try {
            onStock = onStock(bookId, location, phone, request, BookType.DONATED);
        } catch (StockException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getCode());
        }
        return AjaxResultBuilder.buildSuccessfulResult(onStock);
    }

    @RequestMapping("/book/share/{bookId}")
    public @ResponseBody AjaxResult share(@PathVariable(value = "bookId") Long bookId,
                                          @RequestParam("location") Long location, @RequestParam("phone") String phone,
                                          HttpServletRequest request) {
        OnOffStockRecord onStock = null;
        try {
            onStock = onStock(bookId, location, phone, request, BookType.SHARED);
        } catch (StockException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getCode());
        }
        return AjaxResultBuilder.buildSuccessfulResult(onStock);
    }

    private OnOffStockRecord onStock(Long bookId, Long location, String phone, HttpServletRequest request,
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

    @RequestMapping("/book/borrow/{onStockId}")
    public @ResponseBody AjaxResult borrow(@PathVariable(value = "onStockId") Long onStockId, HttpServletRequest request)
                                                                                                                         throws BorrowOrReturnValidationException {
        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(onStockId);
        if (null == onOffStockRecord) {
            throw new BorrowOrReturnValidationException(ExceptionCode.InvalidOnOffStockId.getCode());
        } else {
            Integer borrowStatus = onOffStockRecord.getBorrowStatus();
            if (BorrowStatus.UNBORROWED.getStatus() != borrowStatus) {
                throw new BorrowOrReturnValidationException(ExceptionCode.TheBookHasBeenBorrowed.getCode());

            }

            BorrowReturnRecord borrowReturnRecord = new BorrowReturnRecord();
            borrowReturnRecord.setBookId(onOffStockRecord.getBookId());
            borrowReturnRecord.setBookType(onOffStockRecord.getBookType());
            borrowReturnRecord.setBorrowStatus(BorrowStatus.BORROWED.getStatus());
            borrowReturnRecord.setBorrowUserId(UserIdenetityUtil.getCurrentUserId(request));
            borrowReturnRecord.setBorrowDate(DateUtil.getCurrentDate());
            borrowReturnRecord.setOnOffStockId(onOffStockRecord.getId());
            borrowReturnRecord.setOwnerUserId(onOffStockRecord.getOwnerUserId());
            borrowReturnRecordService.insert(borrowReturnRecord);

            onOffStockRecord.setBorrowId(borrowReturnRecord.getId());
            onOffStockRecordService.updateOnOffStockRecord(onOffStockRecord);

        }

        return AjaxResultBuilder.buildSuccessfulResult(null);
    }

    @RequestMapping("/book/return/{onStockId}")
    public @ResponseBody AjaxResult returnBook(@PathVariable(value = "onStockId") Long onStockId,
                                               HttpServletRequest request) throws BorrowOrReturnValidationException {
        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(onStockId);
        if (null == onOffStockRecord) {
            throw new BorrowOrReturnValidationException(ExceptionCode.InvalidOnOffStockId.getCode());
        } else {

            Integer borrowStatus = onOffStockRecord.getBorrowStatus();
            if (BorrowStatus.BORROWED.getStatus() != borrowStatus) {
                throw new BorrowOrReturnValidationException(ExceptionCode.TheBookShouldBeInBorrowedStatus.getCode());

            }

            Long currentUserId = UserIdenetityUtil.getCurrentUserId(request);
            if (!onOffStockRecord.getBorrowUserId().equals(currentUserId)) {
                throw new BorrowOrReturnValidationException(
                                                            ExceptionCode.TheBookYouReturnedShouldBeBorrowedByYou.getCode());

            }

            BorrowReturnRecord borrowReturnRecord = borrowReturnRecordService.findBorrowReturnRecordById(onOffStockRecord.getBorrowId());
            borrowReturnRecord.setBorrowStatus(BorrowStatus.RETURNED.getStatus());
            borrowReturnRecord.setReturnDate(DateUtil.getCurrentDate());

            borrowReturnRecordService.updateBorrowReturnRecord(borrowReturnRecord);
        }

        return AjaxResultBuilder.buildSuccessfulResult(null);
    }

    @RequestMapping("/book/donate/records")
    public @ResponseBody AjaxResult getDonatedRecords(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                      HttpServletRequest request) {
        Long userId = UserIdenetityUtil.getCurrentUserId(request);
        userId = 1L;
        List<OnOffStockRecord> records = null;
        if (userId != null) {
            Pagination page = new Pagination();
            if (pageNo != null && pageNo >= 0) {
                page.setCurrent(pageNo);
            }
            if (pageSize != null && pageSize > 0) {
                page.setSize(pageSize);
            }
            records = onOffStockRecordService.findDonated(userId, page);
            fillBookInfo(records);
        }
        return AjaxResultBuilder.buildSuccessfulResult(records);
    }

    @RequestMapping("/book/share/records")
    public @ResponseBody AjaxResult getSharedRecords(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                     HttpServletRequest request) {
        Long userId = UserIdenetityUtil.getCurrentUserId(request);
        userId = 1L;
        List<OnOffStockRecord> records = null;
        if (userId != null) {
            Pagination page = new Pagination();
            if (pageNo != null && pageNo >= 0) {
                page.setCurrent(pageNo);
            }
            if (pageSize != null && pageSize > 0) {
                page.setSize(pageSize);
            }
            records = onOffStockRecordService.findShared(userId, page);
            fillBookInfo(records);
        }
        return AjaxResultBuilder.buildSuccessfulResult(records);
    }

    protected BookDetail buildBookDetail(Long id) {

        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(id);
        Book book = bookService.findBookById(onOffStockRecord.getBookId());



        Long bookLocationId = onOffStockRecord.getLocation();
        BookLocation bookLocation = bookLocationService.findBookLocationById(bookLocationId);
        BookLocationVO bookLocationVO = new BookLocationVO();
        BeanUtils.copyProperties(bookLocation, bookLocationVO);

        Long ownerUserId = onOffStockRecord.getOwnerUserId();
        User user = userService.findUserByUserId(ownerUserId);
        BasicUserVO basicUserVO = new BasicUserVO();
        BeanUtils.copyProperties(user, basicUserVO);

        BookDetail bookDetail = new BookDetail();
        bookDetail.setBook(book);
        bookDetail.setOnOffStockRecord(onOffStockRecord);
        bookDetail.setOwnerUserVO(basicUserVO);
        bookDetail.setBookLocationVO(bookLocationVO);

        return bookDetail;
    }

    private void fillBookInfo(List<OnOffStockRecord> records) {
        if (records == null || records.size() < 1) {
            return;
        }
        for (OnOffStockRecord record : records) {
            // TODO 使用缓存以避免每次查询数据库。以ID查询书本信息在很多场景会使用。
            record.setBook(bookService.findBookById(record.getBookId()));
        }
    }

}
