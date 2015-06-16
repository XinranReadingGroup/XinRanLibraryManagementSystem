<ul class="book-list">
    {{#each data}}
    <li class="book-item clearfix">
        <div class="book-img">
            <img src="{{this.book.imgUrl}}">
        </div>
        <div class="book-info">
            <div class="book-title clearfix">
                <span>{{this.book.title}}</span>
                <span>{{this.onOffStockRecord.onStockDate}}</span>
            </div>
            <p class="book-desc">
                {{this.book.summary}}
            </p>
            <div class="book-op" data-id="{{this.onOffStockRecord.id}}">
                <button type="button" class="btn btn-default btn-sm donate-book">捐献</button>
                <button type="button" class="btn btn-default btn-sm borrow-book">借阅</button>
                <button type="button" class="btn btn-default btn-sm share-book">分享</button>
                <button type="button" class="btn btn-default btn-sm comment-book">评论</button>
            </div>
        </div>
    </li>
    {{/each}}
</ul>