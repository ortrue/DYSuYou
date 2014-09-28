/**
 * 开启标签以及内容层<br>
 * 此方法会记录访问的历史记录,当只有id参数时只会显示已经开启且还存在内容层和标签
 * @param {String}id 唯一标识所点击的链接(将作为标签与内容层的id)
 * @param {String}[itemText] 可选项,所点击链接的文字,新建标签时需要使用
 * @param {String}[url] 可选项,所点击链接的url地址,新建内容层时需要使用
 * @param {String}[target] 可选项,打开url的方式(_blank:开启新窗口;)
 * @param {Boolean}[refresh] 可选项,是否刷新页面
 */
function fGoto(id,itemText,url,target,refresh)
{	
	if(!isNull(id)&&typeof(id)=="string")
	{
		//开启新窗口
		if("_blank"==target&&!isNull(url))
		{
			window.open(url,"_blank");
		}
		else
		{
			//如果点击的链接为当前开启的链接,不保存历史记录
			if(currId!=id)
			{	
				//保存历史记录
				saveHistory(id,itemText,url);
			}
			//默认刷新内容层页面(菜单点击时始终刷新)
			refresh=isNull(refresh)?true:refresh;
			//开启标签以及内容层
			openTabAndCtnt(id,itemText,url,refresh);
		}
	}
	
	//保存当前所有tab页信息
	saveOrUpdateCurrentTabAll(id,itemText,url);
	
	//增加当前TabsItem
	addTabsItems();
}

/**
* 标签点击方法<br>
* 显示标签及内容页面
* @param {String}id 所点击标签链接的id(不是标签的id)
* @returns {Object}function 返回fGoto方法
*/
var tabClick = function(id)
{
	return function()
	{	
		//显示点击的标签页面,并且不刷新页面(标签点击时始终不刷新)
		//fGoto(id,null,null,null,false);
		openTabAndCtnt(id,null,null,false);
		
		//重置当前TabsItem
		addTabsItems();
	};
};


var currId = null;//当前开启链接的id
/**
 * 开启标签以及内容层<br>
 * 标签以及内容层存在时只显示,不存在时创建.创建时需要itemText和url参数
 * @param {String}id 链接对象的id
 * @param {String}[itemText] 可选项,链接对象的文字,新建标签时需要使用
 * @param {String}[url]可选项,链接对象的url地址,新建内容层时需要使用
 * @param {Boolean}[refresh] 可选项,是否刷新页面
 */
function openTabAndCtnt(id,itemText,url,refresh)
{
	if(!isNull(id))
	{
		//设置当前开启链接的id
		currId = id;
		
		//将所有标签隐藏(改为tabOff样式)
		var divTab = document.getElementById("divTab");		
		var tabNums = 0;//标签总个数
		var tabs = divTab.childNodes;		
		for(var i=0;i<tabs.length;i++)
		{
			node = tabs[i];
			if(node.nodeName=="#text")
				continue;
			else
			{
				node.className = "tabOff";
				tabNums++;
			}			
		}
		
		//将所有内容层隐藏
		var divContent = document.getElementById("divContent");		
		var divContents = divContent.childNodes;
		for(var i=0;i<divContents.length;i++)
		{
			node =divContents[i];
			if(node.nodeName=="#text")
				continue;
			else
			{
				node.style.display="none";
			}			
		}
	
		//判断标签以及内容层是否存在,存在则开启(显示),不存在则创建
		if(!showTabAndCtnt(id,refresh,url)&&!isNull(itemText)&&!isNull(url))
		{
			//向标签层中添加标签			
			createTab(divTab,id,itemText,tabNums);			
			//向主体内容层中添加内容层
			createContent(divContent,id,url);
		}
		
		//重置标签
		resetTabs();		
	}
}

/**
 * 创建内容层<br>
 * 向主体内容层中添加内容层
 * @param {Object} divContent 主体内容层
 * @param {String} id 链接的id
 * @param {String} url 链接的url地址
 */
function createContent(divContent,id,url)
{
	if(!isNull(divContent)&&!isNull(id)&&!isNull(url))
	{
		//创建一个内容div层
		var divSubCtnt = document.createElement("div");
		divSubCtnt.setAttribute("id", "div"+id);//层的id:"div"+id
		divSubCtnt.setAttribute("style", "");
		//创建一个iframe
		var ifrContent = document.createElement("iframe");
		ifrContent.setAttribute("id", "ifr"+id);
		ifrContent.setAttribute("src", url);
		ifrContent.setAttribute("frameBorder","0");
		ifrContent.setAttribute("width","100%");
		ifrContent.setAttribute("onLoad", "iframeResize(this)");
		ifrContent.setAttribute("onResize", "iframeResize(this)");
		
		//将新的iframe追加到div层里面
		divSubCtnt.appendChild(ifrContent);
		//将新的div内容层追加到主体层中
		divContent.appendChild(divSubCtnt);
	}
}

/**
 * 创建标签<br>
 * 向标签层中添加标签
 * @param {Object}divTab 标签层
 * @param {String}id 链接的id
 * @param {String}itemText 链接的文字
 * @param {String}tabNums 目前标签的总个数
 */
function createTab(divTab,id,itemText,tabNums)
{
	if(!isNull(divTab)&&!isNull(id)&&!isNull(itemText))
	{
		//创建标签表格对象
		var tab = document.createElement("table");			
		tab.setAttribute("id", "tab"+id);//标签的id:"tab"+id
		tab.setAttribute("title",itemText);
		tab.className = "tabOn";//使用.className才能兼容IE			
		//获得标签层内标签的宽度
		//获得标签体的总宽度(减去190)
		var divTabWidth = divTab.offsetWidth-190;
		//新标签的宽度(最宽不超过120px)
		var tabWidth = (divTabWidth/tabNums)>120?120:divTabWidth/tabNums;			
		tab.style.width = tabWidth+"px";
		//标签的点击方法	
		tab.onclick=tabClick(id);			
		//向标签层中添加新标签表格
		divTab.appendChild(tab);
		
		//创建表格体
		var tbody = document.createElement("tbody");			
		//向表格中添加标签表格体
		tab.appendChild(tbody);
		
		//创建一行
		var tabTr = document.createElement("tr");			
		//向表格体中添加行
		tbody.appendChild(tabTr);
		
		//创建四列
		var tabLft = document.createElement("td");
		tabLft.className = "tLft";
		
		var tabBdy = document.createElement("td");
		tabBdy.className = "tBdy";
		var nobr = document.createElement("nobr");
		var textNode = document.createTextNode(itemText);
		nobr.appendChild(textNode);
		tabBdy.appendChild(nobr);
		
		var tabBtn = document.createElement("td");
		tabBtn.className = "tBtn";
		var tabCls = document.createElement("a");
		tabCls.className = "tCls";
		tabCls.setAttribute("title","\u5173\u95ed");//文字:关闭
		//关闭标签按钮连接
		tabCls.setAttribute("href", "javascript:closeTab('"+id+"')");
		
		if(id !='11'){
			tabBtn.appendChild(tabCls);	
		}
		
		var tabRit = document.createElement("td");
		tabRit.className = "tRit";
		
		//向行中依次添加列
		tabTr.appendChild(tabLft);
		tabTr.appendChild(tabBdy);
		tabTr.appendChild(tabBtn);
		tabTr.appendChild(tabRit);
	}
}

/**
 * 根据id显示存在的标签以及内容层<br>
 * @param {String}id 链接的id
 * @param {Boolean}[refresh] 可选项,是否刷新内容层的页面,默认不刷新
 * @return {Boolean}true/false 标签以及内容层是否存在
 */
function showTabAndCtnt(id,refresh,url)
{
	if(!isNull(id))
	{
		//获得标签
		var tab = document.getElementById("tab"+id);		
		//获得内容层
		var divSubCtnt = document.getElementById("div"+id);
		//如果存在,则显示
		if(!isNull(tab)&&!isNull(divSubCtnt))
		{
			//将标签样式设置为点开(tabOn)样式
			tab.className = "tabOn";			
			//显示内容层
			divSubCtnt.style.display="";
			//获得是否刷新页面的值,默认不刷新内容层的页面
			refresh = isNull(refresh)?false:refresh;
			if(refresh)
			{
				//刷新页面
				var ifrContent = document.getElementById("ifr"+id);
				ifrContent.src = url;
			}
			return true;
		}
		else
		{
			return false;
		}
	}
}

/**
* 判断参数是否为空
* @param {Object}param:参数
* @returns {Boolean}true/false 参数是否为空
*/
function isNull(param)
{
	var flag = true;//默认认为对象是空值
	var type = typeof(param);	
	if(type=="string")
	{
		if(null!=param&&""!=param&&"null"!=param)//修改错误:2010-08-21
		{
			flag = false;
		}
	}
	else
	{
		if(null!=param)
		{
			flag = false;
		}
	}
	return flag;
}

/**
* 关闭标签及内容层页面<br>
* 将历史记录索引指向关闭页面后所开启的历史记录位置
* @param {String}id 要关闭的链接id
*/
function closeTab(id)
{
	if(isUUID(id)){
		if(window.confirm("确认关闭吗?")){
			//删除正在上传的服务端邮件
			var doc=window.frames["ifr"+id].document;
			if(doc == null)
				doc=window.frames["ifr"+id].contentDocument;
			
			var mailid=doc.getElementById('mailSID').value;
			if(mailid!=null){
				senderDeleteMail(mailid);
			}
			doCloseTab(id);
		}else {
			return ;
		}
		
//		var ifrms=window.frames["ifr"+id];
//		console.info(ifrms.length+" ifrms:"+ifrms);
//		var iframe_window =ifrms.contentWindow;
//		if(iframe_window){
//			console.info("-->>  "+iframe_window.cancelUpload(1));
//			iframe_window.cancelUpload(1);
//		}
		
	}else{
		doCloseTab(id);
	}
}

function doCloseTab(id){
	//关闭(移除)内容层
	var divContent = document.getElementById("divContent");	
	var ifmSize=divContent.childNodes.length;
	var divId = "div"+id;
	var divSubCtnt = document.getElementById(divId);

	if(ifmSize >= 2){
		if(divSubCtnt)
		{
			divContent.removeChild(divSubCtnt);
		}
		//关闭(移除)标签	
		var divTab = document.getElementById("divTab");
		var tabId = "tab"+id;
		var tab = document.getElementById(tabId);
		if(tab)
		{
			divTab.removeChild(tab);
		}
		
		//显示上一个历史页面,此处只显示目前还存在的上一个页面
		var nowIndex = null;//页面历史记录的索引
		//遍历历史记录,显示上一个存在的标签和页面
		for(var i=historys.length-1;i>=0;i--)
		{
			//如果成功的显示了历史记录中的页面
			if(showTabAndCtnt(historys[i][0]))
			{
				//获得当前页面的索引
				nowIndex = i;
				//设置当前开启的标签页面id
				currId = historys[i][0];
				break;
			}
		}
		
		//改变历史记录索引位置,将位置移到现在所开启标签页的位置
		if(!isNull(nowIndex))
		{
			index = nowIndex;
		}
		//说明已经没有存在的层可以显示(所有标签已经全部关闭),
		//改变历史记录索引位置,将位置移至最后位置之外
		else
		{
			index = historys.length;
			//设置当前开启的标签页面id为空
			currId = null;
		}
		
		//重置标签
		resetTabs();
		
		//删除Tab记录
		var delIds=[id];
		removeCurrentTabAll(delIds);
		
		//重置当前TabsItem
		addTabsItems();
	  }
	
	  if(ifmSize == 2){
		var tabsItemBox=$("#divTabClsAll");
		if(tabsItemBox != null){
			tabsItemBox.remove();
		}
	  }
}

/**
 * 关闭所有标签及内容页面<br>
 * 将历史记录的索引指向最后位置之外
 */
function closeAllTab()
{
	if(window.confirm("确认要关闭所有吗?")){
		var delIds=Array();
		//关闭所有(移除)内容层	
		var divContent = document.getElementById("divContent");	
		var contentChilds = divContent.childNodes;    
		for(var i = contentChilds.length - 1; i >= 1; i--) {
			var objId=contentChilds[i].id;
			delIds.push(objId.substring(3,objId.length));
	
			//删除正在上传的服务端邮件
			var id=objId.substring(3,objId.length);
			if(isUUID(id)){
				var doc=window.frames["ifr"+id].document;
				if(doc == null)
					doc=window.frames["ifr"+id].contentDocument;
				
				var mailid=doc.getElementById('mailSID').value;
				if(mailid!=null){
					senderDeleteMail(mailid);
				}
			}

			divContent.removeChild(contentChilds[i]);  
		} 
	
		//关闭所有(移除)标签	
		var divTab = document.getElementById("divTab");
		var tabChilds = divTab.childNodes;    
		for(var i = tabChilds.length - 1; i >= 1; i--) {           
			divTab.removeChild(tabChilds[i]);      
		} 
		
		divContent.childNodes[0].style.display="block";
		divTab.childNodes[0].className="tabOn";
	
		//改变历史记录索引位置,将位置移至最后位置之外,
		//(返回上一页时即会显示历史记录的最后一页)
		index=historys.length;
		//设置当前开启的标签页面id为空
		currId = null;
		
		//删除Tab记录
		removeCurrentTabAll(delIds);
	}else{
		return ;
	}

}

/**
* 重置标签<br>
* 1.判定是否需要开启"关闭所有标签(关闭所有标签是一个标签)"<br>
* 2.重新设置所有标签的宽度,标签宽度最宽不超过120px<br>
*/
function resetTabs()
{
	//获得标签层
	var divTab = document.getElementById("divTab");
	//获得标签的总数tabNums
	var tabNums = 0;
	var tabs = divTab.childNodes;
	for(var i=0;i<tabs.length;i++)
	{
		node = tabs[i];
		if(node.nodeName=="#text")
			continue;
		else
			tabNums++;
	}
	
	//1.判定是否需要开启"关闭所有"标签
	//如果有超过5个内容层标签,则开启关闭所有标签	
	//alert("tabNums:"+tabNums);
	if(tabNums>1)
	{			
		//先关闭(移除)关闭所有标签			
		var tabId = "divTabClsAll";
		var tab = document.getElementById(tabId);
		if(tab)
		{
			divTab.removeChild(tab);
		}
		//再开启关闭所有标签
		var divTabClsAll = document.createElement("div");
		divTabClsAll.setAttribute("id", tabId);
		divTabClsAll.className = "tabClsAll";
		
		//var lnkClsAll = document.createElement("a");
		//lnkClsAll.setAttribute("href", "javascript:closeAllTab()");
		//lnkClsAll.setAttribute("title", "\u5173\u95ed\u6240\u6709");//文字：关闭所有
		
		var currnetTabsHTML="<i class=\"icon-arrowd\" onclick=\"showTabsItem(this);\"></i>";		
		currnetTabsHTML+="<ul class=\"droptop\" id=\"currentTabs\">";
		currnetTabsHTML+="</ul>";

		//向关闭所有标签添加连接
		divTabClsAll.innerHTML=currnetTabsHTML;
		//向标签层添加关闭所有标签
		divTab.appendChild(divTabClsAll);
	}
	
	//2.重新设置所有标签的宽度
	//获得标签体的总宽度(减去190)
	var divTabWidth = divTab.offsetWidth-190;	
	//单个标签的宽度(最宽不超过120px)
	var tabWidth = (divTabWidth/tabNums)>120?120:divTabWidth/tabNums;
	//设置所有标签的宽度	
	for(var i=0;i<tabs.length;i++)
	{
		node = tabs[i];
		if(node.nodeName=="#text")
			continue;
		else
		{
			node.style.width = tabWidth+"px";
		}			
	}
}

var historys = new Array();//历史记录数组

/**
 * 保存访问的历史记录<br>
 * 历史记录始终在200条以内.将历史记录索引指向新记录
 * @param {String} id 链接对象的id
 * @param {String} [itemText] 可选项，链接对象的文字
 * @param {String} [url] 可选项，链接对象的地址
 */
function saveHistory(id,itemText,url)
{	
	if(!isNull(id))
	{
		//新历史记录
		var his = null;
			
		//如果传送有itemText和url参数,则直接保存历史记录
		if(!isNull(itemText)&&!isNull(url))
		{
			his = new Array(id,itemText,url);		
			//保存新的历史记录
			historys.push(his);
		}
		else
		{
			//否则根据id从历史记录中获得完整数据后再保存
			//根据id取出历史记录数据
			for(var i=0;i<historys.length;i++)
			{
				if(id==historys[i][0])//比较id
				{
					his = new Array(historys[i][0],historys[i][1],historys[i][2]);
				}
			}
			if(!isNull(his))
			{
				//保存新的历史记录
				historys.push(his);
			}
		}
		
		//使历史记录始终在200条以内
		//如果记录大于200条,将最前面的记录移除
		while(historys.length>200)
		{
			historys.splice(0,1);
		}		
		
		//改变历史记录索引位置,将位置移至新记录的位置
		index = historys.length-1;
		
		/*var s = "\u5386\u53f2\u8bb0\u5f55,"+historys.length+"\u6761:";
		for(var i=0;i<historys.length;i++)
		{
			s += "\r\n Index"+i+":"+historys[i][0]+"\t|"+historys[i][1]+"\t|"+historys[i][2];
		}
		alert(s);*/
	}
}

/**
 * 历史记录的当前索引位置<br>
 * 用于记录当前开启页面在历史记录中的位置.<br>
 * 修改此值的情况:
 * 1.有新的历史记录时,移动索引位置到新记录的位置;
 * 2.访问某个历史记录时,移动索引位置到目前访问的位置;
 * 3.所有页面关闭时,移动索引到历史记录的最后位置之外;
 * 4.单个页面关闭时,移动索引到关闭后所显示页面历史记录的位置.
 */
var index = null;

/**
 * 访问历史记录
 * @param {int}disNum 要访问历史的位置,-1表示上一位置,+1表示下一位置
 */
function visitHis(disNum)
{
	if(historys.length>0&&!isNull(disNum))
	{
		//索引(如果索引为空,则预设为历史记录中的最后一个)
		index = index==null?historys.length-1:index;	
		
		//如果改变后的索引在历史记录的范围之内,则改变索引
		if(index+disNum>-1&&index+disNum<historys.length)
		{
			//改变索引
			index+=disNum;
		}
		
		//取出索引对应的历史记录
		var his = historys[index];	
		//开启历史记录
		if(his)
		{
			//访问历史记录,并且不刷新页面
			openTabAndCtnt(his[0],his[1],his[2],false);
		}
	}
}

/**
 * 当前打开所有Tab记录<br>
 * @param {String} id 链接对象的id
 * @param {String} [itemText] 可选项，链接对象的文字
 * @param {String} [url] 可选项，链接对象的地址
 */
var tabAllArray=new Array();
function getCurrentTabAllById(id){
	for(var i=0;i<tabAllArray.length;i++){
		var tabAllObject=tabAllArray[i];
		if(tabAllObject.id == id){
			return tabAllObject;
		}
	}
	return null;
}

function saveOrUpdateCurrentTabAll(id,itemText,url){
	var tabObject=getCurrentTabAllById(id);
	if(tabObject != null){
		for(var i=0;i<tabAllArray.length;i++){
			var tabAllObject=tabAllArray[i];
			if(tabAllObject.id == id){
				tabAllObject.id=id;
				tabAllObject.itemText=itemText;
				tabAllObject.url=url;
			}
		}
	}else{
		if(id != 11){
			var tabAllObject=new Object();
			tabAllObject.id=id;
			tabAllObject.itemText=itemText;
			tabAllObject.url=url;
			tabAllArray.push(tabAllObject);
		}
	}
}

function removeCurrentTabAll(delIds){
	var delArrayIdx=new Array();
	for(var i=0;i<delIds.length;i++){
		for(var j=0;j<tabAllArray.length;j++){
			var obj=tabAllArray[j];
			if(obj.id == delIds[i]){
				delArrayIdx.push(j);//存放要删除的数组的下标
			}
		}
	}
	
	for(var k=0;k<delArrayIdx.length;k++){
		tabAllArray.remove(delArrayIdx[k]);
	}
}

function showTabsItem(obj){
	  $("#currentTabs").toggle();
}

function addTabsItems(){
	$("#currentTabs > li:first").nextAll().remove();
	var tabsItem="<li><a href=\"javascript:closeAllTab();\">\u5173\u95ed\u6240\u6709</a></li>";
	for(var i=0;i<tabAllArray.length;i++){
		var tabAllObject=tabAllArray[i];
		tabsItem +="<li><span>"+tabAllObject.itemText+"</span><a class=\"closed\" title=\"关闭\" href=\"javascript:closeTab('"+tabAllObject.id+"');\">X</a>";
	}
	$("#currentTabs").append(tabsItem);
}

function isExitsFunction(funcName){
	try{
		if(typeof(eval(funcName))=="function"){
			return true;
		}
	}catch(e){
	}
	return false;
}

function isUUID (str, version) {
    var uuid = {
            '3': /^[0-9A-F]{8}-[0-9A-F]{4}-3[0-9A-F]{3}-[0-9A-F]{4}-[0-9A-F]{12}$/i
          , '4': /^[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$/i
          , '5': /^[0-9A-F]{8}-[0-9A-F]{4}-5[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$/i
          , all: /^[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}$/i
        };
	
    var pattern = uuid[version ? version : 'all'];
    return pattern.test(str);
}

function senderDeleteMail(mailid){
	 var path=document.getElementById("basePath").value;
	 var url=path+'mail/senderdeletemail';
	 $.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {mailid:mailid},
		 async:false,
		 success : function(result) {
		 }
	});
}
