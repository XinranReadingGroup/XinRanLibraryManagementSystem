<ul class="book-list">
    {{#each data}}
    <li class="book-item clearfix">
        <div class="book-img">
            <img src="{{this.book.imgUrl}}">
        </div>
        <div class="book-info">
            <div class="book-title clearfix">
                <span>书名：{{this.book.title}}</span>
                <span>作者：{{this.book.title}}</span>
                <span>分享时间：{{this.onOffStockRecord.onStockDate}}</span>
                <span>书的所有者：{{this.ownerUserVO.nickName}}</span>
            </div>
            <p class="book-desc">
                {{this.book.summary}}
            </p>
            <div class="book-op" data-id="{{this.onOffStockRecord.id}}">
                <!-- <button type="button" class="btn btn-default btn-sm donate-book">捐献</button> -->
                <!-- <button type="button" class="btn btn-default btn-sm borrow-book">借阅</button> -->
                <!-- <button type="button" class="btn btn-default btn-sm share-book">分享</button> -->
                <!-- <button type="button" class="btn btn-default btn-sm comment-book">评论</button> -->
                <a class="btn btn-default btn-sm" href="/book/detail/{{this.onOffStockRecord.id}}" target="_blank">详情</a>
            </div>
        </div>
    </li>
    {{/each}}
</ul>