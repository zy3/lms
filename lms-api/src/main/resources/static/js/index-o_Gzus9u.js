var I=(N,b,y)=>new Promise((m,w)=>{var v=s=>{try{p(y.next(s))}catch(a){w(a)}},c=s=>{try{p(y.throw(s))}catch(a){w(a)}},p=s=>s.done?m(s.value):Promise.resolve(s.value).then(v,c);p((y=y.apply(N,b)).next())});import{r as h,v as Q,f as V,o as W,q as j,H as r,R as t,D as _,p as g,j as e,U as x,aF as J,S as n,Q as C,P as k,aG as X,G as E,az as S,aB as Y,V as Z}from"./index-B4LUeTLg.js";import{t as q,a as U}from"./common-CBPeEccM.js";import{q as ee,g as te,r as oe,l as ne}from"./book-D9SslOf9.js";function ae(){const N=h([]);return{loading:h(!1),columns:[{label:"书号",prop:"isbn"},{label:"书名",prop:"bookName"},{label:"借出时间",prop:"borrowTime",width:160},{label:"截止时间",prop:"endTime",width:160},{label:"是否逾期",prop:"overdueInd",slot:"overdueIndSlot",width:100},{label:"读者姓名",prop:"readerName"},{label:"读者类型",prop:"readerType",slot:"readerTypeSlot"},{label:"操作",width:"120",fixed:"right",slot:"operation"}],dataList:N}}const le=E("h5",null,"借阅人信息",-1),re={style:{display:"flex","justify-content":"space-between","align-items":"center","margin-bottom":"16px"}},se={style:{"font-size":"12px",color:"#666"}},ue={key:0,style:{"margin-top":"40px",display:"flex","justify-content":"center",width:"100%"}},fe=Q({name:"ReturnManagement",__name:"index",setup(N){const{loading:b,columns:y,dataList:m}=ae();let w=h(0),v=h(0),c=V({bookPositionEnums:[],readerTypeEnums:[],bookOverdueEnums:[]});const p=h();let s=h(!0),a=V({studentNum:"",name:"",maxBorrowCount:0,borrowCount:0,isbn:"",readerUniqueId:""});const D=V({studentNum:[{required:!0,message:"请输入学（工）号",trigger:"blur"}]}),T=l=>{l&&(l.resetFields(),m.value=[],w.value=0,v.value=0,s.value=!0)},L=l=>I(this,null,function*(){l&&(yield l.validate((o,f)=>{o&&te({studentNum:a.studentNum}).then(u=>{u.code===200?(Object.assign(a,u.data),w.value=a.maxBorrowCount||0,v.value=a.borrowCount||0,s.value=!1,R()):S(u.message,{type:"error"})})}))}),F=()=>I(this,null,function*(){b.value=!0,oe(a).then(l=>{if(l.code===200){b.value=!1;let o=l.data;m.value.push(o),a.isbn=""}else b.value=!1,S(l.message,{type:"error"})})});function $(l,o){m.value.splice(o,1)}function M(){const l=m.value.map(({bookUniqueId:u})=>u),o=Array.from(new Set(l));let f="确定借阅";o.length!==l.length&&(f="存在多本相同的书籍，是否提交"),Y.confirm(f,"信息",{type:"warning"}).then(()=>{const u={bookUniqueIdList:l,readerUniqueId:a.readerUniqueId};ne(u).then(d=>{d.code===200?(T(p.value),S("操作成功",{type:"success"})):S(d.message,{type:"error"})})}).catch(()=>{})}function z(){ee().then(l=>{if(l.code===200){let o=l.data,f=q(o.bookPositionEnums);c.bookPositionEnums.push(...f);let u=q(o.readerTypeEnums);c.readerTypeEnums.push(...u);let d=q(o.bookOverdueEnums);c.bookOverdueEnums.push(...d)}}).catch(()=>{})}let O=h(null);function R(){Z(()=>{a.isbn="",O.value.focus()})}return W(()=>{z()}),(l,o)=>{const f=_("el-input"),u=_("el-form-item"),d=_("el-button"),A=_("el-form"),P=_("el-card"),B=_("el-text"),G=_("el-popconfirm"),K=_("pure-table");return g(),j("div",null,[r(P,null,{header:t(()=>[le]),default:t(()=>[r(A,{ref_key:"orderFormRef",ref:p,"label-width":"100px",inline:!0,"hide-required-asterisk":!0,model:e(a),rules:D},{default:t(()=>[r(u,{label:"学（工）号：",prop:"studentNum"},{default:t(()=>[r(f,{modelValue:e(a).studentNum,"onUpdate:modelValue":o[0]||(o[0]=i=>e(a).studentNum=i),disabled:!e(s),placeholder:"请输入",clearable:""},null,8,["modelValue","disabled"])]),_:1}),e(s)?(g(),x(u,{key:0},{default:t(()=>[r(d,{type:"primary",icon:e(J),onClick:o[1]||(o[1]=i=>L(p.value))},{default:t(()=>[n(" 查询 ")]),_:1},8,["icon"]),r(d,{onClick:o[2]||(o[2]=i=>T(p.value))},{default:t(()=>[n("重置")]),_:1})]),_:1})):e(s)?C("",!0):(g(),x(u,{key:1},{default:t(()=>[r(d,{type:"primary",onClick:o[3]||(o[3]=i=>T(p.value))},{default:t(()=>[n("重选")]),_:1})]),_:1})),e(s)?C("",!0):(g(),x(u,{key:2,label:"姓名:"},{default:t(()=>[n(k(e(a).name),1)]),_:1})),e(s)?C("",!0):(g(),x(u,{key:3,label:"扫描:",prop:"isbn"},{default:t(()=>[r(f,{ref_key:"scanInput",ref:O,modelValue:e(a).isbn,"onUpdate:modelValue":o[4]||(o[4]=i=>e(a).isbn=i),clearable:"",onKeyup:X(F,["enter"])},null,8,["modelValue"])]),_:1})),e(s)?C("",!0):(g(),x(u,{key:4},{default:t(()=>[r(d,{type:"primary",onClick:F},{default:t(()=>[n("手工录入")]),_:1})]),_:1}))]),_:1},8,["model","rules"])]),_:1}),r(P,{style:{"margin-top":"16px"}},{default:t(()=>[E("div",re,[E("h5",null,[n(" 借阅列表（请使用扫码枪"),r(d,{style:{"vertical-align":"baseline"},type:"text",onClick:R},{default:t(()=>[n("扫描")]),_:1}),n("） ")]),E("span",se,[n(" 当前已选"),r(B,{tag:"b",type:"success"},{default:t(()=>[n(k(e(m).length),1)]),_:1}),n("本 累计已借"),r(B,{tag:"b",type:"success"},{default:t(()=>[n(k(e(v)),1)]),_:1}),n("本 剩余应还"),r(B,{tag:"b",type:"success"},{default:t(()=>[n(k(e(v)-e(m).length>0?e(v)-e(m).length:0),1)]),_:1}),n("本 ")])]),E("div",null,[r(K,{border:"",stripe:"","row-key":"bookUniqueId",alignWhole:"center",showOverflowTooltip:"",loading:e(b),data:e(m),columns:e(y)},{positionSlot:t(({row:i})=>[n(k(e(U)(i.position,e(c).bookPositionEnums)),1)]),readerTypeSlot:t(({row:i})=>[n(k(e(U)(i.readerType,e(c).readerTypeEnums)),1)]),operation:t(({row:i,index:H})=>[r(G,{title:"确定移除?",onConfirm:ie=>$(i,H)},{reference:t(()=>[r(d,{link:"",type:"primary"},{default:t(()=>[n(" 移除 ")]),_:1})]),_:2},1032,["onConfirm"])]),overdueIndSlot:t(({row:i})=>[n(k(e(U)(i.overdueInd,e(c).bookOverdueEnums)),1)]),_:1},8,["loading","data","columns"]),e(m).length?(g(),j("div",ue,[r(d,{type:"primary",onClick:M},{default:t(()=>[n("确定归还")]),_:1})])):C("",!0)])]),_:1})])}}});export{fe as default};
