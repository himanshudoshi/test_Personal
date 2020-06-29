﻿// (c) 2010 CodePlex Foundation
(function(){var b="ExtendedBalloonPopupBehavior";function a(){var e="undefined",c="click",d="mouseover",b=false,a=null;Type.registerNamespace("Sys.Extended.UI");Sys.Extended.UI.BalloonPopupControlBehavior=function(d){var c=this;Sys.Extended.UI.BalloonPopupControlBehavior.initializeBase(c,[d]);c._balloonPopupControlID=a;c._position=Sys.Extended.UI.BalloonPopupPosition.Auto;c._balloonStyle=Sys.Extended.UI.BalloonPopupStyle.Rectangle;c._offsetX=0;c._offsetY=0;c._extenderControlID=a;c._displayOnMouseOver=b;c._displayOnFocus=b;c._displayOnClick=true;c._balloonSize="small";c._shadow=true;c._scrollBars=Sys.Extended.UI.ScrollBars.Auto;c._popupElement=a;c._styleElement=a;c._sizeElement=a;c._shadowElement=a;c._directionElement=a;c._contentElement=a;c._popupBehavior=a;c._popupVisible=b;c._focusHandler=a;c._mouseOverHandler=a;c._clickHandler=a;c._popupClickHandler=a;c._bodyClickHandler=a;c._onShowJson=a;c._onHideJson=a;c._popupWidth=0;c._popupHeight=0;c._AutoPosition=a;c._directionClassName=a};Sys.Extended.UI.BalloonPopupControlBehavior.prototype={initialize:function(){var b=this;Sys.Extended.UI.BalloonPopupControlBehavior.callBaseMethod(b,"initialize");var e=b.get_element();b.createPopupElement();b._popupBehavior=$create(Sys.Extended.UI.PopupBehavior,{id:b.get_id()+"BalloonPopupBehavior",parentElement:e},a,a,b._popupElement);b._onShowJson&&b._popupBehavior.set_onShow(b._onShowJson);b._onHideJson&&b._popupBehavior.set_onHide(b._onHideJson);if(b._displayOnFocus)b._focusHandler=Function.createDelegate(b,b._onFocus);if(b._displayOnMouseOver)b._mouseOverHandler=Function.createDelegate(b,b._onMouseOver);if(b._displayOnClick)b._clickHandler=Function.createDelegate(b,b._onFocus);b._popupClickHandler=Function.createDelegate(b,b._onPopupClick);b._bodyClickHandler=Function.createDelegate(b,b._onBodyClick);b._displayOnFocus&&$addHandler(e,"focus",b._focusHandler);b._displayOnMouseOver&&$addHandler(e,d,b._mouseOverHandler);b._displayOnClick&&$addHandler(e,c,b._clickHandler);$addHandler(document,c,b._bodyClickHandler);$addHandler(b._popupElement,c,b._popupClickHandler)},dispose:function(){var b=this,e=b.get_element();b._onShowJson=a;b._onHideJson=a;if(b._popupBehavior){b._popupBehavior.dispose();b._popupBehavior=a}if(b._focusHandler){$removeHandler(e,"focus",b._focusHandler);b._focusHandler=a}if(b._mouseOverHandler){$removeHandler(e,d,b._mouseOverHandler);b._mouseOverHandler=a}if(b._clickHandler){$removeHandler(e,c,b._clickHandler);b._clickHandler=a}if(b._bodyClickHandler){$removeHandler(document,c,b._bodyClickHandler);b._bodyClickHandler=a}if(b._popupClickHandler){$removeHandler(b._popupElement,c,b._popupClickHandler);b._popupClickHandler=a}Sys.Extended.UI.BalloonPopupControlBehavior.callBaseMethod(b,"dispose")},createPopupElement:function(){var d="ajax__content",c="div",b=this,e=b.get_element();b._popupElement=$common.createElementFromTemplate({nodeName:c,properties:{id:b.get_id()+"_balloonPopup",style:{display:"block",position:"absolute"}},cssClasses:["ajax__balloon_popup"]},e.parentNode);b._styleElement=$common.createElementFromTemplate({nodeName:"span",Properties:{id:"ajax__style_wrapper"}},b._popupElement);b._sizeElement=$common.createElementFromTemplate({nodeName:"span",Properties:{id:"ajax__size_wrapper"}},b._styleElement);b._shadowElement=$common.createElementFromTemplate({nodeName:c,Properties:{id:"ajax__shadow_wrapper"}},b._sizeElement);b._directionElement=$common.createElementFromTemplate({nodeName:c,Properties:{id:"ajax__direction_wrapper"}},b._shadowElement);b._contentElement=$common.createElementFromTemplate({nodeName:c,Properties:{id:d},cssClasses:[d]},b._directionElement);if($get(b._balloonPopupControlID)==a)throw"Referred BalloonPopupControlId did not find.";$get(b._balloonPopupControlID).style.display="none";b.setStyle();b.setSize();b._directionElement.className=b._directionClassName;b._popupWidth=b._directionElement.offsetWidth;b._popupHeight=b._directionElement.offsetHeight;if(b.get_balloonPopupPosition()!=Sys.Extended.UI.BalloonPopupPosition.Auto){b.setPosition();b.setContentPadding();b.setScrollBar()}},setStyle:function(){var a="rect",b=this;switch(b.get_balloonPopupStyle()){case Sys.Extended.UI.BalloonPopupStyle.Rectangle:b._styleElement.className=a;b._directionClassName=a;break;case Sys.Extended.UI.BalloonPopupStyle.Cloud:b._styleElement.className="cloud";b._directionClassName="cloud";break;case Sys.Extended.UI.BalloonPopupStyle.Custom:b._styleElement.className=b.get_customClassName();b._directionClassName=b.get_customClassName();break;default:b._styleElement.className=a;b._directionClassName=a}},setSize:function(){var a=" small",b=this;switch(b.get_balloonSize()){case Sys.Extended.UI.BalloonPopupSize.Small:b._sizeElement.className+=a;b._directionClassName+=a;break;case Sys.Extended.UI.BalloonPopupSize.Medium:b._sizeElement.className+=" medium";b._directionClassName+=" medium";break;case Sys.Extended.UI.BalloonPopupSize.Large:b._sizeElement.className+=" large";b._directionClassName+=" large";break;default:b._sizeElement.className+=a;b._directionClassName+=a}},setPosition:function(){var d=" top_right_shadow",c=" top_right",b=this,e=a;if(b.get_balloonPopupPosition()==Sys.Extended.UI.BalloonPopupPosition.Auto)e=b._autoPosition;else e=b.get_balloonPopupPosition();switch(e){case Sys.Extended.UI.BalloonPopupPosition.TopLeft:b._directionElement.className=b._directionClassName+" top_left";b._shadowElement.className=b._directionClassName+" top_left_shadow";break;case Sys.Extended.UI.BalloonPopupPosition.TopRight:b._directionElement.className=b._directionClassName+c;b._shadowElement.className=b._directionClassName+d;break;case Sys.Extended.UI.BalloonPopupPosition.BottomLeft:b._directionElement.className=b._directionClassName+" bottom_left";b._shadowElement.className=b._directionClassName+" bottom_left_shadow";break;case Sys.Extended.UI.BalloonPopupPosition.BottomRight:b._directionElement.className=b._directionClassName+" bottom_right";b._shadowElement.className=b._directionClassName+" bottom_right_shadow";break;default:b._directionElement.className=b._directionClassName+c;b._shadowElement.className=b._directionClassName+d}},setContentPadding:function(){var b=this,a=$common.getPaddingBox(b._contentElement),c=$get(b._balloonPopupControlID);$common.setBounds(c,{x:b._offsetX+a.left,y:b._offsetY+a.top,width:b._popupWidth-a.left-a.right,height:b._popupHeight-a.top-a.bottom})},setScrollBar:function(){var b="hidden",a="scroll",c=$get(this._balloonPopupControlID);switch(this.get_scrollBars()){case Sys.Extended.UI.ScrollBars.Horizontal:$common.setStyle(c,{overflowX:a,overflowY:b});break;case Sys.Extended.UI.ScrollBars.Vertical:$common.setStyle(c,{overflowY:a,overflowX:b});break;case Sys.Extended.UI.ScrollBars.Both:$common.setStyle(c,{overflow:a});break;case Sys.Extended.UI.ScrollBars.None:$common.setStyle(c,{overflow:b});break;default:$common.setStyle(c,{overflow:"auto"})}},showPopup:function(){var a=this;a._contentElement.appendChild($get(a._balloonPopupControlID));$get(a._balloonPopupControlID).style.display="block";if(Sys.Extended.UI.BalloonPopupPosition.Auto==a._position){a._setAutoPosition();a.setPosition();a.setContentPadding();a.setScrollBar()}if(!a._shadow)a._shadowElement.className="";var b=Sys.Extended.UI.BalloonPopupControlBehavior.__VisiblePopup;b&&b._popupBehavior&&b.hidePopup();Sys.Extended.UI.BalloonPopupControlBehavior.callBaseMethod(a,"populate");a._popupBehavior.set_x(a._getLeftOffset());a._popupBehavior.set_y(a._getTopOffset());a._popupBehavior.show();a._popupVisible=true;Sys.Extended.UI.BalloonPopupControlBehavior.__VisiblePopup=a;var c=$common.getCurrentStyle(a._directionElement,"backgroundPositionX"),d=$common.getCurrentStyle(a._directionElement,"backgroundPositionY");$common.setStyle(a._styleElement,{backgroundPositionX:c,backgroundPositionY:d})},hidePopup:function(){this._popupBehavior.hide();this._popupVisible=b;Sys.Extended.UI.BalloonPopupControlBehavior.__VisiblePopup=a},_onFocus:function(a){!this._popupVisible&&this.showPopup();a&&a.stopPropagation()},_onMouseOver:function(a){!this._popupVisible&&this.showPopup();a&&a.stopPropagation()},_onPopupClick:function(a){a.stopPropagation()},_onBodyClick:function(){this._popupVisible&&this.hidePopup()},_onPopulated:function(b,a){Sys.Extended.UI.BalloonPopupControlBehavior.callBaseMethod(this,"_onPopulated",[b,a]);this._popupVisible&&this._popupBehavior.show()},_getLeftOffset:function(){var a=this,c=0,b=Sys.Extended.UI.BalloonPopupPosition.Auto==a._position?a._autoPosition:a._position;if(Sys.Extended.UI.BalloonPopupPosition.BottomLeft==b||Sys.Extended.UI.BalloonPopupPosition.TopLeft==b)c=-1*a._popupWidth+a._offsetX;else if(Sys.Extended.UI.BalloonPopupPosition.BottomRight==b||Sys.Extended.UI.BalloonPopupPosition.TopRight==b)c=a.get_element().offsetWidth+a._offsetX;else c=a._offsetX;return c},_getTopOffset:function(){var a=this,c=0,b=Sys.Extended.UI.BalloonPopupPosition.Auto==a._position?a._autoPosition:a._position;if(Sys.Extended.UI.BalloonPopupPosition.TopLeft==b||Sys.Extended.UI.BalloonPopupPosition.TopRight==b)c=-1*a._popupHeight+a._offsetY;else if(Sys.Extended.UI.BalloonPopupPosition.BottomLeft==b||Sys.Extended.UI.BalloonPopupPosition.BottomRight==b)c=a.get_element().offsetHeight+a._offsetY;else c=a._offsetY;return c},_setAutoPosition:function(){var a=this,d=0,c=0,b=a.get_element();if(b.offsetParent)do{c+=b.offsetLeft;d+=b.offsetTop}while(b=b.offsetParent);var e=a.posTop(),j=a.posLeft(),m=a.pageWidth(),l=a.pageHeight(),k=e+l,h=j+m,g=d-a._popupHeight,n=c-a._popupWidth,i=d+a.get_element().offsetHeight+a._popupHeight,f=c+a.get_element().offsetWidth+a._popupWidth;if(g-e>0&&g-e>k-i)if(h<f)a._autoPosition=Sys.Extended.UI.BalloonPopupPosition.TopLeft;else a._autoPosition=Sys.Extended.UI.BalloonPopupPosition.TopRight;else if(h<f)a._autoPosition=Sys.Extended.UI.BalloonPopupPosition.BottomLeft;else a._autoPosition=Sys.Extended.UI.BalloonPopupPosition.BottomRight},get_onShow:function(){return this._popupBehavior?this._popupBehavior.get_onShow():this._onShowJson},set_onShow:function(b){var a=this;if(a._popupBehavior)a._popupBehavior.set_onShow(b);else a._onShowJson=b;a.raisePropertyChanged("onShow")},get_onShowBehavior:function(){return this._popupBehavior?this._popupBehavior.get_onShowBehavior():a},onShow:function(){this._popupBehavior&&this._popupBehavior.onShow()},get_onHide:function(){return this._popupBehavior?this._popupBehavior.get_onHide():this._onHideJson},set_onHide:function(b){var a=this;if(a._popupBehavior)a._popupBehavior.set_onHide(b);else a._onHideJson=b;a.raisePropertyChanged("onHide")},get_onHideBehavior:function(){return this._popupBehavior?this._popupBehavior.get_onHideBehavior():a},onHide:function(){this._popupBehavior&&this._popupBehavior.onHide()},get_BalloonPopupControlID:function(){return this._balloonPopupControlID},set_BalloonPopupControlID:function(a){if(this._balloonPopupControlID!=a){this._balloonPopupControlID=a;this.raisePropertyChanged("BalloonPopupControlID")}},get_balloonPopupPosition:function(){return this._position},set_balloonPopupPosition:function(a){if(this._position!=a){this._position=a;this.raisePropertyChanged("Position")}},get_balloonPopupStyle:function(){return this._balloonStyle},set_balloonPopupStyle:function(a){if(this._balloonStyle!=a){this._balloonStyle=a;this.raisePropertyChanged("BalloonStyle")}},get_ExtenderControlID:function(){return this._extenderControlID},set_ExtenderControlID:function(a){if(this._extenderControlID!=a){this._extenderControlID=a;this.raisePropertyChanged("ExtenderControlID")}},get_OffsetX:function(){return this._offsetX},set_OffsetX:function(a){if(this._offsetX!=a){this._offsetX=a;this.raisePropertyChanged("OffsetX")}},get_OffsetY:function(){return this._offsetY},set_OffsetY:function(a){if(this._offsetY!=a){this._offsetY=a;this.raisePropertyChanged("OffsetY")}},get_displayOnMouseOver:function(){return this._displayOnMouseOver},set_displayOnMouseOver:function(a){if(this._displayOnMouseOver!=a){this._displayOnMouseOver=a;this.raisePropertyChanged("DisplayOnMouseOver")}},get_displayOnFocus:function(){return this._displayOnFocus},set_displayOnFocus:function(a){if(this._displayOnFocus!=a){this._displayOnFocus=a;this.raisePropertyChanged("DisplayOnFocus")}},get_displayOnClick:function(){return this._displayOnClick},set_displayOnClick:function(a){if(this.displayOnClick!=a){this.displayOnClick=a;this.raisePropertyChanged("DisplayOnClick")}},get_balloonSize:function(){return this._balloonSize},set_balloonSize:function(a){if(this._balloonSize!=a){this._balloonSize=a;this.raisePropertyChanged("BalloonSize")}},get_useShadow:function(){return this._shadow},set_useShadow:function(a){if(this._shadow!=a){this._shadow=a;this.raisePropertyChanged("UseShadow")}},get_scrollBars:function(){return this._scrollBars},set_scrollBars:function(a){if(this._scrollBars!=a){this._scrollBars=a;this.raisePropertyChanged("ScrollBars")}},get_PopupVisible:function(){return this._popupVisible},get_customClassName:function(){return this._customClassName},set_customClassName:function(a){if(this._customClassName!=a){this._customClassName=a;this.raisePropertyChanged("CustomClassName")}},add_showing:function(a){this._popupBehavior&&this._popupBehavior.add_showing(a)},remove_showing:function(a){this._popupBehavior&&this._popupBehavior.remove_showing(a)},raiseShowing:function(a){this._popupBehavior&&this._popupBehavior.raiseShowing(a)},add_shown:function(a){this._popupBehavior&&this._popupBehavior.add_shown(a)},remove_shown:function(a){this._popupBehavior&&this._popupBehavior.remove_shown(a)},raiseShown:function(a){this._popupBehavior&&this._popupBehavior.raiseShown(a)},add_hiding:function(a){this._popupBehavior&&this._popupBehavior.add_hiding(a)},remove_hiding:function(a){this._popupBehavior&&this._popupBehavior.remove_hiding(a)},raiseHiding:function(a){this._popupBehavior&&this._popupBehavior.raiseHiding(a)},add_hidden:function(a){this._popupBehavior&&this._popupBehavior.add_hidden(a)},remove_hidden:function(a){this._popupBehavior&&this._popupBehavior.remove_hidden(a)},raiseHidden:function(a){this._popupBehavior&&this._popupBehavior.raiseHidden(a)},posTop:function(){var a=0;if(typeof window.pageYOffset!=e)a=window.pageYOffset;else if(document.documentElement&&document.documentElement.scrollTop)a=document.documentElement.scrollTop;else if(document.body.scrollTop)a=document.body.scrollTop;return a},posLeft:function(){var a=0;if(typeof window.pageXOffset!=e)a=window.pageXOffset;else if(document.documentElement&&document.documentElement.scrollLeft)a=document.documentElement.scrollLeft;else if(document.body.scrollLeft)a=document.body.scrollLeft;return a},pageHeight:function(){var b=a;if(window.innerHeight!=a)b=window.innerHeight;else if(document.documentElement&&document.documentElement.clientHeight)b=document.documentElement.clientHeight;else if(document.body!=a)b=document.body.clientHeight;return b},pageWidth:function(){var b=a;if(window.innerWidth!=a)b=window.innerWidth;else if(document.documentElement&&document.documentElement.clientWidth)b=document.documentElement.clientWidth;else if(document.body!=a)b=document.body.clientWidth;return b}};Sys.Extended.UI.BalloonPopupControlBehavior.registerClass("Sys.Extended.UI.BalloonPopupControlBehavior",Sys.Extended.UI.DynamicPopulateBehaviorBase);Sys.registerComponent(Sys.Extended.UI.BalloonPopupControlBehavior,{name:"balloonPopupBehavior"});Sys.Extended.UI.BalloonPopupControlBehavior.__VisiblePopup=a;Sys.Extended.UI.BalloonPopupPosition=function(){throw Error.invalidOperation();};Sys.Extended.UI.BalloonPopupPosition.prototype={Auto:0,TopRight:1,TopLeft:2,BottomRight:3,BottomLeft:4};Sys.Extended.UI.BalloonPopupPosition.registerEnum("Sys.Extended.UI.BalloonPopupPosition",b);Sys.Extended.UI.BalloonPopupStyle=function(){throw Error.invalidOperation();};Sys.Extended.UI.BalloonPopupStyle.prototype={Rectangle:0,Cloud:1,Custom:2};Sys.Extended.UI.BalloonPopupStyle.registerEnum("Sys.Extended.UI.BalloonPopupStyle",b);Sys.Extended.UI.BalloonPopupSize=function(){throw Error.invalidOperation();};Sys.Extended.UI.BalloonPopupSize.prototype={Small:0,Medium:1,Large:2};Sys.Extended.UI.BalloonPopupSize.registerEnum("Sys.Extended.UI.BalloonPopupSize",b)}if(window.Sys&&Sys.loader)Sys.loader.registerScript(b,["ExtendedDynamicPopulate","ExtendedPopup","ExtendedAnimationBehavior"],a);else a()})();