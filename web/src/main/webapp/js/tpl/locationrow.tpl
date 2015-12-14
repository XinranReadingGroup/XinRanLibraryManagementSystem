
{{#each this}}
	<div class="row">
	    <div class="col-xs-4 col-md-2"> {{province}}</div>
	    <div class="col-xs-4 col-md-2"> {{city}}</div>
	    <div class="col-xs-4 col-md-2"> {{county}}</div>
	    <div class="col-xs-4 col-md-2"> 


	    	<a class="J-edit btn btn-default btn-sm" data-id="{{id}}" data-province="{{province}}" data-city="{{city}}"  data-county="{{county}}" href="javascript:void(0);">编辑</a>
	    	&nbsp; ｜&nbsp; 
	    	<a class="J-del btn btn-default btn-sm" data-id="{{id}}" href="javascript:void(0);">删除</a>

	    </div>
	</div>
{{/each}}