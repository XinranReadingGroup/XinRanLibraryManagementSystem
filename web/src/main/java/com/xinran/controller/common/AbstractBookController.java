package com.xinran.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinran.business.BookBusinesssService;
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
    protected BookBusinesssService      bookBusinesssService;

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
            onStock = bookBusinesssService.onStock(bookId, location, phone, request, BookType.DONATED);
        } catch (StockException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getCode());
        }
        return AjaxResultBuilder.buildSuccessfulResult(onStock);
    }

    @RequestMapping("/book/share/{bookId}")
    public @ResponseBody AjaxResult share(@PathVariable(value = "bookId") Long bookId,
                                          @RequestParam("location") Long location,
                                          HttpServletRequest request) {
        OnOffStockRecord onStock = null;
        try {
            onStock = bookBusinesssService.onStock(bookId, location, null, request, BookType.SHARED);
        } catch (StockException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getCode());
        }
        return AjaxResultBuilder.buildSuccessfulResult(onStock);
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

            Long currentUserId = UserIdenetityUtil.getCurrentUserId(request);
            bookBusinesssService.borrowBook(onOffStockRecord, currentUserId);

        }

        return AjaxResultBuilder.buildSuccessfulResult(null);
    }



    @RequestMapping("/book/return/{onStockId}")
    public @ResponseBody AjaxResult returnBook(@PathVariable(value = "onStockId") Long onStockId,
                                               HttpServletRequest request) throws BorrowOrReturnValidationException {
        Long currentUserId = UserIdenetityUtil.getCurrentUserId(request);
        bookBusinesssService.returnBook(onStockId, currentUserId);

        return AjaxResultBuilder.buildSuccessfulResult(null);
    }



    @RequestMapping("/book/donate/records")
    public @ResponseBody AjaxResult getDonatedRecords(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                      HttpServletRequest request) {
        Long userId = UserIdenetityUtil.getCurrentUserId(request);
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
            List<BookDetail> bookDetailList = fillBookInfo(records);
            return AjaxResultBuilder.buildSuccessfulResult(bookDetailList);
        }
        return null;
    }

    @RequestMapping("/book/share/records")
    public @ResponseBody AjaxResult getSharedRecords(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                     HttpServletRequest request) {
        Long userId = UserIdenetityUtil.getCurrentUserId(request);
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
            List<BookDetail> bookDetailList = fillBookInfo(records);
            return AjaxResultBuilder.buildSuccessfulResult(bookDetailList);

        }
        return null;
    }

    @RequestMapping("/book/borrow/records")
    public @ResponseBody AjaxResult getBorrowedRecords(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                     HttpServletRequest request) {
        Long userId = UserIdenetityUtil.getCurrentUserId(request);
        List<BookDetail> bookDetailList = new ArrayList<BookDetail>();
        if (userId != null) {
            Pagination page = new Pagination();
            if (pageNo != null && pageNo >= 0) {
                page.setCurrent(pageNo);
            }
            if (pageSize != null && pageSize > 0) {
                page.setSize(pageSize);
            }
            List<BorrowReturnRecord> records = borrowReturnRecordService.findBorrowedBooks(userId, page);
            if (CollectionUtils.isNotEmpty(records)) {
                for (BorrowReturnRecord borrowReturnRecord : records) {
                    BookDetail bookDetail = bookBusinesssService.buildBookDetail(borrowReturnRecord.getOnOffStockId());
                    bookDetailList.add(bookDetail);
                }
            }
        }
        return AjaxResultBuilder.buildSuccessfulResult(bookDetailList);
    }

    @RequestMapping("/book/return/records")
    public @ResponseBody AjaxResult getReturnedRecords(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                       HttpServletRequest request) {
        Long userId = UserIdenetityUtil.getCurrentUserId(request);
        List<BookDetail> bookDetailList = new ArrayList<BookDetail>();
        if (userId != null) {
            Pagination page = new Pagination();
            if (pageNo != null && pageNo >= 0) {
                page.setCurrent(pageNo);
            }
            if (pageSize != null && pageSize > 0) {
                page.setSize(pageSize);
            }
            List<BorrowReturnRecord> records = borrowReturnRecordService.findReturnedBooks(userId, page);
            if (CollectionUtils.isNotEmpty(records)) {
                for (BorrowReturnRecord borrowReturnRecord : records) {
                    BookDetail bookDetail = bookBusinesssService.buildBookDetail(borrowReturnRecord.getOnOffStockId());
                    bookDetailList.add(bookDetail);
                }
            }
        }
        return AjaxResultBuilder.buildSuccessfulResult(bookDetailList);
    }


    private List<BookDetail> fillBookInfo(List<OnOffStockRecord> records) {

        List<BookDetail> bookDetailList = new ArrayList<>();
        for (OnOffStockRecord record : records) {
            // TODO 使用缓存以避免每次查询数据库。以ID查询书本信息在很多场景会使用。
            BookDetail bookDetail = bookBusinesssService.buildBookDetail(record.getId());
            bookDetailList.add(bookDetail);
        }
        return bookDetailList;
    }



}
