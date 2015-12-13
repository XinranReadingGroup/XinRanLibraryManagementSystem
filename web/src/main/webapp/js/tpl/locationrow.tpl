
{{#each data}}
	<div class="row">
	    <div class="col-xs-4 col-md-2"> {{this.province}}</div>
	    <div class="col-xs-4 col-md-2"> {{this.city}}</div>
	    <div class="col-xs-4 col-md-2"> {{this.county}}</div>
	    <div class="col-xs-4 col-md-2"> 


	    	<a class="J-edit btn btn-default btn-sm" data-id="{{this.id}}" href="javascript:void(0);">编辑</a>
	    	&nbsp; ｜&nbsp; 
	    	<a class="J-del btn btn-default btn-sm" data-id="{{this.id}}" href="javascript:void(0);">删除</a>

	    </div>
	</div>
{{/each}}