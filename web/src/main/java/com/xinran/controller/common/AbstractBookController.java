package com.xinran.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xinran.event.impl.BorrowBookEvent;
import com.xinran.event.impl.ReturnBookEvent;
import com.xinran.pojo.*;
import com.xinran.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinran.constant.BookType;
import com.xinran.constant.BorrowStatus;
import com.xinran.constant.SystemResultCode;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.event.Event;
import com.xinran.event.impl.BookOnStockEvent;
import com.xinran.event.util.EventListenerSupport;
import com.xinran.exception.BorrowOrReturnValidationException;
import com.xinran.exception.StockException;
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

//    @Autowired
//    protected this      this;

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

    @Autowired
    protected QRCodeService qrCodeService;

    @RequestMapping("/book/isbn/{isbn}")
    public @ResponseBody AjaxResult getBookByISBN(@PathVariable(value = "isbn") String isbn, HttpServletRequest request) {
        Book book = bookService.findBookByISBN(isbn);
        if(null == book){
            return AjaxResultBuilder.buildFailedResult(404, "cant find the book by the isbn :" +isbn);
        } else {
            return AjaxResultBuilder.buildSuccessfulResult(book);
        }
    }

    @RequestMapping("/book/donate/{bookId}")
    public @ResponseBody AjaxResult donate(@PathVariable(value = "bookId") Long bookId,
                                           @RequestParam("locationId") Long locationId,
                                           @RequestParam(value = "qrCode") String qrCode,
                                           HttpServletRequest request) {
        OnOffStockRecord onStock = null;
        try {
            onStock = this.onStock(bookId, locationId, qrCode, request, BookType.DONATED);
        } catch (StockException e) {
            return AjaxResultBuilder.buildFailedResult(e);
        }
        return AjaxResultBuilder.buildSuccessfulResult(onStock);
    }

    @RequestMapping("/book/share/{bookId}")
    public @ResponseBody AjaxResult share(@PathVariable(value = "bookId") Long bookId,
                                          @RequestParam("locationId") Long locationId, @RequestParam(value = "qrCode") String qrCode,
                                          HttpServletRequest request) {
        OnOffStockRecord onStock = null;
        try {
            onStock = this.onStock(bookId, locationId, qrCode, request, BookType.SHARED);
        } catch (StockException e) {
            return AjaxResultBuilder.buildFailedResult(e);
        }
        return AjaxResultBuilder.buildSuccessfulResult(onStock);
    }

    @RequestMapping("/book/offstock/{onStockId}")
    public
    @ResponseBody
    AjaxResult offStock(@PathVariable(value = "onStockId") Long onStockId, HttpServletRequest request) {
        OnOffStockRecord record = new OnOffStockRecord();
        record.setId(onStockId);
        try {
            onOffStockRecordService.offStock(record);
        } catch (StockException e) {
            return AjaxResultBuilder.buildFailedResult(e);
        }
        return AjaxResultBuilder.buildSuccessfulResult(null);
    }


    @RequestMapping("/book/borrow/{onStockId}")
    public @ResponseBody AjaxResult borrow(@PathVariable(value = "onStockId") Long onStockId, HttpServletRequest request)
                                                                                                                         throws BorrowOrReturnValidationException {
        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(onStockId);
        if (null == onOffStockRecord) {
            throw new BorrowOrReturnValidationException(SystemResultCode.InvalidOnOffStockId);
        } else {
            Integer borrowStatus = onOffStockRecord.getBorrowStatus();
            if (BorrowStatus.UNBORROWED.getStatus() != borrowStatus) {
                throw new BorrowOrReturnValidationException(SystemResultCode.TheBookHasBeenBorrowed);
            }

            Long currentUserId = UserIdenetityUtil.getCurrentUserId(request);
            this.borrowBook(onOffStockRecord, currentUserId);

        }

        return AjaxResultBuilder.buildSuccessfulResult(null);
    }



    @RequestMapping("/book/return/{onStockId}")
    public @ResponseBody AjaxResult returnBook(@PathVariable(value = "onStockId") Long onStockId,
                                               HttpServletRequest request) throws BorrowOrReturnValidationException {
        Long currentUserId = UserIdenetityUtil.getCurrentUserId(request);
        try {
            this.returnBook(onStockId, currentUserId);
        } catch (BorrowOrReturnValidationException e) {
            return AjaxResultBuilder.buildFailedResult(e);
        }

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
                    BookDetail bookDetail = this.buildBookDetail(borrowReturnRecord.getOnOffStockId());
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
                    BookDetail bookDetail = this.buildBookDetail(borrowReturnRecord.getOnOffStockId());
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
            BookDetail bookDetail = this.buildBookDetail(record.getId());
            bookDetailList.add(bookDetail);
        }
        return bookDetailList;
    }

    //
    // @Autowired
    // protected BookService bookService;
    //
    // @Autowired
    // protected UserService userService;
    //
    // @Autowired
    // protected BookLocationService bookLocationService;
    //
    // @Autowired
    // protected OnOffStockRecordService onOffStockRecordService;
    //
    // @Autowired
    // protected BorrowReturnRecordService borrowReturnRecordService;

    public OnOffStockRecord onStock(Long bookId, Long location, String qrcodeContent, HttpServletRequest request,
                                    BookType bookType) throws StockException {
        QRCode qrCode =  qrCodeService.associate(qrcodeContent);


        OnOffStockRecord onOffStockRecord = new OnOffStockRecord();
        onOffStockRecord.setOwnerUserId(UserIdenetityUtil.getCurrentUserId(request));
        onOffStockRecord.setBookType(bookType.getType());
        onOffStockRecord.setLocation(location);
        onOffStockRecord.setBookId(bookId);
        onOffStockRecord.setBorrowStatus(BorrowStatus.UNBORROWED.getStatus());
        onOffStockRecord.setQrCodeId(qrCode.getId());
        onOffStockRecord = onOffStockRecordService.onStock(onOffStockRecord);



        Event event = new BookOnStockEvent(onOffStockRecord);
        EventListenerSupport.fireEvent(event);


        return onOffStockRecord;

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

        Event event = new BorrowBookEvent(borrowReturnRecord);
        EventListenerSupport.fireEvent(event);

    }

    public void returnBook(Long onStockId, Long currentUserId) throws BorrowOrReturnValidationException {
        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(onStockId);

        if (null == onOffStockRecord) {
            throw new BorrowOrReturnValidationException(SystemResultCode.InvalidOnOffStockId);
        } else {

            Integer borrowStatus = onOffStockRecord.getBorrowStatus();
            if (BorrowStatus.BORROWED.getStatus() != borrowStatus) {
                throw new BorrowOrReturnValidationException(SystemResultCode.TheBookShouldBeInBorrowedStatus);

            }

            if (!onOffStockRecord.getBorrowUserId().equals(currentUserId)) {
                throw new BorrowOrReturnValidationException(
                                                            SystemResultCode.TheBookYouReturnedShouldBeBorrowedByYou );

            }

            BorrowReturnRecord borrowReturnRecord = borrowReturnRecordService.findBorrowReturnRecordById(onOffStockRecord.getBorrowId());
            borrowReturnRecord.setBorrowStatus(BorrowStatus.UNBORROWED.getStatus());
            borrowReturnRecord.setReturnDate(DateUtil.getCurrentDate());

            borrowReturnRecordService.updateBorrowReturnRecord(borrowReturnRecord);

            onOffStockRecord.setBorrowStatus(BorrowStatus.UNBORROWED.getStatus());
            onOffStockRecord.setBorrowId(null);
            onOffStockRecord.setBorrowUserId(null);
            onOffStockRecordService.updateOnOffStockRecord(onOffStockRecord);

            Event event = new ReturnBookEvent(borrowReturnRecord);
            EventListenerSupport.fireEvent(event);
        }
    }

    public BookDetail buildBookDetail(Long onOffStockId) {

        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(onOffStockId);
        
        if(onOffStockRecord ==null){
            return new BookDetail();
        }
        
        Book book = bookService.findBookById(onOffStockRecord.getBookId());

        Long bookLocationId = onOffStockRecord.getLocation();
        BookLocation bookLocation = bookLocationService.findBookLocationById(bookLocationId);
        BookLocationVO bookLocationVO = new BookLocationVO();
        if (null != bookLocation) {
//            bookLocationVO.setProvince(AbstractBookLocationController.getMap().get(bookLocation.getProvince()));
//            bookLocationVO.setCity(AbstractBookLocationController.getMap().get(bookLocation.getCity()));
//            bookLocationVO.setCounty(AbstractBookLocationController.getMap().get(bookLocation.getCity()));
            bookLocationVO.setDetail(bookLocation.getDetail());
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
        if (null != user) {
            BeanUtils.copyProperties(user, basicUserVO);
        }
        return basicUserVO;
    }
    
  

	protected List<BookDetail> buildSearchResult(String query) {
		boolean isIsbn=   NumberUtils.isNumber(query);
		    List<OnOffStockRecord> onOffStockRecordList = null;

		  if(isIsbn){
		        // 精确匹配
		        Book book = bookService.findBookByISBN(query);
		        onOffStockRecordList = onOffStockRecordService.findOnOffStockRecordByBookIds(book.getId());
		  }else{
		        // 根据标题like查询
		        List<Book> bookList = bookService.queryByTitleWithPagenate(query, 10, 0);
		        if (CollectionUtils.isNotEmpty(bookList)) {
		            Long[] bookIds = new Long[bookList.size()];
		            for (int i = 0; i < bookList.size(); i++) {
		                bookIds[i]=bookList.get(i).getId();
		            }
		            onOffStockRecordList = onOffStockRecordService.findOnOffStockRecordByBookIds(bookIds);
		        }
		  }
		  
		    List<BookDetail> bookDetailList = new ArrayList<BookDetail>();
		    if (CollectionUtils.isNotEmpty(onOffStockRecordList)) {
		        for (OnOffStockRecord onOffStockRecord : onOffStockRecordList) {
		            bookDetailList.add(buildBookDetail(onOffStockRecord.getId()));
		        }
		    }
		return bookDetailList;
	}
}
