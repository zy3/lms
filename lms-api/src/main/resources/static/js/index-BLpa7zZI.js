var U=(B,b,_)=>new Promise((d,v)=>{var g=s=>{try{m(_.next(s))}catch(a){v(a)}},h=s=>{try{m(_.throw(s))}catch(a){v(a)}},m=s=>s.done?d(s.value):Promise.resolve(s.value).then(g,h);m((_=_.apply(B,b)).next())});import{r as k,v as Q,f as V,o as W,q as P,H as l,R as t,D as f,p as y,j as e,U as C,aF as J,S as n,Q as q,P as x,aG as X,G as N,az as S,aB as Y,V as Z}from"./index-B4LUeTLg.js";import{t as R,a as D}from"./common-CBPeEccM.js";import{q as ee,g as te,j as oe,k as ne}from"./book-D9SslOf9.js";function ae(){const B=k([]);return{loading:k(!1),columns:[{label:"书号",prop:"isbn"},{label:"书名",prop:"bookName"},{label:"作者",prop:"author"},{label:"出版单位",prop:"publisher"},{label:"书籍位置",prop:"position",slot:"positionSlot"},{label:"读者姓名",prop:"readerName"},{label:"读者类型",prop:"readerType",slot:"readerTypeSlot"},{label:"操作",width:"120",fixed:"right",slot:"operation"}],dataList:B}}const le=N("h5",null,"借阅人信息",-1),se={style:{display:"flex","justify-content":"space-between","align-items":"center","margin-bottom":"16px"}},re={style:{"font-size":"12px",color:"#666"}},ue={key:0,style:{"margin-top":"40px",display:"flex","justify-content":"center",width:"100%"}},fe=Q({name:"BorrowManagement",__name:"index",setup(B){const{loading:b,columns:_,dataList:d}=ae();let v=k(0),g=k(0),h=V({bookPositionEnums:[],readerTypeEnums:[]});const m=k();let s=k(!0),a=V({studentNum:"",name:"",maxBorrowCount:0,borrowCount:0,isbn:"",readerUniqueId:""});const $=V({studentNum:[{required:!0,message:"请输入学（工）号",trigger:"blur"}]}),E=u=>{u&&(u.resetFields(),d.value=[],v.value=0,g.value=0,s.value=!0)},M=u=>U(this,null,function*(){u&&(yield u.validate((o,p)=>{o&&te({studentNum:a.studentNum}).then(r=>{r.code===200?(Object.assign(a,r.data),v.value=a.maxBorrowCount||0,g.value=a.borrowCount||0,s.value=!1,L()):S(r.message,{type:"error"})})}))}),I=u=>U(this,null,function*(){b.value=!0,oe(a).then(o=>{if(o.code===200){b.value=!1;let p=o.data;d.value.push(p),a.isbn=""}else b.value=!1,S(o.message,{type:"error"})})});function z(u,o){d.value.splice(o,1)}function A(){const u=d.value.map(({bookUniqueId:r})=>r),o=Array.from(new Set(u));let p="确定借阅";o.length!==u.length&&(p="存在多本相同的书籍，是否提交"),Y.confirm(p,"信息",{type:"warning"}).then(()=>{const c={bookUniqueIdList:d.value.map(({bookUniqueId:w})=>w),readerUniqueId:a.readerUniqueId};ne(c).then(w=>{w.code===200?(E(m.value),S("操作成功",{type:"success"})):S(w.message,{type:"error"})})}).catch(()=>{})}function G(){ee().then(u=>{if(u.code===200){let o=u.data,p=R(o.bookPositionEnums);h.bookPositionEnums.push(...p);let r=R(o.readerTypeEnums);h.readerTypeEnums.push(...r)}}).catch(()=>{})}let F=k(null);function L(){Z(()=>{a.isbn="",F.value.focus()})}return W(()=>{G()}),(u,o)=>{const p=f("el-input"),r=f("el-form-item"),c=f("el-button"),w=f("el-form"),j=f("el-card"),T=f("el-text"),K=f("el-popconfirm"),O=f("pure-table");return y(),P("div",null,[l(j,null,{header:t(()=>[le]),default:t(()=>[l(w,{ref_key:"orderFormRef",ref:m,"label-width":"100px",inline:!0,"hide-required-asterisk":!0,model:e(a),rules:$},{default:t(()=>[l(r,{label:"学（工）号：",prop:"studentNum"},{default:t(()=>[l(p,{modelValue:e(a).studentNum,"onUpdate:modelValue":o[0]||(o[0]=i=>e(a).studentNum=i),disabled:!e(s),placeholder:"请输入",clearable:""},null,8,["modelValue","disabled"])]),_:1}),e(s)?(y(),C(r,{key:0},{default:t(()=>[l(c,{type:"primary",icon:e(J),onClick:o[1]||(o[1]=i=>M(m.value))},{default:t(()=>[n(" 查询 ")]),_:1},8,["icon"]),l(c,{onClick:o[2]||(o[2]=i=>E(m.value))},{default:t(()=>[n("重置")]),_:1})]),_:1})):e(s)?q("",!0):(y(),C(r,{key:1},{default:t(()=>[l(c,{type:"primary",onClick:o[3]||(o[3]=i=>E(m.value))},{default:t(()=>[n("重选")]),_:1})]),_:1})),e(s)?q("",!0):(y(),C(r,{key:2,label:"姓名:"},{default:t(()=>[n(x(e(a).name),1)]),_:1})),e(s)?q("",!0):(y(),C(r,{key:3,label:"扫描:",prop:"isbn"},{default:t(()=>[l(p,{ref_key:"scanInput",ref:F,modelValue:e(a).isbn,"onUpdate:modelValue":o[4]||(o[4]=i=>e(a).isbn=i),clearable:"",onKeyup:X(I,["enter"])},null,8,["modelValue"])]),_:1})),e(s)?q("",!0):(y(),C(r,{key:4},{default:t(()=>[l(c,{type:"primary",onClick:I},{default:t(()=>[n("手工录入")]),_:1})]),_:1}))]),_:1},8,["model","rules"])]),_:1}),l(j,{style:{"margin-top":"16px"}},{default:t(()=>[N("div",se,[N("h5",null,[n(" 借阅列表（请使用扫码枪"),l(c,{style:{"vertical-align":"baseline"},type:"text",onClick:L},{default:t(()=>[n("扫描")]),_:1}),n("） ")]),N("span",re,[n(" 当前已选"),l(T,{tag:"b",type:"success"},{default:t(()=>[n(x(e(d).length),1)]),_:1}),n("本 累计已借"),l(T,{tag:"b",type:"success"},{default:t(()=>[n(x(e(g)),1)]),_:1}),n("本 剩余可借"),l(T,{tag:"b",type:"success"},{default:t(()=>[n(x(e(v)-e(g)-e(d).length),1)]),_:1}),n("本 ")])]),N("div",null,[l(O,{border:"",stripe:"","row-key":"bookUniqueId",alignWhole:"center",showOverflowTooltip:"",loading:e(b),data:e(d),columns:e(_)},{positionSlot:t(({row:i})=>[n(x(e(D)(i.position,e(h).bookPositionEnums)),1)]),readerTypeSlot:t(({row:i})=>[n(x(e(D)(i.readerType,e(h).readerTypeEnums)),1)]),operation:t(({row:i,index:H})=>[l(K,{title:"确定移除?",onConfirm:ie=>z(i,H)},{reference:t(()=>[l(c,{link:"",type:"primary"},{default:t(()=>[n(" 移除 ")]),_:1})]),_:2},1032,["onConfirm"])]),_:1},8,["loading","data","columns"]),e(d).length?(y(),P("div",ue,[l(c,{type:"primary",onClick:A},{default:t(()=>[n("确定借阅")]),_:1})])):q("",!0)])]),_:1})])}}});export{fe as default};
