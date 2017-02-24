
{{#each this}}
	<div class="row">
	    <div class="col-xs-3 col-md-3"> {{province}}</div>
	    <div class="col-xs-3 col-md-3"> {{city}}</div>
	    <div class="col-xs-3 col-md-3"> {{county}}</div>
	     <div class="col-xs-3 col-md-3"> {{detail}}</div>
	    <div class="col-xs-3 col-md-3"> 


	    	<a class="J-edit btn btn-default btn-sm" data-id="{{id}}" data-province="{{province}}" data-city="{{city}}"  data-county="{{county}}" href="javascript:void(0);">编辑</a>
	    	&nbsp; ｜&nbsp; 
	    	<a class="J-del btn btn-default btn-sm" data-id="{{id}}" href="javascript:void(0);">删除</a>

	    </div>
	</div>
{{/each}}