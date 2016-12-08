$("#option_button").click(function(){
	if($(this).text()=="Show Options"){
		
		// option_button의 텍스트가 show일 경우에 클릭하면 아래와 같이 검색창을 초기화시켜라.
		$("#timepicker1,#timepicker2").val(""); //검색창의 시간입력칸을 비워라.
		$("#recipeSelections").find("option").remove(); //recipe선택박스안의 option목록을 비워라.		
		$("#eqp_idSelections").find("option").remove();
		$("#chk_eqp_id").prop('checked',false); //checkbox의 체크표시는 해제시켜라.
		$("#defect_codeSelections").find("option").remove();
		$("#chk_defect_code").prop('checked',false);
		$("#eqp_idSelections,#defect_codeSelections").prop("disabled",true); // eqp, defect_code 선택박스는 모두 비활성화시켜라.
		
		$("#options").animate({ left:"+=330px" }); //슬라이딩 애니메이션
		$(this).animate({ left:"+=310px"});
		$(this).text("Close Options"); //애니메이션이 끝나면 버튼텍스트는 close.
		
		$.ajax({ // jquery ajax코딩이다.
			url : "select_option.jsp", //option 창의 listbox를 채울 데이터를 db로부터 읽어온다.
			dataType : "json",
			success : function(data) { //jquery 코딩으로 list box내의 options를 만든다.
				
				// jquery는 이렇게 코딩이 길어진다. search_result.js의 angular코딩과 비교해보면.
				var recipe=new Array(); // ajax로 받은 데이터를 종류별로 담기 위해, 각각의 array변수를 생성한다.
				var eqp_id=new Array(); 
				var defect_code=new Array(); 
				var a = 0; var b = 0; var c = 0; 
				for(var i=0; i<data.length; i++){ 
					
					//리턴된 원 데이터는 json array로 되어 있다. 그걸 한 line씩 i로 돌린다. 
					//데이터에 포함된 recipe와 eqp_id, defect_code는 각각 개수가 다르다. 왜냐하면, jsp파일에서 query문에 각각 distinct를 주었으니까.
					//그러므로 원 데이터에서 종류별로 추출하여 저장할 때는, 각 종류별로 구분된 number 변수를 증가시키며 저장해야 한다.
					
					if(data[i].recipe) recipe[a++] = data[i].recipe; //for문에서 현재 i번째 라인에 recipe항목이 존재한다면, 그 값을 recipe array에 저장해라. 이 때 저장넘버는 1씩 증가하는 a변수값에 의해 정해진다.
					if(data[i].eqp_id) eqp_id[b++] = data[i].eqp_id; 
					if(data[i].defect_code) defect_code[c++] = data[i].defect_code;
 				}
				
				var options1=""; var options2=""; var options3="";
				
				for (var i = 0; i < recipe.length; i++) { //html파일의 <select> 태그의 자식항목으로 들어갈 option들을 해당array개수만큼 생성한다.
					options1 += '<option value="'+ recipe[i]+'">'+recipe[i]+'</option>';
				}			
				for (i = 0 ; i < eqp_id.length; i++) {
					options2 += '<option value="'+ eqp_id[i]+'">'+eqp_id[i]+'</option>';
				}			
				for (i = 0; i < defect_code.length; i++) {
					options3 += '<option value="'+ defect_code[i]+'">'+defect_code[i]+'</option>';
				}
				$('#recipeSelections').append(options1); //위에서 생성한 옵션항목들을 해당 <select>태그의 자식으로 붙여넣기한다.
				$('#eqp_idSelections').append(options2);	
				$('#defect_codeSelections').append(options3);
				
				$('#recipeSelections').selectric('refresh'); //다 붙여넣기했으면 새로고침하라. refresh는 이렇게 위의 세가지 종류별 append가 모두 끝난 후에 한꺼번에 해야한다.
				$('#eqp_idSelections').selectric('refresh'); 
				$('#defect_codeSelections').selectric('refresh');  
			}
		});
		
	}else{ // button 텍스트가 close일 경우에 버튼을 누르면, 
		$("#options").animate({ left:"-=330px" }); //원상태로 복귀하는 슬라이딩.
		$(this).animate({ left:"-=310px"});
		$(this).text("Show Options");			
	}	
});

$("#reset").on("click", function () {
	//reset버튼의 초기화작업 : 모든 리스트의 선택된 옵션항목은 -1번이 되게하라. 옵션항목은 0번부터 존재한다.
    $('#recipeSelections, #eqp_idSelections,#defect_codeSelections').prop('selectedIndex',-1); 
});

$('#timepicker1').on('change',function(){ //검색창에서 시간을 변경시키면, 그 값에 따라 실시간으로 key_time1칸의 내용이 업데이트된다.
	$('#key_time1').text($('#timepicker1').val());
});
$('#timepicker2').on('change',function(){
	$('#key_time2').text($('#timepicker2').val());
});

$('#recipeSelections').on('change',function(){
	$('#key_recipe').html($('#recipeSelections > option:selected').val()); // 선택항목이 실시간으로 key_recipe칸에 표시된다.
});

$('#eqp_idSelections').on('change',function(){
	$('#key_eqp_id').html($('#eqp_idSelections > option:selected').map(function(){ return this.value; }).toArray().join(","));
});

$('#defect_codeSelections').on('change',function(){
	$('#key_defect_code').html($('#defect_codeSelections > option:selected').map(function(){ return this.value; }).toArray().join(","));
});

$("#chk_eqp_id").change(function() {
    if(this.checked) $("#eqp_idSelections").removeAttr('disabled'); //체크박스를 체크하면, 리스트박스의 disabled속성을 지워라. 즉 선택가능상태가 된다.
    else { //체크가 해제되면, 다시 disabled시켜라.
    	$("#eqp_idSelections").attr('disabled',true);
    	$("#key_eqp_id").text(""); //그리고 실시간으로 반영되던 key창의 내용은 지워라. 아무것도 선택안 한 상태로 된거니까.
    }
});

$("#chk_defect_code").change(function() {
    if(this.checked) $("#defect_codeSelections").removeAttr('disabled');
    else {
    	$("#defect_codeSelections").attr('disabled',true);
    	$("#key_defect_code").text("");
    }
});